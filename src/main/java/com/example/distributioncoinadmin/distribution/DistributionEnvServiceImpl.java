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

    // 기본 네트워크 문구를 TRC20 기준으로 변경
    @Value("${distribution.network-name:TRC20 (TRON)}")
    private String networkName;

    // 없으면 5로 들어감
    @Value("${distribution.gas-fee:5}")
    private BigDecimal gasFee;

    @Override
    public String getNetworkName() {
        return networkName;
    }

    @Override
    public BigDecimal getAvailable() {
        // 이 부분은 기존에 /api/wallet/usdt-balance 에서 잘 돌아간 거랑 동일 로직
        double usdt = tronAssetService.getUsdtBalance(mainAddress);
        return BigDecimal.valueOf(usdt);
    }

    @Override
    public BigDecimal getGasFee() {
        return gasFee;
    }
}
