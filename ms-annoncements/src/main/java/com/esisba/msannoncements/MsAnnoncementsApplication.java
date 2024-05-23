package com.esisba.msannoncements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsAnnoncementsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAnnoncementsApplication.class, args);
    }

}
