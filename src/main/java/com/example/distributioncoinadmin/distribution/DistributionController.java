package com.example.distributioncoinadmin.distribution;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/distribution")
@RequiredArgsConstructor
public class DistributionController {
    private final DistributionService distributionService;


    //분배하기 첫 화면
    @GetMapping("/upload")
    public String showDistributeForm(Model model) {
        model.addAttribute("coinList",java.util.List.of("USDT"));
        model.addAttribute("networkList",java.util.List.of("TRC20"));

        return "distribution/uploadexcel"; //템플릿
    }

    //엑셀 업로드 처리
    @PostMapping("/upload")
    public String uploadExcel(
            @RequestParam("coinSymbol") String coinSymbol,
            @RequestParam("network") String network,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes
    ) throws IOException {
        //엑셀 파싱 및 배치/아이템 저장 후 batchId 리턴
        Long batchId = distributionService.uploadExcel(file, coinSymbol, network);

        //메시지나 상태값 필요하면 flash attribute로 전달
        redirectAttributes.addFlashAttribute("message", "엑셀 업로드가 완료되었습니다.");

        //미리보기 화면으로 이동
        return "redirect:/distribution/preview/" + batchId;
    }

    //파일 형식 다운로드
    @GetMapping("/template")
    public String downloadTemplate(){
        // 나중에 실제 파일 다운로드로 변경 예정
        // 우선은 도움말 페이지나 404가 아니라는 정도만 확보
        return "distribution/template"; // 필요 없으면 나중에 ResponseEntity<Resource> 로 교체
    }
}
