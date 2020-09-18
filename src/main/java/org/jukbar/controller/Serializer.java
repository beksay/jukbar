package org.jukbar.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.jukbar.annotation.Logged;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Named
@RequestScoped
@Logged
public class Serializer {
	
	public String serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return Base64.getEncoder().encodeToString(b.toByteArray());
    }

    public Object deserialize(String value) throws IOException, ClassNotFoundException {
    	byte[] bytes = Base64.getDecoder().decode(value);
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }
}
