package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.OrdersDao;
import org.jukbar.domain.Orders;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OrdersDaoImpl extends GenericDaoImpl<Orders, Integer> implements OrdersDao {

	public OrdersDaoImpl(EntityManager entityManager, Event<Orders> eventSource) {
		super(entityManager, eventSource);
	}

}
