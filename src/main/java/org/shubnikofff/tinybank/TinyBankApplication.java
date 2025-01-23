package org.shubnikofff.tinybank;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Tiny Bank API"))
public class TinyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyBankApplication.class, args);
	}

}
