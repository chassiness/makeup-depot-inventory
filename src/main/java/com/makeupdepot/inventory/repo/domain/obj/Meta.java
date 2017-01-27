package com.makeupdepot.inventory.repo.domain.obj;

/**
 * Created by chassiness on 1/18/17.
 */
public class Meta {
    public enum PaymentType {
        BDO, BPI, SECURITYBANK, EWB, PAYPAL
    }

    public enum OrderStatus {
        NEW("New"),
        CONFIRMED("Confirmed"), //Confirmed means downPayment/payment received.
        READYTOSHIP("Ready to Ship"),
        SHIPPED("Shipped"),
        CANCELLED("Cancelled");

        private final String prettyName;

        OrderStatus(String text) {
            this.prettyName = text;
        }

        public String getPrettyName() {
            return prettyName;
        }
    }

    public enum Platform {
        INSTAGRAM("Instagram"),
        FACEBOOK("Facebook"),
        SHOPEE("Shopee"),
        VIBER("Viber");

        private final String prettyName;

        Platform(String prettyName) {
            this.prettyName = prettyName;
        }

        public String getPrettyName() {
            return prettyName;
        }
    }

    public enum PurchaseStatus {
        PURCHASED("Purchased"),
        ARRIVEDINCA("Arrived in CA"),
        ONHAND("On Hand"),
        SHIPPINGTOMNL("Shipping to MNL"),
        FORPURCHASE("For Purchase"),
        RETURNED("Returned"),
        CANCELLED("Cancelled");

        private final String prettyName;

        PurchaseStatus(String text) {
            this.prettyName = text;
        }

        public String getPrettyName() {
            return prettyName;
        }
    }
}
