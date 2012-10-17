package com.google.gwt.artvan.server;

import java.util.List;

import com.google.gwt.artvan.client.ArtInformation;
import com.google.gwt.artvan.client.ArtInformationService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ArtInformationServiceImpl extends RemoteServiceServlet implements
		ArtInformationService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ArtInformation[] searchByLocation(double lat, double lng) {
		// TODO replace this hardcoded stub with calls to phils artCollection
		// class to retrieve art object.  need cursory distance calc as well
		// and data fill ArtInformation based on that input.
		ArtInformation[] arts = new ArtInformation[4];
		arts[0] = new ArtInformation(47.11, 127.02,
				"http://art-van.appspot.com", "PersistOnePiece", "first item");
		arts[1] = new ArtInformation(47.12, 127.03,
				"http://art-van.appspot.com", "PersisttwoPiece", "second item");
		arts[2] = new ArtInformation(47.13, 127.04,
				"http://art-van.appspot.com", "PersistthreePiece", "third item");
		arts[3] = new ArtInformation(47.14, 127.05,
				"http://art-van.appspot.com", "PersistfourPiece", "fourth item");

		//TESTING   add all objects to persistence and then retrive them 
		// and send to client
		ArtCollection ac = ArtCollection.getInstance();
		for (int i = 0; i<3; i++){
			ac.addArtObject(arts[i]);
		}

		ArtInformation[] result = ac.getListByDistance();
		// art = ArtCollection.getListByDistance(lat, lng);
		// for (i=0; i<art.size(); i++){
		// arts[i] = new ArtInformation(art.lat, art.lng, art.link, art.name,
		// art.description, art.vists, art.avgRAting,art.key);

		return result;
	}

	@Override
	public ArtInformation[] addArtObject(ArtInformation ai) {
		// TODO Auto-generated method stub. test for duplicates before adding
		return null;
	}

	@Override
	public ArtInformation[] deleteAllArt() {
		ArtCollection ac = ArtCollection.getInstance();
		ac.deleteAllArt();
		return null;
	}

	@Override
	public void removeArtObject(ArtInformation ai) {
		// TODO Auto-generated method stub
		// TODO test for equality before deleting
		
	}

}
