package com.esisba.mscommandproducts.API;


import com.esisba.mscommandproducts.proxies.Permission;
import com.esisba.mscommandproducts.proxies.UserInfosDto;
import com.esisba.mscommandproducts.proxies.AuthProxy;
import com.esisba.mscommandproducts.proxies.JwtDto;
import com.esisba.productscoreapi.DTO.ProductDTO;
import com.esisba.productscoreapi.DTO.ProductsGroupDTO;
import com.esisba.productscoreapi.commands.Product.Product_CreateCommand;
import com.esisba.productscoreapi.commands.Product.Product_DeleteCommand;
import com.esisba.productscoreapi.commands.Product.Product_UpdateCommand;
import com.esisba.productscoreapi.commands.ProductsGroup.ProductsGroup_CreateCommand;
import com.esisba.productscoreapi.commands.ProductsGroup.ProductsGroup_DeleteCommand;
import com.esisba.productscoreapi.commands.ProductsGroup.ProductsGroup_UpdateCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;


@RestController
public class CommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping("category/{category}/products_group")
    public CompletableFuture<ResponseEntity<String>> createProductsGroup(@PathVariable String category, @RequestBody ProductsGroupDTO body , @RequestHeader("Authorization") String authorizationHeader){

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.INVENTORY) && user.getCategories().contains(category)){

                    LocalDateTime createdAt = LocalDateTime.now();

                    CompletableFuture<String> gatewayResponse = commandGateway.send(
                            new ProductsGroup_CreateCommand(body.getName() , null , category , authResponse.getCompanyId() , createdAt )
                    );

                    return gatewayResponse
                            .thenApply(response-> ResponseEntity.ok().body("Products Group Created"))
                            .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Error occurred: " + ex.getMessage()));
                    }
                else{
                    return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED"));
                }
            }
            else{
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token"));
            }

        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing"));
        }
    }

    @Autowired
    private AuthProxy authProxy;


    @PutMapping("products_group/{pGroupId}")
    public CompletableFuture<ResponseEntity<String>> updateProductsGroup(@PathVariable String pGroupId , @RequestBody ProductsGroupDTO body , @RequestHeader("Authorization") String authorizationHeader){

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.INVENTORY)){

                CompletableFuture<String> gatewayResponse = commandGateway.send(
                        new ProductsGroup_UpdateCommand(pGroupId , body.getName())
                );
                return gatewayResponse
                        .thenApply(response-> ResponseEntity.ok().body("Products Group Updated Name To : " +  body.getName()))
                        .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error occurred: " + ex.getMessage() ));
                }
                else{
                    return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient Permissions"));
                }
            }
            else{
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unvalid Token"));
            }

        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing"));
        }

    }



    @DeleteMapping("products_group/{pGroupId}")
    public CompletableFuture<ResponseEntity<String>> deleteProductsGroup(@PathVariable String pGroupId , @RequestHeader("Authorization") String authorizationHeader){

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.INVENTORY)){

                    CompletableFuture<String> gatewayResponse = commandGateway.send(
                            new ProductsGroup_DeleteCommand(pGroupId)
                    );
                    return gatewayResponse
                            .thenApply(response-> ResponseEntity.ok().body("Products Group Deleted"))
                            .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Error occurred: " + ex.getMessage() ));
                    }

                else{
                    return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient Permissions"));
                }
            }
            else{
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unvalid Token"));
            }

        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing"));
        }

    }


    @PostMapping("products_group/{groupId}/product")
    public CompletableFuture<ResponseEntity<String>> AddProduct(@PathVariable String groupId , @RequestBody ProductDTO body , @RequestHeader("Authorization") String authorizationHeader){

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.INVENTORY)){

                    LocalDateTime createdAt = LocalDateTime.now();

                    CompletableFuture<String> gatewayResponse = commandGateway.send(
                        new Product_CreateCommand(body.getName() , body.getReference() , body.getPrice() , body.getDescription(),
                                body.getQuantity() , body.getMinimumQuantity() , body.getImagePaths(), groupId , createdAt)
                    );
                    return gatewayResponse
                            .thenApply(response-> ResponseEntity.ok().body("Product Added"))
                            .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Error occurred: " + ex.getMessage() ));
                    }
                else{
                    return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient Permissions"));
                }
            }
            else{
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unvalid Token"));
            }

        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing"));
        }

    }


    @PutMapping("products_group/{groupId}/product/{productId}")
    public CompletableFuture<ResponseEntity<String>> updateProduct(@PathVariable String groupId , @PathVariable String productId , @RequestBody ProductDTO body , @RequestHeader("Authorization") String authorizationHeader){

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.INVENTORY)){
                System.out.println(body.toString());
                CompletableFuture<String> gatewayResponse = commandGateway.send(
                       new Product_UpdateCommand(productId , body.getName() , body.getReference() , body.getPrice(),
                               body.getDiscount() ,body.getDescription() , body.getQuantity() , body.getMinimumQuantity(),
                               body.getImagePaths() , body.getNumberOfRates() , body.getRate() , groupId)
                );
                return gatewayResponse
                        .thenApply(response-> ResponseEntity.ok().body("Product Updated"))
                        .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error occurred: " + ex.getMessage() ));
                }
                else{
                    return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient Permissions"));
                }
            }
            else{
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unvalid Token"));
            }

        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing"));
        }
    }


    @DeleteMapping("products_group/{groupId}/product/{productId}")
    public CompletableFuture<ResponseEntity<String>> removeProduct(@PathVariable String groupId , @PathVariable String productId , @RequestHeader("Authorization") String authorizationHeader){

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);

            JwtDto authResponse = authProxy.validateToken(token);

            if(authResponse.getIsValid()){

                UserInfosDto user = authProxy.getUser(authorizationHeader);

                if(user.getPermissions().contains(Permission.INVENTORY)){

                CompletableFuture<String> gatewayResponse = commandGateway.send(
                        new Product_DeleteCommand(productId, groupId)
                );
                return gatewayResponse
                        .thenApply(response-> ResponseEntity.ok().body("Product Deleted"))
                        .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error occurred: " + ex.getMessage() ));
                }
                else{
                    return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Insufficient Permissions"));
                }
            }
            else{
                return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unvalid Token"));
            }

        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is missing"));
        }
    }


}
