package com.example.distributioncoinadmin.distribution;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "usdt_distribution_batch")
public class UsdtDistributionBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "coin_symbol", nullable = false, length = 20)
    private String coinSymbol;

    @Column(name = "network", nullable = false, length = 50)
    private String network;

    @Column(name = "total_amount", nullable = false, precision = 36, scale = 18)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private BatchStatus status = BatchStatus.READY;

    @Column(name = "progress",  nullable = false)
    private int progress = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    //gettter/setter
    public Long getId() {
        return id;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BatchStatus getStatus() {
        return status;
    }

    public void setStatus(BatchStatus status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
