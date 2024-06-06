package com.esisba.msqueryproducts.DTOs;

import com.esisba.msqueryproducts.documents.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class GroupWithProducts {
    private String name;
    private Page<Product> products;
}
