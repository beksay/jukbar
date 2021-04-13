package org.jukbar.controller.publics;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.jukbar.beans.FilterExample;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Transport;
import org.jukbar.service.TransportService;


@Named
@ConversationScoped
public class TransportAction extends Conversational{

	private static final long serialVersionUID = 1L;
	
	private Transport transport;
	
	@EJB
	private TransportService service;
	
	@PostConstruct
	public void initialize() {
		transport = new Transport();
	}
	
	public String preview(Transport transport) {
		this.transport = transport;
		return "/view/public/transport_preview.xhtml";
    }
	
	public List<Transport> getAllTransports(){
		List<FilterExample> filters = new ArrayList<>();
		//filters.add(new FilterExample("status", TransportStatus.COMPLETED, InequalityConstants.EQUAL));
		return service.findByExample(0, 100, filters);
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}


}
