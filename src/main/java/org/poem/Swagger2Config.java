package org.poem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


/**
 * @author poem
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean
    public Docket createAllRestApi() {
        List<Parameter> paraList = new ArrayList<>();

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API-All")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.poem.code.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(paraList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("数据采集小工具")
                .description("数据采集 接口")
                .termsOfServiceUrl("未提供")
                .contact(new Contact("poem", "", "xue_2013@sina.com"))
                .version("1.0.0")
                .build();
    }
}
