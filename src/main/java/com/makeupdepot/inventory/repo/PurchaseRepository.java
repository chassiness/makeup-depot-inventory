package com.makeupdepot.inventory.repo;

import com.makeupdepot.inventory.repo.domain.obj.Purchase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by chassiness on 1/17/17.
 */
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, String> {

    List<Purchase> findByStatus(@Param("status") String status);
    List<Purchase> findByPurchasedBy(@Param("purchasedby") String purchasedBy);
    List<Purchase> findRetailer(@Param("retailer") String retailer);
    List<Purchase> findOrderNumber(@Param("ordernumber") String orderNumber);
}
