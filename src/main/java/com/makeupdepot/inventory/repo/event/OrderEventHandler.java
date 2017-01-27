package com.makeupdepot.inventory.repo.event;

import com.makeupdepot.inventory.repo.domain.obj.Meta;
import com.makeupdepot.inventory.repo.domain.obj.Order;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.math.BigDecimal;

/**
 * Created by chassiness on 1/17/17.
 */
@RepositoryEventHandler
public class OrderEventHandler {

    @HandleBeforeSave
    public void handleProductSave(Order o) {
        if (o.getStatus().equals(Meta.OrderStatus.NEW) || o.getStatus().equals
                (Meta.OrderStatus.CONFIRMED)) {
            BigDecimal balance = o.calculateBalance();
            if (balance.equals(BigDecimal.ZERO)) {
                o.setStatus(Meta.OrderStatus.READYTOSHIP);
            } else if (balance.compareTo(BigDecimal.ZERO) > 0 && balance.compareTo(o.calculateTotal()) < 1) {
                o.setStatus(Meta.OrderStatus.CONFIRMED);
            }
        }
    }
}
