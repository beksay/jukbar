package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Documents;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface DocumentsService extends GenericService<Documents, Integer> {

}
