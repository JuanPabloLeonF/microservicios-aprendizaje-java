package com.mcsv.qualificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class QualificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QualificationServiceApplication.class, args);
	}

}
