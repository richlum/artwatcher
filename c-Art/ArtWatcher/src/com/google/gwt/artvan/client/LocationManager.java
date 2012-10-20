package com.google.gwt.artvan.client;


public class LocationManager {

	private double lat;
	private double lng;
	//private LatLng location;
	// should return LatLng but need to work out com.google.gwt.maps.client
	void addressToLatLng(String address){
		//TODO : integrate google maps geocoding service
	}
	
	//todo update ArtInformation to reflect distance from user by calling ai.setKmFromUser
	void distanceToUserLocation(double userlat, double userlng, ArtInformation[] result){
		//TODO : use google maps distance functions
		System.out.println("calculating distance to user with retrieved results");
		for (int i =0;i<result.length;i++){
			System.out.println("art: " + result[i].getLat() + " " +
					result[i].getLng() + ", distance = " + result[i].getKmFromUser());
		}
	}
	
	
	void makeMarkerList( ArtInformation[] ai){
		//TODO correct signature, set of pairs of lat long in, return list of markers
		
	}
	
	void makeMapPlaces(){
		//TODO: correct signature and implement display maps given list of markers
	}
	
	void getUserLocation(){
		  
	}

	public void placeOnMap(ArtInformation[] result) {
		// TODO Auto-generated method stub
		System.out.println("updating map with retrieved results");
		for (int i =0;i<result.length;i++){
			System.out.println("art: " + result[i].getLat() + " " +
					result[i].getLng());
		}
		
	}

	public void placeOnMap(double userlat, double userlng) {
		// TODO Auto-generated method stub
		System.out.println("updating map with user location:" +userlat+", "+userlng);
	}

	public double addressToLat(String address) {
		System.out.println("getting lat from address" + address);
		return 0;
	}

	public double addressToLng(String address) {
		// TODO Auto-generated method stub
		System.out.println("getting lng from address" + address);
		return 0;
	}
}
