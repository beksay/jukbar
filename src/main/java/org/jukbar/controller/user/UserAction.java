package org.jukbar.controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.beans.Message;
import org.jukbar.conversations.ConversationUser;
import org.jukbar.domain.Country;
import org.jukbar.domain.Documents;
import org.jukbar.domain.Person;
import org.jukbar.domain.Role;
import org.jukbar.domain.Transport;
import org.jukbar.domain.User;
import org.jukbar.enums.DocStatus;
import org.jukbar.enums.TransportStatus;
import org.jukbar.enums.UserStatus;
import org.jukbar.service.DocumentsService;
import org.jukbar.service.PersonService;
import org.jukbar.service.RoleService;
import org.jukbar.service.TransportService;
import org.jukbar.service.UserService;
import org.jukbar.util.MailSender;
import org.jukbar.util.web.FacesMessages;
import org.jukbar.util.web.LoginUtil;
import org.jukbar.util.web.Messages;
import org.jukbar.validator.EntityValidator;
import org.primefaces.event.SelectEvent;


@SuppressWarnings("unused")
@ManagedBean
@ViewScoped
public class UserAction {

	@EJB
	private UserService service;
	@EJB
	private PersonService personService;
	@EJB
	private TransportService transportService;
	@EJB
	private DocumentsService documentsService;
	
	@Inject
	private LoginUtil loginUtil;
	@Inject
	private ConversationUser conversation;	
	
	private User user;
	private Person person;
	private Transport transport;
	private Documents documents;
	
	private String username;
    private String password;
    
    private Boolean refuseButton;
    private Boolean refuseDoc;
    private String reasonDoc;
	private String reasonTransport;
    
	@PostConstruct
	public void init() {
		user=conversation.getUser();
		if (user==null)	user= new User();
		person=conversation.getPerson();
		if (person==null) person= new Person();
		if (transport==null) transport= new Transport();
		refuseButton = false;
		refuseDoc = false;
	}

	public String reg() {
		return form();
	}
	
	public String registerLogin(){
		return registerLog();
	}
	
	public String block(User user) {
		user.setCountFailed(0);
		user.setStatus(UserStatus.INACTIVE);
		service.merge(user);		
		return "user_journal.xhtml?faces-redirect=true";
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		user=(User) event.getObject();
		conversation.setUser(user);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/persons/sender_card.xhtml?cid="+conversation.getId()); 
    }
	
	public void onRowSelectDriver(SelectEvent event) throws IOException {
		user=(User) event.getObject();
		conversation.setUser(user);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/persons/driver_card.xhtml?cid="+conversation.getId()); 
    }
	
	public String unblock(User user) {
		user.setCountFailed(0);
		user.setStatus(UserStatus.ACTIVE);
		service.merge(user);
		return "ujer_journal.xhtml?faces-redirect=true";
	}
	
	public String refreshPassword(User user) {
		setPassword("123");
		try {
			String hashPassword = loginUtil.getHashPassword(password);
			user.setPassword(hashPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		user.setCountFailed(0);
		user.setStatus(UserStatus.ACTIVE);
		service.merge(user);	
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "123!","123!"));
		return "user_journal.xhtml?faces-redirect=true";
	}
	
	public String delete(User c) {
		System.out.println(c);
		service.remove(c);
		return cancel();
	}
	
	public String cancel() {
		user = null;
		return list();
	}

	private String list() {
		return "/view/user/user_journal.xhtml?faces-redirect=true";
	}
	
	private String form() {
		return "/view/user/user_form.xhtml";
	}

	private String registerLog(){
		return "/view/public/registration.xhtml";
	}		
	
	public void deletePassword(User user){
		user.setPassword("2dcb6c95b1fdda5605aa58356915327d95e8b11ad729d67255aa1b934f7c904467aa47d3cc1590b838162428f15c5bbe1fb45fc351a1e92f9003e0366749c2f8");
		service.merge(user);
		
		FacesMessage message = new FacesMessage(Messages.getMessage("passwordChanged"));
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, message);
	}
	
	public String change() {
		refuseButton = true;
		return null;
	}
	
	public String cancelChange() {
		refuseButton = false;
		return null;
	}
	
	public String confirmTransport() {
		transport = transportService.findById(user.getPerson().getTransport().getId(), false);
		transport.setStatus(TransportStatus.COMPLETED);
		transportService.merge(transport);
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		refuseButton = false;
		return null;
	}
	
	public String refuseTransport() {
		transport = transportService.findById(user.getPerson().getTransport().getId(), false);
		transport.setStatus(TransportStatus.REFUSED);
		transport.setReason(reasonTransport);
		transportService.merge(transport);
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		refuseButton = false;
		return null;
	}
	
	public String changeDoc() {
		refuseDoc = true;
		return null;
	}
	
	public String cancelChangeDoc() {
		refuseDoc = false;
		return null;
	}
	
	public String confirmDoc() {
		documents = documentsService.findById(user.getPerson().getDocuments().getId(), false);
		documents.setStatus(DocStatus.COMPLETED);
		documentsService.merge(documents);
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		refuseDoc = false;
		return null;
	}
	
	public String refuseDoc() {
		documents = documentsService.findById(user.getPerson().getDocuments().getId(), false);
		documents.setStatus(DocStatus.REFUSED);
		documents.setReason(reasonDoc);
		documentsService.merge(documents);
		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		refuseDoc = false;
		return null;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public Transport getTransport() {
		return transport;
	}
	
	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public Boolean getRefuseButton() {
		return refuseButton;
	}
	
	public void setRefuseButton(Boolean refuseButton) {
		this.refuseButton = refuseButton;
	}
	
	public Boolean getRefuseDoc() {
		return refuseDoc;
	}
	
	public void setRefuseDoc(Boolean refuseDoc) {
		this.refuseDoc = refuseDoc;
	}
	
	public String getReasonDoc() {
		return reasonDoc;
	}
	
	public void setReasonDoc(String reasonDoc) {
		this.reasonDoc = reasonDoc;
	}
	
	public String getReasonTransport() {
		return reasonTransport;
	}
	
	public void setReasonTransport(String reasonTransport) {
		this.reasonTransport = reasonTransport;
	}
}
