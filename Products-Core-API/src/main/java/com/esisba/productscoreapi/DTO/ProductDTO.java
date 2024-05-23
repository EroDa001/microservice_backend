package com.esisba.productscoreapi.DTO;

import com.esisba.productscoreapi.embedded.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.value.qual.IntRange;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDTO {
    private String name;
    private Double price;
    private Discount discount;
    private String description;
    private String reference;
    private Integer quantity;
    private Integer minimumQuantity;
    private List<String> imagePaths;
    private Integer numberOfRates;
    private Float rate;
}
