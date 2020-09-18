package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Shipments;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface ShipmentsService extends GenericService<Shipments, Integer> {

}
