package com.makeupdepot.inventory.misc;

import java.math.BigDecimal;

/**
 * Created by chassiness on 11/16/16.
 */
public class Currency {

    public enum Type {
        USD, Php
    }

    private Type type;
    private BigDecimal value;

    private Currency() {
    }

    public Currency(Type type, BigDecimal value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
