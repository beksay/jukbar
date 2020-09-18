package org.jukbar.controller.dict;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jukbar.conversations.ConversationCountry;
import org.jukbar.conversations.ConversationOblast;
import org.jukbar.domain.Oblast;
import org.jukbar.service.OblastService;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class OblastController{

	@EJB
	private OblastService service;
	
	private Oblast oblast;
	
	@Inject
	private ConversationOblast conversation;	
	@Inject
	private ConversationCountry conversationCountry;

	@PostConstruct
	public void init() {
		oblast = conversation.getOblast();
		if (oblast==null) oblast= new Oblast();
	}
	
	public String add() {
		oblast = conversation.getOblast();
		if (oblast==null) oblast= new Oblast();
        return form();
    }

    public String edit(Oblast oblast) {
        this.oblast = oblast;
        conversation.setOblast(oblast);
        return form();
    }

    public String save() {
    	if (oblast.getId() == null) {
    		oblast.setCountry(conversationCountry.getCountry());
    		oblast = service.persist(oblast);
    	} else { 
    		oblast = service.merge(oblast);
    	}
        return cancel();
    }
    
    public String delete(Oblast oblast) {
        service.remove(oblast);
        return cancel();
    }

    public String cancel() {
    	oblast = null;
        return list();
    }
	
	public void onRowSelect(SelectEvent event) throws IOException {
		oblast=(Oblast) event.getObject();
		conversation.setOblast(oblast);
		System.out.println("Oblast===" +oblast);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/dictionary/region_list.xhtml?cid="+conversation.getId());
        
    }
	
	private String list(){
		return "/view/dictionary/oblast_list.xhtml?faces-redirect=true";
	}
	
	private String form(){
		return "/view/dictionary/oblast_form.xhtml?faces-redirect=true";
	}

	public Oblast getOblast() {
		return oblast;
	}

	public void setOblast(Oblast oblast) {
		this.oblast = oblast;
	}

	public ConversationCountry getConversationCountry() {
		return conversationCountry;
	}

	public void setConversationCountry(ConversationCountry conversationCountry) {
		this.conversationCountry = conversationCountry;
	}


}
