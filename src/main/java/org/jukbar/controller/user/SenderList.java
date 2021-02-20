package org.jukbar.controller.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jukbar.annotation.Logged;
import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.controller.BaseController;
import org.jukbar.domain.Role;
import org.jukbar.model.UserModel;
import org.jukbar.service.RoleService;
import org.jukbar.service.UserService;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.data.PageEvent;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@ManagedBean
@ViewScoped
public class SenderList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private UserService service;
	@EJB
	private RoleService roleService;
	
	private UserModel model;
	
	private String searchString;
	private Integer first;
	
	@PostConstruct
	private void init() {
		restoreState();
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("role.id", 4, InequalityConstants.EQUAL));
		if (searchString != null && searchString.length()>0) {
			filters.add(new FilterExample(true, "person.firstName", '%' + searchString.toLowerCase() + '%', InequalityConstants.LIKE, true));
			filters.add(new FilterExample(true, "person.lastName", '%' + searchString.toLowerCase() + '%', InequalityConstants.LIKE, true));
		}
		model = new UserModel(filters, service);
	}
	
	public String clearData() {
		searchString = null;
		init();
		return null;
	}
	
	public void saveState() {
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("model", model);
		session.setAttribute("first", first);
	}
	
	public void restoreState() {
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		model = (UserModel) session.getAttribute("model");
		first = (Integer) session.getAttribute("first");
	}
	
	public void removeState() {
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("model", null);
		session.setAttribute("first", null);
		
		model = null;
		first = null;
	}
	
	public void onPageChange(PageEvent event) {  
    	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpSession session = request.getSession();
		setFirst(((DataTable) event.getSource()).getRows() * event.getPage());
		session.setAttribute("first", first);
	}
	
	public List<Role> getAvailableRolesEmployee() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("name", "manager", InequalityConstants.EQUAL));
		examples.add(new FilterExample("name", "admin", InequalityConstants.EQUAL));
		return roleService.findByExample(0, 10, examples);
	}

	public UserModel getModel() {
		return model;
	}
	
	public void setModel(UserModel model) {
		this.model = model;
	}
	
	public Integer getFirst() {
		return first;
	}
	
	public void setFirst(Integer first) {
		this.first = first;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

    
}
