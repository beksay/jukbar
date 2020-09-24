package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.PaymentDao;
import org.jukbar.domain.Payment;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class PaymentDaoImpl extends GenericDaoImpl<Payment, Integer> implements PaymentDao {

	public PaymentDaoImpl(EntityManager entityManager, Event<Payment> eventSource) {
		super(entityManager, eventSource);
	}

}
