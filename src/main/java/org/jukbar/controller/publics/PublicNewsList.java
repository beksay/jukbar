package org.jukbar.controller.publics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.controller.BaseController;
import org.jukbar.model.NewsModel;
import org.jukbar.service.NewsService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@ManagedBean
@ViewScoped
public class PublicNewsList extends BaseController implements Serializable {
	
	private static final long serialVersionUID = -6100072166946495229L;
	@EJB
	private NewsService service;
	private NewsModel model;
	
	@PostConstruct
	private void init() {
		List<FilterExample> filters = new ArrayList<>();
		filters.add(new FilterExample("active", true, InequalityConstants.EQUAL));
		model = new NewsModel(filters, service);
	}


	public NewsModel getModel() {
		return model;
	}
	
	public void setModel(NewsModel model) {
		this.model = model;
	}
	
	public NewsService getService() {
		return service;
	}
	
	public void setService(NewsService service) {
		this.service = service;
	}
	
    
}
