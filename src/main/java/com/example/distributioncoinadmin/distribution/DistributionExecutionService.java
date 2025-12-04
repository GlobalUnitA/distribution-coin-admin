package com.example.distributioncoinadmin.distribution;

public interface DistributionExecutionService {
    void executeBatchAsync(Long batchId);

    BatchProgressDto getProgress(Long batchId);
}
