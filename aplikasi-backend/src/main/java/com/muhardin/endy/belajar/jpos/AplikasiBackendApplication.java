package com.muhardin.endy.belajar.jpos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(
        basePackageClasses = {AplikasiBackendApplication.class, Jsr310JpaConverters.class}
)
public class AplikasiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AplikasiBackendApplication.class, args);
	}
}
