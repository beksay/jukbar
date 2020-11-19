package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.FaqDao;
import org.jukbar.domain.Faq;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class FaqDaoImpl extends GenericDaoImpl<Faq, Integer> implements FaqDao {

	public FaqDaoImpl(EntityManager entityManager, Event<Faq> eventSource) {
		super(entityManager, eventSource);
	}

}
