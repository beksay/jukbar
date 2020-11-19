package org.jukbar.service;

import javax.ejb.Local;

import org.jukbar.domain.Faq;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Local
public interface FaqService extends GenericService<Faq, Integer> {

}
