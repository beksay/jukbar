package org.jukbar.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.TransportDao;
import org.jukbar.dao.impl.TransportDaoImpl;
import org.jukbar.domain.Transport;
import org.jukbar.service.TransportService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(TransportService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TransportServiceImpl extends GenericServiceImpl<Transport, Integer, TransportDao> implements TransportService {

	@Override
	protected TransportDao getDao() {
		return new TransportDaoImpl(em, se);
	}

}
