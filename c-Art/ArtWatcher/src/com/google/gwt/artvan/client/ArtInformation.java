package com.google.gwt.artvan.client;

import java.io.Serializable;



public class ArtInformation  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double lat; //maps to Art.latitude
	private double lng; //maps to Art.longitute
	private String name;
	private String description;  //maps to Art.title
	private String link;
	private int visits;
	private double rating; // maps to Art.avgRating
	private double kmFromUser;

	
	public double getLat() {
		return lat;
	}


	public void setLat(double lat) {
		this.lat = lat;
	}


	public double getLng() {
		return lng;
	}


	public void setLng(double lng) {
		this.lng = lng;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getVisits() {
		return visits;
	}


	public void setVisits(int visits) {
		this.visits = visits;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}



	// explicit contructor setting all fields
	public ArtInformation( 
			 double lat,
			 double lng,
			 String link,
			 String name,
			 String description,
			 int visits,
			 double rating) {
		setLat(lat);
		setLng(lng);
		setLink(link);
		setName(name);
		setDescription(description);
		setVisits(visits);
		setRating(rating);

	}
	// convience constructor explicitly setting all but visits and ratings (default zero)
	public ArtInformation( 
			 double lat,
			 double lng,
			 String link,
			 String name,
			 String description) {
		setLat(lat);
		setLng(lng);
		setLink(link);
		setName(name);
		setDescription(description);
		setVisits(0);
		setRating(0);

	}
	// required no arg constructor for gwt rpc compile
	//   uninitialized private members ok?
	public ArtInformation() {

	}
	
	public String toString(){
		String mystr = "name= " + this.getName() +
				" lat= " + this.getLat() + 
				" lng= " + this.getLng() +
				" link= " + this.getLink() +
				" desc= " + this.getDescription() +
				" rate= " + this.getRating() +
				" visit= " + this.getVisits();
		return mystr;
	}


	public double getKmFromUser() {
		return kmFromUser;
	}


	public void setKmFromUser(double kmFromUser) {
		this.kmFromUser = kmFromUser;
	}
	
	
}