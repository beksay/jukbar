package org.jukbar.validator;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jukbar.beans.FilterExample;
import org.jukbar.beans.InequalityConstants;
import org.jukbar.enums.ScopeConstants;
import org.jukbar.service.UserService;
import org.jukbar.util.web.FacesScopeQualifier;
import org.jukbar.util.web.Messages;
import org.jukbar.util.web.ScopeQualifier;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Named
@RequestScoped
@FacesValidator("emailValidator")
public class EmailValidator implements Validator {
	
	@EJB
	private UserService userService;
	
	private Pattern pattern;
	private Matcher matcher;
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	public static void main(String[] args) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		
		Matcher matcher = pattern.matcher("chingiz.kybunychbekova@gmail.com");
		
		if(!matcher.matches()) System.out.println("error");
		System.out.println("good");
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		System.out.println("validate");
		if(value == null) generateError("emailNotFound");
		
		ScopeQualifier qualifier = new FacesScopeQualifier();
		String email = qualifier.getValue("email", ScopeConstants.SESSION_SCOPE);
		
		if(email != null && email.equals(value)) return;
		
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		matcher = pattern.matcher(value.toString());
		
		if(!matcher.matches()) generateError("notEmail");
		
		List<FilterExample> examples = new ArrayList<FilterExample>();
		examples.add(new FilterExample("email", value.toString(), InequalityConstants.EQUAL, true));

		if (userService.countByExample(examples) > 0) generateError("emailAlreadyExist"); 
	}
	
	public void generateError(String error) {
		FacesMessage message = new FacesMessage(Messages.getMessage(error));
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(message);
	}

}
