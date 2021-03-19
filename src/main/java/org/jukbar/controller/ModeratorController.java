package org.jukbar.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.conversations.ConversationModerator;
import org.jukbar.domain.Orders;
import org.jukbar.enums.OrderStatus;
import org.jukbar.enums.TransportStatus;
import org.jukbar.service.OrdersService;
import org.jukbar.service.TransportService;

@ManagedBean
@ViewScoped
public class ModeratorController{

	@EJB
	private OrdersService orderService;
	@EJB
	private TransportService transportService;
	
	private Orders orders;

	@Inject
	private ConversationModerator conversation;	

	@PostConstruct
	public void init() { 
		orders = conversation.getOrders();
		if (orders==null) orders= new Orders();
	}
	
	public void send(Orders orders) {
		orders.setStatus(OrderStatus.IN_PROGRESS);
		orders.setDateProgress(new Date());
		orderService.merge(orders);
	}
	
	public void delete(Orders orders) {
		orderService.remove(orders);
	}
	
	public String list(){
		return "/view/moderator/order_list.xhtml?faces-redirect=true";
	}
	
	public Long getOrderAmount() {
        List<FilterExample> examples=new ArrayList<>();
        examples.add(new FilterExample("status", OrderStatus.NEW, InequalityConstants.EQUAL));
        Long c = orderService.countByExample(examples);
        return c;
	}
	
	public Long getTransportAmount() {
        List<FilterExample> examples=new ArrayList<>();
        examples.add(new FilterExample("status", TransportStatus.IN_PROGRESS, InequalityConstants.EQUAL));
        Long c = transportService.countByExample(examples);
        return c;
	}
	
	public ConversationModerator getConversation() {
		return conversation;
	}
	
	public void setConversation(ConversationModerator conversation) {
		this.conversation = conversation;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
}
