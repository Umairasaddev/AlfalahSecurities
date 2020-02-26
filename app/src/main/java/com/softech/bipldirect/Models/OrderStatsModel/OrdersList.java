package com.softech.bipldirect.Models.OrderStatsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersList {

    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("orderType")
    @Expose
    private String orderType;
    @SerializedName("execVolume")
    @Expose
    private String execVolume;
    @SerializedName("triggerPrice")
    @Expose
    private String triggerPrice;
    @SerializedName("marketRate")
    @Expose
    private String marketRate;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("market")
    @Expose
    private String market;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("side")
    @Expose
    private String side;
    @SerializedName("volume")
    @Expose
    private Integer volume;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("discVol")
    @Expose
    private String discVol;
    @SerializedName("orderProp")
    @Expose
    private String orderProp;
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("exchange")
    @Expose
    private String exchange;
    @SerializedName("client")
    @Expose
    private String client;

    /**
     * No args constructor for use in serialization
     */
    public OrdersList() {
    }

    /**
     * @param market
     * @param orderNo
     * @param price
     * @param symbol
     * @param side
     * @param orderType
     * @param execVolume
     * @param volume
     * @param value
     * @param date
     * @param identifier
     * @param exchange
     */
    public OrdersList(String symbol, String orderType, String execVolume, String triggerPrice, String marketRate, String date, String market, String orderNo, Double price, String side, Integer volume, Double value, String discVol, String orderProp, String identifier, String exchange,String client) {

        this.symbol = symbol;
        this.orderType = orderType;
        this.execVolume = execVolume;
        this.triggerPrice = triggerPrice;
        this.marketRate = marketRate;
        this.date = date;
        this.market = market;
        this.orderNo = orderNo;
        this.price = price;
        this.side = side;
        this.volume = volume;
        this.value = value;
        this.discVol = discVol;
        this.orderProp = orderProp;
        this.identifier = identifier;
        this.exchange = exchange;
        this.client=client;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getExecVolume() {

        Integer val = 0;
        if(execVolume!=null && execVolume.length()>0){
            try {
                val = Integer.parseInt(execVolume);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return val;
    }

    public void setExecVolume(String execVolume) {
        this.execVolume = execVolume;
    }

    public String getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(String triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public String getMarketRate() {
        return marketRate;
    }

    public void setMarketRate(String marketRate) {
        this.marketRate = marketRate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDiscVol() {
        return discVol;
    }

    public void setDiscVol(String discVol) {
        this.discVol = discVol;
    }

    public String getOrderProp() {
        return orderProp;
    }

    public void setOrderProp(String orderProp) {
        this.orderProp = orderProp;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getClient() {
        return client;
    }

}