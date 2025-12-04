package com.example.distributioncoinadmin.distribution;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsdtDistributionItemRepository extends JpaRepository<UsdtDistributionItem,Long> {
    List<UsdtDistributionItem> findByBatchId(Long batchId);
}
