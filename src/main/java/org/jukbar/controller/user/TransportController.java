package org.jukbar.controller.user;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.jukbar.annotation.Logged;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Person;
import org.jukbar.domain.Transport;
import org.jukbar.enums.TransportStatus;
import org.jukbar.service.PersonService;
import org.jukbar.service.TransportService;
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
	private PersonService personService;
	@EJB
	private TransportService transportService;
	
	private Person person;
	private Transport transport;
	private Boolean editTs;

	@PostConstruct
	public void init() {
		if (person==null) person= new Person();
		if (transport==null) transport= new Transport();
		editTs = false;
	}
	
	public String change() {
		editTs = true;
		return null;
	}
	
	public String cancel() {
		init();
		editTs=false;
		return null;
	}
	
	public String save() {		
		transport.setStatus(TransportStatus.NEW);
		person.setTransport(transport);
		person.setTransport(person.getTransport() == null ? transportService.persist(person.getTransport()) : transportService.merge(person.getTransport()));

		personService.merge(person);

		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editTs = false;
		return null;
	}
	
	public String sendModerator() {		
		transport.setStatus(TransportStatus.IN_PROGRESS);
        transportService.merge(transport);
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("sendedToModerator"), null) );
		editTs = false;
		return null;
	}
	
	public String goProfile(Person person) {
		this.person = person;
		if(person.getTransport() !=null){
			transport = transportService.findById(person.getTransport().getId(), false);	
		}
		return profileList();
	}
	
	private String profileList() {
		return "/view/transport/my_transport.xhtml";
	}
	
	public String mainForm() {
		return "/view/main.xhtml";
	}
	
	public Boolean geteditTs() {
		return editTs;
	}
	
	public void seteditTs(Boolean editTs) {
		this.editTs = editTs;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}

