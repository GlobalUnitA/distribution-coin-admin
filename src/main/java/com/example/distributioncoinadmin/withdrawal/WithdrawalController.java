package com.example.distributioncoinadmin.withdrawal;

import com.example.distributioncoinadmin.wallet.TronAssetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/withdrawal")
public class WithdrawalController {

    private final TronAssetService tronAssetService;
    private final String mainAddress;
    private final double defaultNetworkFee;

    public WithdrawalController(
            TronAssetService tronAssetService,
            @Value("${tron.wallet.main-address}") String mainAddress,
            @Value("${withdrawal.usdt-network-fee:10}") double defaultNetworkFee // 없으면 10
    ) {
        this.tronAssetService = tronAssetService;
        this.mainAddress = mainAddress;
        this.defaultNetworkFee = defaultNetworkFee;
    }

    // 출금 코인 선택 화면
    @GetMapping({"", "/"})
    public String showWithdrawSelect(Model model) {

        // 실제 지갑 USDT 사용가능 수량
        double availableUsdt = tronAssetService.getUsdtBalance(mainAddress);

        model.addAttribute("availableAmount", availableUsdt);
        model.addAttribute("networkFee", defaultNetworkFee);

        // -> templates/withdrawal/select.html
        return "withdrawal/select";
    }
}
