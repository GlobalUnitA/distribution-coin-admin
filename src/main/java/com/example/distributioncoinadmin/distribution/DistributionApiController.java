package com.example.distributioncoinadmin.distribution;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/distribution")
@RequiredArgsConstructor
public class DistributionApiController {
    private final DistributionExecutionService executionService;

    @GetMapping("/progress")
    public BatchProgressDto getProgress(@RequestParam("batchId") Long batchId) {
        return executionService.getProgress(batchId);
    }
}
