package org.jukbar.service;

import java.io.IOException;

import javax.ejb.Local;

import org.jukbar.domain.Attachment;
import org.jukbar.dto.AttachmentBinaryDTO;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface AttachmentService extends GenericService<Attachment, Integer> {
	
	Attachment saveFromDTO(AttachmentBinaryDTO binary) throws IOException;

}
