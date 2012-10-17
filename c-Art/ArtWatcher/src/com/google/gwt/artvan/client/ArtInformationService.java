package com.google.gwt.artvan.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.artvan.client.ArtInformation;

@RemoteServiceRelativePath("artInfo")
public interface ArtInformationService extends RemoteService {

	ArtInformation[] searchByLocation(double lat, double lng);

	ArtInformation[] addArtObject(ArtInformation ai);
	ArtInformation[] deleteAllArt();
	void removeArtObject(ArtInformation ai);

}