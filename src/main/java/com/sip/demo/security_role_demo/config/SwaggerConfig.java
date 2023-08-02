package com.sip.demo.security_role_demo.config;

import java.time.LocalDate;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {
    
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                            .select()
                            .apis(RequestHandlerSelectors.any())
                            .paths(PathSelectors.any())
                            .build()
                            .apiInfo(apiInfo())
                            .pathMapping("/")
                            .useDefaultResponseMessages(false)
                            .directModelSubstitute(LocalDate.class,String.class)
                            .genericModelSubstitutes(ResponseEntity.class);


    }

    ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                    .title("Role based Spring Security Swagger ui")
                    .version("1.5.0")
                    .description("Spring Security")
                    .build();
    }
}
