/*
 * SpringBoot application start (standalone application)
 */
package com.mmajdis.ufoo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import sun.security.pkcs11.wrapper.Constants;

import java.io.PrintWriter;

/**
 * @author Matej Majdis
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class UFooExampleServerApplication {

    public static void main(String[] args) {
        if(com.mmajdis.ufoo.util.Constants.TESTING_MODE) {
            cleanTestData();
        }
        SpringApplication.run(UFooExampleServerApplication.class, args);
    }

    private static void cleanTestData() {
        try(PrintWriter writer = new PrintWriter("./distances.csv", "UTF-8")) {
            writer.print("IP,distance");
            writer.close();
        } catch (Exception ex) {
            System.err.println("Error while cleaning test data");
        }
    }
}
