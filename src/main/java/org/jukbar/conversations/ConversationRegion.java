package org.jukbar.conversations;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

import org.jukbar.annotation.Logged;
import org.jukbar.domain.Region;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */
@Logged
@Named
@ConversationScoped
public class ConversationRegion extends Conversational {
	
	private static final long serialVersionUID = -6100072166946495229L;
	
	private Region region;

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	
}
