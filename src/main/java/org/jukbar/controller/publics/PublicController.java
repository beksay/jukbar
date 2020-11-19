package org.jukbar.controller.publics;

import java.util.ArrayList;
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


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Named
@ConversationScoped
public class PublicController extends Conversational {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private FaqService faqService;

	private String searchString;
	
	@PostConstruct
	public void init() {
	
	}
	
	public List<Faq> allFaqList(){
		List<FilterExample> examples=new ArrayList<>();
		if (searchString != null && searchString.length()>0) {
			examples.add(new FilterExample(true, "title", '%' + searchString.toLowerCase() + '%', InequalityConstants.LIKE, true));
		}
        return faqService.findByExample(0, 1000, SortEnum.ASCENDING, examples, "id");
	}
	
	public String faqList() {
		return "/view/public/faq_list.xhtml?faces-redirect=true";
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
	
}
