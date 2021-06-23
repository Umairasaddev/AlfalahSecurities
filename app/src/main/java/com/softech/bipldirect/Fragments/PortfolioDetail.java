package com.softech.bipldirect.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.R;


public class
PortfolioDetail extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private TextView volume;
    private TextView costperunit;
    private TextView totalcost;
    private TextView currprice;
    private TextView currval;
    private TextView invest;
    private TextView gainloss;
    private TextView portweight;
    private TextView portsym;

    private String mParam1;
    private PortfolioSymbol portfolioSymbol;

    public PortfolioDetail() {
    }

    public static PortfolioDetail newInstance(String param1) {
        PortfolioDetail fragment = new PortfolioDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

            portfolioSymbol = new Gson().fromJson(mParam1, PortfolioSymbol.class);
        }
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Portfolio Detail");
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio_detail, container, false);
        bindView(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (portfolioSymbol != null) {

            this.volume.setText(portfolioSymbol.getVolume());
            this.costperunit.setText(portfolioSymbol.getCostPerUnit());
            this.totalcost.setText(portfolioSymbol.getTotalCost());
            this.currprice.setText(portfolioSymbol.getCurrentPrice());
            this.currval.setText(portfolioSymbol.getCurrentValue());
            this.invest.setText(portfolioSymbol.getRetOfInv());
            this.gainloss.setText(portfolioSymbol.getCapGainLoss());
            this.portweight.setText(portfolioSymbol.getPfWeight());
            this.portsym.setText(portfolioSymbol.getSymbol());

        } else {
            Alert.showErrorAlert(getActivity());
        }
    }

    private void bindView(View bindSource) {
        volume = bindSource.findViewById(R.id.port_vol);
        costperunit = bindSource.findViewById(R.id.port_cost_unit);
        totalcost = bindSource.findViewById(R.id.port_totalcost);
        currprice = bindSource.findViewById(R.id.port_curr_price);
        currval = bindSource.findViewById(R.id.port_curr_val);
        invest = bindSource.findViewById(R.id.port_inves);
        gainloss = bindSource.findViewById(R.id.port_gainloss);
        portweight = bindSource.findViewById(R.id.port_weight);
        portsym = bindSource.findViewById(R.id.port_symbol);
    }
}
