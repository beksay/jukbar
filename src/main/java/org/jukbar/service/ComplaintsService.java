package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Complaints;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface ComplaintsService extends GenericService<Complaints, Integer> {

}
