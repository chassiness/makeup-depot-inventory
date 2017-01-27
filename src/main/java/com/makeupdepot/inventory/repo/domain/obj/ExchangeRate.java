package com.makeupdepot.inventory.repo.domain.obj;

import com.makeupdepot.inventory.misc.Currency;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by chassiness on 1/17/17.
 */
@Data
public class ExchangeRate {

    @NotNull private Date created;
    @NotNull private Currency fromCurrency, toCurrency;
    @NotNull private Status status;

    protected enum Status {
        ACTIVE, INACTIVE
    }
}
