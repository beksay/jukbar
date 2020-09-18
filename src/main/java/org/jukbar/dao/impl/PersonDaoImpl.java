package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.PersonDao;
import org.jukbar.domain.Person;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class PersonDaoImpl extends GenericDaoImpl<Person, Integer> implements PersonDao {

	public PersonDaoImpl(EntityManager entityManager, Event<Person> eventSource) {
		super(entityManager, eventSource);
	}

}
