package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Payment;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface PaymentService extends GenericService<Payment, Integer> {

}
