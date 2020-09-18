package org.jukbar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.domain.PersistentEntity;
import org.jukbar.enums.SortEnum;
import org.jukbar.service.GenericService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public abstract class BaseModel<S extends GenericService<E, ID>, E extends PersistentEntity<ID>, ID extends Serializable> extends LazyDataModel<E> {

	private static final long serialVersionUID = 1575455424649374631L;
	
	protected List<FilterExample> filters = new ArrayList<>();
	protected S service;
	
	private int rowCount;
	private String[] fetchProperties;
	private String[] joinProperties;
	private List<E> data = Collections.emptyList();
	
	protected abstract ID getKey(String key);

	
	public BaseModel(List<FilterExample> filters, S service) {
		this.filters = filters;
		this.service = service;
		if(filters==null || filters.size()==0) {
			this.filters=new ArrayList<>();
			this.filters.add(new FilterExample("id",null, InequalityConstants.IS_NOT_NULL_SINGLE));
		}
		initRowCount();
	}
	
	public BaseModel(List<FilterExample> filters, String[] joinProperties, S service) {
		this.filters = filters;
		this.joinProperties = joinProperties;
		this.service = service;
		if(filters==null || filters.size()==0) {
			this.filters=new ArrayList<>();
			this.filters.add(new FilterExample("id",null, InequalityConstants.IS_NOT_NULL_SINGLE));
		}
		initRowCount();
	}
	
	@Override
	public Object getRowKey(E entity) {
		return entity.getId();
	}
	
	@Override
	public E getRowData(String key) {
		try {
			ID id = getKey(key);
			if(data == null) return null;
			
			for (E e : data) {
				if(e.getId().equals(id)) return e;
			}
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return null;
	}
	@Override	
	 public List<E> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		try {
			sortField = sortField == null ? "id" : sortField;
			
			System.out.println(sortField + " " + sortOrder);
			System.out.println(filters);
			
			SortEnum sortEnum = sortOrder == null || sortOrder.equals(SortOrder.DESCENDING) ? SortEnum.DESCENDING : SortEnum.ASCENDING;
			data = this.filters == null || this.filters.isEmpty() 
					? getEntries(first, first + pageSize, sortEnum, sortField)
					: getByExample(first, first + pageSize, sortEnum, sortField); 
			
			System.out.println(data);
			return data;
		} catch(Exception e){
			return Collections.emptyList();
		}
	}
	
	private void initRowCount(){
		try {
			rowCount = this.filters == null || this.filters.isEmpty()
					? (int)service.countAll()
					: service.countByExample(filters).intValue();
					System.out.println(rowCount);
		} catch (Exception e) {
			e.printStackTrace();
			rowCount = 0;
		}
	}
	
	private List<E> getEntries(int first, int pageSize, SortEnum sortEnum, String sortField) {
		System.out.println(fetchProperties);
		return fetchProperties == null 
					? service.findEntries(first, first + pageSize, sortField, sortEnum)
					: service.findEntries(first, first + pageSize, sortField, sortEnum, fetchProperties);
	}		

	
	private List<E> getByExample(int first, int pageSize, SortEnum sortEnum, String sortField) {
		System.out.println(fetchProperties);
		if(fetchProperties == null && getJoinProperties() == null) return service.findByExample(first, first + pageSize, sortEnum, this.filters, sortField);
		else if(getJoinProperties() == null && getFetchProperties() != null) return service.findByExample(first, first + pageSize, sortEnum, this.filters, sortField, fetchProperties);
		else return service.findByExample(first, first + pageSize, sortEnum, this.filters, sortField, fetchProperties, joinProperties);
	}
	
	@Override
	public int getRowCount() {
		return rowCount;
	}
	
	public void setFilters(List<FilterExample> filters) {
		this.filters = filters;
		initRowCount();
	}
	
	public List<FilterExample> getFilters() {
		return filters;
	}
	
	public String[] getFetchProperties() {
		return fetchProperties;
	}
	
	public void setFetchProperties(String[] fetchProperties) {
		this.fetchProperties = fetchProperties;
	}
	
	public String[] getJoinProperties() {
		return joinProperties;
	}
	
	public void setJoinProperties(String[] joinProperties) {
		this.joinProperties = joinProperties;
	}
	
}
