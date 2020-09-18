package org.jukbar.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.TransportTypeDao;
import org.jukbar.dao.impl.TransportTypeDaoImpl;
import org.jukbar.domain.TransportType;
import org.jukbar.service.TransportTypeService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(TransportTypeService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TransportTypeServiceImpl extends GenericServiceImpl<TransportType, Integer, TransportTypeDao> implements TransportTypeService {

	@Override
	protected TransportTypeDao getDao() {
		return new TransportTypeDaoImpl(em, se);
	}

}
