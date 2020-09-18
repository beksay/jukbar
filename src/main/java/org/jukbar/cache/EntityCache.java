package org.jukbar.cache;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class EntityCache {

	private long time; 
	private Object data;
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
}
