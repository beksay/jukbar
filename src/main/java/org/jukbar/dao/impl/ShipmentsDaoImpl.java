package org.jukbar.dao.impl;

import javax.enterprise.event.Event;
import javax.persistence.EntityManager;

import org.jukbar.dao.ShipmentsDao;
import org.jukbar.domain.Shipments;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class ShipmentsDaoImpl extends GenericDaoImpl<Shipments, Integer> implements ShipmentsDao {

	public ShipmentsDaoImpl(EntityManager entityManager, Event<Shipments> eventSource) {
		super(entityManager, eventSource);
	}

}
