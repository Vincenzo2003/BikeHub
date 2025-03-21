package com.vincenzo.bikehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"com.vincenzo.bikehub.entity"})
@EnableScheduling
@EnableJpaAuditing
public class BikeHub {

    public static void main(String[] args) {
        SpringApplication.run(BikeHub.class, args);
    }

}
