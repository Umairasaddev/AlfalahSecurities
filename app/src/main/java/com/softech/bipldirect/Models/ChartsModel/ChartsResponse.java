package com.softech.bipldirect.Models.ChartsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChartsResponse {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("period")
    @Expose
    private String period;

    /**
     * @return The response
     */
    public Response getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     * @return The error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return The code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return The period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * @param period The period
     */
    public void setPeriod(String period) {
        this.period = period;
    }

}
