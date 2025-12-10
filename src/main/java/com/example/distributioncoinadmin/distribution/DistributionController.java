package com.example.distributioncoinadmin.distribution;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private final UsdtDistributionBatchRepository batchRepository;
    private final UsdtDistributionItemRepository itemRepository;
    private final DistributionExecutionService executionService;

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

        // 1) 엑셀 파싱
        List<DistributionPreviewRow> rows = previewService.parseExcel(file);
        if(rows == null){
            rows = List.of();
        }

        // 2) 엑셀 출금수량 합계 계산
        BigDecimal totalAmount = rows.stream()
                .map(r -> r.getAmount() == null ? BigDecimal.ZERO : r.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3) 배치 저장 (로그용)
        UsdtDistributionBatch batch = new UsdtDistributionBatch();
        batch.setCoinSymbol(coinSymbol);
        batch.setNetwork(network);
        batch.setTotalAmount(totalAmount);
        batch.setStatus(BatchStatus.READY);
        batch.setProgress(0);
        batchRepository.save(batch);

        // 4) 아이템 로그 저장 (PENDING)
        for(DistributionPreviewRow row : rows){
            UsdtDistributionItem item = new UsdtDistributionItem();

            item.setBatch(batch);
            item.setName(row.getName());
            item.setWalletAddress(row.getWalletAddress());
            item.setAmount(row.getAmount());
            item.setStatus(ItemStatus.PENDING);
            itemRepository.save(item);
        }

        // 5) 기존 환경 정보
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


        // 6) Model에 값 세팅
        model.addAttribute("coinSymbol", coinSymbol);
        model.addAttribute("network", network);
        model.addAttribute("networkName", networkName);
        model.addAttribute("available", available);
        model.addAttribute("gasFee", gasFee);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("rows", rows);

        //분배 실행용 batchId 내려줌
        model.addAttribute("batchId", batch.getId());

        return "distribution/preview";
    }

    @PostMapping("/execute")
    @ResponseBody
    public String execute(@RequestParam("batchId") Long batchId){
        log.info("분배 실행 버튼 클릭, batchId={}", batchId);

        //비동기 분배 실행 시작
        executionService.executeBatchAsync(batchId);

        //TODO : 지금은 그냥 upload 페이지로 쏴주지만 필요할 경우 경로 변경.
        return "OK";
    }
}
