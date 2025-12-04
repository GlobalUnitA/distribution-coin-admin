package com.example.distributioncoinadmin.distribution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExcelApplyServiceImpl implements ExcelApplyService {
    private final UserUsdtInfoRepository userUsdtInfoRepository;

    @Override
    @Transactional
    public void applyRow(UsdtDistributionItem item) {
        UsdtDistributionBatch batch = item.getBatch();

        String walletAddress = item.getWalletAddress();
        String coinSymbol    = batch.getCoinSymbol();
        String network       = batch.getNetwork();

        // wallet_address + coin_symbol + network 기준으로 조회
        UserUsdtInfo info = userUsdtInfoRepository.findByWalletAddressAndCoinSymbolAndNetwork(walletAddress, coinSymbol, network).orElseGet(UserUsdtInfo::new);

        // 신규/기존 공통 세팅
        info.setWalletAddress(walletAddress);
        info.setCoinSymbol(coinSymbol);
        info.setNetwork(network);
        info.setUserName(item.getName());

        // 엑셀에 적힌 수량을 그대로 반영 (덮어쓰기)
        // 누적 방식으로 바꾸려면: info.setAmount(
        //      Optional.ofNullable(info.getAmount()).orElse(BigDecimal.ZERO).add(item.getAmount())
        // );
        info.setAmount(item.getAmount());
        userUsdtInfoRepository.save(info);
    }
}
