package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Role;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface RoleService extends GenericService<Role, Integer> {

}
