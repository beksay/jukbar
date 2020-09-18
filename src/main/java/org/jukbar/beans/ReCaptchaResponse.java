package org.jukbar.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Author PC
 * Created on 9/12/2016.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReCaptchaResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean success;
    private String challengeTs;
    private String hostname;
    private String errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @XmlElement(name = "challenge_ts")
    public String getChallengeTs() {
        return challengeTs;
    }

    public void setChallengeTs(String challengeTs) {
        this.challengeTs = challengeTs;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @XmlElement(name = "error_code")
    public String getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(String errorCodes) {
        this.errorCodes = errorCodes;
    }

    @Override
    public String toString() {
        return "ReCaptchaResponse{" +
                "success=" + success +
                ", challengeTs='" + challengeTs + '\'' +
                ", hostname='" + hostname + '\'' +
                ", errorCodes='" + errorCodes + '\'' +
                '}';
    }
}
