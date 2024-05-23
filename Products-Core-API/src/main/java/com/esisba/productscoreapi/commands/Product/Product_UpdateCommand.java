package com.esisba.productscoreapi.commands.Product;

import com.esisba.productscoreapi.embedded.Discount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.checkerframework.common.value.qual.IntRange;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class Product_UpdateCommand {
    private String productId;

    private String name;
    private String reference;
    private Double price;

    private Discount discount;
    private String description;
    private Integer quantity;
    private Integer minimumQuantity;

    private List<String> imagePaths;

    private Integer numberOfRates;
    private Float rate;



    @TargetAggregateIdentifier
    private String productsGroupId;
}
