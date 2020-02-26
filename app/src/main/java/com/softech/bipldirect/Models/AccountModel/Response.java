package com.softech.bipldirect.Models.AccountModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @SerializedName("cashBlocked")
    @Expose
    private String cashBlocked;
    @SerializedName("ordersList")
    @Expose
    private List<OrdersList> ordersList = new ArrayList<OrdersList>();
    @SerializedName("MSGTYPE")
    @Expose
    private String MSGTYPE;
    @SerializedName("availableMargin")
    @Expose
    private String availableMargin;
    @SerializedName("cashUnBlocked")
    @Expose
    private String cashUnBlocked;
    @SerializedName("cashBalance")
    @Expose
    private String cashBalance;
    @SerializedName("custody")
    @Expose
    private String custody;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param ordersList
     * @param cashBlocked
     * @param MSGTYPE
     * @param availableMargin
     * @param cashUnBlocked
     * @param cashBalance
     * @param custody
     */
    public Response(String cashBlocked, List<OrdersList> ordersList, String MSGTYPE, String availableMargin, String cashUnBlocked, String cashBalance, String custody) {
        this.cashBlocked = cashBlocked;
        this.ordersList = ordersList;
        this.MSGTYPE = MSGTYPE;
        this.availableMargin = availableMargin;
        this.cashUnBlocked = cashUnBlocked;
        this.cashBalance = cashBalance;
        this.custody = custody;
    }

    /**
     * @return The cashBlocked
     */
    public String getCashBlocked() {
        return cashBlocked;
    }

    /**
     * @param cashBlocked The cashBlocked
     */
    public void setCashBlocked(String cashBlocked) {
        this.cashBlocked = cashBlocked;
    }

    /**
     * @return The ordersList
     */
    public List<OrdersList> getOrdersList() {
        return ordersList;
    }

    /**
     * @param ordersList The ordersList
     */
    public void setOrdersList(List<OrdersList> ordersList) {
        this.ordersList = ordersList;
    }

    /**
     * @return The MSGTYPE
     */
    public String getMSGTYPE() {
        return MSGTYPE;
    }

    /**
     * @param MSGTYPE The MSGTYPE
     */
    public void setMSGTYPE(String MSGTYPE) {
        this.MSGTYPE = MSGTYPE;
    }

    /**
     * @return The availableMargin
     */
    public String getAvailableMargin() {
        return availableMargin;
    }

    /**
     * @param availableMargin The availableMargin
     */
    public void setAvailableMargin(String availableMargin) {
        this.availableMargin = availableMargin;
    }

    /**
     * @return The cashUnBlocked
     */
    public String getCashUnBlocked() {
        return cashUnBlocked;
    }

    /**
     * @param cashUnBlocked The cashUnBlocked
     */
    public void setCashUnBlocked(String cashUnBlocked) {
        this.cashUnBlocked = cashUnBlocked;
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

    /**
     * @return The custody
     */
    public String getCustody() {
        return custody;
    }

    /**
     * @param custody The custody
     */
    public void setCustody(String custody) {
        this.custody = custody;
    }

}
