package com.google.gwt.artvan.client;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;

public class ArtList {

	private Vector<ArtInformation> artworks;
	private ArtInformationServiceAsync artInfoService = GWT
			.create(ArtInformationService.class);
	private AsyncCallback<ArtInformation[]> callback;
	private LocationManager locationMgr;
	private ArtFinder artfinder;
	private ArtInformation[] ai;
	
	public ArtInformation[] getAi() {
		return ai;
	}

	public void setAi(ArtInformation[] ai) {
		this.ai = ai;
	}

	public double userlat = 99.9999;
	public double userlng = 99.999;
	public String sortSelectionIndex ="none";

	public ArtList(ArtFinder artfinder) {
		this.artfinder = artfinder;
		final ArtFinder finalartfinder = artfinder;
		final FlexTable artFlexTable = artfinder.getFlexTable();
		final Label lastUpdatedLabel = artfinder.getLastUpdatedLabel();
		final String sortSetting = artfinder.getSortbyindex();
		final LocationManager locationManager = artfinder.getLocationManager();
		userlat = artfinder.getUserlat();
		userlng = artfinder.getUserlng();
		sortSelectionIndex = artfinder.getSortbyindex();
		
		System.out.println("Artlist:userlat = " + userlat); 
		
		final ArtFinder af_ref = artfinder;
		
		artworks = new Vector<ArtInformation>();
		if (locationMgr != null) {
			locationMgr = new LocationManager();
		}
		if (artInfoService == null) {
			artInfoService = GWT.create(ArtInformationService.class);
		}

		// Set up the callback object.
		callback = new AsyncCallback<ArtInformation[]>() {
	
			
			public void onFailure(Throwable caught) {
				// TODO: Do something with errors.
				caught.printStackTrace();
			}

			public void onSuccess(ArtInformation[] result) {
				// todo move this code back into artfinder and call it.
				//
				if (result==null) return;
				setAi(result);
				System.out.println("retreived result count = " + result.length );
				userlat = finalartfinder.getUserlat();
				userlng = finalartfinder.getUserlng();
				//sortSelectionIndex = finalartfinder.getSortbyindex();
				LocationManager locationManager = finalartfinder.getLocationManager();
				//todo put this in the else clause below after testing
				locationManager.distanceToUserLocation(userlat, userlng, result);
				Arrays.sort(result, new byDistance());
				
				if ((result!=null)&&(result.length>0))
					locationManager.placeOnMap(result);
				String sortSetting = "Rating";
				System.out.println(sortSetting);
				System.out.println("onSuccess:user location :" +  userlat+ "," + userlng);
				if (sortSetting.equalsIgnoreCase("Rating")) {
					Arrays.sort(result, new byRating());
				} else if (sortSetting.equalsIgnoreCase("Most Visited")) {
					Arrays.sort(result, new byVisits());
				}

				if (result != null) {
					artworks.addAll(Arrays.asList(result));
					updateTable(result);
				}
			}

			public void updateTable(ArtInformation[] artInfo) {
				int existing_rowcount = artFlexTable.getRowCount();

				for (int i = 0; i < artInfo.length; i++) {
					updateTable(artInfo[i], i + 1);
				}
				// we have fewer rows than before, clear the excess
				for (int i = artInfo.length; i < existing_rowcount; i++) {
					if (i != 0) // just so we don't remove the header if length
								// = 0
						artFlexTable.removeRow(i);
				}
				lastUpdatedLabel.setText("Last update : "
						+ DateTimeFormat.getMediumDateTimeFormat().format(
								new Date()));

			}

			private void updateTable(ArtInformation artInfo, int row) {

				String latlong = " " + artInfo.getLat() + " "
						+ artInfo.getLng();
				String desc = artInfo.getDescription();
				// fill row with art info
				artFlexTable.setText(row, 0, artInfo.getName());
				artFlexTable.setText(row, 1, latlong);
				artFlexTable.setText(row, 2, desc);
				artFlexTable.setText(row, 3, "" + artInfo.getVisits());

				// add button to add visits
				final int currentRow = row;
				Button visitedButton = new Button("+");
				visitedButton.setSize("20px", "20px");
				visitedButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						// increase visitor count of related art object
						int numVisits = Integer.parseInt(artFlexTable.getText(
								currentRow, 3)); // get current number of visits
													// shown in table
						artFlexTable.setText(currentRow, 3, "" + (++numVisits));
						// TODO Persist new visit count to server
					}
				});
				artFlexTable.setWidget(row, 4, visitedButton);

				// add ratings selector
				RadioButton rb1 = new RadioButton("Rating", "1");
				RadioButton rb2 = new RadioButton("Rating", "2");
				RadioButton rb3 = new RadioButton("Rating", "3");
				RadioButton rb4 = new RadioButton("Rating", "4");
				RadioButton rb5 = new RadioButton("Rating", "5");
				FlowPanel ratingsPanel = new FlowPanel();
				ratingsPanel.add(rb1);
				ratingsPanel.add(rb2);
				ratingsPanel.add(rb3);
				ratingsPanel.add(rb4);
				ratingsPanel.add(rb5);
				artFlexTable.setWidget(row, 5, ratingsPanel);

			}
			
			class byRating implements Comparator{

				@Override
				public int compare(Object arg0, Object arg1) {
					if ((((ArtInformation)arg0).getRating() - ((ArtInformation)arg1).getRating()) > 0){
						return 1;
					}else {
						return 0;
					}
			
				}
			}
			
			class byVisits implements Comparator{

				@Override
				public int compare(Object o1, Object o2) {
					if ((((ArtInformation)o1).getVisits() - ((ArtInformation)o2).getVisits()) > 0){
						return 1;
					}else {
						return 0;
					}
				}
				
			}
			
			class byDistance implements Comparator{

				@Override
				public int compare(Object o1, Object o2) {
					if ((((ArtInformation)o1).getKmFromUser() - ((ArtInformation)o2).getKmFromUser()) > 0){
						return 1;
					}else {
						return 0;
					}
				}
				
			}

		};
	}

	Vector<ArtInformation> searchByAddres(String address) {
		//
		LocationManager locationManager = artfinder.getLocationManager();
		double lat = locationManager.addressToLat(address);
		double lng = locationManager.addressToLng(address);
		artInfoService.searchByLocation(lat, lng, callback);
		return artworks;
	}

	Vector<ArtInformation> searchByLocation(double lat, double lng) {
		// updates private artworks member
		artInfoService.searchByLocation(lat, lng, callback);

		return artworks;
	}

	Vector<ArtInformation> getTopRated() {
		// TODO: build the list before returning it
		return artworks;
	}

	void updateArtList(Vector<ArtInformation> artList) {
		// TODO: build the list before returning it
	}

	Vector<ArtInformation> getArtWorks() {
		// TODO: build the list before returning it
		return artworks;
	}

	Vector<ArtInformation> sortByNUmVisits(Vector<ArtInformation> artList) {
		// TODO: build the list before returning it
		return artworks;
	}

	void deleteAllArt() {
		artInfoService.deleteAllArt(callback);

	}

}