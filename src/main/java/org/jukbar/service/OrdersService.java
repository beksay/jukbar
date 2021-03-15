package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Orders;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface OrdersService extends GenericService<Orders, Integer> {

}
