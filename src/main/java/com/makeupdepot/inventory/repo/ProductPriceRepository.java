package com.makeupdepot.inventory.repo;

import com.makeupdepot.inventory.repo.domain.obj.Product;
import com.makeupdepot.inventory.repo.domain.obj.ProductPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by chassiness on 11/16/16.
 */

@RepositoryRestResource(collectionResourceRel = "prices", path = "prices")
public interface ProductPriceRepository extends MongoRepository<ProductPrice, String> {

    List<Product> findByProductBrand(@Param("brand") String brand);
    List<Product> findByProductType(@Param("type") String type);
    List<Product> findByProductRetailer(@Param("retailer") String retailer);
    List<Product> findByProductUnitCost(@Param("unitcost") String unitCost);

}
