package org.jukbar.model;

import java.util.List;

import org.jukbar.beans.FilterExample;
import org.jukbar.domain.Region;
import org.jukbar.service.RegionService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class RegionModel extends BaseModel<RegionService, Region, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public RegionModel(List<FilterExample> filters, RegionService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
