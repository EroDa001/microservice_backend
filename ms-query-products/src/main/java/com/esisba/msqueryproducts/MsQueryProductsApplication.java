package com.esisba.msqueryproducts;

import com.esisba.productscoreapi.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({AxonConfig.class})
@EnableFeignClients
public class MsQueryProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsQueryProductsApplication.class, args);
    }

}
