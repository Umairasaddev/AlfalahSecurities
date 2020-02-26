
package com.softech.bipldirect.Models.CashBookModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("cashData")
    @Expose
    private List<CashDatum> cashData = null;
    @SerializedName("MSGTYPE")
    @Expose
    private String mSGTYPE;

    public List<CashDatum> getCashData() {
        return cashData;
    }

    public void setCashData(List<CashDatum> cashData) {
        this.cashData = cashData;
    }

    public String getMSGTYPE() {
        return mSGTYPE;
    }

    public void setMSGTYPE(String mSGTYPE) {
        this.mSGTYPE = mSGTYPE;
    }

}
