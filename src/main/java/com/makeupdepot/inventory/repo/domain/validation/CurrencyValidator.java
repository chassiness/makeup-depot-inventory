package com.makeupdepot.inventory.repo.domain.validation;

import com.makeupdepot.inventory.misc.Currency;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by chassiness on 11/17/16.
 */
public class CurrencyValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Currency.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "field.required", "Type is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "field.required", "Value is required.");

    }
}
