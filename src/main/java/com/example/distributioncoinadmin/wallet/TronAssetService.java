package com.example.distributioncoinadmin.wallet;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class TronAssetService {
    private final RestTemplate restTemplate = new RestTemplate();

    //1) 전체 자산 계산
    public double getTotalAssets(String wallet){
        String url = "https://apilist.tronscanapi.com/api/account/tokens?address=" + wallet;

        Map response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> tokens = (List<Map<String, Object>>) response.get("tokens");

        double total = 0.0;
        for (Map<String, Object> token : tokens) {
            double amount = Double.parseDouble(token.get("amount").toString());
            double price = Double.parseDouble(token.get("tokenPriceInUsd").toString());
            total += amount * price;
        }

        return total;
    }

    //2) USDT 잔액만 반환
    public double getUsdtBalance(String wallet){
        String url = "https://apilist.tronscanapi.com/api/account/tokens?address=" + wallet;

        Map response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> tokens = (List<Map<String, Object>>) response.get("tokens");

        for (Map<String, Object> token : tokens) {
            String name = token.get("tokenName").toString();
            if (name.equalsIgnoreCase("Tether USD") || name.equalsIgnoreCase("USDT")) {
                return Double.parseDouble(token.get("amount").toString());
            }
        }
        return 0;
    }
}
