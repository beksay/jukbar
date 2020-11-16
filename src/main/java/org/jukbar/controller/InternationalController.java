package org.jukbar.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jukbar.conversations.ConversationManager;
import org.jukbar.domain.Shipments;
import org.jukbar.enums.ShipmentStatus;
import org.jukbar.service.ShipmentsService;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class InternationalController{

	@EJB
	private ShipmentsService service;
	
	private Shipments shipments;
	
	@Inject
	private ConversationManager conversation;	

	@PostConstruct
	public void init() {
		shipments = conversation.getShipments();
		if (shipments==null) shipments= new Shipments();
	}
	
	public String sendProgress(Shipments shipments) {
		shipments.setStatus(ShipmentStatus.IN_PROGRESS);
		shipments.setDateProgress(new Date());
		BigDecimal percentage = new BigDecimal("5");
		BigDecimal price =  shipments.getAmount().multiply(percentage).divide(new BigDecimal(100));
		shipments.setPrice(price);
		service.merge(shipments);
		return listNew();
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
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/shipments/shipments_preview.xhtml?cid="+conversation.getId());
    }
  
	
	private String listNew(){
		return "/view/manager/new_shipments_list.xhtml?faces-redirect=true";
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
