package org.poem;

import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 */
@EnableEurekaClient
@EnableAceAuthClient
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign"})
@EnableSwagger2
@SpringBootApplication
public class MetadataGatherApp {

    public static void main(String[] args) {
        SpringApplication.run( MetadataGatherApp.class, args );
    }
}
