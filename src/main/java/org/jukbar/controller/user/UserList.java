package org.jukbar.controller.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jukbar.annotation.Logged;
import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.controller.BaseController;
import org.jukbar.domain.Role;
import org.jukbar.model.UserModel;
import org.jukbar.service.RoleService;
import org.jukbar.service.UserService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class UserList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private UserService service;
	@EJB
	private RoleService roleService;
	private UserModel model;
	
	private String name;
	private Role role;
	
	@PostConstruct
	private void init() {
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("role.id", Arrays.asList(3,4), InequalityConstants.IN));	
		if(name != null) filters.add(new FilterExample("person.firstName", name + "%", InequalityConstants.LIKE));
		if(role != null) filters.add(new FilterExample("role", role, InequalityConstants.EQUAL));	
		model = new UserModel(filters, service);
	}
	
	public String clearData() {
		name = null;
		role=null;
		init();
		
		return null;
	}
	
	public List<Role> getAvailableRolesEmployee() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("name", "manager", InequalityConstants.EQUAL));
		examples.add(new FilterExample("name", "admin", InequalityConstants.EQUAL));
		return roleService.findByExample(0, 10, examples);
	}

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	public UserModel getModel() {
		return model;
	}

	public void setModel(UserModel model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
    
}
