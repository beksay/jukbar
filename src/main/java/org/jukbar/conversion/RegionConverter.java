package org.jukbar.conversion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.FacesConverter;

import org.jukbar.dao.RegionDao;
import org.jukbar.domain.Region;
import org.jukbar.service.RegionService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@RequestScoped
@FacesConverter(value="regionConverter")
public class RegionConverter extends EntityConvertor<Region, Integer, RegionDao, RegionService> {

	@EJB
	private RegionService service;
	
	@Override
	protected RegionService getService() {
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
