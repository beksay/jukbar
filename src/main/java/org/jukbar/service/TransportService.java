package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Transport;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface TransportService extends GenericService<Transport, Integer> {

}
