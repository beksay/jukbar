package org.jukbar.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jukbar.annotation.Logged;
import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.controller.CountrySelector;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Oblast;
import org.jukbar.domain.Region;
import org.jukbar.domain.Transport;
import org.jukbar.enums.SortEnum;
import org.jukbar.enums.TransportStatus;
import org.jukbar.service.OblastService;
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
	@EJB
	private OblastService oblastService;
	@Inject
	private LoginUtil loginUtil;
	@Inject
	private CountrySelector selector;

	private Transport transport;
	private Set<Oblast> oblasts;

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
		transport.setOblasts(new HashSet<Oblast>());
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
		setOblasts(transport.getOblasts());
		return form();
	}
	
	public String save() {		
		transport.setStatus(TransportStatus.NEW);
		transport.setUser(loginUtil.getCurrentUser());
		transport.setOblast(selector.getOblast());
		transport.setOblasts(oblasts);
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
		transport.setDate(new Date());
        transportService.merge(transport);
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("sendedToModerator"), null) );
		return list();
	}
	
	public List<Oblast> getOblastList() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("country.id", 1, InequalityConstants.EQUAL)); 
		return oblastService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
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


	public Set<Oblast> getOblasts() {
		return oblasts;
	}


	public void setOblasts(Set<Oblast> oblasts) {
		this.oblasts = oblasts;
	}
	
}

