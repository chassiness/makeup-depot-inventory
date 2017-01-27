package com.makeupdepot.inventory.repo.domain.obj;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by chassiness on 1/16/17.
 */
@Data
public class Client {
    @Id
    private String clientId;

    @NotNull @Indexed
    private String name;

    @NotNull
    private String shippingAddress, mobileNumber;

    @Indexed
    private List<Channel> socialMediaChannels;

    @Data
    class Channel {
        Meta.Platform platform;
        String username;
    }
}
