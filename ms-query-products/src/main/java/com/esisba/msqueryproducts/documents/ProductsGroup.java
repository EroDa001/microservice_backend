package com.esisba.msqueryproducts.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data @AllArgsConstructor @NoArgsConstructor
public class ProductsGroup {
    @Id
    private String productsGroupId;
    private String name;
    private List<String> productsIds;
    private String categoryId;
    private Integer companyId;
}
