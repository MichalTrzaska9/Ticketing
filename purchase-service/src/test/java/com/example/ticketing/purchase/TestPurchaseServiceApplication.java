package com.example.ticketing.purchase;

import org.springframework.boot.SpringApplication;

public class TestPurchaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(PurchaseServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
