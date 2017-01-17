package com.makeupdepot.inventory.repo.domain.validation;

import com.makeupdepot.inventory.repo.domain.obj.ProductPrice;
import org.springframework.validation.Errors;

import java.util.Date;

/**
 * Created by chassiness on 12/5/16.
 */
public class ProductCreateValidator extends ProductSaveValidator {

    @Override
    public void validate(Object o, Errors errors) {
        validateRequiredFields(errors);

        ProductPrice productPrice = (ProductPrice) o;
        Date today = new Date();
        productPrice.setCreated(today);
        productPrice.setLastUpdated(today);
    }
}
