package com.connectbundle.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ConnectApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConnectApplication.class, args);
	}

	@RequestMapping("/tester")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello adsfd %s!", name);
	}


}
