package com.softech.bipldirect.Models.MarginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Developed by Hasham.Tahir on 7/18/2016.
 */
public class Response {

    @SerializedName("equityFreeCash")
    @Expose
    private String equityFreeCash;
    @SerializedName("equityCashWithdrawalinProcess")
    @Expose
    private String equityCashWithdrawalinProcess;
    @SerializedName("netLiquidityEquity")
    @Expose
    private String netLiquidityEquity;
    @SerializedName("equityMarginRequired")
    @Expose
    private String equityMarginRequired;
    @SerializedName("equityReducedValue")
    @Expose
    private String equityReducedValue;
    @SerializedName("equityMarginPerc")
    @Expose
    private String equityMarginPerc;
    @SerializedName("equityProfitLos")
    @Expose
    private String equityProfitLos;
    @SerializedName("custodyList")
    @Expose
    private List<CustodyList> custodyList = new ArrayList<CustodyList>();
    @SerializedName("equityBlockedMTMProfit")
    @Expose
    private String equityBlockedMTMProfit;
    @SerializedName("equityCashWithdrawal")
    @Expose
    private String equityCashWithdrawal;
    @SerializedName("equityCashBalance")
    @Expose
    private String equityCashBalance;
    @SerializedName("equityMarginRequiredAsCash")
    @Expose
    private String equityMarginRequiredAsCash;
    @SerializedName("equityNetMarginCall")
    @Expose
    private String equityNetMarginCall;
    @SerializedName("MSGTYPE")
    @Expose
    private String mSGTYPE;
    @SerializedName("totalWorth")
    @Expose
    private String totalWorth;
    @SerializedName("openPositionEquity")
    @Expose
    private String openPositionEquity;
    @SerializedName("equityFreeMargin")
    @Expose
    private String equityFreeMargin;
    @SerializedName("equityCashMarginCall")
    @Expose
    private String equityCashMarginCall;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param equityFreeCash
     * @param mSGTYPE
     * @param equityCashWithdrawalinProcess
     * @param netLiquidityEquity
     * @param equityMarginRequired
     * @param equityReducedValue
     * @param equityMarginPerc
     * @param equityProfitLos
     * @param custodyList
     * @param equityBlockedMTMProfit
     * @param equityCashWithdrawal
     * @param equityCashBalance
     * @param equityMarginRequiredAsCash
     * @param equityNetMarginCall
     * @param totalWorth
     * @param openPositionEquity
     * @param equityFreeMargin
     * @param equityCashMarginCall
     */
    public Response(String equityFreeCash, String equityCashWithdrawalinProcess, String netLiquidityEquity, String equityMarginRequired, String equityReducedValue, String equityMarginPerc, String equityProfitLos, List<CustodyList> custodyList, String equityBlockedMTMProfit, String equityCashWithdrawal, String equityCashBalance, String equityMarginRequiredAsCash, String equityNetMarginCall, String mSGTYPE, String totalWorth, String openPositionEquity, String equityFreeMargin, String equityCashMarginCall) {
        this.equityFreeCash = equityFreeCash;
        this.equityCashWithdrawalinProcess = equityCashWithdrawalinProcess;
        this.netLiquidityEquity = netLiquidityEquity;
        this.equityMarginRequired = equityMarginRequired;
        this.equityReducedValue = equityReducedValue;
        this.equityMarginPerc = equityMarginPerc;
        this.equityProfitLos = equityProfitLos;
        this.custodyList = custodyList;
        this.equityBlockedMTMProfit = equityBlockedMTMProfit;
        this.equityCashWithdrawal = equityCashWithdrawal;
        this.equityCashBalance = equityCashBalance;
        this.equityMarginRequiredAsCash = equityMarginRequiredAsCash;
        this.equityNetMarginCall = equityNetMarginCall;
        this.mSGTYPE = mSGTYPE;
        this.totalWorth = totalWorth;
        this.openPositionEquity = openPositionEquity;
        this.equityFreeMargin = equityFreeMargin;
        this.equityCashMarginCall = equityCashMarginCall;
    }

    /**
     * @return The equityFreeCash
     */
    public String getEquityFreeCash() {
        return equityFreeCash;
    }

    /**
     * @param equityFreeCash The equityFreeCash
     */
    public void setEquityFreeCash(String equityFreeCash) {
        this.equityFreeCash = equityFreeCash;
    }

    /**
     * @return The equityCashWithdrawalinProcess
     */
    public String getEquityCashWithdrawalinProcess() {
        return equityCashWithdrawalinProcess;
    }

    /**
     * @param equityCashWithdrawalinProcess The equityCashWithdrawalinProcess
     */
    public void setEquityCashWithdrawalinProcess(String equityCashWithdrawalinProcess) {
        this.equityCashWithdrawalinProcess = equityCashWithdrawalinProcess;
    }

    /**
     * @return The netLiquidityEquity
     */
    public String getNetLiquidityEquity() {
        return netLiquidityEquity;
    }

    /**
     * @param netLiquidityEquity The netLiquidityEquity
     */
    public void setNetLiquidityEquity(String netLiquidityEquity) {
        this.netLiquidityEquity = netLiquidityEquity;
    }

    /**
     * @return The equityMarginRequired
     */
    public String getEquityMarginRequired() {
        return equityMarginRequired;
    }

    /**
     * @param equityMarginRequired The equityMarginRequired
     */
    public void setEquityMarginRequired(String equityMarginRequired) {
        this.equityMarginRequired = equityMarginRequired;
    }

    /**
     * @return The equityReducedValue
     */
    public String getEquityReducedValue() {
        return equityReducedValue;
    }

    /**
     * @param equityReducedValue The equityReducedValue
     */
    public void setEquityReducedValue(String equityReducedValue) {
        this.equityReducedValue = equityReducedValue;
    }

    /**
     * @return The equityMarginPerc
     */
    public String getEquityMarginPerc() {
        return equityMarginPerc;
    }

    /**
     * @param equityMarginPerc The equityMarginPerc
     */
    public void setEquityMarginPerc(String equityMarginPerc) {
        this.equityMarginPerc = equityMarginPerc;
    }

    /**
     * @return The equityProfitLos
     */
    public String getEquityProfitLos() {
        return equityProfitLos;
    }

    /**
     * @param equityProfitLos The equityProfitLos
     */
    public void setEquityProfitLos(String equityProfitLos) {
        this.equityProfitLos = equityProfitLos;
    }

    /**
     * @return The custodyList
     */
    public List<CustodyList> getCustodyList() {
        return custodyList;
    }

    /**
     * @param custodyList The custodyList
     */
    public void setCustodyList(List<CustodyList> custodyList) {
        this.custodyList = custodyList;
    }

    /**
     * @return The equityBlockedMTMProfit
     */
    public String getEquityBlockedMTMProfit() {
        return equityBlockedMTMProfit;
    }

    /**
     * @param equityBlockedMTMProfit The equityBlockedMTMProfit
     */
    public void setEquityBlockedMTMProfit(String equityBlockedMTMProfit) {
        this.equityBlockedMTMProfit = equityBlockedMTMProfit;
    }

    /**
     * @return The equityCashWithdrawal
     */
    public String getEquityCashWithdrawal() {
        return equityCashWithdrawal;
    }

    /**
     * @param equityCashWithdrawal The equityCashWithdrawal
     */
    public void setEquityCashWithdrawal(String equityCashWithdrawal) {
        this.equityCashWithdrawal = equityCashWithdrawal;
    }

    /**
     * @return The equityCashBalance
     */
    public String getEquityCashBalance() {
        return equityCashBalance;
    }

    /**
     * @param equityCashBalance The equityCashBalance
     */
    public void setEquityCashBalance(String equityCashBalance) {
        this.equityCashBalance = equityCashBalance;
    }

    /**
     * @return The equityMarginRequiredAsCash
     */
    public String getEquityMarginRequiredAsCash() {
        return equityMarginRequiredAsCash;
    }

    /**
     * @param equityMarginRequiredAsCash The equityMarginRequiredAsCash
     */
    public void setEquityMarginRequiredAsCash(String equityMarginRequiredAsCash) {
        this.equityMarginRequiredAsCash = equityMarginRequiredAsCash;
    }

    /**
     * @return The equityNetMarginCall
     */
    public String getEquityNetMarginCall() {
        return equityNetMarginCall;
    }

    /**
     * @param equityNetMarginCall The equityNetMarginCall
     */
    public void setEquityNetMarginCall(String equityNetMarginCall) {
        this.equityNetMarginCall = equityNetMarginCall;
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
     * @return The totalWorth
     */
    public String getTotalWorth() {
        return totalWorth;
    }

    /**
     * @param totalWorth The totalWorth
     */
    public void setTotalWorth(String totalWorth) {
        this.totalWorth = totalWorth;
    }

    /**
     * @return The openPositionEquity
     */
    public String getOpenPositionEquity() {
        return openPositionEquity;
    }

    /**
     * @param openPositionEquity The openPositionEquity
     */
    public void setOpenPositionEquity(String openPositionEquity) {
        this.openPositionEquity = openPositionEquity;
    }

    /**
     * @return The equityFreeMargin
     */
    public String getEquityFreeMargin() {
        return equityFreeMargin;
    }

    /**
     * @param equityFreeMargin The equityFreeMargin
     */
    public void setEquityFreeMargin(String equityFreeMargin) {
        this.equityFreeMargin = equityFreeMargin;
    }

    /**
     * @return The equityCashMarginCall
     */
    public String getEquityCashMarginCall() {
        return equityCashMarginCall;
    }

    /**
     * @param equityCashMarginCall The equityCashMarginCall
     */
    public void setEquityCashMarginCall(String equityCashMarginCall) {
        this.equityCashMarginCall = equityCashMarginCall;
    }
}
