package com.eventcafecloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
                // Thymleaf를 사용하여 rest api인 controller만 표시되도록 처리
                .paths(PathSelectors.ant("/api/**"))
                .build();
    }

//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Practice Swagger")
//                .description("practice swagger config")
//                .version("1.0")
//                .build();
//    }
}