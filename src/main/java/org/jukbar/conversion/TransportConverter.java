package org.jukbar.conversion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.FacesConverter;

import org.jukbar.dao.TransportTypeDao;
import org.jukbar.domain.TransportType;
import org.jukbar.service.TransportTypeService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@RequestScoped
@FacesConverter(value="transportConverter")
public class TransportConverter extends EntityConvertor<TransportType, Integer, TransportTypeDao, TransportTypeService> {

	@EJB
	private TransportTypeService service;
	
	@Override
	protected TransportTypeService getService() {
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
