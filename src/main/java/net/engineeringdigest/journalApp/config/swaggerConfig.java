package net.engineeringdigest.journalApp.config;

import java.util.*;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.*;

@Configuration
public class swaggerConfig {

        @Bean
        public OpenAPI customConfig() {
                return new OpenAPI().info(
                                new Info().title("Journal App APIs")
                                                .description("By Ayan"))
                                .servers(List.of(new Server().url("http://localhost:8080/journal").description("Local"),
                                                new Server().url("http://localhost:8081/journal").description("Live")))
                                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                                .components(new Components().addSecuritySchemes(
                                                "bearerAuth", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")
                                                                .in(SecurityScheme.In.HEADER)
                                                                .name("Authorization")));
        }
}
