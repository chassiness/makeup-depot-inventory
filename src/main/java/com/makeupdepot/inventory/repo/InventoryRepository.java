package com.makeupdepot.inventory.repo;

import com.makeupdepot.inventory.repo.domain.obj.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by chassiness on 1/21/17.
 */
public interface InventoryRepository extends PagingAndSortingRepository<Inventory, String> {

    List<Inventory> findByBrand(@Param("brand") String brand);
    List<Inventory> findByType(@Param("type") String type);
    List<Inventory> findByShade(@Param("shade") String shade);
    List<Inventory> findByBrandAndType(@Param("brand") String brand, @Param("type") String type);
    List<Inventory> findByBrandAndTypeAndShade(@Param("brand") String brand, @Param("type") String type, @Param
            ("shade") String shade);

}
