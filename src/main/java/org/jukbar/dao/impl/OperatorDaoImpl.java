package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.OperatorDao;
import org.jukbar.domain.Operator;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OperatorDaoImpl extends GenericDaoImpl<Operator, Integer> implements OperatorDao {

	public OperatorDaoImpl(EntityManager entityManager, Event<Operator> eventSource) {
		super(entityManager, eventSource);
	}

}
