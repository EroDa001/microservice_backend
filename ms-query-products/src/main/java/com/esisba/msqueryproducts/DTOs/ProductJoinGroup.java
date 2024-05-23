package com.esisba.msqueryproducts.DTOs;

import com.esisba.msqueryproducts.documents.ProductsGroup;
import com.esisba.productscoreapi.DTO.ProductsGroupDTO;
import com.esisba.productscoreapi.embedded.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProductJoinGroup {
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
    private ProductsGroup productGroup;
}
