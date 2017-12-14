package com.ga2arch.cryptoedge.actor.main.bean;

import com.ga2arch.cryptoedge.domain.LoggableEntity;

public class CoinsErrorBean extends LoggableEntity {
    private String error;

    public CoinsErrorBean(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
