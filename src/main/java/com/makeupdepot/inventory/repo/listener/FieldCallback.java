package com.makeupdepot.inventory.repo.listener;

import org.springframework.data.annotation.Id;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by chassiness on 1/18/17.
 */
public class FieldCallback implements ReflectionUtils.FieldCallback {
    private boolean idFound;

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.makeAccessible(field);

        if (field.isAnnotationPresent(Id.class)) {
            idFound = true;
        }
    }

    public boolean isIdFound() {
        return idFound;
    }
}
