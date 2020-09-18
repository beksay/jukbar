package org.jukbar.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.ShipmentsDao;
import org.jukbar.dao.impl.ShipmentsDaoImpl;
import org.jukbar.domain.Shipments;
import org.jukbar.service.ShipmentsService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(ShipmentsService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ShipmentsServiceImpl extends GenericServiceImpl<Shipments, Integer, ShipmentsDao> implements ShipmentsService {

	@Override
	protected ShipmentsDao getDao() {
		return new ShipmentsDaoImpl(em, se);
	}

}
