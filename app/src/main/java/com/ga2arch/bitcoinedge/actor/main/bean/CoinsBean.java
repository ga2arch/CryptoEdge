package com.ga2arch.bitcoinedge.actor.main.bean;

import com.ga2arch.bitcoinedge.domain.LoggableEntity;

import java.util.List;

public class CoinsBean extends LoggableEntity {
    List<CoinBean> coins;

    public CoinsBean(List<CoinBean> coins) {
        this.coins = coins;
    }

    public List<CoinBean> getCoins() {
        return coins;
    }
}
