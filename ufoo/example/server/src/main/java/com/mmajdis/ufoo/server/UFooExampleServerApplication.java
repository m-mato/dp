/*
 * SpringBoot application start (standalone application)
 */
package com.mmajdis.ufoo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author Matej Majdis
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class UFooExampleServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UFooExampleServerApplication.class, args);
    }
}
