package com.compass.uol.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class SwaggerConfig {

    @Bean
     OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("workshop-springboot3-jpa")
                .description("Criar uma aplicação web utilizando Spring Boot com JPA/Hibernate para implementação de serviços REST")
                .version("1.0")
                .contact(new Contact()
                    .name("Bruno Pressi")
                    .url("https://github.com/BrunoPressi")
                    .email("BrunoPressi2012@gmail.com")
                )
            )
            .addSecurityItem(new SecurityRequirement().addList("JWT"))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes("JWT", new SecurityScheme()
                    .type(SecurityScheme.Type.APIKEY)
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization")
                )
            );
    }
    
}
