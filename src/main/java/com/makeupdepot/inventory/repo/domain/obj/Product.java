package com.makeupdepot.inventory.repo.domain.obj;

import com.makeupdepot.inventory.misc.Currency;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by chassiness on 12/5/16.
 */
public class Product extends DateTimeEntity {

    @Id private String id;

    @NotNull private Item item;
    @NotNull private Currency retailPrice;

    @Transient private Currency projectedMarginAtFullPrice;
    @Transient private Currency projectedMarginAtApplicableDiscount;

    private Product() {
    }

    public Product(Item item, Currency retailPrice, Currency exchangeRate) {
        this.item = item;
        this.retailPrice = retailPrice;
        this.projectedMarginAtFullPrice = computeMarginAtRetailPrice(exchangeRate);
        this.projectedMarginAtApplicableDiscount = computeMarginAtApplicableDiscount(exchangeRate);
    }

    public static Comparator<Product> unitPriceComparator() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product first, Product second) {
                if (first == null || second == null) {
                    throw new IllegalArgumentException("Item prices to be compared cannot be null.");
                }
                BigDecimal firstCost = first.item.getUnitCost().getValue();
                BigDecimal secondCost = second.item.getUnitCost().getValue();
                return firstCost.subtract(secondCost).intValue();
            }
        };
    }

    public static Comparator<Product> retailPriceComparator() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product first, Product second) {
                if (first == null || second == null) {
                    throw new IllegalArgumentException("Item prices to be compared cannot be null.");
                }
                BigDecimal firstCost = first.retailPrice.getValue();
                BigDecimal secondCost = second.retailPrice.getValue();
                return firstCost.subtract(secondCost).intValue();
            }
        };
    }

    public Currency computeMarginAtRetailPrice(Currency exchangeRate) {
        return new Currency(exchangeRate.getType(),
                retailPrice.getValue().subtract(this.item.computeTotalCost(exchangeRate).getValue()));
    }

    public Currency computeMarginAtApplicableDiscount(Currency exchangeRate) {
        BigDecimal cost = this.item.getUnitCost().getValue();
        BigDecimal applicableDiscount = this.item.getApplicableDiscount();
        if (applicableDiscount != null) {
            cost = cost.subtract(cost.multiply(applicableDiscount));
        }
        cost = this.item.applyTaxAndShipping(cost);
        return new Currency(exchangeRate.getType(), retailPrice.getValue().subtract(cost.multiply(exchangeRate.getValue())));
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Currency getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Currency retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Currency getProjectedMarginAtFullPrice() {
        return projectedMarginAtFullPrice;
    }

    public void setProjectedMarginAtFullPrice(Currency projectedMarginAtFullPrice) {
        this.projectedMarginAtFullPrice = projectedMarginAtFullPrice;
    }

    public Currency getProjectedMarginAtApplicableDiscount() {
        return projectedMarginAtApplicableDiscount;
    }

    public void setProjectedMarginAtApplicableDiscount(Currency projectedMarginAtApplicableDiscount) {
        this.projectedMarginAtApplicableDiscount = projectedMarginAtApplicableDiscount;
    }
}
