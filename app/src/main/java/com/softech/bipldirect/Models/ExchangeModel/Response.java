package com.softech.bipldirect.Models.ExchangeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @SerializedName("exchanges")
    @Expose
    private List<Exchange> exchanges = new ArrayList<Exchange>();
    @SerializedName("MSGTYPE")
    @Expose
    private String mSGTYPE;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param exchanges
     * @param mSGTYPE
     */
    public Response(List<Exchange> exchanges, String mSGTYPE) {
        this.exchanges = exchanges;
        this.mSGTYPE = mSGTYPE;
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
     * @return The mSGTYPE
     */
    public String getMSGTYPE() {
        return mSGTYPE;
    }

    /**
     * @param mSGTYPE The MSGTYPE
     */
    public void setMSGTYPE(String mSGTYPE) {
        this.mSGTYPE = mSGTYPE;
    }

}
