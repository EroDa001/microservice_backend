package com.esisba.msqueryproducts.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class ProductGroupDTO {
    private String name;
    private List<String> productsIds;
    private String categoryId;
    private Integer companyId;
}
