package org.jukbar.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.jukbar.domain.Transport;
import org.jukbar.enums.OrderStatus;
import org.jukbar.enums.TransportStatus;
import org.jukbar.service.OrdersService;
import org.jukbar.service.TransportService;
import org.jukbar.util.web.FacesMessages;
import org.jukbar.util.web.Messages;

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
		FacesMessages.addMessage(Messages.getMessage("sendOrder"), Messages.getMessage("sendOrder"), null);
	}
	
	public void delete(Orders orders) {
		orderService.remove(orders);
		FacesMessages.addMessage(Messages.getMessage("deleteOrder"), Messages.getMessage("deleteOrder"), null);
	}
	
	public void confirmTransport(Transport transport) {
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.MONTH, 1);
		transport.setDate(calendar.getTime());
		transport.setStatus(TransportStatus.COMPLETED);
		transportService.merge(transport);
		FacesMessages.addMessage(Messages.getMessage("transportConfirmed"), Messages.getMessage("transportConfirmed"), null);
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
