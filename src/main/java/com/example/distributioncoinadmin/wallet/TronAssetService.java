package com.example.distributioncoinadmin.wallet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TronAssetService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;

    public TronAssetService(@Value("${tronscan.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    private List<Map<String, Object>> fetchTokens(String wallet) {
        String url = "https://apilist.tronscanapi.com/api/account/tokens"
                + "?address=" + wallet
                + "&start=0&limit=50&hidden=0&show=3&sortType=0&sortBy=0";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("TRON-PRO-API-KEY", apiKey);  // ★ 여기 중요
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> resp =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            Map response = resp.getBody();
            if (response == null) {
                return Collections.emptyList();
            }

            Object data = response.get("data");
            if (!(data instanceof List<?> list)) {
                return Collections.emptyList();
            }

            //noinspection unchecked
            return (List<Map<String, Object>>) list;
        } catch (RestClientException e) {
            // 필요하면 log.warn/ error 추가
            return Collections.emptyList();
        }
    }

    public double getUsdtBalance(String wallet) {
        List<Map<String, Object>> tokens = fetchTokens(wallet);

        for (Map<String, Object> token : tokens) {
            String abbr = String.valueOf(token.get("tokenAbbr"));
            if (!"USDT".equalsIgnoreCase(abbr)) {
                continue;
            }

            Object amountObj = token.get("amount");
            if (amountObj == null) return 0.0;

            try {
                return Double.parseDouble(amountObj.toString());
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
}
