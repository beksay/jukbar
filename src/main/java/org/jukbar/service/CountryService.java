package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Country;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface CountryService extends GenericService<Country, Integer> {

}
