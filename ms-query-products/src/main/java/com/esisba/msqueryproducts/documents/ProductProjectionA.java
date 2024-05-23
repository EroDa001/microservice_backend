package com.esisba.msqueryproducts.documents;

import com.esisba.productscoreapi.embedded.Discount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "", types = Product.class)
public interface ProductProjectionA {

    @Value("#{target.imagePaths.get(0)}")
    String getImagePath();

    float getCreatedAt();
    float getRate();

    int getNumberOfRates();

    double getPrice();

    String getName();

    int getQuantity();

    int getMinimumQuantity();

    Discount getDiscount();


}
