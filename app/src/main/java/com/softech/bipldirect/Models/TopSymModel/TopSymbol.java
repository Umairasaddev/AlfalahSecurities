package com.softech.bipldirect.Models.TopSymModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TopSymbol {

    @SerializedName("dataType")
    @Expose
    private String dataType;
    @SerializedName("lastTradePrice")
    @Expose
    private String lastTradePrice;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("turnover")
    @Expose
    private String turnover;
    @SerializedName("change")
    @Expose
    private String change;

    /**
     * No args constructor for use in serialization
     */
    public TopSymbol() {
    }

    /**
     * @param dataType
     * @param symbol
     * @param lastTradePrice
     * @param change
     * @param turnover
     */
    public TopSymbol(String dataType, String lastTradePrice, String symbol, String turnover, String change) {
        this.dataType = dataType;
        this.lastTradePrice = lastTradePrice;
        this.symbol = symbol;
        this.turnover = turnover;
        this.change = change;
    }

    /**
     * @return The dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType The dataType
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return The lastTradePrice
     */
    public String getLastTradePrice() {
        return lastTradePrice;
    }

    /**
     * @param lastTradePrice The lastTradePrice
     */
    public void setLastTradePrice(String lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
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
     * @return The turnover
     */
    public String getTurnover() {
        return turnover;
    }

    /**
     * @param turnover The turnover
     */
    public void setTurnover(String turnover) {
        this.turnover = turnover;
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

}
