package com.amazonitrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Amazonitrics {
    public static void main(String[] args) {
        SpringApplication.run(Amazonitrics.class, args);
    }
}
