
package com.softech.bipldirect.Models.CashBookModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashDatum {

    @SerializedName("transDate")
    @Expose
    private String transDate;
    @SerializedName("index")
    @Expose
    private Integer index;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("debit")
    @Expose
    private String debit;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("runningBalance")
    @Expose
    private String runningBalance;

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getRunningBalance() {
        return runningBalance;
    }

    public void setRunningBalance(String runningBalance) {
        this.runningBalance = runningBalance;
    }

}
