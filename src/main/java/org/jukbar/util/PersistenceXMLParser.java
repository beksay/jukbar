package org.jukbar.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class PersistenceXMLParser {
	
	public static Collection<String> getEntities() throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
		InputStream stream = PersistenceXMLParser.class.getClassLoader().getResourceAsStream("META-INF/persistence.xml");
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder =  builderFactory.newDocumentBuilder();
		Document xmlDocument = builder.parse(stream);
		
		XPath xPath =  XPathFactory.newInstance().newXPath();
		String expression = "/persistence/persistence-unit/class";

		NodeList list= (NodeList)xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		List<String> result = new ArrayList<>();
		
		for (int i = 0; i < list.getLength(); i++) {
		    Node node = list.item(i);
		    result.add(node.getTextContent());
		}
		
		return result;
	}

}
