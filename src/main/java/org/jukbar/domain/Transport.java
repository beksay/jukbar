package org.jukbar.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private Oblast oblast;
    private Region region;
    private User user;
    private Attachment document;
    private Attachment picture;
    private Date date;
    
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
	
	@ManyToOne
	@JoinColumn(name="oblast_id")
	public Oblast getOblast() {
		return oblast;
	}
	
	public void setOblast(Oblast oblast) {
		this.oblast = oblast;
	}
	
	@ManyToOne
	@JoinColumn(name="region_id")
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="document_id")
	public Attachment getDocument() {
		return document;
	}

	public void setDocument(Attachment document) {
		this.document = document;
	}

	@ManyToOne
	@JoinColumn(name="picture_id")
	public Attachment getPicture() {
		return picture;
	}

	public void setPicture(Attachment picture) {
		this.picture = picture;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
