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
import javax.persistence.Transient;

import org.jukbar.enums.Gender;

@Entity
@Table(name = "person")
public class Person extends AbstractEntity<Integer>{

    private static final long serialVersionUID = 1L;
    private String lastName;
    private String firstName;
    private String middleName; 
    private Date birthdate;
    private Gender gender;   
    private String phone;
    private String account;
    private BigDecimal money;
    private Boolean requirement;
    private TransportType transportType;
    private Date birthDate;
    private Documents documents;
    private Transport transport;

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    @Temporal(TemporalType.DATE)
	@Column(name="birthdate")
    public Date getBirthdate() {
		return birthdate;
	}
    
    public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
   
    @Enumerated(EnumType.ORDINAL)
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Transient
    public String getFullName() {
        return lastName + " " + firstName + " " + (middleName != null ? middleName : "");
    }
    
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public BigDecimal getMoney() {
		return money;
	}
	
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	public Boolean getRequirement() {
		return requirement;
	}
	
	public void setRequirement(Boolean requirement) {
		this.requirement = requirement;
	}
	
	@ManyToOne
	@JoinColumn(name="transport_type_id")
	public TransportType getTransportType() {
		return transportType;
	}
    
	public void setTransportType(TransportType transportType) {
		this.transportType = transportType;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@ManyToOne
	@JoinColumn(name = "documents_id")
	public Documents getDocuments() {
		return documents;
	}
	public void setDocuments(Documents documents) {
		this.documents = documents;
	}
	
	@ManyToOne
    @JoinColumn(name="transport_id")
	public Transport getTransport() {
		return transport;
	}
	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	
}
