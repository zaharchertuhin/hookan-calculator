package com.example.hookah;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Hookah Calculator API", version = "1.0", description = "API для калькулятора табаков"))

public class HookahCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(HookahCalculatorApplication.class, args);
    }
}