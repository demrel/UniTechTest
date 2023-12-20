package com.UniTech.UniTechTest.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .packagesToScan("com.UniTech.UniTechTest.controller")
                        .group("Api")
                        .build();
        }

        @Bean
        public OpenAPI springShopOpenAPI() {
                return new OpenAPI()
                        .components(new Components()
                                .addSecuritySchemes("bearer-token", new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                        .info(new Info().title("UnitTech API")
                                .description("UnitTech API")
                                .version("1.0")
                                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                        .addSecurityItem(new SecurityRequirement().addList("bearer-token"));
        }
}
