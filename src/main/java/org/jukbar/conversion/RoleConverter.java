package org.jukbar.conversion;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.convert.FacesConverter;

import org.jukbar.dao.RoleDao;
import org.jukbar.domain.Role;
import org.jukbar.service.RoleService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@RequestScoped
@FacesConverter(value="roleConverter")
public class RoleConverter extends EntityConvertor<Role, Integer, RoleDao, RoleService> {

	@EJB
	private RoleService service;
	
	@Override
	protected RoleService getService() {
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
