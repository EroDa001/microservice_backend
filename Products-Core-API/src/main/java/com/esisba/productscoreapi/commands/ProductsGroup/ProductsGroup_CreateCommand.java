package com.esisba.productscoreapi.commands.ProductsGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProductsGroup_CreateCommand {

    private String name;

    private List<String> productsIds;

    private String categoryId;

    private Integer companyId;

    private LocalDateTime createdAt;

}
