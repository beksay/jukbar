package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Operator;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface OperatorService extends GenericService<Operator, Integer> {

}
