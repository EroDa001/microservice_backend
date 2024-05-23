package com.esisba.mscommandproducts;

import com.esisba.productscoreapi.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class})
@EnableFeignClients
public class MsCommandProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCommandProductsApplication.class, args);
    }

}
