package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Person;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface PersonService extends GenericService<Person, Integer> {

}
