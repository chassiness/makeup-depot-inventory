package com.makeupdepot.inventory.repo.domain.obj;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chassiness on 1/16/17.
 */
public class Client {
    @Id
    private String clientId;

    @NotNull @Indexed
    private String name;
    @NotNull
    private String shippingAddress, mobileNumber;

    public enum Channel {
        FB, IG, SHOPEE, VIBER
    }

    @Indexed
    private String username;
    private Channel channel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
