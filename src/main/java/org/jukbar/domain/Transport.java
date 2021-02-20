package org.jukbar.domain;

import javax.persistence.Column;
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
    private String tsYear;
    private String number;
    private TransportStatus status;
    private String reason;
    
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

	@Enumerated(EnumType.ORDINAL)
	public TransportStatus getStatus() {
		return status;
	}

	public void setStatus(TransportStatus status) {
		this.status = status;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name="ts_year")
	public String getTsYear() {
		return tsYear;
	}

	public void setTsYear(String tsYear) {
		this.tsYear = tsYear;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	} 
	
}
