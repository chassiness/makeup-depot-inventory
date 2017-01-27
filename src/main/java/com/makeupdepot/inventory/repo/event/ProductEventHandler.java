package com.makeupdepot.inventory.repo.event;

import com.makeupdepot.inventory.misc.Currency;
import com.makeupdepot.inventory.repo.domain.obj.Item;
import com.makeupdepot.inventory.repo.domain.obj.Product;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by chassiness on 1/17/17.
 */
@RepositoryEventHandler
public class ProductEventHandler {

    @HandleBeforeSave
    public void handleProductSave(Product p) {
        p.setLastUpdated(new Date());
    }

    @HandleBeforeCreate
    public void handleProductCreate(Product p) {
        Date today = new Date();
        p.setCreated(today);
        p.setLastUpdated(today);

        Item item = p.getItem();
        if (item != null) {
            if (item.getTax() == null)
                item.setTax(BigDecimal.ZERO);
            if (item.getShipping() == null)
                item.setShipping(new Currency(Currency.Type.USD, BigDecimal.ZERO));
        }
    }
}
