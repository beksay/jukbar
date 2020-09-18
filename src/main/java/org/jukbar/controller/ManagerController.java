package org.jukbar.controller;

import java.io.IOException;
import java.math.BigDecimal;
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
import org.jukbar.beans.InequalityConstants;
import org.jukbar.conversations.ConversationManager;
import org.jukbar.domain.Shipments;
import org.jukbar.enums.ShipmentStatus;
import org.jukbar.service.ShipmentsService;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ManagerController{

	@EJB
	private ShipmentsService service;
	
	private Shipments shipments;
	private String startLocation;
	private String endLocation;
	
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
		BigDecimal percentage = new BigDecimal("1");
		BigDecimal price =  shipments.getAmount().multiply(percentage).divide(new BigDecimal(100));
		shipments.setPrice(price);
		service.merge(shipments);
		return listNew();
	}
	
	public void onRowSelectNew(SelectEvent event) throws IOException {
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
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/manager/new_preview.xhtml?cid="+conversation.getId());
        
    }
	
	public void onRowSelectInProgress(SelectEvent event) throws IOException {
		shipments=(Shipments) event.getObject();
		conversation.setShipments(shipments);
		if (shipments.getOblastFrom().getCity() == true) {
			conversation.setStartLocation(shipments.getOblastFrom().getLocation());
		} else {
			conversation.setStartLocation(shipments.getRegionFrom().getLocation());
		}

		if (shipments.getOblastTo().getCity() == true) {
			conversation.setEndLocation(shipments.getOblastTo().getLocation());
		} else {
			conversation.setEndLocation(shipments.getRegionTo().getLocation());
		}
		System.out.println("shipments===" +shipments);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/manager/in_progress_preview.xhtml?cid="+conversation.getId());
        
    }
	
	public void onRowSelectTaken(SelectEvent event) throws IOException {
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
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/manager/taken_preview.xhtml?cid="+conversation.getId());
        
    }
	
	private String listNew(){
		return "/view/manager/new_shipments_list.xhtml?faces-redirect=true";
	}
	
	public Long getNewList() {
        List<FilterExample> examples=new ArrayList<>();
        examples.add(new FilterExample("status", ShipmentStatus.NEW, InequalityConstants.EQUAL));
        Long c = service.countByExample(examples);
        return c;
	}

	public Shipments getShipments() {
		return shipments;
	}

	public void setShipments(Shipments shipments) {
		this.shipments = shipments;
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
	
	public ConversationManager getConversation() {
		return conversation;
	}
	
	public void setConversation(ConversationManager conversation) {
		this.conversation = conversation;
	}
}
