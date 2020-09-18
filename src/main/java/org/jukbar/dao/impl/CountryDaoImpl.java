package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.CountryDao;
import org.jukbar.domain.Country;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class CountryDaoImpl extends GenericDaoImpl<Country, Integer> implements CountryDao {

	public CountryDaoImpl(EntityManager entityManager, Event<Country> eventSource) {
		super(entityManager, eventSource);
	}

}
