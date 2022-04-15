package com.art1.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger 2 Configuration
 */
@Configuration
@Profile({ "local", "develop", "production" })
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("art1").select()
                .apis(RequestHandlerSelectors.basePackage("com.art1.modules")).paths(PathSelectors.any())
                .build();
    }

}