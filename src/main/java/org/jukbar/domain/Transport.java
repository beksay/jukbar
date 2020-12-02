package org.jukbar.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.jukbar.enums.TransportStatus;

@Entity
@Table(name = "transport")
public class Transport extends AbstractEntity<Integer>{

    private static final long serialVersionUID = 1L;
    private String marka;
    private String capacity;
    private Integer year;
    private TransportStatus status;
    
	public String getMarka() {
		return marka;
	}
	
	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Enumerated(EnumType.ORDINAL)
	public TransportStatus getStatus() {
		return status;
	}

	public void setStatus(TransportStatus status) {
		this.status = status;
	} 
	
}
