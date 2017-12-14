package com.ga2arch.cryptoedge.actor.main.bean;

import com.ga2arch.cryptoedge.domain.LoggableEntity;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class CoinBean extends LoggableEntity {
    private String id;
    private String name;
    private String symbol;
    private String rank;
    private BigDecimal priceUsd;
    private BigDecimal priceEur;
    private BigDecimal priceBtc;
    @SerializedName("percent_change_1h")
    private BigDecimal percentChange1h;
    @SerializedName("percent_change_24h")
    private BigDecimal percentChange24h;
    @SerializedName("percent_change_7d")
    private BigDecimal percentChange7d;
    private String lastUpdated;

    public CoinBean() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getRank() {
        return rank;
    }

    public BigDecimal getPriceUsd() {
        return priceUsd;
    }

    public BigDecimal getPriceBtc() {
        return priceBtc;
    }

    public BigDecimal getPercentChange1h() {
        return percentChange1h;
    }

    public BigDecimal getPercentChange24h() {
        return percentChange24h;
    }

    public BigDecimal getPercentChange7d() {
        return percentChange7d;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setPriceUsd(BigDecimal priceUsd) {
        this.priceUsd = priceUsd;
    }

    public void setPriceBtc(BigDecimal priceBtc) {
        this.priceBtc = priceBtc;
    }

    public void setPercentChange1h(BigDecimal percentChange1h) {
        this.percentChange1h = percentChange1h;
    }

    public void setPercentChange24h(BigDecimal percentChange24h) {
        this.percentChange24h = percentChange24h;
    }

    public void setPercentChange7d(BigDecimal percentChange7d) {
        this.percentChange7d = percentChange7d;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(BigDecimal priceEur) {
        this.priceEur = priceEur;
    }
}
