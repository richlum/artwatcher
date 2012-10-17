package com.google.gwt.artvan.client;

import java.io.Serializable;



public class StockPrice  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String symbol;
	private double price;
	private double change;
	// required no arg constructor for gwt rpc compile
	//   uninitialized private members ok?
	public StockPrice() {

	}
	
	
	public StockPrice(String string, double price2, double change2) {
		this.symbol = string;
		this.price = price2;
		this.change = change2;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getChangePercent() {
		return 100.0*change;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	
	
}
