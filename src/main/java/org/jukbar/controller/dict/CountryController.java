package org.jukbar.controller.dict;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jukbar.conversations.ConversationCountry;
import org.jukbar.domain.Country;
import org.jukbar.service.CountryService;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class CountryController{

	@EJB
	private CountryService service;
	
	private Country country;
	
	@Inject
	private ConversationCountry conversation;	

	@PostConstruct
	public void init() {
		country = conversation.getCountry();
		if (country==null) country= new Country();
	}
	
	public void onRowSelect(SelectEvent event) throws IOException {
		country=(Country) event.getObject();
		conversation.setCountry(country);
		System.out.println("Country===" +country);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/jukbar/view/dictionary/oblast_list.xhtml?cid="+conversation.getId());
        
    }
	
	public String list(){
		return "/view/dictionary/country_list.xhtml?faces-redirect=true";
	}

	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}
}
