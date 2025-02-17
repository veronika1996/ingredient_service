package com.ingredients_service.cucumber.glue;

import com.ingredients_service.IngredientsServiceApplication;
import com.ingredients_service.config.GlobalExceptionHandler;
import com.ingredients_service.config.SecurityConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = {IngredientsServiceApplication.class, SecurityConfig.class, GlobalExceptionHandler.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class SpringIntegrationTest {

    @Test
    public void test() {
        System.out.println("Running Cucumber tests...");
    }
}
