package com.example.illicitcapitalflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class IllicitCapitalFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(IllicitCapitalFlowApplication.class, args);
    }
}