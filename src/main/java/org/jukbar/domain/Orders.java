package org.jukbar.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jukbar.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Orders extends AbstractEntity<Integer>{

    private static final long serialVersionUID = 1L;
    private OrderStatus status;
    private TransportType transportType;		
    private Oblast oblastFrom;
    private Region regionFrom;
    private String addressFrom;
    private Date date;	
    private Oblast oblastTo;
    private Region regionTo;
    private String addressTo;
    private BigDecimal amount;
    private String products;
    private Date dateCreated;
    private Date dateProgress;
    private Date dateTaken;
    private String phone;
    private String addPhone;
    private User owner;
    
    @ManyToOne
	@JoinColumn(name="transport_type_id")
	public TransportType getTransportType() {
		return transportType;
	}
    
	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}
	
	@ManyToOne
	@JoinColumn(name="oblast_from_id")
	public Oblast getOblastFrom() {
		return oblastFrom;
	}
	
	public void setOblastFrom(Oblast oblastFrom) {
		this.oblastFrom = oblastFrom;
	}
	
	@ManyToOne
	@JoinColumn(name="region_from_id")
	public Region getRegionFrom() {
		return regionFrom;
	}
	
	public void setRegionFrom(Region regionFrom) {
		this.regionFrom = regionFrom;
	}
	
	@Column(name = "address_from",length = 2000)
	public String getAddressFrom() {
		return addressFrom;
	}
	
	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date")
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@ManyToOne
	@JoinColumn(name="oblast_to_id")
	public Oblast getOblastTo() {
		return oblastTo;
	}
	
	public void setOblastTo(Oblast oblastTo) {
		this.oblastTo = oblastTo;
	}
	
	@ManyToOne
	@JoinColumn(name="region_to_id")
	public Region getRegionTo() {
		return regionTo;
	}
	
	public void setRegionTo(Region regionTo) {
		this.regionTo = regionTo;
	}
	
	@Column(name = "address_to",length = 2000)
	public String getAddressTo() {
		return addressTo;
	}
	
	public void setAddressTo(String addressTo) {
		this.addressTo = addressTo;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(name = "products",length = 5000)
	public String getProducts() {
		return products;
	}
	
	public void setProducts(String products) {
		this.products = products;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_created")
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Enumerated(EnumType.ORDINAL)
	public OrderStatus getStatus() {
		return status;
	}
	
	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_progress")
	public Date getDateProgress() {
		return dateProgress;
	}

	public void setDateProgress(Date dateProgress) {
		this.dateProgress = dateProgress;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_taken")
	public Date getDateTaken() {
		return dateTaken;
	}

	public void setDateTaken(Date dateTaken) {
		this.dateTaken = dateTaken;
	}

	@ManyToOne
	@JoinColumn(name="owner_id")
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Column(name = "add_phone")
	public String getAddPhone() {
		return addPhone;
	}

	public void setAddPhone(String addPhone) {
		this.addPhone = addPhone;
	}
	
}
