package org.jukbar.controller.publics;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Transport;


@Named
@ConversationScoped
public class TransportAction extends Conversational{

	private static final long serialVersionUID = 1L;
	
	private Transport transport;
	
	@PostConstruct
	public void initialize() {
		transport = new Transport();
	}
	
	public String preview(Transport transport) {
		this.transport = transport;
		return "/view/public/transport_preview.xhtml";
    }

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}


}
