package org.jukbar.domain;

import javax.persistence.*;

/**
 * @author Kuttubek Aidaraliev
 */

@Entity
@Table(name = "region")
public class Region extends AbstractEntity<Integer> {

    private static final long serialVersionUID = -1716718384374303808L;
    private String name;
    private Oblast oblast;
    private String location;

    public Region() {
    }

    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name="oblast_id")
	public Oblast getOblast() {
		return oblast;
	}

	public void setOblast(Oblast oblast) {
		this.oblast = oblast;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}