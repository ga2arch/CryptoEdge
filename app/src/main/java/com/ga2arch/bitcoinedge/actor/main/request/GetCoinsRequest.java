package com.ga2arch.bitcoinedge.actor.main.request;

import com.ga2arch.bitcoinedge.domain.Request;

public class GetCoinsRequest extends Request {

    public GetCoinsRequest() {
    }

    @Override
    public String getId() {
        return getClass().getSimpleName();
    }

    @Override
    public long getVersion() {
        return 0;
    }
}
