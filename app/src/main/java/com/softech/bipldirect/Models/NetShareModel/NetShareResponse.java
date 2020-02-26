
package com.softech.bipldirect.Models.NetShareModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NetShareResponse {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("code")
    @Expose
    private String code;

    /**
     * No args constructor for use in serialization
     * 
     */
    public NetShareResponse() {
    }

    /**
     * 
     * @param response
     * @param error
     * @param code
     */
    public NetShareResponse(Response response, String error, String code) {
        super();
        this.response = response;
        this.error = error;
        this.code = code;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
