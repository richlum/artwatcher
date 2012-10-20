package com.google.gwt.artvan.server;


import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XMLHandler extends DefaultHandler {

	private double latitude;
	private double longitude; 
	private String name;
	private String title;
	private String link;

	private boolean  isCoordinates;
	private boolean  isName; 
	private boolean  isDescription;
	private ArtCollection artCollection;
	
	
	public XMLHandler(InputStream artXML) {
		artCollection = ArtCollection.getInstance();	
			isCoordinates = false;
			isName = false;
			isDescription = false;
		
         SAXParserFactory factory = SAXParserFactory.newInstance();
         try {
        	 SAXParser saxParser = factory.newSAXParser();
        	 saxParser.parse(new InputSource(artXML), this);
        	
         } catch (Throwable t) {
             t.printStackTrace();
         }
         finally {
         }
}
	
public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {

	if (qName.equalsIgnoreCase("name")) {
		isName = true;
	}
	if (qName.equalsIgnoreCase("description")) {
		isDescription = true;
	}
	if (qName.equalsIgnoreCase("coordinates")) {
		isCoordinates = true;
	}
}

public void endElement(String uri, String localName, String qName)
		throws SAXException {

	if (qName.equalsIgnoreCase("Placemark")) {
		artCollection.addArtObject(name, title, latitude, longitude, link);
	}
	if (isDescription){
		isDescription = false;
	}
	
	
	
	
}

public void characters(char ch[], int start, int length)
		throws SAXException {

	String data = new String(ch, start, length);
	
	if (isName) {
		name = data;
		isName = false;
	}
	if (isDescription) {
			if (data.indexOf("TITLE:") > 0){
				title = data.substring(7, data.indexOf("URL") - 1);	
			}
			if (data.indexOf("ArtworkID=") > 0){
				link = "app.vancouver.ca/PublicArt_net/ArtworkDetails.aspx?ArtworkID=" +
						data.substring(77, 80) +
						"&Neighbourhood=&Ownership=&Program=";
			}
	}
	if (isCoordinates) {
		latitude = new Double(data.substring(0, 10));
		longitude = new Double(data.substring(12, 20));
		isCoordinates = false;
	}
}

}