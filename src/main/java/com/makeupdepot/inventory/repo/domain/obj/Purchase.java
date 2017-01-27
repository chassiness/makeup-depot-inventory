package com.makeupdepot.inventory.repo.domain.obj;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Id
    private String id;

    @NotNull
    private Meta.PurchaseStatus purchaseStatus;
    @NotNull
    private String orderNumber;
    private String retailer;

    @NotNull
    @JsonFormat(pattern = "MMM-dd-yyyy")
    private Date purchaseDate;

    @NotNull
    private String purchasedBy;

    @NotEmpty
    private List<Item> items;

    private Currency totalCostPeso;
    private Currency totalCostDollar;

    private Purchase() {
    }

    public Purchase(String status, String orderNumber, Date purchaseDate, String purchasedBy, List<Item> items) {
        this.orderNumber = orderNumber;
        this.purchaseDate = purchaseDate;
        this.purchasedBy = purchasedBy;
        this.items = items;
    }

}
