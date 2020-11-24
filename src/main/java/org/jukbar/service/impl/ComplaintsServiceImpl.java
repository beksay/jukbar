package org.jukbar.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.ComplaintsDao;
import org.jukbar.dao.impl.ComplaintsDaoImpl;
import org.jukbar.domain.Complaints;
import org.jukbar.service.ComplaintsService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(ComplaintsService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ComplaintsServiceImpl extends GenericServiceImpl<Complaints, Integer, ComplaintsDao> implements ComplaintsService {

	@Override
	protected ComplaintsDao getDao() {
		return new ComplaintsDaoImpl(em, se);
	}

}
