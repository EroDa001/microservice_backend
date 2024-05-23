package com.esisba.msqueryproducts.API;

import com.esisba.msqueryproducts.DAO.ProductsGroupRepository;
import com.esisba.msqueryproducts.DAO.ProductsRepository;
import com.esisba.msqueryproducts.Proxies.AuthProxy;
import com.esisba.msqueryproducts.Proxies.JwtDto;
import com.esisba.msqueryproducts.Proxies.Permission;
import com.esisba.msqueryproducts.Proxies.UserInfosDto;
import com.esisba.msqueryproducts.documents.ProductsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("supplier")
public class SupplierQueryController {

    @Autowired
    private ProductsGroupRepository productsGroupRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private AuthProxy authProxy;

    @GetMapping("/myProducts")
    public ResponseEntity<Page> getProducts(@RequestParam int page, @RequestParam int size , @RequestHeader("Authorization") String authorizationHeader){


        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.INVENTORY)){

                    PageRequest pagination = PageRequest.of(page, size);

                    Page<ProductsGroup> groups = productsGroupRepository.findProductsGroupsByCompanyId(authResponse.getCompanyId() , pagination);

                    List<String> productIds = groups.stream()
                            .flatMap(group -> group.getProductsIds().stream())
                            .toList();

                    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

                    return ResponseEntity.ok().body(productsRepository.findProductsByProductIdIn(productIds, pageable ));

                }
                else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Page.empty());
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Page.empty());
            }

        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Page.empty());
        }
    }

    @GetMapping("/myProductsGroups")
    public ResponseEntity<Page> getProductsGroups(@RequestParam int page, @RequestParam int size , @RequestHeader("Authorization") String authorizationHeader ){
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.INVENTORY)) {

                    PageRequest pagination = PageRequest.of(page , size , Sort.by("categoryId"));
                    return ResponseEntity.ok().body(productsGroupRepository.findProductsGroupsByCompanyId(authResponse.getCompanyId() , pagination));

                }
                else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Page.empty());
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Page.empty());
            }

        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Page.empty());
        }
    }


}