package com.esisba.msqueryproducts.DAO;

import com.esisba.msqueryproducts.documents.Product;
import com.esisba.msqueryproducts.documents.ProductsGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductsGroupRepository extends MongoRepository<ProductsGroup, String> {
    List<ProductsGroup> findProductsGroupsByCompanyIdIn(List<Integer> ids);
    Page<ProductsGroup> findProductsGroupsByCompanyId(Integer id, Pageable pageable);
    ProductsGroup findProductsGroupByCompanyIdAndProductsGroupId(Integer idc , String idp);


}
