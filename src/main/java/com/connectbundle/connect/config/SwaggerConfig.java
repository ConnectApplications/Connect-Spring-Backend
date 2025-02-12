package com.connectbundle.connect.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Connect Platform Backend API", version = "1.0", description = "Connect Platform Backend API Documentation"))

public class SwaggerConfig {

}
