package com.example.distributioncoinadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DistributionCoinAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributionCoinAdminApplication.class, args);
    }

}
