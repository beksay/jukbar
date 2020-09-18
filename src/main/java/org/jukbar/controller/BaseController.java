package org.jukbar.controller;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public abstract class BaseController {
	
	public static final String USER_KEY = "user_key";
	
	protected String getRootErrorMessage(Exception e) {
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            return errorMessage;
        }

        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

}
