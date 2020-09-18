package org.jukbar.conversion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.FacesConverter;

import org.jukbar.dao.PersonDao;
import org.jukbar.domain.Person;
import org.jukbar.service.PersonService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@RequestScoped
@FacesConverter(value="personConverter")
public class PersonConverter extends EntityConvertor<Person, Integer, PersonDao, PersonService> {

	@EJB
	private PersonService service;
	
	@Override
	protected PersonService getService() {
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
