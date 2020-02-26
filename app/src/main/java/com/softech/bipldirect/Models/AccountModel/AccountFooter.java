package com.softech.bipldirect.Models.AccountModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saimshafqat on 30/11/2017.
 */

public class AccountFooter extends AccountDetail
{

    private String totalHoldings;
    private String totalPortfolio;

    public String getTotalHoldings() {
        return totalHoldings;
    }

    public void setTotalHoldings(String totalHoldings) {
        this.totalHoldings = totalHoldings;
    }

    public String getTotalPortfolio() {
        return totalPortfolio;
    }

    public void setTotalPortfolio(String totalPortfolio) {
        this.totalPortfolio = totalPortfolio;
    }
public  AccountFooter()
{

}
    public AccountFooter(String totalHoldings, String totalPortfolio) {
        this.totalHoldings = totalHoldings;
        this.totalPortfolio = totalPortfolio;
    }
}
