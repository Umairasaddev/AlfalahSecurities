package com.softech.bipldirect.Models.MarketModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Exchange {

    @SerializedName("sellPrice")
    @Expose
    private String sellPrice;
    @SerializedName("indicator")
    @Expose
    private String indicator;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("change")
    @Expose
    private String change;
    @SerializedName("turnOver")
    @Expose
    private String turnOver;
    @SerializedName("lowPrice")
    @Expose
    private String lowPrice;
    @SerializedName("lotSize")
    @Expose
    private String lotSize;
    @SerializedName("exchangeCode")
    @Expose
    private String exchangeCode;
    @SerializedName("sellVolume")
    @Expose
    private String sellVolume;
    @SerializedName("buyVolume")
    @Expose
    private String buyVolume;
    @SerializedName("market")
    @Expose
    private String market;
    @SerializedName("upperLimit")
    @Expose
    private String upperLimit;
    @SerializedName("lowerLimit")
    @Expose
    private String lowerLimit;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("current")
    @Expose
    private String current;
    @SerializedName("buyPrice")
    @Expose
    private String buyPrice;
    @SerializedName("highPrice")
    @Expose
    private String highPrice;
    @SerializedName("changePer")
    @Expose
    private String changePer;

    /**
     * No args constructor for use in serialization
     */
    public Exchange() {
    }

    /**
     * @param sellPrice
     * @param indicator
     * @param symbol
     * @param change
     * @param turnOver
     * @param lowPrice
     * @param lotSize
     * @param exchangeCode
     * @param sellVolume
     * @param buyVolume
     * @param market
     * @param upperLimit
     * @param lowerLimit
     * @param name
     * @param buyPrice
     * @param current
     * @param highPrice
     * @param changePer
     */
    public Exchange(String sellPrice, String indicator, String symbol, String change, String turnOver, String lowPrice, String lotSize, String exchangeCode, String sellVolume, String buyVolume, String market, String upperLimit, String lowerLimit, String name, String current, String buyPrice, String highPrice, String changePer) {
        this.sellPrice = sellPrice;
        this.indicator = indicator;
        this.symbol = symbol;
        this.change = change;
        this.turnOver = turnOver;
        this.lowPrice = lowPrice;
        this.lotSize = lotSize;
        this.exchangeCode = exchangeCode;
        this.sellVolume = sellVolume;
        this.buyVolume = buyVolume;
        this.market = market;
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.name = name;
        this.current = current;
        this.buyPrice = buyPrice;
        this.highPrice = highPrice;
        this.changePer = changePer;
    }

    /**
     * @return The sellPrice
     */
    public String getSellPrice() {
        return sellPrice;
    }

    /**
     * @param sellPrice The sellPrice
     */
    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * @return The indicator
     */
    public String getIndicator() {
        return indicator;
    }

    /**
     * @param indicator The indicator
     */
    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    /**
     * @return The symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol The symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return The change
     */
    public String getChange() {
        return change;
    }

    /**
     * @param change The change
     */
    public void setChange(String change) {
        this.change = change;
    }

    /**
     * @return The turnOver
     */
    public String getTurnOver() {
        return turnOver;
    }

    /**
     * @param turnOver The turnOver
     */
    public void setTurnOver(String turnOver) {
        this.turnOver = turnOver;
    }

    /**
     * @return The lowPrice
     */
    public String getLowPrice() {
        return lowPrice;
    }

    /**
     * @param lowPrice The lowPrice
     */
    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    /**
     * @return The lotSize
     */
    public String getLotSize() {
        return lotSize;
    }

    /**
     * @param lotSize The lotSize
     */
    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }

    /**
     * @return The exchangeCode
     */
    public String getExchangeCode() {
        return exchangeCode;
    }

    /**
     * @param exchangeCode The exchangeCode
     */
    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    /**
     * @return The sellVolume
     */
    public String getSellVolume() {
        return sellVolume;
    }

    /**
     * @param sellVolume The sellVolume
     */
    public void setSellVolume(String sellVolume) {
        this.sellVolume = sellVolume;
    }

    /**
     * @return The buyVolume
     */
    public String getBuyVolume() {
        return buyVolume;
    }

    /**
     * @param buyVolume The buyVolume
     */
    public void setBuyVolume(String buyVolume) {
        this.buyVolume = buyVolume;
    }

    /**
     * @return The market
     */
    public String getMarket() {
        return market;
    }

    /**
     * @param market The market
     */
    public void setMarket(String market) {
        this.market = market;
    }

    /**
     * @return The upperLimit
     */
    public String getUpperLimit() {
        return upperLimit;
    }

    /**
     * @param upperLimit The upperLimit
     */
    public void setUpperLimit(String upperLimit) {
        this.upperLimit = upperLimit;
    }

    /**
     * @return The lowerLimit
     */
    public String getLowerLimit() {
        return lowerLimit;
    }

    /**
     * @param lowerLimit The lowerLimit
     */
    public void setLowerLimit(String lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The current
     */
    public String getCurrent() {
        return current;
    }

    /**
     * @param current The current
     */
    public void setCurrent(String current) {
        this.current = current;
    }

    /**
     * @return The buyPrice
     */
    public String getBuyPrice() {
        return buyPrice;
    }

    /**
     * @param buyPrice The buyPrice
     */
    public void setBuyPrice(String buyPrice) {
        this.buyPrice = buyPrice;
    }

    /**
     * @return The highPrice
     */
    public String getHighPrice() {
        return highPrice;
    }

    /**
     * @param highPrice The highPrice
     */
    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    /**
     * @return The changePer
     */
    public String getChangePer() {
        return changePer;
    }

    /**
     * @param changePer The changePer
     */
    public void setChangePer(String changePer) {
        this.changePer = changePer;
    }

}
