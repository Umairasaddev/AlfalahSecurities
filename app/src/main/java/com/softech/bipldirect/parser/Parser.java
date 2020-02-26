package com.softech.bipldirect.parser;

import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Developed by Robin.Yaqoob on 30-Mar-16.
 */
public class Parser {

    private static ArrayList<PortfolioSymbol> portfolioSymbolArrayList = new ArrayList<PortfolioSymbol>();


    public static List<PortfolioSymbol> getPortFolio(String resp) {

        JSONObject response = null;
        try {
            response = new JSONObject(resp);
            JSONObject responseJson = response.getJSONObject("response");
            String code = response.getString("code");
            if (code.equals("200")) {

                JSONArray symbols = responseJson.getJSONArray("symbols");
                for (int i = 0; i < symbols.length(); i++) {
                    JSONObject jsonObj = symbols.getJSONObject(i);
                    String totalCost = jsonObj.getString("totalCost");
                    String capGainLoss = jsonObj.getString("capGainLoss");
                    String symbol = jsonObj.getString("symbol");
                    String retOfInv = jsonObj.getString("retOfInv");
                    String costPerUnit = jsonObj.getString("costPerUnit");
                    String volume = jsonObj.getString("volume");
                    String pfWeight = jsonObj.getString("pfWeight");
                    String currentPrice = jsonObj.getString("currentPrice");
                    String currentValue = jsonObj.getString("currentValue");

                    PortfolioSymbol ps = new PortfolioSymbol(totalCost, capGainLoss, symbol,
                            retOfInv, costPerUnit, volume, pfWeight, currentPrice,
                            currentValue);

                    portfolioSymbolArrayList.add(ps);


                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return portfolioSymbolArrayList;
    }


}
