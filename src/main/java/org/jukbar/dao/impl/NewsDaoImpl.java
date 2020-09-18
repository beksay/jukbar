package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.NewsDao;
import org.jukbar.domain.News;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class NewsDaoImpl extends GenericDaoImpl<News, Integer> implements NewsDao {

	public NewsDaoImpl(EntityManager entityManager, Event<News> eventSource) {
		super(entityManager, eventSource);
	}

}
