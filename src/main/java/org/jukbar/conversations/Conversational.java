package org.jukbar.conversations;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
public abstract class Conversational implements Serializable {
	
	private static final long serialVersionUID = 282062606859800901L;
	
	@Inject
	private Conversation conversation;
	private Object data;
	
	@PostConstruct
	public void initialize() {
		if(conversation.isTransient()) {
			conversation.setTimeout(300000L);
			conversation.begin();
		}
		if(FacesContext.getCurrentInstance().getExternalContext().getFlash().get("filtersData") != null) data = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("filtersData");
	}
	
    public void closeConversation() {
    	System.out.println("closeConversation = " +  FacesContext.getCurrentInstance().getExternalContext().getFlash().get("filtersData"));
    	System.out.println("data = " + data);
		if(!conversation.isTransient()) conversation.end();
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("filtersData", data);
	}
    
    public void setData(Object data) {
    	System.out.println("data = " + data);
		this.data = data;
	}
    
    public Object getData() {
		return data;
	}
    
    public String getId(){
    	return conversation.getId();
    }

}
