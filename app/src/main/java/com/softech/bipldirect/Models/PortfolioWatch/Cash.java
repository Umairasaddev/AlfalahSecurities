
package com.softech.bipldirect.Models.PortfolioWatch;

import com.softech.bipldirect.Models.PortfolioModel.Portfolio;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Cash  implements Serializable
{

    private String workingCapital;
    private String custody;
    private String cash;


    public String getWorkingCapital() {
        return workingCapital;
    }

    public void setWorkingCapital(String workingCapital) {
        this.workingCapital = workingCapital;
    }

    public String getCustody() {
        return custody;
    }

    public void setCustody(String custody) {
        this.custody = custody;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }


}
