package com.softech.bipldirect.Models.SymbolsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Symbol {

    @SerializedName("market")
    @Expose
    private String market;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("exchangeCode")
    @Expose
    private String exchangeCode;

    /**
     * No args constructor for use in serialization
     */
    public Symbol() {
    }

    /**
     * @param market
     * @param symbol
     * @param exchangeCode
     */
    public Symbol(String market, String symbol, String exchangeCode) {
        this.market = market;
        this.symbol = symbol;
        this.exchangeCode = exchangeCode;
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

}
