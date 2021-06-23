package com.softech.bipldirect.Models.LoginModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SuppressWarnings("ALL")
public class Response {

    @SerializedName("changePwd")
    @Expose
    private String changePassword;

    @SerializedName("client")
    @Expose
    private String client;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("feedPort")
    @Expose
    private String feedPort;
    @SerializedName("feedIP")
    @Expose
    private String feedIP;
    @SerializedName("regularMarketCode")
    @Expose
    private String regularMarketCode;
    @SerializedName("pinCode")
    @Expose
    private String pinCode;
    @SerializedName("systemDate")
    @Expose
    private String systemDate;
    @SerializedName("oddLotMarketCode")
    @Expose
    private String oddLotMarketCode;
    @SerializedName("applicationTitle")
    @Expose
    private String applicationTitle;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("trnCodes")
    @Expose
    private String trnCodes;
    @SerializedName("action")
    @Expose
    private Integer action;
    @SerializedName("serverCode")
    @Expose
    private String serverCode;
    @SerializedName("MSGTYPE")
    @Expose
    private String MSGTYPE;
    @SerializedName("trader")
    @Expose
    private String trader;
    @SerializedName("exchange")
    @Expose
    private String exchange;
    @SerializedName("ordType")
    @Expose
    private List<OrdType> ordTypes;

    @SerializedName("userType")
    @Expose
    private int usertype;
    @SerializedName("secondLevelPassword")
    @Expose
    private String secondLevelPassword;


    @SerializedName("clientList")
    @Expose
    private String clientlist;

    @SerializedName("ordProp")
    @Expose
    private List<OrdProp> ordProps;
    @SerializedName("showSecLevPswd")
    @Expose
    private boolean showSecLevPswd;


    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param client
     * @param remarks
     * @param feedPort
     * @param feedIP
     * @param regularMarketCode
     * @param pinCode
     * @param systemDate
     * @param oddLotMarketCode
     * @param applicationTitle
     * @param userId
     * @param serverCode
     * @param trnCodes
     * @param action
     * @param MSGTYPE
     * @param trader
     * @param exchange
     * @param ordTypes
     * @param ordProps
     */
    public Response(String client, String remarks, String feedPort, String feedIP, String regularMarketCode, String pinCode, String systemDate, String oddLotMarketCode, String applicationTitle, String userId, String trnCodes, Integer action, String serverCode, String MSGTYPE, String trader, String exchange, List<OrdType> ordTypes, List<OrdProp> ordProps, int usertype, String clientlist, String secondLevelPassword, boolean showSecLevPswd,String changePassword) {
        this.client = client;
        this.remarks = remarks;
        this.feedPort = feedPort;
        this.feedIP = feedIP;
        this.regularMarketCode = regularMarketCode;
        this.pinCode = pinCode;
        this.systemDate = systemDate;
        this.oddLotMarketCode = oddLotMarketCode;
        this.applicationTitle = applicationTitle;
        this.userId = userId;
        this.trnCodes = trnCodes;
        this.action = action;
        this.serverCode = serverCode;
        this.MSGTYPE = MSGTYPE;
        this.trader = trader;
        this.exchange = exchange;
        this.ordTypes = ordTypes;
        this.ordProps = ordProps;
        this.usertype = usertype;
        this.secondLevelPassword = secondLevelPassword;
        this.showSecLevPswd = showSecLevPswd;
        this.changePassword=changePassword;
    }

    /**
     * @return The client
     */


    /**
     * @param client The client
     */
    public void setSecondLevelPassword(String client) {
        this.secondLevelPassword = secondLevelPassword;
    }

    public String getSecondLevelPassword() {
        return secondLevelPassword;
    }

    /**
     * @param client The client
     */
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return The remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks The remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return The feedPort
     */
    public String getFeedPort() {
        return feedPort;
    }

    /**
     * @param feedPort The feedPort
     */
    public void setFeedPort(String feedPort) {
        this.feedPort = feedPort;
    }

    /**
     * @return The feedIP
     */
    public String getFeedIP() {
        return feedIP;
    }

    /**
     * @param feedIP The feedIP
     */
    public void setFeedIP(String feedIP) {
        this.feedIP = feedIP;
    }

    /**
     * @return The regularMarketCode
     */
    public String getRegularMarketCode() {
        return regularMarketCode;
    }

    /**
     * @param regularMarketCode The regularMarketCode
     */
    public void setRegularMarketCode(String regularMarketCode) {
        this.regularMarketCode = regularMarketCode;
    }

    /**
     * @return The pinCode
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * @param pinCode The pinCode
     */
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    /**
     * @return The systemDate
     */
    public String getSystemDate() {
        return systemDate;
    }

    /**
     * @param systemDate The systemDate
     */
    public void setSystemDate(String systemDate) {
        this.systemDate = systemDate;
    }

    /**
     * @return The oddLotMarketCode
     */
    public String getOddLotMarketCode() {
        return oddLotMarketCode;
    }

    /**
     * @param oddLotMarketCode The oddLotMarketCode
     */
    public void setOddLotMarketCode(String oddLotMarketCode) {
        this.oddLotMarketCode = oddLotMarketCode;
    }

    /**
     * @return The applicationTitle
     */
    public String getApplicationTitle() {
        return applicationTitle;
    }

    /**
     * @param applicationTitle The applicationTitle
     */
    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    /**
     * @return The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId The userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return The trnCodes
     */
    public String getTrnCodes() {
        return trnCodes;
    }

    /**
     * @param trnCodes The trnCodes
     */
    public void setTrnCodes(String trnCodes) {
        this.trnCodes = trnCodes;
    }

    /**
     * @return The action
     */
    public Integer getAction() {
        return action;
    }

    /**
     * @param action The action
     */
    public void setAction(Integer action) {
        this.action = action;
    }

    /**
     * @return The serverCode
     */
    public String getServerCode() {
        return serverCode;
    }

    /**
     * @param serverCode The serverCode
     */
    public void setServerCode(String serverCode) {
        this.serverCode = serverCode;
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
     * @return The trader
     */
    public String getTrader() {
        return trader;
    }

    /**
     * @param trader The trader
     */
    public void setClientlist(String clientlist) {
        this.clientlist = clientlist;
    }


    public ArrayList<String> getClientlist() {

        return new ArrayList<String>(Arrays.asList(clientlist.split("\\s*,\\s*")));
    }


    public int getUsertype() {
        return usertype;
    }

    /**
     * @param trader The trader
     */
    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public boolean isShowSecLevPswd() {
        return showSecLevPswd;
    }

    public void setShowSecLevPswd(boolean showSecLevPswd) {
        this.showSecLevPswd = showSecLevPswd;
    }

    /**
     * @return The exchange
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * @param exchange The exchange
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public List<OrdType> getOrdTypes() {
        return ordTypes;
    }

    public void setOrdTypes(List<OrdType> ordTypes) {
        this.ordTypes = ordTypes;
    }

    public List<OrdProp> getOrdProps() {
        return ordProps;
    }

    public void setOrdProps(List<OrdProp> ordProps) {
        this.ordProps = ordProps;
    }

    public String getChangePassword() {
        return changePassword;
    }
    public void setChangePassword(String changePassword) {
        this.changePassword = changePassword;
    }

    @Override
    public String toString() {
        return "Response{" +
                "changePassword='" + changePassword + '\'' +
                ", client='" + client + '\'' +
                ", remarks='" + remarks + '\'' +
                ", feedPort='" + feedPort + '\'' +
                ", feedIP='" + feedIP + '\'' +
                ", regularMarketCode='" + regularMarketCode + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", systemDate='" + systemDate + '\'' +
                ", oddLotMarketCode='" + oddLotMarketCode + '\'' +
                ", applicationTitle='" + applicationTitle + '\'' +
                ", userId='" + userId + '\'' +
                ", trnCodes='" + trnCodes + '\'' +
                ", action=" + action +
                ", serverCode='" + serverCode + '\'' +
                ", MSGTYPE='" + MSGTYPE + '\'' +
                ", trader='" + trader + '\'' +
                ", exchange='" + exchange + '\'' +
                ", ordTypes=" + ordTypes +
                ", usertype=" + usertype +
                ", secondLevelPassword='" + secondLevelPassword + '\'' +
                ", clientlist='" + clientlist + '\'' +
                ", ordProps=" + ordProps +
                ", showSecLevPswd=" + showSecLevPswd +
                '}';
    }
}
