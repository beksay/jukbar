package org.jukbar.controller.dict;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.Faq;
import org.jukbar.enums.SortEnum;
import org.jukbar.service.FaqService;

@Named
@ConversationScoped
public class FaqController extends Conversational implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private FaqService service;
	
	private Faq faq;
	private String searchString;

	@PostConstruct
	public void init() {
		if (faq==null) faq= new Faq();
	}
	
	public String add() {
		if (faq==null) faq= new Faq();
        return form();
    }

    public String edit(Faq faq) {
        this.faq = faq;
        return form();
    }

    public String save() {
    	faq.setDateCreated(new Date());
    	if (faq.getId() == null) {
    		faq = service.persist(faq);
    	} else { 
    		faq = service.merge(faq);
    	}
        return cancel();
    }
    
    public String delete(Faq faq) {
        service.remove(faq);
        return cancel();
    }

    public String cancel() {
    	faq = null;
        return list();
    }
	
	private String list(){
		return "/view/faq/faq_list.xhtml?faces-redirect=true";
	}
	
	private String form(){
		return "/view/faq/faq_form.xhtml?faces-redirect=true";
	}
	
	public List<Faq> allFaqList(){
		List<FilterExample> examples=new ArrayList<>();
		if (searchString != null && searchString.length()>0) {
			examples.add(new FilterExample(true, "title", '%' + searchString.toLowerCase() + '%', InequalityConstants.LIKE, true));
		}
        return service.findByExample(0, 1000, SortEnum.ASCENDING, examples, "id");
	}

	public Faq getFaq() {
		return faq;
	}
	
	public void setFaq(Faq faq) {
		this.faq = faq;
	}

	public String getSearchString() {
		return searchString;
	}
	
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

}
