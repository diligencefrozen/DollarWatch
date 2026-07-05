package com.jeesung.dollarwatch.exchange;

public class ExchangeRate {
	private String base;
	private String target;
	private String rate;
	private String result;
	private String message;
	private String memberId;

	public ExchangeRate() {

	}
	
	public ExchangeRate(String base, String target, String rate, String result, String message, String memberId) {
		super();
		this.base = base;
		this.target = target;
		this.rate = rate;
		this.result = result;
		this.message = message;
		this.memberId = memberId;
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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	
	

}
