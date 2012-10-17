package com.google.gwt.artvan.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.gwt.artvan.client.ArtInformation;
import com.google.gwt.artvan.client.NotLoggedInException;

public class ArtCollection {
	private static ArtCollection ArtCollectionSingleton = null;

	private static final Logger LOG = Logger.getLogger(StockServiceImpl.class
			.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
// old stuff supporting stockwatcher ... maintain until we
	// are confident persistence works properly after refactoring so 
	// that we have a working reference point while we add art stuff
	
	public void addStock(String symbol, User user) throws NotLoggedInException {
		PersistenceManager pm = getPersistenceManager();
		try {
			Stock s = (Stock) pm.makePersistent(new Stock(user, symbol));
			s.setDbl(88.1);
		} finally {
			pm.close();
		}
	}

	public String[] getStocks(User user) {

		PersistenceManager pm = getPersistenceManager();
		List<String> symbols = new ArrayList<String>();
		try {
			Query q = pm.newQuery(Stock.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			List<Stock> stocks = (List<Stock>) q.execute(user);
			for (Stock stock : stocks) {
				symbols.add(stock.getSymbol());
			}
		} finally {
			pm.close();
		}
		return (String[]) symbols.toArray(new String[0]);
	}

	public void removeStock(String symbol, User user) {
		PersistenceManager pm = getPersistenceManager();
		try {
			long deleteCount = 0;
			Query q = pm.newQuery(Stock.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			List<Stock> stocks = (List<Stock>) q.execute(user);
			for (Stock stock : stocks) {
				if (symbol.equals(stock.getSymbol())) {
					deleteCount++;
					pm.deletePersistent(stock);
				}
			}
			if (deleteCount != 1) {
				LOG.log(Level.WARNING, "removeStock deleted " + deleteCount
						+ " Stocks");
			}
		} finally {
			pm.close();
		}
	}

	//new art functions
	void addArtObject(ArtInformation ai){
		PersistenceManager pm = getPersistenceManager();
		try {
			Art art = new Art(ai);
			Art persistent_art = (Art) pm.makePersistent(art);
		}catch (Exception e){
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
	}
	
	ArtInformation[] getListByDistance(){
		PersistenceManager pm = getPersistenceManager();
		ArtInformation[] ai_array = null;
		try {
			Query q = pm.newQuery(Art.class);
			List<Art> arts = (List<Art>) q.execute();
			//System.out.println("getList retrived count = " + arts.size());
			ai_array = new ArtInformation[arts.size()];
			for (int i=0;i<arts.size(); i++){
				ai_array[i]=arts.get(i).getArtInformation();
			}	
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
//		for (int i=0; i< ai_array.length; i++){
//			System.out.println(ai_array[i].toString());
//		}
		return ai_array;
		
	}
	
	
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	private ArtCollection() {

	}

	public static ArtCollection getInstance() {
		if (ArtCollectionSingleton == null) {
			ArtCollectionSingleton = new ArtCollection();
		}
		return ArtCollectionSingleton;
	}

	public List<Art> getAllArt() {
		PersistenceManager pm = getPersistenceManager();

		List<Art> arts = null;
		try {
			Query q = pm.newQuery(Art.class);
			arts = (List<Art>) q.execute();
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			pm.close();
		}
		return arts;
	}

	public void deleteArt(List<Art> artlist) {
		PersistenceManager pm = getPersistenceManager();
		int deleteCount=0;
		
		try {
			
			for (int i=0; i<artlist.size();i++){
				deleteCount++;
				artlist.get(i).getKey();
				pm.deletePersistent(artlist.get(i));
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			pm.close();
		}
		System.out.println("Deleted " + deleteCount + " objects");
		//return true;
	}

	public void deleteAllArt() {
		PersistenceManager pm = getPersistenceManager();
		int deleteCount=0;
		
		try {
			Query q = pm.newQuery(Art.class);
			List<Art> arts = (List<Art>) q.execute();
			//System.out.println("getList retrived count = " + arts.size());
			for (int i=0;i<arts.size(); i++){
				deleteCount++;
				pm.deletePersistent(arts.get(i));
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			pm.close();
		}
		System.out.println("Deleted " + deleteCount + " objects");
		//return true;
		
	}
}
