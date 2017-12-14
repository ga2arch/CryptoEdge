package com.ga2arch.bitcoinedge.domain;

import java.util.Date;

public abstract class Request extends LoggableEntity {

    private Date timestamp;
    private long version;

    abstract public String getId();

    abstract public long getVersion();

    public Date getTimestamp() {
        return timestamp;
    }
}
