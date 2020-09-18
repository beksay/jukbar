package org.jukbar.service.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jukbar.dao.RegionDao;
import org.jukbar.dao.impl.RegionDaoImpl;
import org.jukbar.domain.Region;
import org.jukbar.service.RegionService;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Stateless
@Local(RegionService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegionServiceImpl extends GenericServiceImpl<Region, Integer, RegionDao> implements RegionService {

	@Override
	protected RegionDao getDao() {
		return new RegionDaoImpl(em, se);
	}

}
