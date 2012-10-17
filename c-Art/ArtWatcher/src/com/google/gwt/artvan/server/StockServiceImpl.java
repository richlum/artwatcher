package com.google.gwt.artvan.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.artvan.client.NotLoggedInException;
import com.google.gwt.artvan.client.StockService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class StockServiceImpl extends RemoteServiceServlet implements
StockService {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  public void addStock(String symbol) throws NotLoggedInException {
    checkLoggedIn();
    ArtCollection ac = ArtCollection.getInstance();
    ac.addStock(symbol, getUser());
  }

  public void removeStock(String symbol) throws NotLoggedInException {
    checkLoggedIn();
    ArtCollection ac = ArtCollection.getInstance();
    ac.removeStock(symbol,getUser());
    
  }

  public String[] getStocks() throws NotLoggedInException {
    checkLoggedIn();
    ArtCollection ac = ArtCollection.getInstance();
    
    return (ac.getStocks(getUser()));
  }

  private void checkLoggedIn() throws NotLoggedInException {
    if (getUser() == null) {
      throw new NotLoggedInException("Not logged in.");
    }
  }

  private User getUser() {
    UserService userService = UserServiceFactory.getUserService();
    return userService.getCurrentUser();
  }


}