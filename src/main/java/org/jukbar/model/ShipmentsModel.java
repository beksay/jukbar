package org.jukbar.model;

import java.util.List;

import org.jukbar.beans.FilterExample;
import org.jukbar.domain.Shipments;
import org.jukbar.service.ShipmentsService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class ShipmentsModel extends BaseModel<ShipmentsService, Shipments, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public ShipmentsModel(List<FilterExample> filters, ShipmentsService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
