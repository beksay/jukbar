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
import org.jukbar.domain.Oblast;
import org.jukbar.domain.Orders;
import org.jukbar.domain.Region;
import org.jukbar.enums.SortEnum;
import org.jukbar.service.OblastService;
import org.jukbar.service.OrdersService;
import org.jukbar.service.RegionService;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Named
@ConversationScoped
public class PublicOrderList extends Conversational {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private OrdersService orderService;
	@EJB
	private OblastService oblastService;
	@EJB
	private RegionService regionService;

	private Oblast oblast;
	private Region region;

	private Oblast oblastTo;
	private Region regionTo;
	
	@PostConstruct
	public void init() {
	   allOrderList();
	}
	
	public List<Orders> allOrderList(){
		List<FilterExample> examples=new ArrayList<>();
		if(oblast !=null) examples.add(new FilterExample("oblastFrom", oblast, InequalityConstants.EQUAL));  
        if(region !=null) examples.add(new FilterExample("regionFrom", region, InequalityConstants.EQUAL));
        
		if(oblastTo !=null) examples.add(new FilterExample("oblastTo", oblastTo, InequalityConstants.EQUAL));  
        if(regionTo !=null) examples.add(new FilterExample("regionTo", regionTo, InequalityConstants.EQUAL));
        return orderService.findByExample(0, 10, SortEnum.ASCENDING, examples, "date");
	}
	
	public String clearData() {
		oblast=null;
		region=null;
		oblastTo=null;
		regionTo=null;
		init();
		return null;
	}
	
	public List<Oblast> getOblastListLocal() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("country.id", 1, InequalityConstants.EQUAL)); 
		return oblastService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
	}
	
	public List<Region> getRegionList() {
		List<FilterExample> examples = new ArrayList<>();
		if(oblast !=null) {
			examples.add(new FilterExample("oblast", oblast, InequalityConstants.EQUAL)); 
		}else {
			examples.add(new FilterExample("id", InequalityConstants.IS_NULL_SINGLE)); 
		}
		return regionService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
	}
	
	public List<Oblast> getOblastToListLocal() {
		List<FilterExample> examples = new ArrayList<>();
		examples.add(new FilterExample("country.id", 1, InequalityConstants.EQUAL)); 
		return oblastService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
	}
	
	public List<Region> getRegionToList() {
		List<FilterExample> examples = new ArrayList<>();
		if(oblastTo !=null) {
			examples.add(new FilterExample("oblast", oblastTo, InequalityConstants.EQUAL)); 
		}else {
			examples.add(new FilterExample("id", InequalityConstants.IS_NULL_SINGLE)); 
		}
		return regionService.findByExample(0, 20, SortEnum.ASCENDING, examples, "id");
	}

	public Oblast getOblast() {
		return oblast;
	}
	
	public void setOblast(Oblast oblast) {
		this.oblast = oblast;
	}
	
	public Oblast getOblastTo() {
		return oblastTo;
	}
	
	public void setOblastTo(Oblast oblastTo) {
		this.oblastTo = oblastTo;
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Region getRegionTo() {
		return regionTo;
	}
	
	public void setRegionTo(Region regionTo) {
		this.regionTo = regionTo;
	}
	
	
}
