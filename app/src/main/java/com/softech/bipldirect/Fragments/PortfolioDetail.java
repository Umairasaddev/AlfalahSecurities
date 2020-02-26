package com.softech.bipldirect.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class
PortfolioDetail extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.port_vol)
    TextView volume;
    @BindView(R.id.port_cost_unit)
    TextView costperunit;
    @BindView(R.id.port_totalcost)
    TextView totalcost;
    @BindView(R.id.port_curr_price)
    TextView currprice;
    @BindView(R.id.port_curr_val)
    TextView currval;
    @BindView(R.id.port_inves)
    TextView invest;
    @BindView(R.id.port_gainloss)
    TextView gainloss;
    @BindView(R.id.port_weight)
    TextView portweight;
    @BindView(R.id.port_symbol)
    TextView portsym;

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
        ButterKnife.bind(this, view);
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
}
