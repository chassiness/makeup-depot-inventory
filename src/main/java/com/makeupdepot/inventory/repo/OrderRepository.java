package com.makeupdepot.inventory.repo;

import com.makeupdepot.inventory.repo.domain.obj.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by chassiness on 1/17/17.
 */

public interface OrderRepository extends PagingAndSortingRepository<Order, String> {

    List<Order> findByClientName(@Param("name") String name);
    List<Order> findByClientUsername(@Param("username") String username);
    List<Order> findByStatus(@Param("status") String status);
    List<Order> findByBatch(@Param("batch") String batch);
}
