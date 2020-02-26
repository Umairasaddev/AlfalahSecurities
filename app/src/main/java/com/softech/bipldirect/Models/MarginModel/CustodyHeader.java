package com.softech.bipldirect.Models.MarginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saimshafqat on 23/11/2017.
 */

public class CustodyHeader extends MarginDetail
{
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

    private String clientcode;
    private String date;

    public String getClientcode() {
        return clientcode;
    }

    public void setClientcode(String clientcode) {
        this.clientcode = clientcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEquityFreeCash(String equityFreeCash) {
        this.equityFreeCash = equityFreeCash;
    }

    public void setEquityCashWithdrawalinProcess(String equityCashWithdrawalinProcess) {
        this.equityCashWithdrawalinProcess = equityCashWithdrawalinProcess;
    }

    public void setNetLiquidityEquity(String netLiquidityEquity) {
        this.netLiquidityEquity = netLiquidityEquity;
    }

    public void setEquityMarginRequired(String equityMarginRequired) {
        this.equityMarginRequired = equityMarginRequired;
    }

    public void setEquityReducedValue(String equityReducedValue) {
        this.equityReducedValue = equityReducedValue;
    }

    public void setEquityMarginPerc(String equityMarginPerc) {
        this.equityMarginPerc = equityMarginPerc;
    }

    public void setEquityProfitLos(String equityProfitLos) {
        this.equityProfitLos = equityProfitLos;
    }

    public void setEquityBlockedMTMProfit(String equityBlockedMTMProfit) {
        this.equityBlockedMTMProfit = equityBlockedMTMProfit;
    }

    public void setEquityCashWithdrawal(String equityCashWithdrawal) {
        this.equityCashWithdrawal = equityCashWithdrawal;
    }

    public void setEquityCashBalance(String equityCashBalance) {
        this.equityCashBalance = equityCashBalance;
    }

    public void setEquityMarginRequiredAsCash(String equityMarginRequiredAsCash) {
        this.equityMarginRequiredAsCash = equityMarginRequiredAsCash;
    }

    public void setEquityNetMarginCall(String equityNetMarginCall) {
        this.equityNetMarginCall = equityNetMarginCall;
    }

    public void setmSGTYPE(String mSGTYPE) {
        this.mSGTYPE = mSGTYPE;
    }

    public void setTotalWorth(String totalWorth) {
        this.totalWorth = totalWorth;
    }

    public void setOpenPositionEquity(String openPositionEquity) {
        this.openPositionEquity = openPositionEquity;
    }

    public void setEquityFreeMargin(String equityFreeMargin) {
        this.equityFreeMargin = equityFreeMargin;
    }

    public void setEquityCashMarginCall(String equityCashMarginCall) {
        this.equityCashMarginCall = equityCashMarginCall;
    }

    public CustodyHeader() {

    }

    public CustodyHeader(String equityFreeCash, String equityCashWithdrawalinProcess, String netLiquidityEquity, String equityMarginRequired, String equityReducedValue, String equityMarginPerc, String equityProfitLos, String equityBlockedMTMProfit, String equityCashWithdrawal, String equityCashBalance, String equityMarginRequiredAsCash, String equityNetMarginCall, String mSGTYPE, String totalWorth, String openPositionEquity, String equityFreeMargin, String equityCashMarginCall,String clientcode, String date) {
        this.clientcode = clientcode;
        this.date = date;
        this.equityFreeCash = equityFreeCash;
        this.equityCashWithdrawalinProcess = equityCashWithdrawalinProcess;
        this.netLiquidityEquity = netLiquidityEquity;
        this.equityMarginRequired = equityMarginRequired;
        this.equityReducedValue = equityReducedValue;
        this.equityMarginPerc = equityMarginPerc;
        this.equityProfitLos = equityProfitLos;
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

    public String getEquityFreeCash() {
        return equityFreeCash;
    }

    public String getEquityCashWithdrawalinProcess() {
        return equityCashWithdrawalinProcess;
    }

    public String getNetLiquidityEquity() {
        return netLiquidityEquity;
    }

    public String getEquityMarginRequired() {
        return equityMarginRequired;
    }

    public String getEquityReducedValue() {
        return equityReducedValue;
    }

    public String getEquityMarginPerc() {
        return equityMarginPerc;
    }

    public String getEquityProfitLos() {
        return equityProfitLos;
    }

    public String getEquityBlockedMTMProfit() {
        return equityBlockedMTMProfit;
    }

    public String getEquityCashWithdrawal() {
        return equityCashWithdrawal;
    }

    public String getEquityCashBalance() {
        return equityCashBalance;
    }

    public String getEquityMarginRequiredAsCash() {
        return equityMarginRequiredAsCash;
    }

    public String getEquityNetMarginCall() {
        return equityNetMarginCall;
    }

    public String getmSGTYPE() {
        return mSGTYPE;
    }

    public String getTotalWorth() {
        return totalWorth;
    }

    public String getOpenPositionEquity() {
        return openPositionEquity;
    }

    public String getEquityFreeMargin() {
        return equityFreeMargin;
    }

    public String getEquityCashMarginCall() {
        return equityCashMarginCall;
    }
}

