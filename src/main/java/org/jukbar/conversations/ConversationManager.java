package org.jukbar.conversations;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.jukbar.annotation.Logged;
import org.jukbar.domain.Shipments;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
@Logged
@Named
@ConversationScoped
public class ConversationManager extends Conversational {
	
	private static final long serialVersionUID = -6100072166946495229L;
	
	private Shipments shipments;
	private String startLocation;
	private String endLocation;

	public Shipments getShipments() {
		return shipments;
	}

	public void setShipments(Shipments shipments) {
		this.shipments = shipments;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	
}
