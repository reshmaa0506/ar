package com.example.zoom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;


/**
 * The Swagger Configuration.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Build docket.
     *
     * @return the docket
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors
                        .withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(apiKey()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    /**
     * Build api key.
     *
     * @return the api key
     */
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization",
                "header");
    }

    /**
     * Build the security context.
     *
     * @return the security context
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections
                        .singletonList(SecurityReference.builder()
                                .reference("Authorization")
                                .scopes(new AuthorizationScope[0])
                                .build()))
                .build();
    }

    /**
     * Build api info.
     *
     * @return the API info
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CallSlate APIs")
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("")
                .version("1.0")
                .contact(new Contact("", "", ""))
                .build();
    }
}
