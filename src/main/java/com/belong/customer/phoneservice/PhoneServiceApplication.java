package com.belong.customer.phoneservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PhoneServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneServiceApplication.class, args);
	}


//	@Bean
//	public CommandLineRunner loadData(PhoneRepository phoneRepository) {
//		return args -> {
//			phoneRepository.save(new Phone(11221L, "03 6887 4556", "ACTIVE"));
//			phoneRepository.save(new Phone(11222L, "03 7887 4556", "ACTIVE"));
//			phoneRepository.save(new Phone(11223L, "03 8887 4556", "INACTIVE"));
//			phoneRepository.save(new Phone(11223L, "03 9887 4556", "ACTIVE"));
//
//		};
//	}
}
