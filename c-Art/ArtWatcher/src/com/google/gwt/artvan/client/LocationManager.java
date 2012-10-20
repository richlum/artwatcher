package com.google.gwt.artvan.client;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class LocationManager {

	//private double lat;
	//private double lng;
	
	public void addMap(final ArtFinder af) {
		// TODO Auto-generated constructor stub
		// build map
		Maps.loadMapsApi("AIzaSyBCzkdwkEfrlBoWpzcqUaoR7PaM7d3kSQ0", "2", false, new Runnable() {
			public void run() {
				buildUI(af);
			}
		});
		System.out.println("called that map to build");
	}
	
	private void buildUI(ArtFinder af) {
		LatLng vancouver = LatLng.newInstance(49.25, -123.11);
		final MapWidget map = new MapWidget(vancouver, 12);
		map.setSize("80%", "60%");
		map.addControl(new LargeMapControl());
		//final DockLayoutPanel dock = new DockLayoutPanel(Unit.PX);
		//dock.addNorth(map, 1000);
		//dock.setSize("800px", "600px");
		//pagePanel.add(dock);
		//RootPanel.get("mapRoot").add(dock);
		DockLayoutPanel mapPanel = af.getMapPanel();
		HorizontalPanel pagePanel = af.getPagePanel();
		mapPanel.addNorth(map, 1000);
		mapPanel.setSize("800px", "600px");
		pagePanel.add(mapPanel);
		//getLocationManager().placeOnMap(getUserlat(),getUserlng());
		System.out.println("added that map");
	}

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
