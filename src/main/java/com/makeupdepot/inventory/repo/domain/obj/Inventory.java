package com.makeupdepot.inventory.repo.domain.obj;

import org.springframework.data.mongodb.core.index.CompoundIndex;

/**
 * Created by chassiness on 1/21/17.
 */

@CompoundIndex(name = "product", def = "{'brand': 1, 'type': 1, 'shade': 1}", unique = true)
public class Inventory {

    private String brand, type, shade;
    private int quantity;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
