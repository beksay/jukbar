package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.ComplaintsDao;
import org.jukbar.domain.Complaints;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class ComplaintsDaoImpl extends GenericDaoImpl<Complaints, Integer> implements ComplaintsDao {

	public ComplaintsDaoImpl(EntityManager entityManager, Event<Complaints> eventSource) {
		super(entityManager, eventSource);
	}

}
