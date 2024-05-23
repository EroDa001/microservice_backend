package com.esisba.msqueryproducts.API;

import com.esisba.msqueryproducts.DAO.ProductsGroupRepository;
import com.esisba.msqueryproducts.DAO.ProductsRepository;
import com.esisba.msqueryproducts.DTOs.ProductJoinGroup;
import com.esisba.msqueryproducts.DTOs.Products_Ids;
import com.esisba.msqueryproducts.DTOs.Products_suppliersList;
import com.esisba.msqueryproducts.documents.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("client")
public class ClientQueryController {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductsGroupRepository productsGroupRepository;

    @GetMapping("productsBySuppliers")
    public Page<ProductProjectionA> getProducts(@RequestBody Products_suppliersList body , @RequestParam int page, @RequestParam int size){

//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
//            String token = authorizationHeader.substring(7);
//


        List<ProductsGroup> groups = productsGroupRepository.findProductsGroupsByCompanyIdIn(body.getSuppliers());

        List<String> productIds = groups.stream()
                .flatMap(group -> group.getProductsIds().stream())
                .toList();

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));


        return productsRepository.findProductsByProductIdIn(productIds, pageable );

    }

    @GetMapping("/productsGroup/{id}/products")
    public Page<ProductProjectionB> getProductsByGroup(@PathVariable String id , @RequestParam int page, @RequestParam int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        return productsRepository.findProductsByProductsGroupId(id , pageable);
    }

    @GetMapping("/productsByIds")
    public Page<ProductProjectionA> getProductsById(@RequestBody Products_Ids body , @RequestParam int page, @RequestParam int size){

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        return productsRepository.findProductsByProductIdIn(body.getIds() , pageable);
    }

    @GetMapping("/productByIdJoinGroup/{id}")
    public ProductJoinGroup getProductByIdJoinGroup(@PathVariable String id){
         Product product = productsRepository.findById(id).get();
         ProductsGroup group = productsGroupRepository.findById(product.getProductsGroupId()).get();

         ProductJoinGroup result = new ProductJoinGroup(product.getCreatedAt(),product.getName(),product.getPrice(),product.getDiscount(),
                 product.getDescription(),product.getReference(),product.getQuantity(),product.getMinimumQuantity(),product.getImagePaths(),
                 product.getNumberOfRates(),product.getRate(),group);

         return result;
    }

    @GetMapping("/company/{id}/productsGroups")
    public Page<ProductsGroup> getGroupsOfCompany(@PathVariable Integer id , @RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page, size);
        return productsGroupRepository.findProductsGroupsByCompanyId(id, pageable);
    }

}
