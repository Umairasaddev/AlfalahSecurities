package com.softech.bipldirect.Models.ChartsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @SerializedName("lows")
    @Expose
    private List<Double> lows = new ArrayList<Double>();
    @SerializedName("dates")
    @Expose
    private List<String> dates = new ArrayList<String>();
    @SerializedName("MSGTYPE")
    @Expose
    private String mSGTYPE;
    @SerializedName("opens")
    @Expose
    private List<Double> opens = new ArrayList<Double>();
    @SerializedName("years")
    @Expose
    private String years;
    @SerializedName("volumes")
    @Expose
    private List<String> volumes = new ArrayList<String>();
    @SerializedName("closes")
    @Expose
    private List<Double> closes = new ArrayList<Double>();
    @SerializedName("highs")
    @Expose
    private List<Double> highs = new ArrayList<Double>();

    /**
     * @return The lows
     */
    public List<Double> getLows() {
        return lows;
    }

    /**
     * @param lows The lows
     */
    public void setLows(List<Double> lows) {
        this.lows = lows;
    }

    /**
     * @return The dates
     */
    public List<String> getDates() {
        return dates;
    }

    /**
     * @param dates The dates
     */
    public void setDates(List<String> dates) {
        this.dates = dates;
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
     * @return The opens
     */
    public List<Double> getOpens() {
        return opens;
    }

    /**
     * @param opens The opens
     */
    public void setOpens(List<Double> opens) {
        this.opens = opens;
    }

    /**
     * @return The years
     */
    public String getYears() {
        return years;
    }

    /**
     * @param years The years
     */
    public void setYears(String years) {
        this.years = years;
    }

    /**
     * @return The volumes
     */
    public List<String> getVolumes() {
        return volumes;
    }

    /**
     * @param volumes The volumes
     */
    public void setVolumes(List<String> volumes) {
        this.volumes = volumes;
    }

    /**
     * @return The closes
     */
    public List<Double> getCloses() {
        return closes;
    }

    /**
     * @param closes The closes
     */
    public void setCloses(List<Double> closes) {
        this.closes = closes;
    }

    /**
     * @return The highs
     */
    public List<Double> getHighs() {
        return highs;
    }

    /**
     * @param highs The highs
     */
    public void setHighs(List<Double> highs) {
        this.highs = highs;
    }

}