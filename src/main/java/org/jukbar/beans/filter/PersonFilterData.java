package org.jukbar.beans.filter;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class PersonFilterData extends FilterData {
	
	private static final long serialVersionUID = 3689878884019459508L;
	private String lastName;
    private String firstName;
    
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}
