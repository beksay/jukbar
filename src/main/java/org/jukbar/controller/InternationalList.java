package org.jukbar.controller;

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
import org.jukbar.domain.Country;
import org.jukbar.domain.Oblast;
import org.jukbar.domain.Region;
import org.jukbar.enums.ShipmentStatus;
import org.jukbar.enums.SortEnum;
import org.jukbar.model.ShipmentsModel;
import org.jukbar.service.CountryService;
import org.jukbar.service.OblastService;
import org.jukbar.service.RegionService;
import org.jukbar.service.ShipmentsService;
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
public class InternationalList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private ShipmentsService service;
	@EJB
	private OblastService oblastService;
	@EJB
	private CountryService countryService;
	@EJB
	private RegionService regionService;
	private ShipmentsModel model;
	
	private Country country;
	private Oblast oblast;
	private Region region;
	
	private Country countryTo;
	private Oblast oblastTo;
	private Region regionTo;
	
	private Integer first;
	
	@PostConstruct
	private void init() {
		restoreState();
		filterData();
	}
	
	public void filterData() {
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("status", ShipmentStatus.IN_PROGRESS, InequalityConstants.EQUAL));  
		if(country !=null) filters.add(new FilterExample("countryFrom", country, InequalityConstants.EQUAL)); 
		if(oblast !=null) filters.add(new FilterExample("oblastFrom", oblast, InequalityConstants.EQUAL));  
        if(region !=null) filters.add(new FilterExample("regionFrom", region, InequalityConstants.EQUAL));
        
        if(countryTo !=null) filters.add(new FilterExample("countryTo", countryTo, InequalityConstants.EQUAL)); 
		if(oblastTo !=null) filters.add(new FilterExample("oblastTo", oblastTo, InequalityConstants.EQUAL));  
        if(regionTo !=null) filters.add(new FilterExample("regionTo", regionTo, InequalityConstants.EQUAL));
		model = new ShipmentsModel(filters, service);
	}
	
	public String clearData() {
		country = null;
		oblast=null;
		region=null;
		countryTo=null;
		oblastTo=null;
		regionTo=null;
		init();
		return null;
	}
	
	public List<Country> getCountryList() {
		List<FilterExample> examples = new ArrayList<>();
		return countryService.findByExample(0, 10, SortEnum.ASCENDING, examples, "id");
	}
	
	public List<Oblast> getOblastList() {
		List<FilterExample> examples = new ArrayList<>();
		if(country !=null) {
			examples.add(new FilterExample("country", country, InequalityConstants.EQUAL)); 
		}else {
			examples.add(new FilterExample("id", InequalityConstants.IS_NULL_SINGLE)); 
		}
		return oblastService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
	}
	
	public List<Region> getRegionList() {
		List<FilterExample> examples = new ArrayList<>();
		if(oblast !=null) {
			examples.add(new FilterExample("oblast", oblast, InequalityConstants.EQUAL)); 
		}else {
			examples.add(new FilterExample("id", InequalityConstants.IS_NULL_SINGLE)); 
		}
		return regionService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
	}
	
	public List<Country> getCountryToList() {
		List<FilterExample> examples = new ArrayList<>();
		return countryService.findByExample(0, 10, SortEnum.ASCENDING, examples, "id");
	}
	
	public List<Oblast> getOblastToList() {
		List<FilterExample> examples = new ArrayList<>();
		if(countryTo !=null) {
			examples.add(new FilterExample("country", countryTo, InequalityConstants.EQUAL)); 
		}else {
			examples.add(new FilterExample("id", InequalityConstants.IS_NULL_SINGLE)); 
		}
		return oblastService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
	}
	
	public List<Region> getRegionToList() {
		List<FilterExample> examples = new ArrayList<>();
		if(oblastTo !=null) {
			examples.add(new FilterExample("oblast", oblastTo, InequalityConstants.EQUAL)); 
		}else {
			examples.add(new FilterExample("id", InequalityConstants.IS_NULL_SINGLE)); 
		}
		return regionService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
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
		model = (ShipmentsModel) session.getAttribute("model");
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

	public ShipmentsService getService() {
		return service;
	}
	
	public void setService(ShipmentsService service) {
		this.service = service;
	}
	
	public ShipmentsModel getModel() {
		return model;
	}
	
	public void setModel(ShipmentsModel model) {
		this.model = model;
	}

	public Oblast getOblast() {
		return oblast;
	}
	
	public void setOblast(Oblast oblast) {
		this.oblast = oblast;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Integer getFirst() {
		return first;
	}
	
	public void setFirst(Integer first) {
		this.first = first;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Country getCountryTo() {
		return countryTo;
	}

	public void setCountryTo(Country countryTo) {
		this.countryTo = countryTo;
	}

	public Oblast getOblastTo() {
		return oblastTo;
	}

	public void setOblastTo(Oblast oblastTo) {
		this.oblastTo = oblastTo;
	}

	public Region getRegionTo() {
		return regionTo;
	}

	public void setRegionTo(Region regionTo) {
		this.regionTo = regionTo;
	}

    
}
