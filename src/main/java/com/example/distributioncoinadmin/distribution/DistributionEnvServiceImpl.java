package com.example.distributioncoinadmin.distribution;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DistributionEnvServiceImpl implements DistributionEnvService {
    private final com.example.distributioncoinadmin.wallet.TronAssetService tronAssetService;

    @Value("${tron.wallet.main-address}")
    private String mainAddress;

    @Value("${distribution.network-name:BEP20 (Binance Smart Chain)}")
    private String networkName;

    @Value("${distribution.gas-fee:5}")
    private BigDecimal gasFee;

    @Override
    public String getNetworkName() {
        return networkName;
    }

    @Override
    public BigDecimal getAvailable() {
        // TronScan에서 현재 USDT 보유량 조회
        double usdt = tronAssetService.getUsdtBalance(mainAddress);
        return BigDecimal.valueOf(usdt);
    }

    @Override
    public BigDecimal getGasFee() {
        return gasFee;
    }
}
