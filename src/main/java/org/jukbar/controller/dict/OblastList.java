package org.jukbar.controller.dict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jukbar.annotation.Logged;
import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.controller.BaseController;
import org.jukbar.conversations.ConversationCountry;
import org.jukbar.model.OblastModel;
import org.jukbar.service.OblastService;
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
public class OblastList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private OblastService service;
	private OblastModel model;
	@Inject
	private ConversationCountry conversationCountry;
	
	private String searchString;
	private Integer first;
	
	@PostConstruct
	private void init() {
		restoreState();
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		if (conversationCountry!=null) {
			filters.add(new FilterExample("country", conversationCountry.getCountry(), InequalityConstants.EQUAL));
		} else {
			filters.add(new FilterExample("id", InequalityConstants.IS_NULL_SINGLE));
		}

		if (searchString != null && searchString.length()>0) {
			filters.add(new FilterExample(true, "name", '%' + searchString.toLowerCase() + '%', InequalityConstants.LIKE, true));
		}
		model = new OblastModel(filters, service);
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
		model = (OblastModel) session.getAttribute("model");
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

	public OblastModel getModel() {
		return model;
	}
	
	public void setModel(OblastModel model) {
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
