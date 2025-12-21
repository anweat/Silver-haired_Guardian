package com.yinling.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HealthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthServiceApplication.class, args);
    }
}
