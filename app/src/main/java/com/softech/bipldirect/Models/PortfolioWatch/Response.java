package com.softech.bipldirect.Models.PortfolioWatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Response {

    @SerializedName("cash")
    @Expose
    private Cash cash;
    @SerializedName("MSGTYPE")
    @Expose
    private String MSGTYPE;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param cash
     * @param cash
     */
    public Response(Cash cash, String MSGTYPE) {
        this.cash = cash;
        this.MSGTYPE = MSGTYPE;
    }

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }
/**
     * @return The symbols
     */


    /**
     * @param symbols The symbols
     */


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
