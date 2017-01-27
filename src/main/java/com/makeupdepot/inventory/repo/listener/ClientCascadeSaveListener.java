package com.makeupdepot.inventory.repo.listener;

import com.makeupdepot.inventory.custom.annotation.CascadeSave;
import com.makeupdepot.inventory.repo.domain.obj.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by chassiness on 1/17/17.
 */
public class ClientCascadeSaveListener extends AbstractMongoEventListener<Object> {
    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        ReflectionUtils.doWithFields(event.getSource().getClass(),
                new CascadeCallback(event.getSource(), mongoOperations));
    }


}
