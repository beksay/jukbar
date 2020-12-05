package org.jukbar.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.DocumentsDao;
import org.jukbar.dao.impl.DocumentsDaoImpl;
import org.jukbar.domain.Documents;
import org.jukbar.service.DocumentsService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(DocumentsService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DocumentsServiceImpl extends GenericServiceImpl<Documents, Integer, DocumentsDao> implements DocumentsService {

	@Override
	protected DocumentsDao getDao() {
		return new DocumentsDaoImpl(em, se);
	}

}
