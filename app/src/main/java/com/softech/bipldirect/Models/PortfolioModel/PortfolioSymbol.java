package com.softech.bipldirect.Models.PortfolioModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class PortfolioSymbol extends Portfolio implements Serializable {

    @SerializedName("totalCost")
    @Expose
    private String totalCost;
    @SerializedName("capGainLoss")
    @Expose
    private String capGainLoss;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("retOfInv")
    @Expose
    private String retOfInv;
    @SerializedName("costPerUnit")
    @Expose
    private String costPerUnit;
    @SerializedName("volume")
    @Expose
    private String volume;
    @SerializedName("pfWeight")
    @Expose
    private String pfWeight;
    @SerializedName("currentPrice")
    @Expose
    private String currentPrice;
    @SerializedName("currentValue")
    @Expose
    private String currentValue;

    @SerializedName("announcementValue")
    @Expose
    private String announcementValue;

    public String getAnnouncementValue() {
        return announcementValue;
    }

    public void setAnnouncementValue(String announcementValue) {
        this.announcementValue = announcementValue;
    }

    public String getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(String announcementDate) {
        this.announcementDate = announcementDate;
    }

    @SerializedName("announcementDate")
    @Expose
    private String announcementDate;

    /**
     * No args constructor for use in serialization
     */
    public PortfolioSymbol() {
    }

    /**
     * @param capGainLoss
     * @param totalCost
     * @param symbol
     * @param costPerUnit
     * @param retOfInv
     * @param pfWeight
     * @param volume
     * @param currentPrice
     * @param currentValue
     */
    public PortfolioSymbol(String totalCost, String capGainLoss, String symbol, String retOfInv, String costPerUnit, String volume, String pfWeight, String currentPrice, String currentValue) {
        this.totalCost = totalCost;
        this.capGainLoss = capGainLoss;
        this.symbol = symbol;
        this.retOfInv = retOfInv;
        this.costPerUnit = costPerUnit;
        this.volume = volume;
        this.pfWeight = pfWeight;
        this.currentPrice = currentPrice;
        this.currentValue = currentValue;
    }

    /**
     * @return The totalCost
     */
    public String getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost The totalCost
     */
    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    /**
     * @return The capGainLoss
     */
    public String getCapGainLoss() {
        return capGainLoss;
    }

    /**
     * @param capGainLoss The capGainLoss
     */
    public void setCapGainLoss(String capGainLoss) {
        this.capGainLoss = capGainLoss;
    }

    /**
     * @return The symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol The symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return The retOfInv
     */
    public String getRetOfInv() {
        return retOfInv;
    }

    /**
     * @param retOfInv The retOfInv
     */
    public void setRetOfInv(String retOfInv) {
        this.retOfInv = retOfInv;
    }

    /**
     * @return The costPerUnit
     */
    public String getCostPerUnit() {
        return costPerUnit;
    }

    /**
     * @param costPerUnit The costPerUnit
     */
    public void setCostPerUnit(String costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    /**
     * @return The volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * @param volume The volume
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * @return The pfWeight
     */
    public String getPfWeight() {
        return pfWeight;
    }

    /**
     * @param pfWeight The pfWeight
     */
    public void setPfWeight(String pfWeight) {
        this.pfWeight = pfWeight;
    }

    /**
     * @return The currentPrice
     */
    public String getCurrentPrice() {
        return currentPrice;
    }

    /**
     * @param currentPrice The currentPrice
     */
    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * @return The currentValue
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * @param currentValue The currentValue
     */
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

}
