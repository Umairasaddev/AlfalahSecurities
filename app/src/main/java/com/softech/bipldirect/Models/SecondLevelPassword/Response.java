
package com.softech.bipldirect.Models.SecondLevelPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("secondLevPwd")
    @Expose
    private String secondLevPwd;
    @SerializedName("MSGTYPE")
    @Expose
    private String mSGTYPE;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    public String getSecondLevPwd() {
        return secondLevPwd;
    }

    public void setSecondLevPwd(String secondLevPwd) {
        this.secondLevPwd = secondLevPwd;
    }

    public String getMSGTYPE() {
        return mSGTYPE;
    }

    public void setMSGTYPE(String mSGTYPE) {
        this.mSGTYPE = mSGTYPE;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
