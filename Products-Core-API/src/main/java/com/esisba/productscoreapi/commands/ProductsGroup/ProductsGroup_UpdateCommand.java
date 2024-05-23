package com.esisba.productscoreapi.commands.ProductsGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsGroup_UpdateCommand {

    @TargetAggregateIdentifier
    private String productsGroupId;
    private String name;

}
