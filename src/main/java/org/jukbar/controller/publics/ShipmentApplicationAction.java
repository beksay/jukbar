package org.jukbar.controller.publics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jukbar.beans.FilterExample;
import org.jukbar.controller.CountrySelector;
import org.jukbar.controller.CountrySelector2;
import org.jukbar.conversations.ConversationShipment;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Country;
import org.jukbar.domain.Oblast;
import org.jukbar.domain.Region;
import org.jukbar.domain.Shipments;
import org.jukbar.domain.TransportType;
import org.jukbar.enums.ShipmentStatus;
import org.jukbar.enums.ShipmentType;
import org.jukbar.service.CountryService;
import org.jukbar.service.OblastService;
import org.jukbar.service.RegionService;
import org.jukbar.service.ShipmentsService;
import org.jukbar.service.TransportTypeService;
import org.jukbar.util.web.FacesMessages;
import org.jukbar.util.web.Messages;
import org.jukbar.validator.EntityValidator;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;


@Named
@ConversationScoped
public class ShipmentApplicationAction extends Conversational{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ShipmentsService service;
	@EJB
	private OblastService oblastService;
	@EJB
	private CountryService countryService;
	@EJB
	private RegionService regionService;
	@EJB
	private TransportTypeService transportService;
	
	@Inject
	private EntityValidator validator;
	@Inject
	private CountrySelector selector;
	@Inject
	private CountrySelector2 selector2;
	@Inject
	private ConversationShipment conversation;	
	
	private Shipments shipments;
	private MapModel simpleModel;
	private String startLocation;
	private String endLocation;
	
	@PostConstruct
	public void initialize() {
		shipments = new Shipments();
		
		simpleModel = new DefaultMapModel();
        LatLng coord1 = new LatLng(42.868300, 74.600131);
        simpleModel.addOverlay(new Marker(coord1, "TEST"));
	}
    
	public String saveLocal() {
		if(shipments == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		shipments.setOblastFrom(selector.getOblast());
		if(selector.getOblast()!=null && selector.getOblast().getCity()!=true) {
			shipments.setRegionFrom(selector.getRegion());
		}
		
		shipments.setOblastTo(selector2.getOblast());
		if(selector2.getOblast()!=null && selector2.getOblast().getCity()!=true) {
			shipments.setRegionTo(selector2.getRegion());
		}
		shipments.setType(ShipmentType.LOCAL);
		shipments.setDateCreated(new Date());
		shipments.setStatus(ShipmentStatus.NEW);
		
		validator.validate(shipments);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		
		if (shipments.getId() == null) {
			service.persist(shipments);
		} else {
			service.merge(shipments);
		}

		shipments = new Shipments();
		
		return "/view/public/application/thank_you.xhtml?faces-redirect=true";
	}
	
	public String save() {
		if(shipments == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		shipments.setCountryFrom(selector.getCountry());
		shipments.setOblastFrom(selector.getOblast());
		if(selector.getOblast()!=null && selector.getOblast().getCity()!=true) {
			shipments.setRegionFrom(selector.getRegion());
		}
		
		shipments.setCountryTo(selector2.getCountry());
		shipments.setOblastTo(selector2.getOblast());
		if(selector2.getOblast()!=null && selector2.getOblast().getCity()!=true) {
			shipments.setRegionTo(selector2.getRegion());
		}
		shipments.setType(ShipmentType.INTERNATIONAL);
		shipments.setDateCreated(new Date());
		shipments.setStatus(ShipmentStatus.NEW);
		
		validator.validate(shipments);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		
		if (shipments.getId() == null) {
			service.persist(shipments);
		} else {
			service.merge(shipments);
		}

		shipments = new Shipments();
		
		return "/view/public/application/thank_you.xhtml?faces-redirect=true";
	}
	
	public String returnHome() {
		shipments = null;
		return home();
	}
	
	private String home(){
		return "/home.xhtml";
	}
	
	public String viewLocal(Shipments shipments) {
		this.shipments = shipments;
		
		if(shipments.getOblastFrom().getCity()==true) {
			   setStartLocation(shipments.getOblastFrom().getLocation());
		}else {
		   setStartLocation(shipments.getRegionFrom().getLocation());
		}
		
		if(shipments.getOblastTo().getCity()==true) {
		    setEndLocation(shipments.getOblastTo().getLocation());
		}else {
		   setEndLocation(shipments.getRegionTo().getLocation());
		}
		
		return "/view/public/local/local_preview.xhtml";
		
	}
	
	public String viewInternational(Shipments shipments) {
		this.shipments = shipments;
		
		if(shipments.getOblastFrom().getCity()==true) {
			   setStartLocation(shipments.getOblastFrom().getLocation());
		}else {
		   setStartLocation(shipments.getRegionFrom().getLocation());
		}
		
		if(shipments.getOblastTo().getCity()==true) {
		    setEndLocation(shipments.getOblastTo().getLocation());
		}else {
		   setEndLocation(shipments.getRegionTo().getLocation());
		}
		
		return "/view/public/international/international_preview.xhtml";
		
	}
	
	public String internationalForm(){
		shipments = new Shipments();
		selector.setCountry(new Country());
		selector.setOblast(new Oblast());
		selector.setRegion(new Region());
		selector2.setCountry(new Country());
		selector2.setOblast(new Oblast());
		selector2.setRegion(new Region());
		return "/view/public/application/international_form.xhtml";
	}
	
	public String localForm(){
		shipments = new Shipments();
		selector.setOblast(new Oblast());
		selector.setRegion(new Region());
		selector2.setOblast(new Oblast());
		selector2.setRegion(new Region());
		return "/view/public/application/local_form.xhtml";
	}
	
	public String mainForm(){
		return "/view/public/application/main.xhtml";
	}
	
	public List<TransportType> getTransportTypeList() {
		List<FilterExample> examples = new ArrayList<>();
		return transportService.findByExample(0, 10, examples);
	}

	public Shipments getShipments() {
		return shipments;
	}

	public void setShipments(Shipments shipments) {
		this.shipments = shipments;
	}

	public MapModel getSimpleModel() {
		return simpleModel;
	}

	public void setSimpleModel(MapModel simpleModel) {
		this.simpleModel = simpleModel;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}


}
