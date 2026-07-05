package com.jeesung.dollarwatch.exchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

@Service
public class ExchangeTrendService {

    private static final Set<String> SUPPORTED_CODES = new HashSet<String>(Arrays.asList(
        "USD", "KRW", "JPY", "EUR", "GBP", "CAD", "AUD"
    ));

    public ExchangeTrendResult getTrend(ExchangeSearch search) {
        ExchangeTrendResult result = new ExchangeTrendResult();
        String base = cleanCurrency(search == null ? null : search.getBase(), "USD");
        String target = cleanCurrency(search == null ? null : search.getTarget(), "KRW");

        result.setBase(base);
        result.setTarget(target);

        try {
            if (!isSupported(base) || !isSupported(target)) {
                return fail(result, "지원하지 않는 통화입니다.");
            }

            if (base.equals(target)) {
                return fail(result, "기준 통화와 대상 통화는 서로 달라야 합니다.");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String to = sdf.format(cal.getTime());
            cal.add(Calendar.DATE, -7);
            String from = sdf.format(cal.getTime());

            String apiUrl =
                "https://api.frankfurter.dev/v2/rates?from=" + from
                + "&to=" + to
                + "&base=" + URLEncoder.encode(base, "UTF-8")
                + "&quotes=" + URLEncoder.encode(target, "UTF-8");

            HttpsURLConnection huc = (HttpsURLConnection) new URL(apiUrl).openConnection();
            huc.setRequestMethod("GET");
            huc.setConnectTimeout(5000);
            huc.setReadTimeout(5000);
            huc.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(
                new InputStreamReader(huc.getInputStream(), "UTF-8")
            );

            String line;
            StringBuffer sb = new StringBuffer();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();

            List<ExchangeTrend> trends = parseV2Rows(sb.toString(), target);

            if (trends.size() == 0) {
                return fail(result, target + " 환율 데이터가 없습니다.");
            }

            result.setResult("success");
            result.setMessage("최근 7일 환율 추이 조회 성공");
            result.setTrends(trends);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return fail(result, "환율 추이 API 호출 실패");
        }
    }

    private List<ExchangeTrend> parseV2Rows(String json, String target) throws Exception {
        ObjectMapper om = new ObjectMapper();
        List<Map<String, Object>> apiList = om.readValue(
            json,
            new TypeReference<List<Map<String, Object>>>() {}
        );

        List<ExchangeTrend> trends = new ArrayList<ExchangeTrend>();

        if (apiList == null) {
            return trends;
        }

        for (Map<String, Object> item : apiList) {
            String quote = String.valueOf(item.get("quote"));

            if (!target.equalsIgnoreCase(quote)) {
                continue;
            }

            Object rateObj = item.get("rate");

            if (rateObj == null) {
                continue;
            }

            BigDecimal bd = new BigDecimal(String.valueOf(rateObj));
            ExchangeTrend et = new ExchangeTrend();
            et.setDate(String.valueOf(item.get("date")));
            et.setRate(formatRate(bd));
            trends.add(et);
        }

        return trends;
    }

    private ExchangeTrendResult fail(ExchangeTrendResult result, String message) {
        result.setResult("fail");
        result.setMessage(message);
        result.setTrends(new ArrayList<ExchangeTrend>());
        return result;
    }

    private boolean isSupported(String code) {
        return code != null && SUPPORTED_CODES.contains(code);
    }

    private String cleanCurrency(String value, String defaultValue) {
        if (value == null || value.trim().equals("")) {
            return defaultValue;
        }

        return value.trim().toUpperCase();
    }

    private String formatRate(BigDecimal rate) {
        if (rate.compareTo(new BigDecimal("10")) >= 0) {
            return rate.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        }

        return rate.setScale(4, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
    }
}
