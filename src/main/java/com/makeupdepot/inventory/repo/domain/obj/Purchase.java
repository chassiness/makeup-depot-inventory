package com.makeupdepot.inventory.repo.domain.obj;

import com.makeupdepot.inventory.misc.Currency;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Created by chassiness on 1/5/17.
 */
@Data
public class Purchase {

    public enum Status {
        PURCHASED, ARRIVEDINCA, ONHAND, SHIPPINGTOMNL, FORPURCHASE, RETURNED, CANCELLED;
    }

    @Id
    private String id;

    @NotNull
    private Status status;
    @NotNull
    private String orderNumber;
    private String retailer;

    @NotNull
    private Date purchaseDate;
    @NotNull
    private String purchasedBy;

    @NotEmpty
    private List<Product> items;

    private Currency totalCostPeso;
    private Currency totalCostDollar;

    private Purchase() {
    }

    public Purchase(String status, String orderNumber, Date purchaseDate, String purchasedBy, List<Product> items) {
        this.orderNumber = orderNumber;
        this.purchaseDate = purchaseDate;
        this.purchasedBy = purchasedBy;
        this.items = items;
    }

}
