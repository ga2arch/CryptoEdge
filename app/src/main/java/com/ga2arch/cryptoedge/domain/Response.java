package com.ga2arch.cryptoedge.domain;

import java.util.Date;

public abstract class Response extends LoggableEntity {

    private Date timestamp;

    public Response(Date timestamp) {
        this.timestamp = timestamp;
    }

    abstract public String getId();

    abstract public long getVersion();

    public Date getTimestamp() {
        return timestamp;
    }
}
