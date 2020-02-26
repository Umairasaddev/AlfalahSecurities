package com.softech.bipldirect.Models.ProfileModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("accountOpenDate")
    @Expose
    private String accountOpenDate;
    @SerializedName("CityName")
    @Expose
    private String cityName;
    @SerializedName("SmsService")
    @Expose
    private String smsService;
    @SerializedName("TraderMobile")
    @Expose
    private String traderMobile;
    @SerializedName("TraderCode")
    @Expose
    private String traderCode;
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @SerializedName("Dividend")
    @Expose
    private String dividend;
    @SerializedName("BranchName")
    @Expose
    private String branchName;
    @SerializedName("TraderName")
    @Expose
    private String traderName;
    @SerializedName("MSGTYPE")
    @Expose
    private String mSGTYPE;
    @SerializedName("AccountNo")
    @Expose
    private String accountNo;
    @SerializedName("TraderEmail")
    @Expose
    private String traderEmail;
    @SerializedName("AccountTitle")
    @Expose
    private String accountTitle;
    @SerializedName("AccountBranch")
    @Expose
    private String accountBranch;
    @SerializedName("Nominee")
    @Expose
    private String nominee;
    @SerializedName("ZakatStatus")
    @Expose
    private String zakatStatus;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("ClientName")
    @Expose
    private String clientName;
    @SerializedName("AccountCity")
    @Expose
    private String accountCity;
    @SerializedName("NICExpiryDate")
    @Expose
    private String nICExpiryDate;
    @SerializedName("JointHolders")
    @Expose
    private String jointHolders;
    @SerializedName("CdcAccountNo")
    @Expose
    private String cdcAccountNo;
    @SerializedName("TradingAccountNo")
    @Expose
    private String tradingAccountNo;
    @SerializedName("Client_Code")
    @Expose
    private String clientCode;
    @SerializedName("NIC")
    @Expose
    private String nIC;

    /**
     * No args constructor for use in serialization
     */
    public Response() {
    }

    /**
     * @param accountOpenDate
     * @param clientCode
     * @param cityName
     * @param tradingAccountNo
     * @param traderCode
     * @param zakatStatus
     * @param clientName
     * @param nominee
     * @param smsService
     * @param accountTitle
     * @param mSGTYPE
     * @param dividend
     * @param accountNo
     * @param bankName
     * @param nICExpiryDate
     * @param traderName
     * @param accountBranch
     * @param traderMobile
     * @param email
     * @param address
     * @param accountCity
     * @param branchName
     * @param jointHolders
     * @param traderEmail
     * @param cdcAccountNo
     * @param nIC
     */
    public Response(String accountOpenDate, String cityName, String smsService, String traderMobile, String traderCode, String bankName, String dividend, String branchName, String traderName, String mSGTYPE, String accountNo, String traderEmail, String accountTitle, String accountBranch, String nominee, String zakatStatus, String email, String address, String clientName, String accountCity, String nICExpiryDate, String jointHolders, String cdcAccountNo, String tradingAccountNo, String clientCode, String nIC) {
        this.accountOpenDate = accountOpenDate;
        this.cityName = cityName;
        this.smsService = smsService;
        this.traderMobile = traderMobile;
        this.traderCode = traderCode;
        this.bankName = bankName;
        this.dividend = dividend;
        this.branchName = branchName;
        this.traderName = traderName;
        this.mSGTYPE = mSGTYPE;
        this.accountNo = accountNo;
        this.traderEmail = traderEmail;
        this.accountTitle = accountTitle;
        this.accountBranch = accountBranch;
        this.nominee = nominee;
        this.zakatStatus = zakatStatus;
        this.email = email;
        this.address = address;
        this.clientName = clientName;
        this.accountCity = accountCity;
        this.nICExpiryDate = nICExpiryDate;
        this.jointHolders = jointHolders;
        this.cdcAccountNo = cdcAccountNo;
        this.tradingAccountNo = tradingAccountNo;
        this.clientCode = clientCode;
        this.nIC = nIC;
    }

    /**
     * @return The accountOpenDate
     */
    public String getAccountOpenDate() {
        return accountOpenDate;
    }

    /**
     * @param accountOpenDate The accountOpenDate
     */
    public void setAccountOpenDate(String accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    /**
     * @return The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName The CityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * @return The smsService
     */
    public String getSmsService() {
        return smsService;
    }

    /**
     * @param smsService The SmsService
     */
    public void setSmsService(String smsService) {
        this.smsService = smsService;
    }

    /**
     * @return The traderMobile
     */
    public String getTraderMobile() {
        return traderMobile;
    }

    /**
     * @param traderMobile The TraderMobile
     */
    public void setTraderMobile(String traderMobile) {
        this.traderMobile = traderMobile;
    }

    /**
     * @return The traderCode
     */
    public String getTraderCode() {
        return traderCode;
    }

    /**
     * @param traderCode The TraderCode
     */
    public void setTraderCode(String traderCode) {
        this.traderCode = traderCode;
    }

    /**
     * @return The bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName The BankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return The dividend
     */
    public String getDividend() {
        return dividend;
    }

    /**
     * @param dividend The Dividend
     */
    public void setDividend(String dividend) {
        this.dividend = dividend;
    }

    /**
     * @return The branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName The BranchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return The traderName
     */
    public String getTraderName() {
        return traderName;
    }

    /**
     * @param traderName The TraderName
     */
    public void setTraderName(String traderName) {
        this.traderName = traderName;
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
     * @return The accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * @param accountNo The AccountNo
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * @return The traderEmail
     */
    public String getTraderEmail() {
        return traderEmail;
    }

    /**
     * @param traderEmail The TraderEmail
     */
    public void setTraderEmail(String traderEmail) {
        this.traderEmail = traderEmail;
    }

    /**
     * @return The accountTitle
     */
    public String getAccountTitle() {
        return accountTitle;
    }

    /**
     * @param accountTitle The AccountTitle
     */
    public void setAccountTitle(String accountTitle) {
        this.accountTitle = accountTitle;
    }

    /**
     * @return The accountBranch
     */
    public String getAccountBranch() {
        return accountBranch;
    }

    /**
     * @param accountBranch The AccountBranch
     */
    public void setAccountBranch(String accountBranch) {
        this.accountBranch = accountBranch;
    }

    /**
     * @return The nominee
     */
    public String getNominee() {
        return nominee;
    }

    /**
     * @param nominee The Nominee
     */
    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    /**
     * @return The zakatStatus
     */
    public String getZakatStatus() {
        return zakatStatus;
    }

    /**
     * @param zakatStatus The ZakatStatus
     */
    public void setZakatStatus(String zakatStatus) {
        this.zakatStatus = zakatStatus;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The Address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The clientName
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * @param clientName The ClientName
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return The accountCity
     */
    public String getAccountCity() {
        return accountCity;
    }

    /**
     * @param accountCity The AccountCity
     */
    public void setAccountCity(String accountCity) {
        this.accountCity = accountCity;
    }

    /**
     * @return The nICExpiryDate
     */
    public String getNICExpiryDate() {
        return nICExpiryDate;
    }

    /**
     * @param nICExpiryDate The NICExpiryDate
     */
    public void setNICExpiryDate(String nICExpiryDate) {
        this.nICExpiryDate = nICExpiryDate;
    }

    /**
     * @return The jointHolders
     */
    public String getJointHolders() {
        return jointHolders;
    }

    /**
     * @param jointHolders The JointHolders
     */
    public void setJointHolders(String jointHolders) {
        this.jointHolders = jointHolders;
    }

    /**
     * @return The cdcAccountNo
     */
    public String getCdcAccountNo() {
        return cdcAccountNo;
    }

    /**
     * @param cdcAccountNo The CdcAccountNo
     */
    public void setCdcAccountNo(String cdcAccountNo) {
        this.cdcAccountNo = cdcAccountNo;
    }

    /**
     * @return The tradingAccountNo
     */
    public String getTradingAccountNo() {
        return tradingAccountNo;
    }

    /**
     * @param tradingAccountNo The TradingAccountNo
     */
    public void setTradingAccountNo(String tradingAccountNo) {
        this.tradingAccountNo = tradingAccountNo;
    }

    /**
     * @return The clientCode
     */
    public String getClientCode() {
        return clientCode;
    }

    /**
     * @param clientCode The Client_Code
     */
    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    /**
     * @return The nIC
     */
    public String getNIC() {
        return nIC;
    }

    /**
     * @param nIC The NIC
     */
    public void setNIC(String nIC) {
        this.nIC = nIC;
    }

}
