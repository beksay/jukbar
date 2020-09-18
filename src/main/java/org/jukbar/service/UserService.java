package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.User;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface UserService extends GenericService<User, Integer> {

}
