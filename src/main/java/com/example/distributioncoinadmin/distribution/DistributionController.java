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

        //2) 엑셀 출금수량 합계 계산
        BigDecimal totalAmount = rows.stream().map(DistributionPreviewRow::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        //3) 기존 서비스값을 Model에 넣기 (DTO 없이 개별 변수로 처리)
        model.addAttribute("coinSymbol", coinSymbol);
        model.addAttribute("network", network);

        model.addAttribute("networkName", envService.getNetworkName());
        model.addAttribute("available", envService.getAvailable());
        model.addAttribute("gasFee", envService.getGasFee());

        //4) 미리 보기 전용 값
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("rows", rows);

        return "distribution/preview";
    }
}
