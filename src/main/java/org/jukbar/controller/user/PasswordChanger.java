package org.jukbar.controller.user;


import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jukbar.annotation.Logged;
import org.jukbar.conversations.Conversational;
import org.jukbar.domain.User;
import org.jukbar.enums.ScopeConstants;
import org.jukbar.service.UserService;
import org.jukbar.util.web.FacesMessages;
import org.jukbar.util.web.FacesScopeQualifier;
import org.jukbar.util.web.LoginUtil;
import org.jukbar.util.web.Messages;
import org.jukbar.util.web.ScopeQualifier;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Logged
@Named
@ConversationScoped
public class PasswordChanger extends Conversational {

	private static final long serialVersionUID = 5651758429305872940L;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	@EJB
	private UserService service;
	
	@Inject
	private LoginUtil loginUtil;
	
	public PasswordChanger() {}
	
	public String change() {
		User user = new FacesScopeQualifier().getValue(LoginUtil.CURRENT_USER_SESSION_KEY, ScopeConstants.SESSION_SCOPE);
		System.out.println("go to add [change password] by user " + user);
		return "/view/profile/change_password.xhtml?faces-redirect=true";
	}
	
	public String cancel() {
		return "/view/profile/my_profile.xhtml?faces-redirect=true";
	}
	
	public String doChange() throws Exception {
		User user = new FacesScopeQualifier().getValue(LoginUtil.CURRENT_USER_SESSION_KEY, ScopeConstants.SESSION_SCOPE);
		System.out.println("go to doAdd [change password] by user " + user);
		String hashPassword = loginUtil.getHashPassword(newPassword);
		
		if(newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)){
			FacesMessages.addMessage(Messages.getMessage("passwordNotMatch"), Messages.getMessage("passwordNotMatch"), null);
			return null;
		}
		
		if(oldPassword == null || !user.getPassword().equals(loginUtil.getHashPassword(oldPassword))) {
			FacesMessages.addMessage(Messages.getMessage("invalidPassword"), Messages.getMessage("invalidPassword"), null);
			return null;
		}
		
		Calendar calendar = new GregorianCalendar();
		calendar.add(Calendar.DAY_OF_MONTH, 90);
		
		user.setPassword(hashPassword);
		user.setCountFailed(0);
		user.setDatePasswordExpired(calendar.getTime());
		user = service.merge(user);
		
		ScopeQualifier qualifier = new FacesScopeQualifier();
		qualifier.remove("changePassword", ScopeConstants.SESSION_SCOPE);
		
		return cancel();
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
}

