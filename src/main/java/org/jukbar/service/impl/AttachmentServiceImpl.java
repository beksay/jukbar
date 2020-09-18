package org.jukbar.service.impl;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.AttachmentDao;
import org.jukbar.dao.impl.AttachmentDaoImpl;
import org.jukbar.domain.Attachment;
import org.jukbar.dto.AttachmentBinaryDTO;
import org.jukbar.dto.AttachmentDataSource;
import org.jukbar.dto.IdentifyResponse;
import org.jukbar.service.AttachmentService;
import org.jukbar.soa.RepositoryService;
import org.jukbar.soa.RepositoryServiceFactory;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(AttachmentService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AttachmentServiceImpl extends GenericServiceImpl<Attachment, Integer, AttachmentDao> implements AttachmentService {

	private RepositoryService service;
	
	@PostConstruct
	private void init(){
		try {
			service = RepositoryServiceFactory.getInstance().getService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected AttachmentDao getDao() {
		return new AttachmentDaoImpl(em,se);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Attachment saveFromDTO(AttachmentBinaryDTO binary) throws IOException {
		binary.setName(binary.getAttachment().getRepositoryName());
		DataHandler handler = new DataHandler(new AttachmentDataSource(binary));
		IdentifyResponse response = service.save(binary.getAttachment().getFileName(), handler);
		binary.getAttachment().setRepositoryName(response.getChecksum());
		
		return persist(binary.getAttachment());
	}

}
