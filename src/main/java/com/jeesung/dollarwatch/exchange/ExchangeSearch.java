package com.jeesung.dollarwatch.exchange;

public class ExchangeSearch {
	
	private String base;
	private String target;
	
	public ExchangeSearch() {

	}
	
	public ExchangeSearch(String base, String target) {
		super();
		this.base = base;
		this.target = target;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	
	
	

}
