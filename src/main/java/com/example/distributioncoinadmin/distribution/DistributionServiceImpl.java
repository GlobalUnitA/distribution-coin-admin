package com.example.distributioncoinadmin.distribution;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class DistributionServiceImpl implements DistributionService {

    @Override
    public Long uploadExcel(MultipartFile file, String coinSymbol, String network) throws IOException{
        //TODO : 여기에서 엑셀 파싱 + 분배 배치/아이템 저장 로직 구현

        log.info("엑셀 업로드 요청 - coinSymbol={}, network={}, originalFilename={}",
                coinSymbol, network, file.getOriginalFilename());


        //임시 더미 ID 리턴
        return 1L;
    }
}
