package com.example.distributioncoinadmin.distribution;

import java.math.BigDecimal;

public interface DistributionEnvService {
    String getNetworkName(); //bep20등
    BigDecimal getAvailable(); //사용가능 수량
    BigDecimal getGasFee();
}
