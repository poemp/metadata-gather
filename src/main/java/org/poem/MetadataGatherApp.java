package org.poem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Administrator
 */
@EnableSwagger2
@SpringBootApplication
public class MetadataGatherApp {

    public static void main(String[] args) {
        SpringApplication.run( MetadataGatherApp.class, args );
    }
}
