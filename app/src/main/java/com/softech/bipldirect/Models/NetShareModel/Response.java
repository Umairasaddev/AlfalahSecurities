
package com.softech.bipldirect.Models.NetShareModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("MSGTYPE")
    @Expose
    private String mSGTYPE;
    @SerializedName("netSahreCustodayData")
    @Expose
    private List<NetShareCustody> netShareCustody = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param mSGTYPE
     * @param netShareCustody
     */
    public Response(String mSGTYPE, List<NetShareCustody> netShareCustody) {
        super();
        this.mSGTYPE = mSGTYPE;
        this.netShareCustody = netShareCustody;
    }

    public String getMSGTYPE() {
        return mSGTYPE;
    }

    public void setMSGTYPE(String mSGTYPE) {
        this.mSGTYPE = mSGTYPE;
    }

    public List<NetShareCustody> getNetShareCustody() {
        return netShareCustody;
    }

    public void setNetShareCustody(List<NetShareCustody> netShareCustody) {
        this.netShareCustody = netShareCustody;
    }

}
