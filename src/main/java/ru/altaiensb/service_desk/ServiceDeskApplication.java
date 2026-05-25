package ru.altaiensb.service_desk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ServiceDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDeskApplication.class, args);
	}

}
