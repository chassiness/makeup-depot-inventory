package com.makeupdepot.inventory.repo.domain.obj;

import com.makeupdepot.inventory.misc.Currency;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chassiness on 12/14/16.
 */
@Data
public class Order {
    @Id
    private String orderId;

    @NotNull
    private List<ProductPrice> products;
    private Currency totalAmount;

    @DBRef
    private Client client;

    private Payment downPayment;
    private Payment balancePayment;
    private BigDecimal balanceAmt;
    private BigDecimal shippingFee;

    private OrderStatus status;
    private String batch;
    private boolean shipped;
    private String trackingDetails;
    private List<String> notes;

    private Order() {
    }

    public Order(List<ProductPrice> products, Client client) {
        this.products = products;
        this.client = client;
    }

    public enum PaymentType {
        BDO, BPI, SECURITYBANK, EWB, PAYPAL
    }

    //Confirmed means downPayment/payment received.
    public enum OrderStatus {
        NEW, CONFIRMED, READYTOSHIP, SHIPPED, CANCELLED
    }

    @Data
    class Payment {
        private Date paymentDate;
        private BigDecimal amount;
        private PaymentType paymentType;
    }

    public void addNote(String note) {
        if (this.notes == null) {
            notes = new ArrayList<>();
        }
        if (note != null && !note.isEmpty()) {
            notes.add(note);
        }
    }

    public BigDecimal calculateBalance() {
        BigDecimal paymentMade = new BigDecimal(0);
        if (this.downPayment != null) {
            paymentMade.add(this.downPayment.amount);
        }
        if (this.balancePayment != null) {
            paymentMade.add(this.balancePayment.amount);
        }

        return totalAmount.getValue().subtract(paymentMade);
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = new BigDecimal(0);
        for (ProductPrice product : products) {
            total.add(product.getRetailPrice().getValue());
        }
        return total.add(shippingFee);
    }
}
