package com.google.gwt.artvan.server;


import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.artvan.client.ArtInformation;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Art {
	
	@Persistent
	  private Date createDate;
	@Persistent
	  private double latitude;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	  private Key key; 
	@Persistent
	  private double longitude; 
	 
	@Persistent
	  private String name;
	@Persistent
	  private String title;
	@Persistent
	  private String link;
	@Persistent
	  private int visits;
	@Persistent
	  private double avgRating;

	  
	  
	  
	  public Art() {
	    this.createDate = new Date();
	  }

	  
	  public Art(double lat, double lng, 
				String aName, String aLink, String aTitle){

			latitude = lat;
			longitude = lng; 
			name = aName; 
		//	key = aKey; 
			link = aLink;
			title = aTitle; 
			visits = 0; 
			avgRating = 0;
		}
	 
	  
	  
	  public Date getCreateDate() {
	    return this.createDate;
	  }

	  

		public double distance(double lat, double lng){
			double temp = Math.pow((lat-latitude), 2);
			temp = temp + Math.pow((lng - longitude), 2);
			return Math.sqrt(temp);
		}

		public int getVisits(){
			return visits;	
		}

		public void addVisit(){
			visits++;
		}

		public Key getKey(){
			return key;
		}

		public void setAvgRating(double rating){
			avgRating = rating;
		}

		public double getLat(){
			return latitude;
		}
		
		public double getLong(){
			return longitude;
		}
		public String getName(){
			return name; 
		}
		
		public String getURL(){
			return link;
		}
		public double getAvgRating(){
			return avgRating;
		}
		public String getTitle(){
			return title;
		}
		
		public boolean hasLat(double lat){
		return (lat == latitude);
		}
		public boolean hasLong(double lng){
			return (lng == longitude);
			}
		public boolean hasName(String name){
			return (name == this.name);
		}
		public boolean hasKey(int key){
			return true; //(key == this.key);
		}
		public boolean hasMoreVisits(int visits){
			return (visits < this.visits);
		}
		
		
		
		@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Art other = (Art) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	public Art(ArtInformation ai){
		latitude = ai.getLat();
		longitude = ai.getLng();
		name = ai.getName();
		link = ai.getLink();
		title = ai.getDescription(); 
		visits = ai.getVisits();
		avgRating = ai.getRating();
	}

	ArtInformation getArtInformation(){
		ArtInformation ai = new ArtInformation(
				latitude,
				longitude,
				link,
				name,
				title,
				visits,
				avgRating);
		return ai;
	}
		
	  
	  
	  
	}
