package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.RegionDao;
import org.jukbar.domain.Region;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class RegionDaoImpl extends GenericDaoImpl<Region, Integer> implements RegionDao {

	public RegionDaoImpl(EntityManager entityManager, Event<Region> eventSource) {
		super(entityManager, eventSource);
	}

}
