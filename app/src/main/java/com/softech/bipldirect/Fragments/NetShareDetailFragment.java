package com.softech.bipldirect.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softech.bipldirect.Models.NetShareModel.NetShareCustody;
import com.softech.bipldirect.R;

/**
 * Developed by hasham on 4/20/17.
 */

public class NetShareDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private TextView textView_symbol;
    private TextView textView_symbolName;
    private TextView textView_cdcTradable;
    private TextView textView_closingRate;
    private TextView textView_CorpAct;
    private TextView textView_CustodyBalance;
    private TextView textView_Forward;
    private TextView textView_PhyTradable;
    private TextView textView_Registered;
    private TextView textView_Regular;
    private TextView textView_Spot;
    private TextView textView_UnRegistered;

    NetShareCustody shareCustody = null;

    public NetShareDetailFragment() {
    }

    public static Fragment newInstance(String param1) {
        NetShareDetailFragment fragment = new NetShareDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            String mParam1 = getArguments().getString(ARG_PARAM1);
            shareCustody = new Gson().fromJson(mParam1, NetShareCustody.class);
        }

        setHasOptionsMenu(true);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_home);
        item.setVisible(false);

        MenuItem item2 = menu.findItem(R.id.action_net_share);
        item2.setVisible(false);

        MenuItem item1 = menu.findItem(R.id.action_feed_status);
        item1.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_custody_detail, container, false);
        bindView(view);
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Detail");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        if(shareCustody!=null) {
            
            textView_symbol.setText(shareCustody.getSymbol());
            textView_symbolName.setText(shareCustody.getSymbolName());
            textView_cdcTradable.setText(shareCustody.getCdcTradable());
            textView_closingRate.setText(shareCustody.getCloseRate());
            textView_CorpAct.setText(shareCustody.getCorporateActivity());
            textView_CustodyBalance.setText(shareCustody.getCustodyBalance());
            textView_Forward.setText(shareCustody.getForward());
            textView_PhyTradable.setText(shareCustody.getPhysicalTradable());
            textView_Registered.setText(shareCustody.getRegistered());
            textView_Regular.setText(shareCustody.getRegular());
            textView_Spot.setText(shareCustody.getSpot());
            textView_UnRegistered.setText(shareCustody.getUnregistered());
        }
        
    }

    private void bindView(View bindSource) {
        textView_symbol = bindSource.findViewById(R.id.textView_symbol);
        textView_symbolName = bindSource.findViewById(R.id.textView_symbolName);
        textView_cdcTradable = bindSource.findViewById(R.id.textView_cdcTradable);
        textView_closingRate = bindSource.findViewById(R.id.textView_closingRate);
        textView_CorpAct = bindSource.findViewById(R.id.textView_CorpAct);
        textView_CustodyBalance = bindSource.findViewById(R.id.textView_CustodyBalance);
        textView_Forward = bindSource.findViewById(R.id.textView_Forward);
        textView_PhyTradable = bindSource.findViewById(R.id.textView_PhyTradable);
        textView_Registered = bindSource.findViewById(R.id.textView_Registered);
        textView_Regular = bindSource.findViewById(R.id.textView_Regular);
        textView_Spot = bindSource.findViewById(R.id.textView_Spot);
        textView_UnRegistered = bindSource.findViewById(R.id.textView_UnRegistered);
    }
}
