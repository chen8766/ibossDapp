package com.chen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author chen
 * @date 2020-10-28-21:43
 */
@SpringBootApplication
@EnableCaching
public class IBossDappApplication {

    public static void main(String[] args) {
        SpringApplication.run(IBossDappApplication.class, args);
    }
}
