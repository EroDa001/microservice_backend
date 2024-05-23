package com.esisba.productscoreapi.events.ProudctsGroup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProductsGroup_CreateEvent {
    private String productsGroupId;
    private String name;
    private List<String> productsIds;
    private String categoryId;
    private Integer companyId;
}
