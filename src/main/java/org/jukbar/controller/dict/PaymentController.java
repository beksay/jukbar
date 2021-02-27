package org.jukbar.controller.dict;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Payment;
import org.jukbar.enums.PaymentType;
import org.jukbar.service.PaymentService;
import org.jukbar.util.web.LoginUtil;

@Named
@ConversationScoped
public class PaymentController extends Conversational {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PaymentService service;
	@Inject
	private LoginUtil loginUtil;
	
	private Payment payment;

	@PostConstruct
	public void init() {
		if (payment==null) payment= new Payment();
	}
	
	public String getBoldDocuments(Payment payment) {
        List<FilterExample> examples=new ArrayList<>();
        examples.add(new FilterExample("type", PaymentType.INCOME, InequalityConstants.EQUAL)); 
        Long c = service.countByExample(examples);
        examples=new ArrayList<>();
        examples.add(new FilterExample("type", PaymentType.OUTCOME, InequalityConstants.EQUAL)); 
        Long c2 = service.countByExample(examples);
        if (c < 0) {
        	return "GREENDOC";
		}else if(c2 < 0){
			return "REDDOC";
		}else {
			return "";
		}
	}
	
	public BigDecimal allAmount() {
		List<FilterExample> examples=new ArrayList<>();
        examples.add(new FilterExample("type", PaymentType.INCOME, InequalityConstants.EQUAL)); 
        examples.add(new FilterExample("person", loginUtil.getCurrentUser().getPerson(), InequalityConstants.EQUAL)); 
		BigDecimal incomeAmount = service.sumByExample("amount", examples);
        examples=new ArrayList<>();
        examples.add(new FilterExample("type", PaymentType.OUTCOME, InequalityConstants.EQUAL)); 
        examples.add(new FilterExample("person", loginUtil.getCurrentUser().getPerson(), InequalityConstants.EQUAL));
        BigDecimal outcomeAmount = service.sumByExample("amount", examples);
        if(incomeAmount==null) incomeAmount = BigDecimal.ZERO;
        if(outcomeAmount==null) outcomeAmount = BigDecimal.ZERO;
        return incomeAmount.subtract(outcomeAmount);
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}


}
