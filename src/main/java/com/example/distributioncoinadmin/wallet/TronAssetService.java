package com.example.distributioncoinadmin.wallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TronAssetService {

    private static final Logger log = LoggerFactory.getLogger(TronAssetService.class);

    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey;

    public TronAssetService(@Value("${tronscan.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    // TronScan 지갑 토큰 목록 조회 (TRX + TRC10 + TRC20)
    private List<Map<String, Object>> fetchWalletTokens(String wallet) {
        // asset_type=1 → 자산(TRX, TRC10, TRC20)만
        String url = "https://apilist.tronscanapi.com/api/account/wallet"
                + "?address=" + wallet
                + "&asset_type=1";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("TRON-PRO-API-KEY", apiKey);
            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> resp =
                    restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            Map body = resp.getBody();
            if (body == null) {
                log.warn("TronScan wallet 응답이 null 입니다. url={}", url);
                return Collections.emptyList();
            }

            Object data = body.get("data");
            if (!(data instanceof List<?> list)) {
                log.warn("TronScan wallet 응답 구조가 예상과 다릅니다. data={}", data);
                return Collections.emptyList();
            }

            //noinspection unchecked
            return (List<Map<String, Object>>) list;

        } catch (RestClientException e) {
            log.error("TronScan wallet 호출 실패. url=" + url, e);
            return Collections.emptyList();
        }
    }

    // USDT 잔액 (지갑 화면의 "사용가능 수량")
    public double getUsdtBalance(String wallet) {
        List<Map<String, Object>> tokens = fetchWalletTokens(wallet);

        for (Map<String, Object> token : tokens) {
            String abbr = String.valueOf(token.get("token_abbr"));
            if (!"USDT".equalsIgnoreCase(abbr)) {
                continue;
            }

            String balanceStr = String.valueOf(token.get("balance")); // 예: "702.263524" 또는 "702263524"
            Object decObj = token.get("token_decimal");
            int decimals = 0;
            if (decObj != null) {
                try {
                    decimals = Integer.parseInt(decObj.toString());
                } catch (NumberFormatException ignored) { }
            }

            try {
                BigDecimal raw = new BigDecimal(balanceStr);

                // balance가 이미 소수(702.263524) 형태라면 그대로 사용
                if (balanceStr.contains(".")) {
                    return raw.setScale(6, RoundingMode.DOWN).doubleValue();
                }

                // balance가 정수(702263524) 형태라면 10^decimals 로 나눔
                if (decimals > 0) {
                    BigDecimal divisor = BigDecimal.TEN.pow(decimals);
                    BigDecimal value = raw.divide(divisor, 6, RoundingMode.DOWN);
                    return value.doubleValue();
                }

                // 소수점도 없고 decimals 도 0이면 그대로 반환
                return raw.doubleValue();

            } catch (NumberFormatException e) {
                return 0.0;
            }
        }

        return 0.0;
    }


    // 필요하면 총 자산 USD도 같이 쓸 수 있게 남겨둠
    public double getTotalAssetsUsd(String wallet) {
        List<Map<String, Object>> tokens = fetchWalletTokens(wallet);

        BigDecimal totalUsd = BigDecimal.ZERO;
        for (Map<String, Object> token : tokens) {
            Object usdObj = token.get("token_value_in_usd");  // 문자열일 가능성 높음
            if (usdObj == null) continue;

            try {
                totalUsd = totalUsd.add(new BigDecimal(usdObj.toString()));
            } catch (NumberFormatException e) {
                log.warn("token_value_in_usd 파싱 실패. value={}", usdObj);
            }
        }
        return totalUsd.doubleValue();
    }
}
