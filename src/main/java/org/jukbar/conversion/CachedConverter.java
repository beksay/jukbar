package org.jukbar.conversion;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.jukbar.cache.CacheManager;
import org.jukbar.cache.EntityCacheManager;

/***
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@FacesConverter(value="cachedConverter")
public class CachedConverter implements Converter {
	
	/*private static final Map<Object, String> caches = new WeakHashMap<>();
	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			lock.readLock().lock();
			for (Entry<Object, String> entry : caches.entrySet()) {
	            if (entry.getValue().equals(value)) {
	                return entry.getKey();
	            }
	        }
	        return null;
		} finally {
			lock.readLock().lock();
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object object) {
		boolean contain = false;
		try {
			lock.readLock().lock();
			contain = caches.containsKey(object);
		} finally {
			lock.readLock().unlock();
		}
		
		if(!contain){
            String uuid = UUID.randomUUID().toString();
            try {
            	lock.writeLock().lock();
                caches.put(object, uuid);
            } finally {
            	lock.writeLock().unlock();
            }
            return uuid;
        } else {
        	try {
        		lock.readLock().lock();
        		return caches.get(object);
        	} finally {
        		lock.readLock().unlock();
        	}
        }
	}*/
	
	private CacheManager manager = EntityCacheManager.getInstance();
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null) return null;
		return manager.get(value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if(object == null) return null;
		try {
			String key = manager.put(object);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e);
		}
	}
}
