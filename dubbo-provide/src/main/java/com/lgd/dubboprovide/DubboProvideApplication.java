package com.lgd.dubboprovide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = {"classpath:dubbo-provider.xml"})
public class DubboProvideApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProvideApplication.class, args);
    }
}
