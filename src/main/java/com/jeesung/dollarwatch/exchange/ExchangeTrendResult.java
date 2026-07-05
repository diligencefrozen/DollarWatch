package com.jeesung.dollarwatch.exchange;

import java.util.List;

public class ExchangeTrendResult {
	
	
	
	private String base;
	private String target;
	private String result;
	private String message;
	private List<ExchangeTrend> trends;
	
	public ExchangeTrendResult() {

	}
		
	public ExchangeTrendResult(String base, String target, String result, String message, List<ExchangeTrend> trends) {
		super();
		this.base = base;
		this.target = target;
		this.result = result;
		this.message = message;
		this.trends = trends;
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ExchangeTrend> getTrends() {
		return trends;
	}

	public void setTrends(List<ExchangeTrend> trends) {
		this.trends = trends;
	}
	
	
	
	

}
