package com.jeesung.dollarwatch.exchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService {

    private static final Set<String> SUPPORTED_CODES = new HashSet<String>(Arrays.asList(
        "USD", "KRW", "JPY", "EUR", "GBP", "CAD", "AUD"
    ));

    public ExchangeRate getRate(ExchangeSearch search) {
        ExchangeRate er = new ExchangeRate();
        String base = cleanCurrency(search == null ? null : search.getBase(), "USD");
        String target = cleanCurrency(search == null ? null : search.getTarget(), "KRW");

        er.setBase(base);
        er.setTarget(target);

        try {
            if (!isSupported(base) || !isSupported(target)) {
                return fail(er, "지원하지 않는 통화입니다.");
            }

            if (base.equals(target)) {
                er.setRate("1.00");
                er.setResult("success");
                er.setMessage("같은 통화입니다.");
                return er;
            }

            String apiUrl = "https://open.er-api.com/v6/latest/" + base;

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

            ObjectMapper om = new ObjectMapper();
            Map resultMap = om.readValue(sb.toString(), Map.class);
            String result = String.valueOf(resultMap.get("result"));

            if (!"success".equals(result)) {
                return fail(er, "API 응답 실패");
            }

            Map rates = (Map) resultMap.get("rates");

            if (rates == null || rates.get(target) == null) {
                return fail(er, "지원하지 않는 통화입니다.");
            }

            BigDecimal bd = new BigDecimal(String.valueOf(rates.get(target)));
            er.setRate(formatRate(bd));
            er.setResult("success");
            er.setMessage("조회 성공");
            return er;

        } catch (Exception e) {
            e.printStackTrace();
            return fail(er, "환율 API 호출 실패");
        }
    }

    private ExchangeRate fail(ExchangeRate er, String message) {
        er.setRate("0");
        er.setResult("fail");
        er.setMessage(message);
        return er;
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
