package com.raf.cloudproviderbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class CloudProviderBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudProviderBackendApplication.class, args);
    }

}
