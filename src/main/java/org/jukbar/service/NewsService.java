package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.News;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface NewsService extends GenericService<News, Integer> {

}
