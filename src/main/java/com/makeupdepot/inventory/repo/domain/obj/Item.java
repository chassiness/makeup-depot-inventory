package com.makeupdepot.inventory.repo.domain.obj;

import com.makeupdepot.inventory.misc.Currency;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by chassiness on 11/16/16.
 */

@Data
@CompoundIndexes({
        @CompoundIndex(name = "product", def = "{'brand': 1, 'type': 1, 'shade': 1}"),
        @CompoundIndex(name = "retailprice_asc", def = "{'retailPrice': 1, 'brand': 1}"),
        @CompoundIndex(name = "unitcost_asc", def = "{'unitCost.value': 1, 'brand': 1}")
})
public class Item {

    @NotNull
    private String brand;
    @NotNull
    private String type, shade;

    @NotNull
    private Currency unitCost;
    private BigDecimal tax;
    private Currency shipping;
    private BigDecimal applicableDiscount;

    private String notes, retailer;

    public Item(String brand, String type, Currency unitCost) {
        this.brand = brand;
        this.type = type;
        this.unitCost = unitCost;
    }

    private Item() {
    }

    public Currency computeTotalCost() {
        return new Currency(this.unitCost.getType(), applyTaxAndShipping(this.unitCost.getValue()));
    }

    protected BigDecimal applyTaxAndShipping(BigDecimal cost) {
        BigDecimal totalCost = cost;
        if (tax != null) {
            totalCost = totalCost.add(totalCost.multiply(tax));
        }
        if (shipping != null) {
            totalCost = totalCost.add(shipping.getValue());
        }
        return totalCost;
    }

    public Currency computeTotalCost(Currency exchangeRate) {
        return new Currency(exchangeRate.getType(), computeTotalCost().getValue().multiply(exchangeRate.getValue()));
    }

    public Currency getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Currency unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Currency getShipping() {
        return shipping;
    }

    public void setShipping(Currency shipping) {
        this.shipping = shipping;
    }

    public BigDecimal getApplicableDiscount() {
        return applicableDiscount;
    }

    public void setApplicableDiscount(BigDecimal applicableDiscount) {
        this.applicableDiscount = applicableDiscount;
    }

    //    public Currency computeMarginAtRetailPrice(Currency exchangeRate) {
//        return new Currency(exchangeRate.getType(),
//                retailPrice.getValue().subtract(computeTotalCost(exchangeRate).getValue()));
//    }
//
//    public Currency computeMarginAtApplicableDiscount(Currency exchangeRate) {
//        BigDecimal cost = unitCost.getValue();
//        if (applicableDiscount != null) {
//            cost = cost.subtract(cost.multiply(applicableDiscount));
//        }
//        cost = applyTaxAndShipping(cost);
//        return new Currency(exchangeRate.getType(), retailPrice.getValue().subtract(cost.multiply(exchangeRate.getValue())));
//    }
}
