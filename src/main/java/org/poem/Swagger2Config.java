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
    public Docket createWebRestApi() {
        List<Parameter> paraList = new ArrayList<>();
        ParameterBuilder paraBuilder = new ParameterBuilder();
        springfox.documentation.service.Parameter param1 = paraBuilder
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        paraList.add(param1);
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api doc")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.poem.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(paraList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("元数据查看工具")
                .description("元数据查看工具接口")
                .termsOfServiceUrl("未提供")
                .contact(new Contact("poem", "", ""))
                .version("0.0.1")
                .build();
    }
}
