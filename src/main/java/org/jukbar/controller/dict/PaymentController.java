package org.jukbar.controller.dict;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Payment;
import org.jukbar.service.PaymentService;

@Named
@ConversationScoped
public class PaymentController extends Conversational {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PaymentService service;
	
	private Payment payment;

	@PostConstruct
	public void init() {
		if (payment==null) payment= new Payment();
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}


}
