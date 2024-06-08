package com.esisba.msqueryproducts.DAO;

import com.esisba.msqueryproducts.documents.Product;
import com.esisba.msqueryproducts.documents.ProductProjectionB;
import com.esisba.msqueryproducts.documents.ProductsGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.List;

public interface ProductsRepository extends MongoRepository<Product, String> {
    <T> Page<T> findProductsByProductIdIn(List<String> ids, Pageable pageable);

    @Query("{ $expr: { $gte: [\"$quantity\", \"$minimumQuantity\"] } ,'_id': { $in: ?0 }  }")
    <T> Page<T> getAvailableProducts(List<String> ids, Pageable pageable);

    @Query("{ $expr: { $lt: [\"$quantity\", \"$minimumQuantity\"] } , '_id': { $in: ?0 }  }")
    <T> Page<T> getOutOfStockProducts(List<String> ids, Pageable pageable);

    Page<ProductProjectionB> findProductsByProductsGroupId(String id, Pageable pageable);
}
