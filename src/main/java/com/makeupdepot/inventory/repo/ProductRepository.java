package com.makeupdepot.inventory.repo;

import com.makeupdepot.inventory.repo.domain.obj.Item;
import com.makeupdepot.inventory.repo.domain.obj.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by chassiness on 11/16/16.
 */

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {

    List<Product> findByItemBrandIgnoreCase(@Param("brand") String brand);
    List<Product> findByItemTypeIgnoreCase(@Param("type") String type);
    List<Product> findByItemRetailerIgnoreCase(@Param("retailer") String retailer);
    List<Product> findByItemUnitCostValue(@Param("unitcost") String unitCost);

}
