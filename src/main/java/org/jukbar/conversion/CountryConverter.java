package org.jukbar.conversion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.FacesConverter;

import org.jukbar.dao.CountryDao;
import org.jukbar.domain.Country;
import org.jukbar.service.CountryService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@RequestScoped
@FacesConverter(value="countryConverter")
public class CountryConverter extends EntityConvertor<Country, Integer, CountryDao, CountryService> {

	@EJB
	private CountryService service;
	
	@Override
	protected CountryService getService() {
		return service;
	}

	@Override
	protected Integer getID(String key) {
		try {
			return Integer.parseInt(key);
		} catch(Exception e) {
			return null;
		}
	}

}
