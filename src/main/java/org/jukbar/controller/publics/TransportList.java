package org.jukbar.controller.publics;

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

import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.controller.BaseController;
import org.jukbar.domain.Transport;
import org.jukbar.enums.TransportStatus;
import org.jukbar.model.TransportModel;
import org.jukbar.service.TransportService;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.data.PageEvent;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@ViewScoped
public class TransportList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private TransportService service;
	
	private TransportModel model;
	private Transport transport;
	
	private String searchString;
	private Integer first;
	
	@PostConstruct
	private void init() {
		restoreState();
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("status", TransportStatus.COMPLETED, InequalityConstants.EQUAL));
		if (searchString != null && searchString.length()>0) {
			filters.add(new FilterExample("marka", '%' + searchString.toLowerCase() + '%', InequalityConstants.LIKE));
		}
		model = new TransportModel(filters, service);
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
		model = (TransportModel) session.getAttribute("model");
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

	public TransportModel getModel() {
		return model;
	}
	
	public void setModel(TransportModel model) {
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

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	} 
}
