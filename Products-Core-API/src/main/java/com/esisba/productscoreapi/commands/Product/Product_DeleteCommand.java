package com.esisba.productscoreapi.commands.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Product_DeleteCommand {
    private String productId;

    @TargetAggregateIdentifier
    private String productsGroupId;
}
