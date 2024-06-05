package com.riwi.pruebaDesempenoSpringBoot.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Api for course management in Riwi", version = "1.0", description = "ApiRestFull documentation for managing courses with your students, lessons, and multimedia content"))
public class OpenApiConfig {

}