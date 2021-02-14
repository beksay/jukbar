package org.jukbar.controller.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.jukbar.annotation.Logged;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Attachment;
import org.jukbar.domain.Documents;
import org.jukbar.domain.Person;
import org.jukbar.dto.AttachmentBinaryDTO;
import org.jukbar.enums.DocStatus;
import org.jukbar.service.AttachmentService;
import org.jukbar.service.DocumentsService;
import org.jukbar.service.PersonService;
import org.jukbar.util.Translit;
import org.jukbar.util.Util;
import org.jukbar.util.web.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
	@EJB
	AttachmentService atService;
	
	private Person person;
	private Documents documents;
	private Boolean editDoc;
	
	private AttachmentBinaryDTO passport;
	private AttachmentBinaryDTO driverLicense;
    private AttachmentBinaryDTO carLicense;
    
    private List<Attachment> removedFiles = new ArrayList<Attachment>();

	@PostConstruct
	public void init() {
		if (person==null) person= new Person();
		if (documents==null) documents= new Documents();
		if (passport==null) passport= new AttachmentBinaryDTO();
		if (driverLicense==null) driverLicense= new AttachmentBinaryDTO();
		if (carLicense==null) carLicense= new AttachmentBinaryDTO();
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
		
		if(passport != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(passport);
			passport.setAttachment(attachment);
			try {
				attachment = passport.getAttachment().getId() == null ? atService.saveFromDTO(passport) : passport.getAttachment();
				documents.setPassport(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}
		
		if(driverLicense != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(driverLicense);
			driverLicense.setAttachment(attachment);
			try {
				attachment = driverLicense.getAttachment().getId() == null ? atService.saveFromDTO(driverLicense) : driverLicense.getAttachment();
				documents.setDriverLicense(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}
		
		if(carLicense != null) {
    		Attachment attachment = new Attachment();
			attachment = createAttachment(carLicense);
			carLicense.setAttachment(attachment);
			try {
				attachment = carLicense.getAttachment().getId() == null ? atService.saveFromDTO(carLicense) : carLicense.getAttachment();
				documents.setCarLicense(attachment);
				attachment = new Attachment();
			} catch (IOException e) {e.printStackTrace();}			
		}	
		
		person.setDocuments(documents);
		person.setDocuments(person.getDocuments() == null ? docService.persist(person.getDocuments()) : docService.merge(person.getDocuments()));

		personService.merge(person);

		FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("dataDaved"), null) );
		editDoc = false;
		return null;
	}
	
	public String sendModerator() {	
		if (passport != null && driverLicense != null && carLicense != null) {
			if(passport != null) {
	    		Attachment attachment = new Attachment();
				attachment = createAttachment(passport);
				passport.setAttachment(attachment);
				try {
					attachment = passport.getAttachment().getId() == null ? atService.saveFromDTO(passport) : passport.getAttachment();
					documents.setPassport(attachment);
					attachment = new Attachment();
				} catch (IOException e) {e.printStackTrace();}			
			}
			
			if(driverLicense != null) {
	    		Attachment attachment = new Attachment();
				attachment = createAttachment(driverLicense);
				driverLicense.setAttachment(attachment);
				try {
					attachment = driverLicense.getAttachment().getId() == null ? atService.saveFromDTO(driverLicense) : driverLicense.getAttachment();
					documents.setDriverLicense(attachment);
					attachment = new Attachment();
				} catch (IOException e) {e.printStackTrace();}			
			}
			
			if(carLicense != null) {
	    		Attachment attachment = new Attachment();
				attachment = createAttachment(carLicense);
				carLicense.setAttachment(attachment);
				try {
					attachment = carLicense.getAttachment().getId() == null ? atService.saveFromDTO(carLicense) : carLicense.getAttachment();
					documents.setCarLicense(attachment);
					attachment = new Attachment();
				} catch (IOException e) {e.printStackTrace();}			
			}
			
			documents.setStatus(DocStatus.IN_PROGRESS);
			docService.merge(documents);
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("sendedToModerator"), null) );
		}else {
			FacesContext.getCurrentInstance().addMessage("form", new FacesMessage( FacesMessage.SEVERITY_INFO,  Messages.getMessage("uploadAllDocuments"), null) );
		}		
		editDoc = false;
		return null;
	}
	
	public String goProfile(Person person) {
		this.person = person;
		if(person.getDocuments() !=null){
			documents = docService.findById(person.getDocuments().getId(), false);	
	
			try {
				if (documents.getPassport() != null) {
					passport = Util.createAttachmentDTO(documents.getPassport());
				}else {
					passport = null;
				}
				if (documents.getDriverLicense() != null) {
					driverLicense = Util.createAttachmentDTO(documents.getDriverLicense());
				}else {
					driverLicense = null;
				}
				if (documents.getCarLicense() != null) {
					carLicense = Util.createAttachmentDTO(documents.getCarLicense());
				}else {
					carLicense = null;
				}
			} catch (Exception e) {
				passport = null;
				driverLicense = null;
			}
		}
		return profileList();
	}
	
	public String goProfileMain(Person person) {
		this.person = person;
		if(person.getDocuments() !=null){
			documents = docService.findById(person.getDocuments().getId(), false);	
	
			try {
				if (documents.getPassport() != null) {
					passport = Util.createAttachmentDTO(documents.getPassport());
				}else {
					passport = null;
				}
				if (documents.getDriverLicense() != null) {
					driverLicense = Util.createAttachmentDTO(documents.getDriverLicense());
				}else {
					driverLicense = null;
				}
				if (documents.getCarLicense() != null) {
					carLicense = Util.createAttachmentDTO(documents.getCarLicense());
				}else {
					carLicense = null;
				}
			} catch (Exception e) {
				passport = null;
				driverLicense = null;
			}
		}
		return profileListMain();
	}
	
	private String profileList() {
		return "/view/documents/my_documents.xhtml";
	}
	
	private String profileListMain() {
		return "/view/documents/my_documents_main.xhtml";
	}
	
	public String mainForm() {
		return "/view/main.xhtml";
	}
	
	public void assertRemovedFiles() {
		if(removedFiles.isEmpty()) return;
		
		for (Attachment attachment : removedFiles) {
			atService.remove(attachment);
		}
		
		removedFiles.clear();
	}
	
	public void removePassport() {		
		if(passport.getAttachment() != null && passport.getAttachment().getId() != null) removedFiles.add(passport.getAttachment());
		passport = null;
		documents.setPassport(null);
	}
    
    public void removeDriverLicense() {		
		if(driverLicense.getAttachment() != null && driverLicense.getAttachment().getId() != null) removedFiles.add(driverLicense.getAttachment());
		driverLicense = null;
		documents.setDriverLicense(null);
	}
    
    public void removeCarLicense() {		
		if(carLicense.getAttachment() != null && carLicense.getAttachment().getId() != null) removedFiles.add(carLicense.getAttachment());
		carLicense = null;
		documents.setCarLicense(null);
	}
    
    public void handleFileUploadPassport(FileUploadEvent event) throws IOException { 
    	String fileName = Translit.translit(event.getFile().getFileName());
    	passport = createFileBinary(event.getFile());
        FacesMessage msg = new FacesMessage(Messages.getMessage("successfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    
    public void handleFileUploadDriverLicense(FileUploadEvent event) throws IOException { 
    	String fileName = Translit.translit(event.getFile().getFileName());
    	driverLicense = createFileBinary(event.getFile());
        FacesMessage msg = new FacesMessage(Messages.getMessage("successfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    public void handleFileUploadCarLicense(FileUploadEvent event) throws IOException { 
    	String fileName = Translit.translit(event.getFile().getFileName());
    	carLicense = createFileBinary(event.getFile());
        FacesMessage msg = new FacesMessage(Messages.getMessage("successfullyUploaded").replaceAll("\\{0\\}", fileName));  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
    
    private AttachmentBinaryDTO createFileBinary(UploadedFile file) throws IOException {
    	AttachmentBinaryDTO binary = new AttachmentBinaryDTO();
		binary.setName(Translit.translit(file.getFileName()));
		binary.setMimeType(file.getContentType());
		binary.setBody(IOUtils.toByteArray(file.getInputstream()));
		
		return binary;
	}
    
    private Attachment createAttachment(AttachmentBinaryDTO binary) {
		if(binary.getAttachment() != null && binary.getAttachment().getId() != null) return binary.getAttachment();
		Attachment attachment = new Attachment();
		attachment.setFileName(binary.getName());
		attachment.setLocked(false);
		attachment.setPublicInfo(true);
		attachment.setData(binary.getBody());
		return attachment;
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

	public AttachmentBinaryDTO getPassport() {
		return passport;
	}

	public void setPassport(AttachmentBinaryDTO passport) {
		this.passport = passport;
	}

	public AttachmentBinaryDTO getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(AttachmentBinaryDTO driverLicense) {
		this.driverLicense = driverLicense;
	}

	public AttachmentBinaryDTO getCarLicense() {
		return carLicense;
	}

	public void setCarLicense(AttachmentBinaryDTO carLicense) {
		this.carLicense = carLicense;
	}
	
}

