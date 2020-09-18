package org.jukbar.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class Digest {

	private final String algorithm;
	
	public Digest(String algorithm) {
		this.algorithm = algorithm;
	}
	
	public String doEncrypt(String message) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] dataBytes = message.getBytes();

            md.update(dataBytes, 0, dataBytes.length); 
            byte[] digestedBytes = md.digest();

            //convert the byte to hex format
            for (byte digestedByte : digestedBytes) {
                sb.append(Integer.toString((digestedByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

       return sb.toString();
	}
	
	public String doEncypt(byte[] dataBytes) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
 
        md.update(dataBytes, 0, dataBytes.length);
        byte[] mdbytes = md.digest();
 
        //convert the byte to hex format
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
 
       return sb.toString();
 
	}
}
