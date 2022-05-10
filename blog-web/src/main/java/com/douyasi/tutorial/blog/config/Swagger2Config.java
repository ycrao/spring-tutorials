package com.douyasi.tutorial.blog.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger2Config
 *
 * @author raoyc
 */
@Configuration
// @EnableOpenApi
@EnableSwagger2
public class Swagger2Config {

    @Value(value = "${swagger2.enabled:false}")
    private Boolean swaggerEnabled;

    @Bean
    public Docket docket() {
        
        return new
                Docket(DocumentationType.SWAGGER_2)
                // Docket(DocumentationType.OAS_30)
                .enable(swaggerEnabled)
                //.ignoredParameterTypes()
                .select()
                // only scan package `com.douyasi.tutorial.blog`
                // .apis(RequestHandlerSelectors.basePackage("com.douyasi.tutorial.blog"))
                // only scan @Api annotation class
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                // only scan @ApiOperation annotation method
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SAMPLE BLOG API DOCUMENT")
                .description("swagger api document")
                .version("1.0")
                .build();
    }

}
