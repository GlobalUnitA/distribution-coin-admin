package com.example.distributioncoinadmin.distribution;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DistributionService {
    Long uploadExcel(MultipartFile file, String coinSymbol, String network) throws IOException;
}
