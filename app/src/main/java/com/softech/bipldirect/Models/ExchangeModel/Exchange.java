package com.softech.bipldirect.Models.ExchangeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exchange {

    @SerializedName("indicator")
    @Expose
    private String indicator;
    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("change")
    @Expose
    private String change;
    @SerializedName("equal")
    @Expose
    private String equal;
    @SerializedName("turnOver")
    @Expose
    private String turnOver;
    @SerializedName("exchangeCode")
    @Expose
    private String exchangeCode;
    @SerializedName("up")
    @Expose
    private String up;
    @SerializedName("lastIndex")
    @Expose
    private String lastIndex;
    @SerializedName("lowIndex")
    @Expose
    private String lowIndex;
    @SerializedName("highIndex")
    @Expose
    private String highIndex;
    @SerializedName("monitoryVolume")
    @Expose
    private String monitoryVolume;
    @SerializedName("current")
    @Expose
    private String current;
    @SerializedName("down")
    @Expose
    private String down;
    @SerializedName("changePer")
    @Expose
    private String changePer;

    /**
     * No args constructor for use in serialization
     */
    public Exchange() {
    }

    /**
     * @param indicator
     * @param symbol
     * @param change
     * @param equal
     * @param turnOver
     * @param exchangeCode
     * @param up
     * @param lastIndex
     * @param lowIndex
     * @param highIndex
     * @param monitoryVolume
     * @param current
     * @param down
     * @param changePer
     */
    public Exchange(String indicator, String symbol, String change, String equal, String turnOver, String exchangeCode, String up, String lastIndex, String lowIndex, String highIndex, String monitoryVolume, String current, String down, String changePer) {
        this.indicator = indicator;
        this.symbol = symbol;
        this.change = change;
        this.equal = equal;
        this.turnOver = turnOver;
        this.exchangeCode = exchangeCode;
        this.up = up;
        this.lastIndex = lastIndex;
        this.lowIndex = lowIndex;
        this.highIndex = highIndex;
        this.monitoryVolume = monitoryVolume;
        this.current = current;
        this.down = down;
        this.changePer = changePer;
    }

    /**
     * @return The indicator
     */
    public String getIndicator() {
        return indicator;
    }

    /**
     * @param indicator The indicator
     */
    public void setIndicator(String indicator) {
        this.indicator = indicator;
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
     * @return The change
     */
    public String getChange() {
        return change;
    }

    /**
     * @param change The change
     */
    public void setChange(String change) {
        this.change = change;
    }

    /**
     * @return The equal
     */
    public String getEqual() {
        return equal;
    }

    /**
     * @param equal The equal
     */
    public void setEqual(String equal) {
        this.equal = equal;
    }

    /**
     * @return The turnOver
     */
    public String getTurnOver() {
        return turnOver;
    }

    /**
     * @param turnOver The turnOver
     */
    public void setTurnOver(String turnOver) {
        this.turnOver = turnOver;
    }

    /**
     * @return The exchangeCode
     */
    public String getExchangeCode() {
        return exchangeCode;
    }

    /**
     * @param exchangeCode The exchangeCode
     */
    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    /**
     * @return The up
     */
    public String getUp() {
        return up;
    }

    /**
     * @param up The up
     */
    public void setUp(String up) {
        this.up = up;
    }

    /**
     * @return The lastIndex
     */
    public String getLastIndex() {
        return lastIndex;
    }

    /**
     * @param lastIndex The lastIndex
     */
    public void setLastIndex(String lastIndex) {
        this.lastIndex = lastIndex;
    }

    /**
     * @return The lowIndex
     */
    public String getLowIndex() {
        return lowIndex;
    }

    /**
     * @param lowIndex The lowIndex
     */
    public void setLowIndex(String lowIndex) {
        this.lowIndex = lowIndex;
    }

    /**
     * @return The highIndex
     */
    public String getHighIndex() {
        return highIndex;
    }

    /**
     * @param highIndex The highIndex
     */
    public void setHighIndex(String highIndex) {
        this.highIndex = highIndex;
    }

    /**
     * @return The monitoryVolume
     */
    public String getMonitoryVolume() {
        return monitoryVolume;
    }

    /**
     * @param monitoryVolume The monitoryVolume
     */
    public void setMonitoryVolume(String monitoryVolume) {
        this.monitoryVolume = monitoryVolume;
    }

    /**
     * @return The current
     */
    public String getCurrent() {
        return current;
    }

    /**
     * @param current The current
     */
    public void setCurrent(String current) {
        this.current = current;
    }

    /**
     * @return The down
     */
    public String getDown() {
        return down;
    }

    /**
     * @param down The down
     */
    public void setDown(String down) {
        this.down = down;
    }

    /**
     * @return The changePer
     */
    public String getChangePer() {
        return changePer;
    }

    /**
     * @param changePer The changePer
     */
    public void setChangePer(String changePer) {
        this.changePer = changePer;
    }

}
