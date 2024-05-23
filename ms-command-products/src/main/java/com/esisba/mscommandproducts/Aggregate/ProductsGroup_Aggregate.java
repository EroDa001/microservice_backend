package com.esisba.mscommandproducts.Aggregate;

import com.esisba.productscoreapi.commands.Product.Product_CreateCommand;
import com.esisba.productscoreapi.commands.Product.Product_DeleteCommand;
import com.esisba.productscoreapi.commands.Product.Product_UpdateCommand;
import com.esisba.productscoreapi.commands.ProductsGroup.ProductsGroup_CreateCommand;
import com.esisba.productscoreapi.commands.ProductsGroup.ProductsGroup_DeleteCommand;
import com.esisba.productscoreapi.commands.ProductsGroup.ProductsGroup_UpdateCommand;
import com.esisba.productscoreapi.events.Products.Product_CreateEvent;
import com.esisba.productscoreapi.events.Products.Product_DeleteEvent;
import com.esisba.productscoreapi.events.Products.Product_UpdateEvent;
import com.esisba.productscoreapi.events.ProudctsGroup.ProductsGroup_CreateEvent;
import com.esisba.productscoreapi.events.ProudctsGroup.ProductsGroup_DeleteEvent;
import com.esisba.productscoreapi.events.ProudctsGroup.ProductsGroup_UpdateEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Aggregate
@NoArgsConstructor
public class ProductsGroup_Aggregate {

    @AggregateIdentifier
    private String productsGroupId;
    private List<String> productsIds;


    @CommandHandler
    public ProductsGroup_Aggregate(ProductsGroup_CreateCommand cmd){
        Assert.notNull(cmd.getCategoryId() , "Category ID shouldn't be null");
        Assert.notNull(cmd.getCompanyId() , "Company ID shouldn't be null");
        Assert.notNull(cmd.getName() , "Products Group name shouldn't be null");

        String newId = UUID.randomUUID().toString();

        AggregateLifecycle.apply(new ProductsGroup_CreateEvent(newId, cmd.getName() , null , cmd.getCategoryId() , cmd.getCompanyId() ));
    }

    @EventSourcingHandler
    public void on(ProductsGroup_CreateEvent event){
        this.productsGroupId = event.getProductsGroupId();
        this.productsIds = new ArrayList<>();
    }

    @CommandHandler
    public void handler(ProductsGroup_UpdateCommand cmd){
        Assert.notNull(cmd.getProductsGroupId() , "Products Group ID shouldn't be null");
        Assert.notNull(cmd.getName() , "Products Group name shouldn't be null");

        AggregateLifecycle.apply(new ProductsGroup_UpdateEvent(cmd.getProductsGroupId() , cmd.getName()));
    }

    @CommandHandler
    public void handler(ProductsGroup_DeleteCommand cmd) throws Exception{
        Assert.notNull(cmd.getProductsGroupId() , "Products Group Id shouldn't be null");

        if(!this.productsIds.isEmpty()){
            throw new Exception("Can't delete a non empty Products Group");
        }

        AggregateLifecycle.apply(new ProductsGroup_DeleteEvent(cmd.getProductsGroupId()));
    }

    @EventSourcingHandler
    public void on(ProductsGroup_DeleteEvent event){
        AggregateLifecycle.markDeleted();
    }




    @CommandHandler
    public void handler(Product_CreateCommand cmd) throws Exception{

        Assert.notNull(cmd.getName() , "Product name shouldn't be null");
        Assert.notNull(cmd.getReference() , "Product name shouldn't be null");
        Assert.notEmpty(cmd.getImagePaths() , "Product name shouldn't be null");
        Assert.isTrue(cmd.getPrice()>0 , "Product price should be higher than 0");
        Assert.isTrue(cmd.getQuantity()>0  , "Quantity should be higher than 0");

        String newId = UUID.randomUUID().toString();

        AggregateLifecycle.apply(new Product_CreateEvent( newId , cmd.getCreatedAt() ,cmd.getName() , cmd.getReference() , cmd.getPrice() ,
                cmd.getDescription() , cmd.getQuantity() , cmd.getMinimumQuantity() , cmd.getImagePaths() , cmd.getProductsGroupId()
        ));
    }

    @EventSourcingHandler
    public void on(Product_CreateEvent event){
        this.productsIds.add(event.getProductId());
    }

    @CommandHandler
    public void handler(Product_UpdateCommand cmd) throws Exception{

        Assert.notNull(cmd.getProductId() , "Product id shouldn't be null");

        if(!this.productsIds.contains(cmd.getProductId())){
            throw new Exception("Product Id Doesn't Exist in Products Group");
        }

        AggregateLifecycle.apply(new Product_UpdateEvent(cmd.getProductId(), cmd.getName() , cmd.getReference() , cmd.getPrice() , cmd.getDiscount(),
                cmd.getDescription() , cmd.getQuantity() , cmd.getMinimumQuantity() , cmd.getImagePaths() , cmd.getNumberOfRates(), cmd.getRate()
        ));
    }

    @CommandHandler
    public void handler(Product_DeleteCommand cmd) throws Exception{

        Assert.notNull(cmd.getProductId() , "Product id shouldn't be null");
        Assert.notNull(cmd.getProductsGroupId() , "Products Group Id shoudln't be null");

        if(!this.productsIds.contains(cmd.getProductId())){
            throw new Exception("Product Id Doesn't Exist in Products Group");
        }

        AggregateLifecycle.apply(new Product_DeleteEvent(cmd.getProductId() ,cmd.getProductsGroupId()));
    }

    @EventSourcingHandler
    public void on(Product_DeleteEvent event){
        this.productsIds.remove(event.getProductId());
    }

}
