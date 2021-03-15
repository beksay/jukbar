package org.jukbar.controller.publics;

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
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Oblast;
import org.jukbar.domain.Orders;
import org.jukbar.domain.Region;
import org.jukbar.domain.TransportType;
import org.jukbar.enums.OrderStatus;
import org.jukbar.service.OblastService;
import org.jukbar.service.OrdersService;
import org.jukbar.service.RegionService;
import org.jukbar.service.TransportTypeService;
import org.jukbar.util.web.FacesMessages;
import org.jukbar.util.web.Messages;
import org.jukbar.validator.EntityValidator;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;


@Named
@ConversationScoped
public class OrderApplicationAction extends Conversational{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private OrdersService service;
	@EJB
	private OblastService oblastService;
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
	
	private Orders orders;
	
	private MapModel simpleModel;
	private String startLocation;
	private String endLocation;
	
	@PostConstruct
	public void initialize() {
		orders = new Orders();
		
		simpleModel = new DefaultMapModel();
        LatLng coord1 = new LatLng(42.868300, 74.600131);
        simpleModel.addOverlay(new Marker(coord1, "TEST"));
	}
    
	public String save() {
		if(orders == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}
		orders.setOblastFrom(selector.getOblast());
		if(selector.getOblast()!=null && selector.getOblast().getCity()!=true) {
			orders.setRegionFrom(selector.getRegion());
		}
		
		orders.setOblastTo(selector2.getOblast());
		if(selector2.getOblast()!=null && selector2.getOblast().getCity()!=true) {
			orders.setRegionTo(selector2.getRegion());
		}

		orders.setDateCreated(new Date());
		orders.setStatus(OrderStatus.NEW);
		
		validator.validate(orders);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		
		if (orders.getId() == null) {
			service.persist(orders);
		} else {
			service.merge(orders);
		}

		orders = new Orders();
		
		return "/view/public/application/thank_you.xhtml?faces-redirect=true";
	}
	
	public String returnHome() {
		orders = null;
		return home();
	}
	
	private String home(){
		return "/home.xhtml";
	}
	
	public String viewLocal(Orders orders) {
		this.orders = orders;
		
		if(orders.getOblastFrom().getCity()==true) {
			   setStartLocation(orders.getOblastFrom().getLocation());
		}else {
		   setStartLocation(orders.getRegionFrom().getLocation());
		}
		
		if(orders.getOblastTo().getCity()==true) {
		    setEndLocation(orders.getOblastTo().getLocation());
		}else {
		   setEndLocation(orders.getRegionTo().getLocation());
		}
		
		return "/view/public/orders/order_preview.xhtml";
		
	}
	
	public String form(){
		orders = new Orders();
		selector.setOblast(new Oblast());
		selector.setRegion(new Region());
		selector2.setOblast(new Oblast());
		selector2.setRegion(new Region());
		return "/view/public/application/order_form.xhtml";
	}
	
	public String mainForm(){
		return "/view/public/application/main.xhtml";
	}
	
	public List<TransportType> getTransportTypeList() {
		List<FilterExample> examples = new ArrayList<>();
		return transportService.findByExample(0, 15, examples);
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

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}


}
