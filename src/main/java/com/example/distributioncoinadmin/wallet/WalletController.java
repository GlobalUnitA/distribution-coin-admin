package com.example.distributioncoinadmin.wallet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final TronAssetService  tronAssetService;

    public WalletController(TronAssetService tronAssetService) {
        this.tronAssetService = tronAssetService;
    }

    @GetMapping("/net-assets")
    public Map<String, Object> getNetAssets(@RequestParam String address) {
        double total = tronAssetService.getTotalAssets(address);
        return Map.of(
                "address", address,
                "netAddetsUsd", total
        );
    }

    @GetMapping("/usdt-balance")
    public Map<String, Object> getUsdtBalance(@RequestParam String address) {
        double usdt = tronAssetService.getUsdtBalance(address);
        return Map.of(
                "address", address,
                "usdtAvailable", usdt
        );
    }

}
