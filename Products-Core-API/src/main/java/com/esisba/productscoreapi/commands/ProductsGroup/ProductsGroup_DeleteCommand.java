package com.esisba.productscoreapi.commands.ProductsGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsGroup_DeleteCommand {

    @TargetAggregateIdentifier
    private String productsGroupId;
}
