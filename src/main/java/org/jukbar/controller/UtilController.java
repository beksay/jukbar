package org.jukbar.controller;

import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jukbar.dto.AttachmentBinaryDTO;
import org.jukbar.util.Translit;
import org.jukbar.annotation.Logged;
import org.jukbar.beans.EntryValue;
import org.jukbar.domain.Attachment;
import org.jukbar.enums.Area;
import org.jukbar.util.Krypto;
import org.jukbar.util.web.HttpUtil;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

 


/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Named
@ApplicationScoped
public class UtilController {
	
	public <T> List<T> getAsList(Set<T> set) {
		if(set == null) return Collections.emptyList();
		return new ArrayList<T>(set);
	}
	
	public String getDownloadURL(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		
		return HttpUtil.getContextUrl(request) + "download?key=" + key;
	}
	
	public String generateKeyAliveCurrentSession(Attachment attachment) {
		if(attachment == null) return null;
		
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
			HttpSession session = request.getSession();
			Integer id = attachment.getId();
			
			Krypto krypto = new Krypto();
		    krypto.setKey(new byte[]{0x21, 0x10, 0x51, 0x09, 0x08, 0x70, 0x07, 04});
		    String keyValue = krypto.doEncrypt((session.getId() + "@@@@@@@" + id.toString()).getBytes());
		    keyValue = URLEncoder.encode(keyValue, "UTF-8");
		    
		    return keyValue;
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	public EntryValue<Area, String> parse(String code) {
        if (code == null || code.length() != 14) return null;
        code = getNormalCode(code);
        int length = code.length();
        Area area;
        if (length == 3) {
            area = Area.RESPUBLIC;
            return new EntryValue<Area, String>(area, code);
        } else if (length <= 5) {
            area = Area.OBLAST;
            return new EntryValue<Area, String>(area, code.substring(0, 3));
        } else if (length <= 8) {
            area = Area.REGION;
            return new EntryValue<Area, String>(area, code.substring(0, 5));
        } else if (length <= 11) {
            area = Area.AYIL_OKMOTY;
            return new EntryValue<Area, String>(area, code.substring(0, 8));
        } else {
            area = Area.VILLAGE;
            return new EntryValue<Area, String>(area, code.substring(0, 11));
        }
    }
	
	public String getCode(String code, Area area) {
		if(area == null || code == null) return code;
		switch (area) {
		case RESPUBLIC:
			return code.substring(0, 3);
		case OBLAST:
			return code.substring(0, 5);
		case REGION:
			return code.substring(0, 8);
		case AYIL_OKMOTY:
			return code.substring(0, 11);
		case VILLAGE:
			return code.substring(0, 13);
		}
		
		return code;
	}
	
	public String getNormalCode(String code) {
        int[] lengths = {3, 5, 8, 11, 13, 14};
        code = removeCharByEnd(code, '0');
        for (int i : lengths) {
            if (code.length() == i) return code;
            if (code.length() < i) {
                return StringUtils.rightPad(code, i, '0');
            }
        }
        return code;
    }
	
	private String removeCharByEnd(String s, char c) {
        for (int i = s.length() - 1; i >= 0; i--) {
            char cur = s.charAt(i);
            if (cur == c) continue;
            return s.substring(0, i + 1);
        }
        return s;
    }
	
}
