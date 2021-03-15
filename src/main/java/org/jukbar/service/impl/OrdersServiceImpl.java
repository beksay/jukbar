package org.jukbar.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.OrdersDao;
import org.jukbar.dao.impl.OrdersDaoImpl;
import org.jukbar.domain.Orders;
import org.jukbar.service.OrdersService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(OrdersService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class OrdersServiceImpl extends GenericServiceImpl<Orders, Integer, OrdersDao> implements OrdersService {

	@Override
	protected OrdersDao getDao() {
		return new OrdersDaoImpl(em, se);
	}

}
