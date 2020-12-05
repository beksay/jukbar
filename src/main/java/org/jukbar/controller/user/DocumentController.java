package org.jukbar.controller.user;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.jukbar.annotation.Logged;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Documents;
import org.jukbar.domain.Person;
import org.jukbar.enums.DocStatus;
import org.jukbar.service.DocumentsService;
import org.jukbar.service.PersonService;
import org.jukbar.util.web.Messages;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@Named
@ConversationScoped
public class DocumentController extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	
	@EJB
	private PersonService personService;
	@EJB
	private DocumentsService docService;
	
	private Person person;
	private Documents documents;
	private Boolean editDoc;

	@PostConstruct
	public void init() {
		if (person==null) person= new Person();
		if (documents==null) documents= new Documents();
		editDoc = false;
	}
	
	public String change() {
		editDoc = true;
		return null;
	}
	
	public String cancel() {
		init();
		editDoc=false;
		return null;
	}
	
	public String save() {		
		documents.setStatus(DocStatus.NEW);
		person.setDocuments(documents);
		person.setDocuments(person.getDocuments() == null ? docService.persist(person.getDocuments()) : docService.merge(person.getDocuments()));

		personService.merge(person);

		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editDoc = false;
		return null;
	}
	
	public String goProfile(Person person) {
		this.person = person;
		if(person.getDocuments() !=null){
			documents = docService.findById(person.getDocuments().getId(), false);	
		}
		return profileList();
	}
	
	private String profileList() {
		return "/view/documents/my_documents.xhtml";
	}
	
	public String mainForm() {
		return "/view/main.xhtml";
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Documents getDocuments() {
		return documents;
	}

	public void setDocuments(Documents documents) {
		this.documents = documents;
	}

	public Boolean getEditDoc() {
		return editDoc;
	}

	public void setEditDoc(Boolean editDoc) {
		this.editDoc = editDoc;
	}
	
}

