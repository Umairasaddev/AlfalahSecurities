package com.softech.bipldirect.Models.PortfolioModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Response {

    @SerializedName("symbols")
    @Expose
    private List<PortfolioSymbol> symbols = new ArrayList<PortfolioSymbol>();
    @SerializedName("MSGTYPE")
    @Expose
    private String MSGTYPE;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param symbols
     * @param MSGTYPE
     */
    public Response(List<PortfolioSymbol> symbols, String MSGTYPE) {
        this.symbols = symbols;
        this.MSGTYPE = MSGTYPE;
    }

    /**
     * @return The symbols
     */
    public List<PortfolioSymbol> getSymbols() {
        return symbols;
    }

    /**
     * @param symbols The symbols
     */
    public void setSymbols(List<PortfolioSymbol> symbols) {
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
