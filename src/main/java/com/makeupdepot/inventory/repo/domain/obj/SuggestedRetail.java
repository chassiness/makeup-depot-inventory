package com.makeupdepot.inventory.repo.domain.obj;

import com.makeupdepot.inventory.misc.Currency;
import org.springframework.data.util.Pair;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chassiness on 11/17/16.
 */
public class SuggestedRetail {

    class PriceOption {
        //Retail price as determined by the expected margin at this percentage of total cost.
        private BigDecimal marginPercentage;
        private BigDecimal suggestedPrice;
        private boolean best;

        public PriceOption(BigDecimal marginPercentage, BigDecimal suggestedPrice) {
            this.marginPercentage = marginPercentage;
            this.suggestedPrice = suggestedPrice;
        }
    }

    @NotNull private ProductPrice product;
    @NotNull private Currency exchangeRate;
    private Currency marginAtRetailPrice;
    private List<PriceOption> priceOptions;

    private SuggestedRetail() {
    }

    public SuggestedRetail(ProductPrice product, Currency exchangeRate) {
        this.product = product;
        this.exchangeRate = exchangeRate;
        setMarginAtRetailPrice(product.computeMarginAtRetailPrice(exchangeRate));
    }

    public void calculate(){
        setMarginAtRetailPrice(getProduct().computeMarginAtRetailPrice(exchangeRate));

    }

    public ProductPrice getProduct() {
        return product;
    }

    public void setProduct(ProductPrice product) {
        this.product = product;
    }

    public Currency getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Currency exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Currency getMarginAtRetailPrice() {
        return marginAtRetailPrice;
    }

    public void setMarginAtRetailPrice(Currency marginAtRetailPrice) {
        this.marginAtRetailPrice = marginAtRetailPrice;
    }

    public List<PriceOption> getPriceOptions() {
        return priceOptions;
    }

    public void setPriceOptions(List<PriceOption> priceOptions) {
        this.priceOptions = priceOptions;
    }

    public void addPriceOption(PriceOption option) {
        if (priceOptions == null) {
            priceOptions = new ArrayList<>();
        }
        priceOptions.add(option);
    }
}
