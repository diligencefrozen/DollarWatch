package com.jeesung.dollarwatch.exchange;

import java.util.Date;

public class ExchangeSearchLog {
    private String base;
    private String target;
    private String rate;
    private String memberId;
    private Date searchedAt;

    public ExchangeSearchLog() {

    }

    public ExchangeSearchLog(String base, String target, String rate, String memberId, Date searchedAt) {
        super();
        this.base = base;
        this.target = target;
        this.rate = rate;
        this.memberId = memberId;
        this.searchedAt = searchedAt;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Date getSearchedAt() {
        return searchedAt;
    }

    public void setSearchedAt(Date searchedAt) {
        this.searchedAt = searchedAt;
    }
}
