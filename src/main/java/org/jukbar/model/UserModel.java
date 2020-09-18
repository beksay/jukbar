package org.jukbar.model;

import java.util.List;

import org.jukbar.beans.FilterExample;
import org.jukbar.domain.User;
import org.jukbar.service.UserService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class UserModel extends BaseModel<UserService, User, Integer> {

	private static final long serialVersionUID = -4871106869905562775L;

	public UserModel(List<FilterExample> filters, UserService service) {
		super(filters, service);
	}
	
	@Override
	protected Integer getKey(String key) {
		return Integer.parseInt(key);
	}
	
}
