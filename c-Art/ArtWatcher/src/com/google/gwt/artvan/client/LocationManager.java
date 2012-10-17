package com.google.gwt.artvan.client;


public class LocationManager {

	private double lat;
	private double lng;
	//private LatLng location;
	// should return LatLng but need to work out com.google.gwt.maps.client
	void addressToLatLng(String address){
		//TODO : integrate google maps geocoding service
	}
	
	double distanceToUserLocation(double lat, double lng){
		//TODO : use google maps distance functions
		return 99.99;
	}
	
	
	void makeMarkerList( ){
		//TODO correct signature, set of pairs of lat long in, return list of markers
		
	}
	
	void makeMapPlaces(){
		//TODO: correct signature and implement display maps given list of markers
	}
	
	void getUserLocation(){
		//TODO  return what?  pair of doubles?  
	}
}
