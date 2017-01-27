package com.makeupdepot.inventory.config;

import com.makeupdepot.inventory.repo.event.ProductEventHandler;
import com.makeupdepot.inventory.repo.listener.ClientCascadeSaveListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Created by chassiness on 11/17/16.
 */
@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

    @Bean
    ProductEventHandler productEventHandler() {
        return new ProductEventHandler();
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ClientCascadeSaveListener orderClientCascadingEventListener() {
        return new ClientCascadeSaveListener();
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener v) {
        v.addValidator("afterCreate", validator());
        v.addValidator("beforeCreate", validator());
        v.addValidator("afterSave", validator());
        v.addValidator("beforeSave", validator());
    }
}
