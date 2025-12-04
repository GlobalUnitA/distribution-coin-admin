package com.example.distributioncoinadmin.distribution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributionExecutionServiceImpl implements DistributionExecutionService {
    private final UsdtDistributionBatchRepository batchRepository;
    private final UsdtDistributionItemRepository itemRepository;
    private final ExcelApplyService excelApplyService;

    @Override
    @Async
    @Transactional
    public void executeBatchAsync(Long batchId){
        UsdtDistributionBatch batch = batchRepository.findById(batchId).orElseThrow(() -> new IllegalArgumentException("batch not found" + batchId));

        batch.setStatus(BatchStatus.PROCESSING);
        batch.setProgress(0);
        batchRepository.save(batch);

        List<UsdtDistributionItem> items = itemRepository.findByBatchId(batchId);
        int total = items.size();
        int processed = 0;

        for(UsdtDistributionItem item : items){
            try {
                //user_usdt_info에 insert or update
                excelApplyService.applyRow(item);

                item.setStatus(ItemStatus.SUCCESS);
                item.setErrorMessage(null);
            } catch(Exception e){
                log.error("distribution apply failed. itemId={}",item.getId(),e);
                item.setStatus(ItemStatus.FAILED);
                item.setErrorMessage(e.getMessage());
            }

            itemRepository.save(item);

            processed++;
            int progress = (int) Math.round(processed * 100.0 / total);
            batch.setProgress(progress);
            batchRepository.save(batch);
        }

        batch.setStatus(BatchStatus.DONE);
        batch.setProgress(100);
        batchRepository.save(batch);
    }

    @Override
    @Transactional(readOnly = true)
    public BatchProgressDto getProgress(Long batchId) {
        UsdtDistributionBatch batch = batchRepository.findById(batchId).orElseThrow(() -> new IllegalArgumentException("batch not found" + batchId));

        return new BatchProgressDto(batch.getProgress(), batch.getStatus());
    }
}
