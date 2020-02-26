package com.softech.bipldirect.Models.OrderStatsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Response {

    @SerializedName("ordersList")
    @Expose
    private List<OrdersList> ordersList = new ArrayList<OrdersList>();
    @SerializedName("MSGTYPE")
    @Expose
    private String MSGTYPE;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param ordersList
     * @param MSGTYPE
     */
    public Response(List<OrdersList> ordersList, String MSGTYPE) {
        this.ordersList = ordersList;
        this.MSGTYPE = MSGTYPE;
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

}
