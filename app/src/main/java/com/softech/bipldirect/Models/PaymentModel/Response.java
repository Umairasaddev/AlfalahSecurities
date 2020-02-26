
package com.softech.bipldirect.Models.PaymentModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("withdrawalLimit")
    @Expose
    private String withdrawalLimit;
    @SerializedName("MSGTYPE")
    @Expose
    private String mSGTYPE;
    @SerializedName("pendingWithdrawal")
    @Expose
    private String pendingWithdrawal;
    @SerializedName("cashBalance")
    @Expose
    private String cashBalance;

    /**
     * @return The withdrawalLimit
     */
    public String getWithdrawalLimit() {
        return withdrawalLimit;
    }

    /**
     * @param withdrawalLimit The withdrawalLimit
     */
    public void setWithdrawalLimit(String withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
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

    /**
     * @return The pendingWithdrawal
     */
    public String getPendingWithdrawal() {
        return pendingWithdrawal;
    }

    /**
     * @param pendingWithdrawal The pendingWithdrawal
     */
    public void setPendingWithdrawal(String pendingWithdrawal) {
        this.pendingWithdrawal = pendingWithdrawal;
    }

    /**
     * @return The cashBalance
     */
    public String getCashBalance() {
        return cashBalance;
    }

    /**
     * @param cashBalance The cashBalance
     */
    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }

}
