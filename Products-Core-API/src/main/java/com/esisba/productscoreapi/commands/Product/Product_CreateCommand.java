package com.esisba.productscoreapi.commands.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.checkerframework.common.value.qual.IntRange;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product_CreateCommand {
    private String name;
    private String reference;
    private Double price;

    private String description;

    private Integer quantity;
    private Integer minimumQuantity;

    private List<String> imagePaths;

    @TargetAggregateIdentifier
    private String productsGroupId;

    private LocalDateTime createdAt;
}
