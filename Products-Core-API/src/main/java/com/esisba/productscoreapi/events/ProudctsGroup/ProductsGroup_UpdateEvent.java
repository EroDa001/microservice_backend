package com.esisba.productscoreapi.events.ProudctsGroup;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsGroup_UpdateEvent {
    private String productsGroupId;
    private String name;

}
