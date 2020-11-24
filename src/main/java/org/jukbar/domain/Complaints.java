package org.jukbar.domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Kantoro Erkulov
 */

@Entity
@Access(AccessType.PROPERTY)
@Cacheable(false)
@Table(name = "complaints")
public class Complaints extends AbstractEntity<Integer> {

	private static final long serialVersionUID = 7990318330652610253L;
	private String theme;
	private String content;
	private Date dateCreated;

    public Complaints() {
    }
    
    @Column(name = "theme")
    public String getTheme() {
		return theme;
	}
    
    public void setTheme(String theme) {
		this.theme = theme;
	}

    @Column(name = "content", length = 1500)
    public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Column(name = "date_created")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}