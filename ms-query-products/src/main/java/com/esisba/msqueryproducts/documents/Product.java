package com.esisba.msqueryproducts.documents;

import com.esisba.productscoreapi.embedded.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class Product {

    @Id
    private String productId;

    private LocalDateTime createdAt;

    private String name;
    private double price;
    private Discount discount;
    private String description;
    private String reference;
    private int quantity;
    private int minimumQuantity;
    private List<String> imagePaths;
    private int numberOfRates;
    private float rate;
    private String productsGroupId;
}
