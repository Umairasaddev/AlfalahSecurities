package com.softech.bipldirect.Models.MarketModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MarketSymbol {

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

    @SerializedName("180AvgVolume")
    @Expose
    private String AvgVol180;
    @SerializedName("180HighPrice")
    @Expose
    private String HighPrice180;
    @SerializedName("180LowPrice")
    @Expose
    private String LowPrice180;
    @SerializedName("30AvgPrice")
    @Expose
    private String AvgPrice30;
    @SerializedName("30AvgVolume")
    @Expose
    private String AvgVolume30;
    @SerializedName("30HighPrice")
    @Expose
    private String HighPrice30;
    @SerializedName("30LowPrice")
    @Expose
    private String LowPrice30;
    @SerializedName("52WAvgPrice")
    @Expose
    private String WAvgPrice52;
    @SerializedName("52WAvgVolume")
    @Expose
    private String WAvgVolume52;
    @SerializedName("52WHighPrice")
    @Expose
    private String WHighPrice52;
    @SerializedName("52WLowPrice")
    @Expose
    private String WLowPrice52;
    @SerializedName("90AvgPrice")
    @Expose
    private String AvgPrice90;
    @SerializedName("90AvgVolume")
    @Expose
    private String AvgVolume90;
    @SerializedName("90HighPrice")
    @Expose
    private String HighPrice90;
    @SerializedName("90LowPrice")
    @Expose
    private String LowPrice90;
    @SerializedName("180AvgPrice")
    @Expose
    private String AvgPrice180;

    public String getAvgPrice180() {
        return AvgPrice180;
    }

    public void setAvgPrice180(String avgPrice180) {
        AvgPrice180 = avgPrice180;
    }

    /**
     * No args constructor for use in serialization
     */
    public MarketSymbol() {
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
    public MarketSymbol(String sellPrice, String indicator, String symbol, String change, String turnOver, String lowPrice, String lotSize, String exchangeCode, String sellVolume, String buyVolume, String market, String upperLimit, String lowerLimit, String name, String current, String buyPrice, String highPrice, String changePer,String avgVol180, String highPrice180, String lowPrice180, String avgPrice30, String avgVolume30, String highPrice30, String lowPrice30, String WAvgPrice52, String WAvgVolume52, String WHighPrice52, String WLowPrice52, String avgPrice90, String avgVolume90, String highPrice90, String lowPrice90,String AvgPrice180) {
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
        AvgVol180 = avgVol180;
        HighPrice180 = highPrice180;
        LowPrice180 = lowPrice180;
        AvgPrice30 = avgPrice30;
        AvgVolume30 = avgVolume30;
        HighPrice30 = highPrice30;
        LowPrice30 = lowPrice30;
        this.WAvgPrice52 = WAvgPrice52;
        this.WAvgVolume52 = WAvgVolume52;
        this.WHighPrice52 = WHighPrice52;
        this.WLowPrice52 = WLowPrice52;
        this.AvgPrice90 = avgPrice90;
        this.AvgVolume90 = avgVolume90;
        this.HighPrice90 = highPrice90;
        this.LowPrice90 = lowPrice90;
        this.AvgPrice180=AvgPrice180;

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
    public String getAvgVol180() {
        return AvgVol180;
    }

    public void setAvgVol180(String avgVol180) {
        AvgVol180 = avgVol180;
    }

    public String getHighPrice180() {
        return HighPrice180;
    }

    public void setHighPrice180(String highPrice180) {
        HighPrice180 = highPrice180;
    }

    public String getLowPrice180() {
        return LowPrice180;
    }

    public void setLowPrice180(String lowPrice180) {
        LowPrice180 = lowPrice180;
    }

    public String getAvgPrice30() {
        return AvgPrice30;
    }

    public void setAvgPrice30(String avgPrice30) {
        AvgPrice30 = avgPrice30;
    }

    public String getAvgVolume30() {
        return AvgVolume30;
    }

    public void setAvgVolume30(String avgVolume30) {
        AvgVolume30 = avgVolume30;
    }

    public String getHighPrice30() {
        return HighPrice30;
    }

    public void setHighPrice30(String highPrice30) {
        HighPrice30 = highPrice30;
    }

    public String getLowPrice30() {
        return LowPrice30;
    }

    public void setLowPrice30(String lowPrice30) {
        LowPrice30 = lowPrice30;
    }

    public String getWAvgPrice52() {
        return WAvgPrice52;
    }

    public void setWAvgPrice52(String WAvgPrice52) {
        this.WAvgPrice52 = WAvgPrice52;
    }

    public String getWAvgVolume52() {
        return WAvgVolume52;
    }

    public void setWAvgVolume52(String WAvgVolume52) {
        this.WAvgVolume52 = WAvgVolume52;
    }

    public String getWHighPrice52() {
        return WHighPrice52;
    }

    public void setWHighPrice52(String WHighPrice52) {
        this.WHighPrice52 = WHighPrice52;
    }

    public String getWLowPrice52() {
        return WLowPrice52;
    }

    public void setWLowPrice52(String WLowPrice52) {
        this.WLowPrice52 = WLowPrice52;
    }

    public String getAvgPrice90() {
        return AvgPrice90;
    }

    public void setAvgPrice90(String avgPrice90) {
        AvgPrice90 = avgPrice90;
    }

    public String getAvgVolume90() {
        return AvgVolume90;
    }

    public void setAvgVolume90(String avgVolume90) {
        AvgVolume90 = avgVolume90;
    }

    public String getHighPrice90() {
        return HighPrice90;
    }

    public void setHighPrice90(String highPrice90) {
        HighPrice90 = highPrice90;
    }

    public String getLowPrice90() {
        return LowPrice90;
    }

    public void setLowPrice90(String lowPrice90) {
        LowPrice90 = lowPrice90;
    }

    @Override
    public String toString() {
        return "MarketSymbol{" +
                "sellPrice='" + sellPrice + '\'' +
                ", indicator='" + indicator + '\'' +
                ", symbol='" + symbol + '\'' +
                ", change='" + change + '\'' +
                ", turnOver='" + turnOver + '\'' +
                ", lowPrice='" + lowPrice + '\'' +
                ", lotSize='" + lotSize + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", sellVolume='" + sellVolume + '\'' +
                ", buyVolume='" + buyVolume + '\'' +
                ", market='" + market + '\'' +
                ", upperLimit='" + upperLimit + '\'' +
                ", lowerLimit='" + lowerLimit + '\'' +
                ", name='" + name + '\'' +
                ", current='" + current + '\'' +
                ", buyPrice='" + buyPrice + '\'' +
                ", highPrice='" + highPrice + '\'' +
                ", changePer='" + changePer + '\'' +
                ", AvgVol180='" + AvgVol180 + '\'' +
                ", HighPrice180='" + HighPrice180 + '\'' +
                ", LowPrice180='" + LowPrice180 + '\'' +
                ", AvgPrice30='" + AvgPrice30 + '\'' +
                ", AvgVolume30='" + AvgVolume30 + '\'' +
                ", HighPrice30='" + HighPrice30 + '\'' +
                ", LowPrice30='" + LowPrice30 + '\'' +
                ", WAvgPrice52='" + WAvgPrice52 + '\'' +
                ", WAvgVolume52='" + WAvgVolume52 + '\'' +
                ", WHighPrice52='" + WHighPrice52 + '\'' +
                ", WLowPrice52='" + WLowPrice52 + '\'' +
                ", AvgPrice90='" + AvgPrice90 + '\'' +
                ", AvgVolume90='" + AvgVolume90 + '\'' +
                ", HighPrice90='" + HighPrice90 + '\'' +
                ", LowPrice90='" + LowPrice90 + '\'' +
                ", AvgPrice180='" + AvgPrice180 + '\'' +
                '}';
    }
}
