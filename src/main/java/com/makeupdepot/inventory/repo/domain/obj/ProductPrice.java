package com.makeupdepot.inventory.repo.domain.obj;

import com.makeupdepot.inventory.misc.Currency;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Created by chassiness on 12/5/16.
 */
public class ProductPrice extends DateTimeEntity {

    @NotNull
    private Product product;
    @NotNull
    private Currency retailPrice;

    private Currency projectedMarginAtFullPrice;
    private Currency projectedMarginAtApplicableDiscount;

    private ProductPrice() {
    }

    public ProductPrice(Product product, Currency retailPrice, Currency exchangeRate) {
        this.product = product;
        this.retailPrice = retailPrice;
        this.projectedMarginAtFullPrice = computeMarginAtRetailPrice(exchangeRate);
        this.projectedMarginAtApplicableDiscount = computeMarginAtApplicableDiscount(exchangeRate);
    }

    public static Comparator<ProductPrice> unitPriceComparator() {
        return new Comparator<ProductPrice>() {
            @Override
            public int compare(ProductPrice first, ProductPrice second) {
                if (first == null || second == null) {
                    throw new IllegalArgumentException("Product prices to be compared cannot be null.");
                }
                BigDecimal firstCost = first.product.getUnitCost().getValue();
                BigDecimal secondCost = second.product.getUnitCost().getValue();
                return firstCost.subtract(secondCost).intValue();
            }
        };
    }

    public static Comparator<ProductPrice> retailPriceComparator() {
        return new Comparator<ProductPrice>() {
            @Override
            public int compare(ProductPrice first, ProductPrice second) {
                if (first == null || second == null) {
                    throw new IllegalArgumentException("Product prices to be compared cannot be null.");
                }
                BigDecimal firstCost = first.retailPrice.getValue();
                BigDecimal secondCost = second.retailPrice.getValue();
                return firstCost.subtract(secondCost).intValue();
            }
        };
    }

    public Currency computeMarginAtRetailPrice(Currency exchangeRate) {
        return new Currency(exchangeRate.getType(),
                retailPrice.getValue().subtract(this.product.computeTotalCost(exchangeRate).getValue()));
    }

    public Currency computeMarginAtApplicableDiscount(Currency exchangeRate) {
        BigDecimal cost = this.product.getUnitCost().getValue();
        BigDecimal applicableDiscount = this.product.getApplicableDiscount();
        if (applicableDiscount != null) {
            cost = cost.subtract(cost.multiply(applicableDiscount));
        }
        cost = this.product.applyTaxAndShipping(cost);
        return new Currency(exchangeRate.getType(), retailPrice.getValue().subtract(cost.multiply(exchangeRate.getValue())));
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
