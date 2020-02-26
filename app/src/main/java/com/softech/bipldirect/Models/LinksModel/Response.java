package com.softech.bipldirect.Models.LinksModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {

    @SerializedName("MSGTYPE")
    @Expose
    private String MSGTYPE;
    @SerializedName("links")
    @Expose
    private List<Link> links = new ArrayList<Link>();

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param mSGTYPE
     * @param links
     */
    public Response(String mSGTYPE, List<Link> links) {
        this.MSGTYPE = mSGTYPE;
        this.links = links;
    }

    /**
     * @return The mSGTYPE
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
     * @return The links
     */
    public List<Link> getLinks() {
        return links;
    }

    /**
     * @param links The links
     */
    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
