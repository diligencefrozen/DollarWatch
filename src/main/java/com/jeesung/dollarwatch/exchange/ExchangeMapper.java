package com.jeesung.dollarwatch.exchange;

import java.util.List;

public interface ExchangeMapper {
    public abstract int saveSearchLog(ExchangeRate er);
    public abstract List<ExchangeSearchLog> getRecentSearchLogs(String memberId);
}
