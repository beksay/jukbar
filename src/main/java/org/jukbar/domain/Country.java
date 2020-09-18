package org.jukbar.domain;

import javax.persistence.*;

/**
 * @author Kuttubek Aidaraliev
 */

@Entity
@Table(name = "country")
public class Country extends AbstractEntity<Integer> {

    private static final long serialVersionUID = -1716718384374303808L;
    private String name;

    public Country() {
    }

    public String getName() {
		return name;
	}
    
    public void setName(String name) {
		this.name = name;
	}

}