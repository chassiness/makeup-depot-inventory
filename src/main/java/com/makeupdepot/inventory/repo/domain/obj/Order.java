package com.makeupdepot.inventory.repo.domain.obj;

import com.makeupdepot.inventory.custom.annotation.CascadeSave;
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
    private List<Product> products;
    private Currency totalAmount;

    @DBRef
    @CascadeSave
    private Client client;

    private Payment downPayment;
    private Payment balancePayment;
    private BigDecimal balanceAmt;
    private BigDecimal shippingFee;

    private Meta.OrderStatus status;
    private String batch;
    private boolean shipped;
    private String trackingDetails;
    private List<String> notes;
    private Meta.Platform platform;

    private Order() {
    }

    public Order(List<Product> products, Client client) {
        this.products = products;
        this.client = client;
    }

    @Data
    class Payment {
        private Date paymentDate;
        private BigDecimal amount;
        private Meta.PaymentType paymentType;
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
        for (Product product : products) {
            total.add(product.getRetailPrice().getValue());
        }
        return total.add(shippingFee);
    }

    public Meta.OrderStatus getStatus() {
        return status;
    }

    public void setStatus(Meta.OrderStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
