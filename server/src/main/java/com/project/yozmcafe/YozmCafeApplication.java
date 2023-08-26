package com.project.yozmcafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YozmCafeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YozmCafeApplication.class, args);
    }

}
