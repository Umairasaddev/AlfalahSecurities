
package com.softech.bipldirect.Models.NetShareModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetShareCustody {

    @SerializedName("physicalTradable")
    @Expose
    private String physicalTradable;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("regular")
    @Expose
    private String regular;
    @SerializedName("cdcTradable")
    @Expose
    private String cdcTradable;
    @SerializedName("closeRate")
    @Expose
    private String closeRate;
    @SerializedName("net")
    @Expose
    private String net;
    @SerializedName("corporateActivity")
    @Expose
    private String corporateActivity;
    @SerializedName("spot")
    @Expose
    private String spot;
    @SerializedName("custodyBalance")
    @Expose
    private String custodyBalance;
    @SerializedName("unregistered")
    @Expose
    private String unregistered;
    @SerializedName("forward")
    @Expose
    private String forward;
    @SerializedName("registered")
    @Expose
    private String registered;
    @SerializedName("marketValue")
    @Expose
    private String marketValue;
    @SerializedName("symbolName")
    @Expose
    private String symbolName;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NetShareCustody() {
    }

    /**
     * 
     * @param physicalTradable
     * @param symbol
     * @param regular
     * @param cdcTradable
     * @param closeRate
     * @param net
     * @param corporateActivity
     * @param spot
     * @param custodyBalance
     * @param unregistered
     * @param forward
     * @param registered
     * @param marketValue
     * @param symbolName
     */
    public NetShareCustody(String physicalTradable, String symbol, String regular, String cdcTradable, String closeRate, String net, String corporateActivity, String spot, String custodyBalance, String unregistered, String forward, String registered, String marketValue, String symbolName) {
        super();
        this.physicalTradable = physicalTradable;
        this.symbol = symbol;
        this.regular = regular;
        this.cdcTradable = cdcTradable;
        this.closeRate = closeRate;
        this.net = net;
        this.corporateActivity = corporateActivity;
        this.spot = spot;
        this.custodyBalance = custodyBalance;
        this.unregistered = unregistered;
        this.forward = forward;
        this.registered = registered;
        this.marketValue = marketValue;
        this.symbolName = symbolName;
    }

    public String getPhysicalTradable() {
        return physicalTradable;
    }

    public void setPhysicalTradable(String physicalTradable) {
        this.physicalTradable = physicalTradable;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getCdcTradable() {
        return cdcTradable;
    }

    public void setCdcTradable(String cdcTradable) {
        this.cdcTradable = cdcTradable;
    }

    public String getCloseRate() {
        return closeRate;
    }

    public void setCloseRate(String closeRate) {
        this.closeRate = closeRate;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getCorporateActivity() {
        return corporateActivity;
    }

    public void setCorporateActivity(String corporateActivity) {
        this.corporateActivity = corporateActivity;
    }

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getCustodyBalance() {
        return custodyBalance;
    }

    public void setCustodyBalance(String custodyBalance) {
        this.custodyBalance = custodyBalance;
    }

    public String getUnregistered() {
        return unregistered;
    }

    public void setUnregistered(String unregistered) {
        this.unregistered = unregistered;
    }

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

}
