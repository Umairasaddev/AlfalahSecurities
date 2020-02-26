package com.softech.bipldirect.Models.MarketModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Response {

    @SerializedName("exchanges")
    @Expose
    private List<Exchange> exchanges = new ArrayList<Exchange>();
    @SerializedName("symbols")
    @Expose
    private List<MarketSymbol> symbols = new ArrayList<MarketSymbol>();
    @SerializedName("MSGTYPE")
    @Expose
    private String MSGTYPE;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param exchanges
     * @param symbols
     * @param MSGTYPE
     */
    public Response(List<Exchange> exchanges, List<MarketSymbol> symbols, String MSGTYPE) {
        this.exchanges = exchanges;
        this.symbols = symbols;
        this.MSGTYPE = MSGTYPE;
    }

    /**
     * @return The exchanges
     */
    public List<Exchange> getExchanges() {
        return exchanges;
    }

    /**
     * @param exchanges The exchanges
     */
    public void setExchanges(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    /**
     * @return The symbols
     */
    public List<MarketSymbol> getSymbols() {
        return symbols;
    }

    /**
     * @param symbols The symbols
     */
    public void setSymbols(List<MarketSymbol> symbols) {
        this.symbols = symbols;
    }

    /**
     * @return The MSGTYPE
     */
    public String getMSGTYPE() {
        return MSGTYPE;
    }

    /**
     * @param MSGTYPE The MSGTYPE
     */
    public void setMSGTYPE(String MSGTYPE) {
        this.MSGTYPE = MSGTYPE;
    }

}
