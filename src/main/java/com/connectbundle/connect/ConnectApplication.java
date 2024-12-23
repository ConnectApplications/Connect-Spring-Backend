package com.connectbundle.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.connectbundle.connect.repository")
public class ConnectApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConnectApplication.class, args);
	}

}
