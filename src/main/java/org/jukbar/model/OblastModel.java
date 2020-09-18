package org.jukbar.model;

import java.util.List;

import org.jukbar.beans.FilterExample;
import org.jukbar.domain.Oblast;
import org.jukbar.service.OblastService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class OblastModel extends BaseModel<OblastService, Oblast, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public OblastModel(List<FilterExample> filters, OblastService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
