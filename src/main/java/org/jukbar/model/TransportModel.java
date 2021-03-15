package org.jukbar.model;

import java.util.List;

import org.jukbar.beans.FilterExample;
import org.jukbar.domain.Transport;
import org.jukbar.service.TransportService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class TransportModel extends BaseModel<TransportService, Transport, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public TransportModel(List<FilterExample> filters, TransportService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
