package com.makeupdepot.inventory.repo.domain.validation;

import com.makeupdepot.inventory.repo.domain.obj.Product;
import com.makeupdepot.inventory.repo.domain.obj.ProductPrice;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * Created by chassiness on 11/16/16.
 */
public class ProductSaveValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        validateRequiredFields(errors);
        ProductPrice productPrice = (ProductPrice) o;
        productPrice.setLastUpdated(new Date());
    }

    protected void validateRequiredFields(Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "brand", "field.required", "Brand is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "field.required", "Type is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "unitCost", "field.required", "Unit Cost is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retailPrice", "field.required", "Retail Price is required.");
    }
}
