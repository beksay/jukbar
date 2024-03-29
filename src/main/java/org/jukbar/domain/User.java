package org.jukbar.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.jukbar.enums.UserStatus;


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Entity
@Table(name="user", schema="public", uniqueConstraints=@UniqueConstraint(columnNames="email"))
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;
	private String password;
	private String email;
	private UserStatus status;
	private Integer countFailed;
	private Role role;
	private Person person;
	

	@ManyToOne
    @JoinColumn(name="person_id")
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Enumerated(EnumType.ORDINAL)
	public UserStatus getStatus() {
		return status;
	}
	
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	@Column(name="count_failed")
	public Integer getCountFailed() {
		return countFailed;
	}
	
	public void setCountFailed(Integer countFailed) {
		this.countFailed = countFailed;
	}
	
	@ManyToOne
    @JoinColumn(name="role_id")
    public Role getRole() {
		return role;
	}
    public void setRole(Role role) {
		this.role = role;
	}
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}