import com.example.distributioncoinadmin.wallet.TronAssetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final TronAssetService tronAssetService;
    private final String mainAddress;

    public WalletController(TronAssetService tronAssetService,
                            @Value("${tron.wallet.main-address}") String mainAddress) {
        this.tronAssetService = tronAssetService;
        this.mainAddress = mainAddress;
    }

    @GetMapping("/usdt-balance")
    public Map<String, Object> getUsdtBalance() {
        double usdt = tronAssetService.getUsdtBalance(mainAddress);
        return Map.of(
                "usdtAvailable", usdt
        );
    }
}
