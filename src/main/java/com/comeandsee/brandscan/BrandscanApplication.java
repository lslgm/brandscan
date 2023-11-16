package com.comeandsee.brandscan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BrandscanApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrandscanApplication.class, args);
    }

}
