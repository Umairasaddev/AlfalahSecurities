package com.softech.bipldirect.Models.AccountModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrdersList extends AccountDetail {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("balance")
    @Expose
    private String balance;
    @SerializedName("symbol")
    @Expose
    private String symbol;

    /**
     * No args constructor for use in serialization
     */
    public OrdersList() {
    }

    /**
     * @param amount
     * @param balance
     * @param symbol
     */
    public OrdersList(String amount, String balance, String symbol) {
        this.amount = amount;
        this.balance = balance;
        this.symbol = symbol;
    }

    /**
     * @return The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return The balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     * @param balance The balance
     */
    public void setBalance(String balance) {
        this.balance = balance;
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

}
