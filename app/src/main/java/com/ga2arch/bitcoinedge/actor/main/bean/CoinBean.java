package com.ga2arch.bitcoinedge.actor.main.bean;

import com.ga2arch.bitcoinedge.domain.LoggableEntity;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class CoinBean extends LoggableEntity {
    private String id;
    private String name;
    private String symbol;
    private String rank;
    private String priceUsd;
    private String priceEur;
    private String priceBtc;
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

    public String getPriceUsd() {
        return priceUsd;
    }

    public String getPriceBtc() {
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

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public void setPriceBtc(String priceBtc) {
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

    public String getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(String priceEur) {
        this.priceEur = priceEur;
    }
}
