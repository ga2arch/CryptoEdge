package com.ga2arch.cryptoedge.domain;

import com.google.gson.Gson;

public class LoggableEntity {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
