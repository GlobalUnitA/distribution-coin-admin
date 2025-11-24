package com.example.distributioncoinadmin.wallet;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TronAssetService {

    private final RestTemplate restTemplate = new RestTemplate();

    private List<Map<String, Object>> fetchTokens(String wallet) {
        String url = "https://apilist.tronscanapi.com/api/account/tokens"
                + "?address=" + wallet
                + "&start=0&limit=50&hidden=0&show=3&sortType=0&sortBy=0";

        Map response = restTemplate.getForObject(url, Map.class);
        if (response == null) {
            return Collections.emptyList();
        }

        Object data = response.get("data");   // ★ 여기!
        if (!(data instanceof List<?> list)) {
            return Collections.emptyList();
        }

        // unchecked 캐스팅
        //noinspection unchecked
        return (List<Map<String, Object>>) list;
    }

    // 전체 USD 자산
    public double getTotalAssets(String wallet) {
        List<Map<String, Object>> tokens = fetchTokens(wallet);

        double total = 0.0;
        for (Map<String, Object> token : tokens) {
            Object amountInUsdObj = token.get("amountInUsd");
            if (amountInUsdObj == null) continue;

            total += Double.parseDouble(amountInUsdObj.toString());
        }
        return total;
    }

    // USDT 잔액
    public double getUsdtBalance(String wallet) {
        List<Map<String, Object>> tokens = fetchTokens(wallet);

        for (Map<String, Object> token : tokens) {
            String abbr = String.valueOf(token.get("tokenAbbr"));
            if (!"USDT".equalsIgnoreCase(abbr)) {
                continue;
            }

            Object amountObj = token.get("amount");
            if (amountObj == null) return 0.0;

            return Double.parseDouble(amountObj.toString());
        }

        return 0.0;
    }
}
