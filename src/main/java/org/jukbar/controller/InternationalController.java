package org.jukbar.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jukbar.beans.FilterExample;
import org.jukbar.conversations.ConversationManager;
import org.jukbar.domain.Country;
import org.jukbar.domain.Oblast;
import org.jukbar.domain.Region;
import org.jukbar.domain.Shipments;
import org.jukbar.domain.TransportType;
import org.jukbar.enums.ShipmentStatus;
import org.jukbar.enums.ShipmentType;
import org.jukbar.service.ShipmentsService;
import org.jukbar.service.TransportTypeService;
import org.jukbar.util.web.FacesMessages;
import org.jukbar.util.web.LoginUtil;
import org.jukbar.util.web.Messages;
import org.jukbar.validator.EntityValidator;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class InternationalController{

	@EJB
	private ShipmentsService service;
	@EJB
	private TransportTypeService transportService;
	
	@Inject
	private EntityValidator validator;
	@Inject
	private CountrySelector selector;
	@Inject
	private CountrySelector2 selector2;
	@Inject
	private LoginUtil loginUtil;
	
	private Shipments shipments;
	
	@Inject
	private ConversationManager conversation;	

	@PostConstruct
	public void init() {
		shipments = conversation.getShipments();
		if (shipments==null) shipments= new Shipments();
	}
	
	public String add(){
		shipments = new Shipments();
		selector.setCountry(new Country());
		selector.setOblast(new Oblast());
		selector.setRegion(new Region());
		selector2.setCountry(new Country());
		selector2.setOblast(new Oblast());
		selector2.setRegion(new Region());
		return form();
	}
	
	public String sendProgress(Shipments shipments) {
		shipments.setStatus(ShipmentStatus.IN_PROGRESS);
		shipments.setDateProgress(new Date());
		service.merge(shipments);
		return list();
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		shipments=(Shipments) event.getObject();
		conversation.setShipments(shipments);
		
		if(shipments.getOblastFrom().getCity()==true) {
		   conversation.setStartLocation(shipments.getOblastFrom().getLocation());
		}else {
			 conversation.setStartLocation(shipments.getRegionFrom().getLocation());		   
		}
		
		if(shipments.getOblastTo().getCity()==true) {
			conversation.setEndLocation(shipments.getOblastTo().getLocation());
		}else {
			conversation.setEndLocation(shipments.getRegionTo().getLocation());
		}
		System.out.println("shipments===" +shipments);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/international/international_preview.xhtml?cid="+conversation.getId());
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
		shipments.setStatus(ShipmentStatus.IN_PROGRESS);
		shipments.setOwner(loginUtil.getCurrentUser());
		
		validator.validate(shipments);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		
		if (shipments.getId() == null) {
			service.persist(shipments);
		} else {
			service.merge(shipments);
		}

		shipments = new Shipments();
		selector.setCountry(new Country());
		selector.setOblast(new Oblast());
		selector.setRegion(new Region());
		selector2.setCountry(new Country());
		selector2.setOblast(new Oblast());
		selector2.setRegion(new Region());
		
		return list();
	}
  
	public String cancel() {
		shipments = null;
		selector.setOblast(new Oblast());
		selector.setRegion(new Region());
		selector2.setOblast(new Oblast());
		selector2.setRegion(new Region());
        return list();
    }
	
	private String list(){
		return "/view/international/international_sender_list.xhtml?faces-redirect=true";
	}
	
	public String form(){
		return "/view/international/international_form.xhtml?faces-redirect=true";
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
	
	public ConversationManager getConversation() {
		return conversation;
	}
	
	public void setConversation(ConversationManager conversation) {
		this.conversation = conversation;
	}
}
