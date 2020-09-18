package org.jukbar.controller.dict;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jukbar.conversations.ConversationOblast;
import org.jukbar.conversations.ConversationRegion;
import org.jukbar.domain.Region;
import org.jukbar.service.RegionService;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class RegionController{

	@EJB
	private RegionService service;
	
	private Region region;
	
	@Inject
	private ConversationRegion conversation;	
	@Inject
	private ConversationOblast conversationOblast;

	@PostConstruct
	public void init() {
		region = conversation.getRegion();
		if (region==null) region= new Region();
	}
	
	public String add() {
		region = conversation.getRegion();
		if (region==null) region= new Region();
        return form();
    }

    public String edit(Region region) {
        this.region = region;
        conversation.setRegion(region);
        return form();
    }

    public String save() {
    	if (region.getId() == null) {
    		region.setOblast(conversationOblast.getOblast());
    		region = service.persist(region);
    	} else { 
    		region = service.merge(region);
    	}
        return cancel();
    }
    
    public String delete(Region Region) {
        service.remove(Region);
        return cancel();
    }

    public String cancel() {
    	region = null;
        return list();
    }
    
    public void onRowSelect(SelectEvent event) throws IOException {
		region=(Region) event.getObject();
		conversation.setRegion(region);
		System.out.println("region===" +region);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/dictionary/village_list.xhtml?cid="+conversation.getId());
        
    }
	
	private String list(){
		return "/view/dictionary/region_list.xhtml?faces-redirect=true";
	}
	
	private String form(){
		return "/view/dictionary/region_form.xhtml?faces-redirect=true";
	}

	public ConversationOblast getConversationOblast() {
		return conversationOblast;
	}

	public void setConversationOblast(ConversationOblast conversationOblast) {
		this.conversationOblast = conversationOblast;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}


}
