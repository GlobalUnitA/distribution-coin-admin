package com.example.distributioncoinadmin.distribution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/distribution")
@RequiredArgsConstructor
public class DistributionController {
    private final DistributionPreviewService previewService;
    private final DistributionEnvService envService;

    @GetMapping("/upload")
    public String showUploadForm(){
        return "distribution/uploadexcel";
    }

    @PostMapping("/preview")
    public String preview(
            @RequestParam("coinSymbol") String coinSymbol,
            @RequestParam("network") String network,
            @RequestParam("file")MultipartFile file,
            Model model
            ) throws IOException {

        //1) 엑셀 파싱
        List<DistributionPreviewRow> rows = previewService.parseExcel(file);
        if(rows == null){
            rows = List.of();
        }

        //2) 엑셀 출금수량 합계 계산
        BigDecimal totalAmount = rows.stream()
                .map(r -> r.getAmount() == null ? BigDecimal.ZERO : r.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("coinSymbol", coinSymbol);
        model.addAttribute("network", network);

        //3) 기존 서비스값을 Model에 넣기 (DTO 없이 개별 변수로 처리)
        String networkName = "N/A";
        BigDecimal available = BigDecimal.ZERO;
        BigDecimal gasFee = BigDecimal.ZERO;

        try {
            networkName = envService.getNetworkName();
            available   = envService.getAvailable();
            gasFee      = envService.getGasFee();
        } catch (Exception e) {
            log.error("분배 미리보기용 환경 정보 조회 중 에러 발생", e);
            // 일단 화면은 뜨게 기본값으로 둠
        }

        model.addAttribute("networkName", networkName);
        model.addAttribute("available", available);
        model.addAttribute("gasFee", gasFee);

        // 4) 미리보기 값
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("rows", rows);

        return "distribution/preview";
    }
}
