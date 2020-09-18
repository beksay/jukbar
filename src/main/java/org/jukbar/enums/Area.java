package org.jukbar.enums;

import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@XmlType(name = "", propOrder = {"RESPUBLIC","OBLAST","REGION","AYIL_OKMOTY", "VILLAGE"})
public enum Area {

	RESPUBLIC,
    OBLAST,
    REGION,
    AYIL_OKMOTY,
    VILLAGE
	
}