package org.jukbar.controller.user;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.jukbar.annotation.Logged;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Transport;
import org.jukbar.domain.User;
import org.jukbar.enums.TransportStatus;
import org.jukbar.service.TransportService;
import org.jukbar.service.UserService;
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
	private UserService service;
	@EJB
	private TransportService transportService;
	
	private User user;
	private Transport transport;
	private Boolean editProfile;

	@PostConstruct
	public void init() {
		if (user==null)	user= new User();
		if (transport==null) transport= new Transport();
		editProfile = false;
	}
	
	public String change() {
		editProfile = true;
		return null;
	}
	
	public String cancel() {
		editProfile=false;
		return null;
	}
	
	public String save() {		
		user.getTransport().setStatus(TransportStatus.NEW);
		user.setTransport(transport);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		transportService.merge(user.getTransport());
		service.merge(user);
		
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editProfile = false;
		return null;
	}
	
	public String goProfile(User user) {
		this.user = user;
		if(user.getTransport() !=null){
			transport = transportService.findById(user.getTransport().getId(), false);	
		}
		return profileList();
	}
	
	private String profileList() {
		return "/view/transport/my_transport.xhtml";
	}
	
	public String mainForm() {
		return "/view/main.xhtml";
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Boolean getEditProfile() {
		return editProfile;
	}
	
	public void setEditProfile(Boolean editProfile) {
		this.editProfile = editProfile;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	
}

