package org.jukbar.controller.dict;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Attachment;
import org.jukbar.domain.News;
import org.jukbar.dto.AttachmentBinaryDTO;
import org.jukbar.service.AttachmentService;
import org.jukbar.service.NewsService;
import org.jukbar.util.Translit;
import org.jukbar.util.Util;
import org.jukbar.util.web.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ConversationScoped
public class NewsController extends Conversational implements Serializable {

    private static final long serialVersionUID = 1L;

	private News news;
	private AttachmentBinaryDTO image;
	private List<Attachment> removedFiles = new ArrayList<Attachment>();
	
    @EJB
    private NewsService newsService;
    @EJB
	AttachmentService atService;
    
    public String add() {
        news = new News();
        news.setActive(false);
        news.setDateNews(new Date());
        image = null;
        
        return navigate("news_form");
    }

    public String edit(News news) {
        this.news = news;
        try {
			if (news.getImage() != null)
				image = Util.createAttachmentDTO(news.getImage());
			else
				image = null;
		} catch (Exception e) {
			image = null;
		}
        return navigate("news_form");
    }

    public String save() {
        if (news != null) {
        	
        	if(image != null) {
        		Attachment attachment = new Attachment();
    			attachment = createAttachment(image);
    			image.setAttachment(attachment);
    			try {
    				attachment = image.getAttachment().getId() == null ? atService.saveFromDTO(image) : image.getAttachment();
    				news.setImage(attachment);
    				attachment = new Attachment();
    			} catch (IOException e) {e.printStackTrace();}			
    		}
        	
        	if (news.getId() == null) {
        		news.setDateCreated(new Date());
        		news = newsService.persist(news);
        		//logController.logForNews(news, 0);
        	} else { 
        		news = newsService.merge(news);
        		//logController.logForNews(news, 1);
        	}
        	
        	assertRemovedFiles();
        	
        }
        return cancel();
    }
    
    public void removeImage() {		
		if(image.getAttachment() != null && image.getAttachment().getId() != null) removedFiles.add(image.getAttachment());
		image = null;
	}
    
    public void assertRemovedFiles() {
		if(removedFiles.isEmpty()) return;
		
		for (Attachment attachment : removedFiles) {
			atService.remove(attachment);
		}
		
		removedFiles.clear();
	}

    public String delete(News news) {
    	//logController.logForNews(news, 2);
        newsService.remove(news);
        return cancel();
    }

    public String cancel() {
        news = null;
        closeConversation();
        return navigate("news_list");
    }

    private String navigate(String path) {
        return path;
    }
    
    // Загрузчик для рисунка
    public void handleFileUploadImage(FileUploadEvent event) throws IOException { 
    	
    	String fileName = Translit.translit(event.getFile().getFileName());
    	image = createFileBinary(event.getFile());
    	
        FacesMessage msg = new FacesMessage(Messages.getMessage("fileSuccessfullyUploaded").replaceAll("\\{0\\}", fileName));  
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
		return attachment;
	}
    
    public News getNews() {
		return news;
	}
    public void setNews(News news) {
		this.news = news;
	}
    
    public AttachmentBinaryDTO getImage() {
		return image;
	}
    public void setImage(AttachmentBinaryDTO image) {
		this.image = image;
	}
}
