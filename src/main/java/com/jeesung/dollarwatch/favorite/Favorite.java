package com.jeesung.dollarwatch.favorite;

import java.math.BigDecimal;
import java.util.Date;

public class Favorite {
	private BigDecimal f_no;
	private String f_member_id;
	private String f_base;
	private String f_target;
	private String f_memo;
	private Date f_reg_date;
	private BigDecimal currentRate;
	private String rateResult;

	public Favorite() {

	}

	public Favorite(BigDecimal f_no, String f_member_id, String f_base, String f_target, String f_memo, Date f_reg_date,
			BigDecimal currentRate, String rateResult) {
		super();
		this.f_no = f_no;
		this.f_member_id = f_member_id;
		this.f_base = f_base;
		this.f_target = f_target;
		this.f_memo = f_memo;
		this.f_reg_date = f_reg_date;
		this.currentRate = currentRate;
		this.rateResult = rateResult;
	}

	public BigDecimal getF_no() {
		return f_no;
	}

	public void setF_no(BigDecimal f_no) {
		this.f_no = f_no;
	}

	public String getF_member_id() {
		return f_member_id;
	}

	public void setF_member_id(String f_member_id) {
		this.f_member_id = f_member_id;
	}

	public String getF_base() {
		return f_base;
	}

	public void setF_base(String f_base) {
		this.f_base = f_base;
	}

	public String getF_target() {
		return f_target;
	}

	public void setF_target(String f_target) {
		this.f_target = f_target;
	}

	public String getF_memo() {
		return f_memo;
	}

	public void setF_memo(String f_memo) {
		this.f_memo = f_memo;
	}

	public Date getF_reg_date() {
		return f_reg_date;
	}

	public void setF_reg_date(Date f_reg_date) {
		this.f_reg_date = f_reg_date;
	}

	public BigDecimal getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(BigDecimal currentRate) {
		this.currentRate = currentRate;
	}

	public String getRateResult() {
		return rateResult;
	}

	public void setRateResult(String rateResult) {
		this.rateResult = rateResult;
	}
	
	

	
	
	
	

}
