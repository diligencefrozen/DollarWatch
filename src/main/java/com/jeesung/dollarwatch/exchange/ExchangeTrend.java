package com.jeesung.dollarwatch.exchange;

public class ExchangeTrend {
	
	
	
	private String date;
	private String rate;
	
	public ExchangeTrend() {

	}
	
	public ExchangeTrend(String date, String rate) {
		super();
		this.date = date;
		this.rate = rate;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
	
	
	

}
