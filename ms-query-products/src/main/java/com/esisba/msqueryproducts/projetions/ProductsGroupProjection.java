package com.esisba.msqueryproducts.projetions;

import com.esisba.msqueryproducts.DAO.ProductsGroupRepository;
import com.esisba.msqueryproducts.DAO.ProductsRepository;
import com.esisba.msqueryproducts.documents.Product;
import com.esisba.msqueryproducts.documents.ProductsGroup;
import com.esisba.productscoreapi.events.Products.Product_CreateEvent;
import com.esisba.productscoreapi.events.Products.Product_DeleteEvent;
import com.esisba.productscoreapi.events.Products.Product_UpdateEvent;
import com.esisba.productscoreapi.events.ProudctsGroup.ProductsGroup_CreateEvent;
import com.esisba.productscoreapi.events.ProudctsGroup.ProductsGroup_DeleteEvent;
import com.esisba.productscoreapi.events.ProudctsGroup.ProductsGroup_UpdateEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductsGroupProjection {

    @Autowired
    private ProductsGroupRepository productsGroupRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @EventHandler
    public void AddProductsGroup(ProductsGroup_CreateEvent event){
        ProductsGroup productsGroup = new ProductsGroup(event.getProductsGroupId() , event.getName() , new ArrayList<String>(),
                event.getCategoryId() , event.getCompanyId());

        productsGroupRepository.save(productsGroup);
    }

    @EventHandler
    public void UpdateProductsGroup(ProductsGroup_UpdateEvent event){
        ProductsGroup productsGroup = productsGroupRepository.findById(event.getProductsGroupId()).get();
        productsGroup.setName(event.getName());
        productsGroupRepository.save(productsGroup);
    }

    @EventHandler
    public void DeleteProductsGroup(ProductsGroup_DeleteEvent event){
        productsGroupRepository.deleteById(event.getProductsGroupId());
    }



    @EventHandler
    public void addProduct(Product_CreateEvent event){
        ProductsGroup productsGroup = productsGroupRepository.findById(event.getProductsGroupId()).get();
        List<String> productsIds = productsGroup.getProductsIds();
        productsIds.add(event.getProductId());
        productsGroup.setProductsIds(productsIds);
        productsGroupRepository.save(productsGroup);

        LocalDateTime createdAt = LocalDateTime.now();

        Product product = new Product(event.getProductId() , event.getCreatedAt() , event.getName(), event.getPrice(), null,
                event.getDescription() , event.getReference() , event.getQuantity() , event.getMinimumQuantity() , event.getImagePaths(),
                0 , 0.0f, event.getProductsGroupId());
        productsRepository.save(product);
    }

    @EventHandler
    public void updateProduct(Product_UpdateEvent event){
        Product product = productsRepository.findById(event.getProductId()).get();

        Optional.ofNullable(event.getName())
                .ifPresent(product::setName);

        Optional.ofNullable(event.getPrice())
                .ifPresent(product::setPrice);

        Optional.ofNullable(event.getDiscount())
                .ifPresent(product::setDiscount);

        Optional.ofNullable(event.getDescription())
                .ifPresent(product::setDescription);

        Optional.ofNullable(event.getReference())
                .ifPresent(product::setReference);

        Optional.ofNullable(event.getQuantity())
                .ifPresent(product::setQuantity);

        Optional.ofNullable(event.getMinimumQuantity())
                .ifPresent(product::setMinimumQuantity);

        Optional.ofNullable(event.getImagePaths())
                .ifPresent(product::setImagePaths);

        Optional.ofNullable(event.getNumberOfRates())
                .ifPresent(product::setNumberOfRates);

        Optional.ofNullable(event.getRate())
                .ifPresent(product::setRate);

        productsRepository.save(product);
    }

    @EventHandler
    public void removeProduct(Product_DeleteEvent event){
        ProductsGroup productsGroup = productsGroupRepository.findById(event.getProductsGroupId()).get();
        List<String> productsIds = productsGroup.getProductsIds();
        productsIds.remove(event.getProductId());
        productsGroup.setProductsIds(productsIds);
        productsGroupRepository.save(productsGroup);

        productsRepository.deleteById(event.getProductId());
    }


}
