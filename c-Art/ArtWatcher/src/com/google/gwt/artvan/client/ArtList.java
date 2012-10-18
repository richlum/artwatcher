package com.google.gwt.artvan.client;

import java.util.Arrays;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ArtList {

	private Vector<ArtInformation> artworks;
	private ArtInformationServiceAsync artInfoService = GWT
			.create(ArtInformationService.class);
	private AsyncCallback<ArtInformation[]> callback;
	private LocationManager locationMgr;

	ArtList() {
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
				// updateTable(result);
				if(result!=null)
				artworks.addAll(Arrays.asList(result));
			}
		};
	}

	Vector<ArtInformation> searchByAddres(String address) {
		// TODO build the list before returning it
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
	
	void deleteAllArt(){
		artInfoService.deleteAllArt(callback);
		
	}

}