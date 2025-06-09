package com.paymentreceiptservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PaymentReceiptServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentReceiptServiceApplication.class, args);
    }
}
