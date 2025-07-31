package core;

import java.util.ArrayList;
import java.util.Date;


public class Data {
	
	/*
	 * The class Data provides an object of type Data. The instance of Data stores the lists of values
	 * of the requested stock product and its meta data.
	 * 
	 */
	
	private ArrayList<Date> date;
	private ArrayList<Double> open;
	private ArrayList<Double> high;
	private ArrayList<Double> low;
	private ArrayList<Double> close;
	private ArrayList<Double> adjClose;
	
	private String name;
	private String currency;
	private String exchangeName;
	private Double marketPrice;
	private String  instrumentType;
    
	public Data (ArrayList<Date> date, 
    			ArrayList<Double> open, 
    			ArrayList<Double> high, 
    			ArrayList<Double> low, 
    			ArrayList<Double> close,
    			ArrayList<Double> adjClose,
    			String name, String currency, String exchangeName,  String instrumentType, Double marketPrice) {
    // VALUES	
    	this.date = date;
    	this.open = open;
    	this.high = high;
    	this.low = low;
    	this.close = close;
    	this.adjClose = adjClose;
    //META	
    	this.name = name;    	
    	this.currency = currency;
    	this.exchangeName = exchangeName;
    	this.marketPrice = marketPrice;
    	this.instrumentType =  instrumentType;
 
    }


	public ArrayList<Date> getDate() {
		return date;
	}


	public void setDate(ArrayList<Date> date) {
		this.date = date;
	}


	public ArrayList<Double> getOpen() {
		return open;
	}


	public void setOpen(ArrayList<Double> open) {
		this.open = open;
	}


	public ArrayList<Double> getHigh() {
		return high;
	}


	public void setHigh(ArrayList<Double> high) {
		this.high = high;
	}


	public ArrayList<Double> getLow() {
		return low;
	}


	public void setLow(ArrayList<Double> low) {
		this.low = low;
	}


	public ArrayList<Double> getClose() {
		return close;
	}


	public void setClose(ArrayList<Double> close) {
		this.close = close;
	}


	public ArrayList<Double> getAdjClose() {
		return adjClose;
	}


	public void setAdjClose(ArrayList<Double> adjClose) {
		this.adjClose = adjClose;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public String getExchangeName() {
		return exchangeName;
	}


	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}


	public Double getMarketPrice() {
		return marketPrice;
	}


	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}


	public String getInstrumentType() {
		return instrumentType;
	}


	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
	}
	
	

}