package com.makeupdepot.inventory.repo.domain.obj;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by chassiness on 12/5/16.
 */
public class DateTimeEntity {

    @NotNull private Date created;
    @NotNull private Date lastUpdated;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
