package org.jukbar.rest.soa.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.core.Response;

import org.jukbar.domain.Operator;
import org.jukbar.domain.Payment;
import org.jukbar.domain.Person;
import org.jukbar.dto.rest.ItemExtra;
import org.jukbar.dto.rest.ItemTo;
import org.jukbar.dto.rest.KicbRequest;
import org.jukbar.dto.rest.KicbResponse;
import org.jukbar.enums.PaymentType;
import org.jukbar.rest.soa.XmlService;
import org.jukbar.service.OperatorService;
import org.jukbar.service.PaymentService;
import org.jukbar.service.PersonService;
import org.jukbar.util.Digest;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class XmlServiceImpl implements XmlService {

	@EJB
	private PersonService personService;
	@EJB
	private PaymentService paymentService;
	@EJB
	private OperatorService operatorService;

	@Override
	public Response kicbRequest(KicbRequest request) throws Exception {

		Operator operator = operatorService.findById(3, false);
		String password = operator.getPassword();

		KicbResponse response = new KicbResponse();
		response.setProtocolVersion("4.00");
		response.setExtracts(new ArrayList<ItemExtra>());
		response.setType(request.getType());
		response.setTerminalId(request.getTerminalId());
		response.setOperatorId(2239);
		response.setTxnId(new Digest("SHA-1").doEncypt(request.getTransactionNumber()));
		response.setTransactionNumber(request.getTransactionNumber());

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");

		ItemExtra extra1 = new ItemExtra();
		extra1.setName("ServerTime");
		extra1.setValue(dateFormat.format(new Date()));
		response.getExtracts().add(extra1);

		if (request.getType() == null) {
			addExtracts(response);

			response.setStatusId(10);
			response.setResultCode(202);
			return Response.ok(response).build();
		}

		String loginRequest = null;
		String passwordRequest = null;

		if (request.getExtracts() != null) {
			for (ItemExtra extra : request.getExtracts()) {
				if ("login".equals(extra.getName()))
					loginRequest = extra.getValue();
				if ("password-md5".equals(extra.getName()))
					passwordRequest = extra.getValue();
			}
		}

		extra1 = new ItemExtra();
		extra1.setName("username");
		extra1.setValue(loginRequest);
		response.getExtracts().add(extra1);

		System.out.println(operator.getUsername() + " = " + loginRequest);
		System.out.println(password + " = " + passwordRequest);

		if (!operator.getUsername().equals(loginRequest) || !password.equals(passwordRequest)) {
			addExtracts(response);

			response.setResultCode(150);
			response.setStatusId(10);
			return Response.ok(response).build();
		}

		try {
			ItemExtra extra = null;

			if (request.getType() != null && request.getType().intValue() == 2) {
				List<Person> persons = personService.findByProperty("account", request.getTo().getAccountNumber());

				if (persons.isEmpty())
					throw new Exception();

				Person person = persons.get(0);

				Payment payment = new Payment();
				payment.setAmount(new BigDecimal(request.getTo().getAmount()));
				payment.setDate(new Date());
				payment.setPerson(person);
				payment.setTransactionId(request.getTransactionNumber());
				payment.setStatus(40);
				payment.setType(PaymentType.INCOME);
				payment.setPurpose("Пополнен счет на");

				try {
					payment = paymentService.persist(payment);
				} catch (Exception e) {
					e.printStackTrace();
					response.setStatusId(10);
					response.setResultCode(155);
				}

				response.setTo(request.getTo());

				response.setStatusId(60);
				response.setResultCode(0);

			} else if (request.getType() != null && request.getType().intValue() == 4) {
				System.out.println("List transaction kicb");
			} else {
				List<Person> persons = personService.findByProperty("account", request.getTo().getAccountNumber());

				if (!persons.isEmpty()) {
					Person person = persons.get(0);

					NumberFormat format = NumberFormat.getNumberInstance();
					format.setMaximumFractionDigits(2);
					format.setGroupingUsed(false);

					response.setStatusId(200);

					extra = new ItemExtra();
					extra.setName("amount");
					extra.setValue(format.format(request.getTo().getAmount()));
					response.getExtracts().add(extra);

					extra = new ItemExtra();
					extra.setName("user_name");
					extra.setValue(person.getFirstName());
					response.getExtracts().add(extra);

					extra = new ItemExtra();
					extra.setName("serviceType");

					String message = "jukbarkg";

					extra.setValue(message);
					response.getExtracts().add(extra);

					extra = new ItemExtra();
					extra.setName("purpose");
					extra.setValue("Оплата");
					response.getExtracts().add(extra);

					response.setResultCode(0);
					response.setStatusId(30);

					ItemTo to = new ItemTo();
					to.setAmount(request.getTo().getAmount());
					to.setServiceId(request.getTo().getServiceId());
					to.setAccountNumber(request.getTo().getAccountNumber());
					response.setTo(to);
				} else {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusId(10);
			response.setResultCode(300);
		}

		addExtracts(response);

		return Response.ok(response).build();
	}

	private void addExtracts(KicbResponse response) {
		ItemExtra extra = new ItemExtra();
		extra.setName("REMOTE_ADDR");
		extra.setValue("127.0.0.1");
		response.getExtracts().add(extra);

		extra = new ItemExtra();
		extra.setName("serial");
		extra.setValue("000000-000000");
		response.getExtracts().add(extra);

		extra = new ItemExtra();
		extra.setName("BALANCE");
		extra.setValue("0");
		response.getExtracts().add(extra);

		extra = new ItemExtra();
		extra.setName("OVERDRAFT");
		extra.setValue("0");
		response.getExtracts().add(extra);
	}
}
