package com.example.distributioncoinadmin.distribution;

public class BatchProgressDto {
    private final int progress;
    private final BatchStatus status;

    public BatchProgressDto(int progress, BatchStatus status) {
        this.progress = progress;
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public BatchStatus getStatus() {
        return status;
    }
}
