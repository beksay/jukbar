package org.jukbar.controller.user;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jukbar.annotation.Logged;
import org.jukbar.controller.CountrySelector;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Oblast;
import org.jukbar.domain.Region;
import org.jukbar.domain.Transport;
import org.jukbar.enums.TransportStatus;
import org.jukbar.service.TransportService;
import org.jukbar.util.web.LoginUtil;
import org.jukbar.util.web.Messages;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@Named
@ConversationScoped
public class TransportController extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	
	@EJB
	private TransportService transportService;
	@Inject
	private LoginUtil loginUtil;
	@Inject
	private CountrySelector selector;

	private Transport transport;

	@PostConstruct
	public void init() {
		if (transport==null) transport= new Transport();
	}
	
	
	public String cancel() {
		transport = new Transport();
		return list();
	}
	
	public String add() {
		transport = new Transport();
		selector.setOblast(new Oblast());
		selector.setRegion(new Region());
		return form();
	}
	
	public String edit(Transport transport) {
		this.transport = transport;
		selector.setOblast(transport.getOblast());
		if(transport.getOblast().getCity()==false) {
			selector.setRegion(transport.getRegion());
		}
		return form();
	}
	
	public String save() {		
		transport.setStatus(TransportStatus.NEW);
		transport.setUser(loginUtil.getCurrentUser());
		transport.setOblast(selector.getOblast());
		if(selector.getOblast()!=null && selector.getOblast().getCity()!=true) {
			transport.setRegion(selector.getRegion());
		}
		if (transport==null) {
			transport = transportService.persist(transport);
		} else {
			transport = transportService.merge(transport);
		}
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		init();
		return null;
	}
	
	public String sendModerator() {		
		transport.setStatus(TransportStatus.IN_PROGRESS);
        transportService.merge(transport);
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("sendedToModerator"), null) );
		return mainForm();
	}
	
	private String list() {
		return "/view/transport/transport_list.xhtml";
	}
	
	private String form() {
		return "/view/transport/transport_form.xhtml";
	}
	
	public String mainForm() {
		init();
		return "/view/main.xhtml";
	}
	
	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	
}

