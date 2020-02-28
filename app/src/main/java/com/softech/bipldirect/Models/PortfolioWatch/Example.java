
package com.softech.bipldirect.Models.PortfolioWatch;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Example implements Serializable
{

    private String mSGTYPE;
    private Cash cash;


    public String getMSGTYPE() {
        return mSGTYPE;
    }

    public void setMSGTYPE(String mSGTYPE) {
        this.mSGTYPE = mSGTYPE;
    }

    public Cash getCash() {
        return cash;
    }

    public void setCash(Cash cash) {
        this.cash = cash;
    }



}
