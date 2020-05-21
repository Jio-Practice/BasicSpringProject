package com.MainApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.DbUtils.CustomerRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = CustomerRepository.class)
@EntityScan("com.CustomerInfo")
@ComponentScan(basePackages = { "com.BasicCrudOps", "com.ValidatorInterfaces", "com.Codes", "com.DbUtils",
		"com.InvalidExceptions" })
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}