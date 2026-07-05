package com.jeesung.dollarwatch.board;

import java.math.BigDecimal;
import java.util.Date;

public class Board {
	private BigDecimal b_no;
	private String b_title;
	private String b_content;
	private String b_writer;
	private Date b_reg_date;
	private String b_img;
	
	public Board() {

	}

	public Board(BigDecimal b_no, String b_title, String b_content, String b_writer, Date b_reg_date, String b_img) {
		super();
		this.b_no = b_no;
		this.b_title = b_title;
		this.b_content = b_content;
		this.b_writer = b_writer;
		this.b_reg_date = b_reg_date;
		this.b_img = b_img;
	}

	public BigDecimal getB_no() {
		return b_no;
	}

	public void setB_no(BigDecimal b_no) {
		this.b_no = b_no;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_content() {
		return b_content;
	}

	public void setB_content(String b_content) {
		this.b_content = b_content;
	}

	public String getB_writer() {
		return b_writer;
	}

	public void setB_writer(String b_writer) {
		this.b_writer = b_writer;
	}

	public Date getB_reg_date() {
		return b_reg_date;
	}

	public void setB_reg_date(Date b_reg_date) {
		this.b_reg_date = b_reg_date;
	}

	public String getB_img() {
		return b_img;
	}

	public void setB_img(String b_img) {
		this.b_img = b_img;
	}
	
	
	

	
	
	
	

}
