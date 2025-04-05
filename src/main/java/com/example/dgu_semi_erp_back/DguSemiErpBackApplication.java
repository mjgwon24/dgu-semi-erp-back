package com.example.dgu_semi_erp_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DguSemiErpBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DguSemiErpBackApplication.class, args);
	}

}
