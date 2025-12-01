package com.example.distributioncoinadmin.distribution;

import java.math.BigDecimal;

public class DistributionPreviewRow {
    private int no;
    private String name;
    private String walletAddress;
    private BigDecimal amount;

    public int getNo(){
        return no;
    }

    public void setNo(int no){
        this.no = no;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getWalletAddress(){
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress){
        this.walletAddress = walletAddress;
    }


    public BigDecimal getAmount(){
        return amount;
    }

    public void setAmount(BigDecimal amount){
        this.amount = amount;
    }
}
