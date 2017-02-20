package com.makeupdepot.inventory.repo;

import com.makeupdepot.inventory.repo.domain.obj.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by chassiness on 1/18/17.
 */
public interface ClientRepository extends MongoRepository<Client, String> {

    List<Client> findByName(@Param("name") String name);
    List<Client> findBySocialMediaChannelsUsername(@Param("username") String name);
    List<Client> findBySocialMediaChannelsPlatform(@Param("platform") String platform);
}
