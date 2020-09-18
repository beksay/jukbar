package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.TransportTypeDao;
import org.jukbar.domain.TransportType;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class TransportTypeDaoImpl extends GenericDaoImpl<TransportType, Integer> implements TransportTypeDao {

	public TransportTypeDaoImpl(EntityManager entityManager, Event<TransportType> eventSource) {
		super(entityManager, eventSource);
	}

}
