package com.makeupdepot.inventory.repo;

import com.makeupdepot.inventory.repo.domain.validation.ProductCreateValidator;
import com.makeupdepot.inventory.repo.domain.validation.ProductSaveValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Created by chassiness on 11/17/16.
 */
@Configuration
public class RepositoryConfiguration extends RepositoryRestConfigurerAdapter {

    @Bean
    public ProductSaveValidator beforeSaveProductValidator() {
        return new ProductSaveValidator();
    }

    @Bean
    public ProductCreateValidator beforeCreateProductValidator() {
        return new ProductCreateValidator();
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
        v.addValidator("beforeCreate", beforeCreateProductValidator());
        v.addValidator("beforeSave", beforeSaveProductValidator());
    }
}
