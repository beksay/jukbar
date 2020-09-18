package org.jukbar.cache;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public interface CacheManager {
	
	Object get(String key);
	String put(Object object);
	int size();
	boolean isStale();
	void clean();

}
