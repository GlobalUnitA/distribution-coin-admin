package com.example.distributioncoinadmin.distribution;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserUsdtInfoRepository extends JpaRepository<UserUsdtInfo,Long> {
    Optional<UserUsdtInfo> findByWalletAddressAndCoinSymbolAndNetwork(
            String walletAddress,
            String coinSymbol,
            String network
    );
}
