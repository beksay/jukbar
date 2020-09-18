package org.jukbar.conversion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.FacesConverter;

import org.jukbar.dao.OblastDao;
import org.jukbar.domain.Oblast;
import org.jukbar.service.OblastService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@RequestScoped
@FacesConverter(value="oblastConverter")
public class OblastConverter extends EntityConvertor<Oblast, Integer, OblastDao, OblastService> {

	@EJB
	private OblastService service;
	
	@Override
	protected OblastService getService() {
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
