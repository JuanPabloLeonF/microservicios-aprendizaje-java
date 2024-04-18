package com.mscv.inventoryservice;

import com.mscv.inventoryservice.entity.Inventory;
import com.mscv.inventoryservice.repository.IInventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(IInventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setCodeSku("iphone_13");
			inventory.setQuantity(100);
			inventoryRepository.save(inventory);

			Inventory inventory2 = new Inventory();
			inventory2.setCodeSku("iphone_13_red");
			inventory2.setQuantity(0);
			inventoryRepository.save(inventory2);
		};
	}
}
