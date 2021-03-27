package org.jukbar.controller.user;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jukbar.beans.Message;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Person;
import org.jukbar.domain.Role;
import org.jukbar.domain.User;
import org.jukbar.enums.UserStatus;
import org.jukbar.service.PersonService;
import org.jukbar.service.RoleService;
import org.jukbar.service.UserService;
import org.jukbar.util.MailSender;
import org.jukbar.util.PasswordBuilder;
import org.jukbar.util.web.FacesMessages;
import org.jukbar.util.web.LoginUtil;
import org.jukbar.util.web.Messages;
import org.jukbar.validator.EntityValidator;


@Named
@ConversationScoped
public class RegistrationAction extends Conversational{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private UserService service;
	@EJB
	private PersonService personService;
	@EJB
	private RoleService roleService;
	
	@Inject
	private EntityValidator validator;
	@Inject
	private LoginUtil loginUtil;
	
	private User user;
	private Person person;
	
	@PostConstruct
	public void initialize() {
		user = new User();
		person = new Person();
	}
    
	public String save() {
		System.out.println("user======================="+user);
		if(user == null){
			FacesMessages.addMessage(Messages.getMessage("invalidData"), Messages.getMessage("invalidData"), null);
			return null;
		}

		List<User> users = service.findByProperty("email", user.getEmail());
    	if(!users.isEmpty()){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("emailIsAlreadyExists"), null) );
			return null;
    	}
    	
    	if (person.getRequirement()==false) {
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("requerementRequired"), null) );
			return null;
		}
    	
    	PasswordBuilder accountBuilder = new PasswordBuilder();
    	accountBuilder.digits(7);
	    
	    String personAccount = accountBuilder.build();
	    
	    List<Person> personAccountCheck = personService.findByProperty("account", personAccount);
    	if(!personAccountCheck.isEmpty()){
    		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_ERROR,  Messages.getMessage("tryAgain"), null) );
			return null;
    	}
	    person.setAccount(personAccount);
	    person.setMoney(new BigDecimal(0));
        
		Role addRole = roleService.findById(2, false);
		user.setRole(addRole);
		user.setPerson(person);
		user.setStatus(UserStatus.ACTIVE);
		user.setCountFailed(0);
		validator.validate(user);
		if(!FacesContext.getCurrentInstance().getMessageList().isEmpty()) return null;
		
		PasswordBuilder builder = new PasswordBuilder();
	    builder.lowercase(2)
	            .uppercase(8)
	            .specials(2)
	            .digits(2)
	            .shuffle();
	    
	    String password = builder.build();
	    Message message = new Message();
		message.setEmail(user.getEmail());
		message.setSubject("jukbar");
		message.setContent(""
				+ "<li><b>Username: </b> " + getUser().getEmail() + "</li>"
				+ "<li><b>Password: </b> " + password + "</li>"
				);
		MailSender.getInstance().asyncSend(message);
        
	    try {
			user.setPassword(loginUtil.getHashPassword(password));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	    
	    user.setPerson(user.getPerson().getId() == null ? personService.persist(user.getPerson()) : personService.merge(user.getPerson()));
	    
		if (user.getId() == null) {
			service.persist(user);
		} else {
			service.merge(user);
		}
		
		FacesContext.getCurrentInstance().addMessage("login-form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("messagesSendToEmail"), null) );
        
		user = new User();
		
		return "/view/public/registration/thank_you.xhtml?faces-redirect=true";
	}
	
	private String listPublic() {
		return "/jukbar/view/user/login.xhtml?faces-redirect=true";
	}
	
	public String loginForm() {
		user = new User();
		person = new Person();
		return "/view/user/login.xhtml?faces-redirect=true";
	}
	
	public String returnHome() {
		user = null;
		return home();
	}
	
	public String returnLogin() {
		user = null;
		return listPublic();
	}
	
	 public String register() {
		closeConversation();
		user = new User();
		person = new Person();
    	return "/view/public/registration/registration.xhtml";
    }
	
	private String home(){
		return "/home.xhtml";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}

}
