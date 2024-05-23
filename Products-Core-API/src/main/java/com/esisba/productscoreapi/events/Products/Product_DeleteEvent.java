package com.esisba.productscoreapi.events.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product_DeleteEvent {
    private String productId;

    private String productsGroupId;

}
