package org.jukbar.util.web;

import java.io.Serializable;
import java.security.Principal;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Named;
import javax.security.auth.Subject;

import org.apache.commons.codec.binary.Base64;
import org.jukbar.domain.User;
import org.jukbar.enums.ScopeConstants;
import org.jukbar.util.Digest;
import org.jukbar.util.Krypto;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Named
@ApplicationScoped
public class LoginUtil implements Constants, Serializable {
	
	private static final long serialVersionUID = 5910093770049428002L;
	
	public boolean userHasRole(User user, String roleName) {
		if(roleName == null || user == null || user.getRole() == null) return false;
		
		if(user.getRole().getName().contains(roleName)) return true;
		
		
		return false;
	}
	
	public boolean userHasRole(User user, String[] roleNames) {
		if(roleNames == null || user == null) return false;
		
		for (String role : roleNames) {
			if(userHasRole(user, role)) return true;
		}
		return false;		
	}
	
	public boolean userHasRole(Subject subject, String[] roleNames) {
		if(roleNames == null) return false;
		
		for (String role : roleNames) {
			for (Principal principal : subject.getPrincipals()) {
				if(principal.getName().equals(role)) return true;
			}
		}
			
		return false;		
	}
	
	public void setCurrentUser(User user) {
		new FacesScopeQualifier().setValue(CURRENT_USER_SESSION_KEY, user, ScopeConstants.SESSION_SCOPE);
	}
	
	public User getCurrentUser() {
		return new FacesScopeQualifier().getValue(CURRENT_USER_SESSION_KEY, ScopeConstants.SESSION_SCOPE);
	}
	
	public boolean isLogged() {
		User user = new FacesScopeQualifier().getValue(CURRENT_USER_SESSION_KEY, ScopeConstants.SESSION_SCOPE);
		return (user == null ? false : true);
	}
	
	public void logout() {
		new FacesScopeQualifier().getSession().invalidate();
	}
	
	public String getHashPassword(String password) throws Exception {
    	Krypto krypto = new Krypto();
		krypto.setKey(new byte[]{0x21, 0x10, 0x51, 0x09, 0x08, 0x70, 0x07, 0x04});
		byte[] bytes = Base64.encodeBase64(krypto.doEncrypt(password.getBytes()).getBytes());
		return new Digest("SHA-512").doEncypt(bytes);
	}

}
