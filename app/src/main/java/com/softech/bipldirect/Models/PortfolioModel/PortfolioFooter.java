package com.softech.bipldirect.Models.PortfolioModel;


public class PortfolioFooter extends Portfolio {

    private double totalInvestment;
    private String totalProfitLoss;

    public double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getTotalProfitLoss() {
        return totalProfitLoss;
    }

    public void setTotalProfitLoss(String totalProfitLoss) {
        this.totalProfitLoss = totalProfitLoss;
    }

    public  PortfolioFooter()
    {

    }

}
