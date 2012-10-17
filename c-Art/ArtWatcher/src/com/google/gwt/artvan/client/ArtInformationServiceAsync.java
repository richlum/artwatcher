package com.google.gwt.artvan.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ArtInformationServiceAsync {
	void searchByLocation(double lat, double lng,  AsyncCallback<ArtInformation[]> callback);
	void addArtObject(ArtInformation ai, AsyncCallback<ArtInformation[]> callback);
	//void deleteAllArt(AsyncCallback<Void> callback);
	void removeArtObject(ArtInformation ai, AsyncCallback<Void> callback);
	void deleteAllArt(AsyncCallback<ArtInformation[]> callback);

}
