package com.makeupdepot.inventory.repo.domain.obj;

/**
 * Created by chassiness on 11/21/16.
 */
public class Margin {
    int floor, ceiling;
    Margin(int floor, int ceiling) { this.floor = floor; this.ceiling = ceiling; }

    static final Margin PCT35 = new Margin(0, 34);
    static final Margin PCT30 = new Margin(35, 49);
    static final Margin PCT25 = new Margin(50, 64);
    static final Margin PCT20 = new Margin(65, Integer.MAX_VALUE);
}
