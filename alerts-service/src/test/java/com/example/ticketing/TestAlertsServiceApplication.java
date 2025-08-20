package com.example.ticketing;

import org.springframework.boot.SpringApplication;

public class TestAlertsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(AlertsServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
