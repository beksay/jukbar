package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.TransportDao;
import org.jukbar.domain.Transport;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class TransportDaoImpl extends GenericDaoImpl<Transport, Integer> implements TransportDao {

	public TransportDaoImpl(EntityManager entityManager, Event<Transport> eventSource) {
		super(entityManager, eventSource);
	}

}
