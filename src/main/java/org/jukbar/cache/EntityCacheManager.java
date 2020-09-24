package org.jukbar.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jukbar.domain.PersistentEntity;
import org.jukbar.util.Digest;
import org.jukbar.util.PersistenceXMLParser;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class EntityCacheManager implements CacheManager {
	
	private static EntityCacheManager instance;
	private static int LIFE_CYCLE_ENTRY_TIME = 3600000;
	private static int LIFE_CYCLE_MANAGER_TIME = 7200000;
	
	private Map<String, EntityCache> manager;
	private Date date;
	
	public static synchronized CacheManager getInstance() {
		if(instance == null){
			instance = new EntityCacheManager();
		}
		return instance;
	}
	
	private EntityCacheManager() {
		init();
	}

	private void init() {
		manager = new ConcurrentHashMap<>();
		date = new Date();
	}

	@Override
	public Object get(String key) {
		return manager.entrySet().stream()
	                .filter(map -> key.equals(map.getKey()))
	                .map(map -> map.getValue().getData())
	                .collect(singletonCollector());
	}
	
	@Override
	public String put(Object object) {
		String key = null;
		try {
			key = getKey(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key == null) return null;
		else if(manager.containsKey(key)) return key;
		
		EntityCache cache = new EntityCache();
		cache.setTime(System.currentTimeMillis());
		cache.setData(object);
		
		manager.put(key, cache);
		
		return key;
	}

	@Override
	public int size() {
		return manager.size();
	}

	@Override
	public boolean isStale() {
		return System.currentTimeMillis() - date.getTime() >= LIFE_CYCLE_MANAGER_TIME;
	}

	@Override
	public void clean() {
		manager.entrySet().removeIf(
				entry -> isStale(entry.getValue().getTime()));
		date = new Date();
	}
	
	private String getKey(Object object) throws Exception {
		if(object instanceof PersistentEntity){
			PersistentEntity<?> entity = (PersistentEntity<?>)object;
			Serializable id = entity.getId();
			
			String name = entity.getClass().getName();
			name = getCorrectName(name);
			
			return new Digest("MD5").doEncypt(name + "_" + id);
		}
		 
		return null;
	}
	
	private String getCorrectName(String name) throws Exception {
		Collection<String> collection = PersistenceXMLParser.getEntities();
		Comparator<String> comparator = (String o1, String o2) -> o1.length() - o2.length();
		String value = collection.stream()
                .filter(e -> name.startsWith(e))
                .max(comparator)
                .get();
		return value == null ? name : value;
	}

	public static <T> Collector<T, ?, T> singletonCollector() {
	    return Collectors.collectingAndThen(
	            Collectors.toList(),
	            list -> {
	            	if(list.isEmpty()) return null;
	            	else if (list.size() != 1) {
	                    throw new IllegalStateException();
	                }
	                return list.get(0);
	            }
	    );
	}
	
	private boolean isStale(long time) {
		return System.currentTimeMillis() - time >= LIFE_CYCLE_ENTRY_TIME;
	}

}
