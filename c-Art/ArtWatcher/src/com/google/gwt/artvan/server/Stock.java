package com.google.gwt.artvan.server;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Stock {

	  @PrimaryKey
	  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	  private Long id;
	  @Persistent
	  private User user;
	  @Persistent
	  private String symbol;
	  @Persistent
	  private Date createDate;
	  @Persistent
	  private double dbl = 99.9;

	  public Stock() {
	    this.createDate = new Date();
	    dbl = 99.8;
	  }

	  public Stock(User user, String symbol) {
	    this();
	    this.user = user;
	    this.symbol = symbol;
	    dbl += 0.1;
	  }

	  public Long getId() {
	    return this.id;
	  }

	  public User getUser() {
	    return this.user;
	  }

	  public String getSymbol() {
	    return this.symbol;
	  }

	  public Date getCreateDate() {
	    return this.createDate;
	  }

	  public void setUser(User user) {
	    this.user = user;
	  }

	  public void setSymbol(String symbol) {
	    this.symbol = symbol;
	  }

	public double getTestvalue() {
		// TODO Auto-generated method stub
		return dbl;
	}

	public void setDbl(double d) {
		// TODO Auto-generated method stub
		dbl=d;
	}
	}
