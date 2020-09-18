package org.jukbar.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * @author tuazku
 */
public class ReCaptchaWs {

    private static final String SERVICE_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET = "6LcEMAcUAAAAAGuZIletJPNHsyz4oKeEfEZjcEZa";

    public static boolean isReCaptchaValid() {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String userResponse = request.getParameter("g-recaptcha-response");

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(SERVICE_URL).queryParam("verbose", true);
        ReCaptchaResponse reCaptchaResponse = target
                .queryParam("secret", SECRET)
                .queryParam("response", userResponse)
                .request().accept("application/json").get(ReCaptchaResponse.class);
        System.out.println(reCaptchaResponse);
        client.close();

        return reCaptchaResponse.isSuccess();
    }
}
