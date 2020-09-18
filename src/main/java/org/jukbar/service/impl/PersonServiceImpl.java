package org.jukbar.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.PersonDao;
import org.jukbar.dao.impl.PersonDaoImpl;
import org.jukbar.domain.Person;
import org.jukbar.service.PersonService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(PersonService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PersonServiceImpl extends GenericServiceImpl<Person, Integer, PersonDao> implements PersonService {

	@Override
	protected PersonDao getDao() {
		return new PersonDaoImpl(em, se);
	}

}
