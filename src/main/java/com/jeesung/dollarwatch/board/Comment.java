package com.jeesung.dollarwatch.board;

import java.math.BigDecimal;
import java.util.Date;

public class Comment {
	private BigDecimal c_no;
	private BigDecimal c_b_no;
	private String c_writer;
	private String c_content;
	private Date c_date;

	public Comment() {

	}
	
	public Comment(BigDecimal c_no, BigDecimal c_b_no, String c_writer, String c_content, Date c_date) {
		super();
		this.c_no = c_no;
		this.c_b_no = c_b_no;
		this.c_writer = c_writer;
		this.c_content = c_content;
		this.c_date = c_date;
	}

	public BigDecimal getC_no() {
		return c_no;
	}

	public void setC_no(BigDecimal c_no) {
		this.c_no = c_no;
	}

	public BigDecimal getC_b_no() {
		return c_b_no;
	}

	public void setC_b_no(BigDecimal c_b_no) {
		this.c_b_no = c_b_no;
	}

	public String getC_writer() {
		return c_writer;
	}

	public void setC_writer(String c_writer) {
		this.c_writer = c_writer;
	}

	public String getC_content() {
		return c_content;
	}

	public void setC_content(String c_content) {
		this.c_content = c_content;
	}

	public Date getC_date() {
		return c_date;
	}

	public void setC_date(Date c_date) {
		this.c_date = c_date;
	}
	
	
	
	

}
