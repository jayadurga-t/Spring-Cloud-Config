package com.durga.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice REST API Documentation",
				description = "JustIn Bank Accounts Microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "JustIn Bank Accounts - Development Team",
						email = "acc.dev@justin.com",
						url = "http://localhost:8080/swagger-ui/index.html#/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://localhost:8080/swagger-ui/index.html#/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "External Documentation for JustIn Bank Microservices REST API Documentation",
				url = "http://localhost:8090/swagger-ui/index.html#/"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
