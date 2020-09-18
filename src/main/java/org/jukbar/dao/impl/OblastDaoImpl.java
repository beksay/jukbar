package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.OblastDao;
import org.jukbar.domain.Oblast;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OblastDaoImpl extends GenericDaoImpl<Oblast, Integer> implements OblastDao {

	public OblastDaoImpl(EntityManager entityManager, Event<Oblast> eventSource) {
		super(entityManager, eventSource);
	}

}
