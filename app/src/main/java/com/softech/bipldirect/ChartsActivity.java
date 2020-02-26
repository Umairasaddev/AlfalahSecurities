package com.softech.bipldirect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Fragments.ChartDurationFragment;
import com.softech.bipldirect.Fragments.ChartTypeFragment;
import com.softech.bipldirect.Fragments.ChartViewFragment;
import com.softech.bipldirect.Models.ChartsModel.ChartsResponse;
import com.softech.bipldirect.Models.MarketModel.MarketSymbol;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.Util;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class ChartsActivity extends BaseActivity implements
        ChartDurationFragment.OnChartDurationListener, ChartTypeFragment.OnChartTypeListener
        , ChartViewFragment.OnChartViewListener {

    @SuppressWarnings("FieldCanBeLocal")
    private final String TAG = "ChartsActivity";
    private FragmentManager fragmentManager;
    private MarketSymbol marketSymbol;
    private Toolbar toolbar;
    private String exchange;
    private String selectedDuration;
    private ChartTypeFragment.Type selectedChartType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectMessageServer();
        setContentView(R.layout.activity_charts);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String symData = getIntent().getStringExtra("sym");
        exchange = getIntent().getStringExtra("exchange");

        if (symData != null) {
            marketSymbol = new Gson().fromJson(symData, MarketSymbol.class);
            replaceFragment(ChartDurationFragment.newInstance(), false);
        } else {
            finish();
        }

    }

    public void replaceFragment(Fragment fragment, boolean isChild) {

        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
            fragmentManager.addOnBackStackChangedListener(getListener());
        }

        String backStateName = fragment.getClass().getName();

        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (isChild) {
            ft.add(R.id.charts_fragment_container, fragment, backStateName);
        } else {
            fragmentManager.popBackStackImmediate();
            ft.replace(R.id.charts_fragment_container, fragment);
        }

        ft.addToBackStack(backStateName);
        ft.commit();
    }

    @Override
    public void onChartDurationInteraction(String val) {

        selectedDuration = val;

        replaceFragment(ChartTypeFragment.newInstance(), true);

        Log.d("ChartDuration", val);

    }

    @Override
    public void onChartTypeInteraction(ChartTypeFragment.Type type) {

        selectedChartType = type;

        replaceFragment(ChartViewFragment.newInstance(type, marketSymbol.getSymbol()), true);

        Log.d("ChartType", type + "");

    }

    @Override
    public void onChartViewInteraction(int action) {

        switch (action) {

            case 0: {//get chart data from server

                JsonObject request_obj = new JsonObject();

                request_obj.addProperty("MSGTYPE", Constants.CHARTS_REQ_IDENTIFIER);
                request_obj.addProperty("symbol", marketSymbol.getSymbol());
                request_obj.addProperty("exchange", exchange);
                request_obj.addProperty("market", marketSymbol.getMarket());
                request_obj.addProperty("duration", selectedDuration);

                Map<Integer, String> map = new HashMap<>();
                map.put(1, Constants.CHARTS_REQ_IDENTIFIER);
                map.put(2, request_obj.toString());

                write(map, true);
            }
            break;
        }
    }

    @Override
    public void onMessageReceived(String action, String resp) {

        Gson gson = new Gson();

        JsonParser jsonParser = new JsonParser();

        try {
            JsonObject jsonObject = jsonParser.parse(resp).getAsJsonObject();

            JsonObject response = jsonObject.get("response").getAsJsonObject();
            String MSGTYPE = response.get("MSGTYPE").getAsString();
            String error = jsonObject.get("error").getAsString();
            String code = jsonObject.get("code").getAsString();

            Log.d(TAG, "MSGTYPE: " + MSGTYPE);
            Log.d(TAG, "action: " + action);

            if (code.equals("200") && error.equals("")) {

                switch (MSGTYPE) {

                    case Constants.CHARTS_REQ_RESPONSE: {

                        final ChartsResponse result = gson.fromJson(resp, ChartsResponse.class);

                        if (result != null) {

                            if (result.getCode().equals("200")) {

                                final ChartViewFragment frag = (ChartViewFragment)
                                        fragmentManager.findFragmentByTag(
                                                ChartViewFragment.class.getName());

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (frag != null) {
                                            frag.setResult(result);
                                        }
                                    }
                                });

                            } else {
                                Alert.show(context, getString(R.string.app_name), result.getError());
                            }

                        } else {
                            Alert.showErrorAlert(context);
                        }
                    }
                    break;
                }

            } else {
                Alert.show(context, getString(R.string.app_name), error);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert.showErrorAlert(context);
        }

    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        return new FragmentManager.OnBackStackChangedListener() {
            @SuppressWarnings("ConstantConditions")
            public void onBackStackChanged() {

                Util.hideKeyboard(ChartsActivity.this);

                int backStackEntryCount = fragmentManager.getBackStackEntryCount();
                Log.d("onBackPressed", "OnBackStackChangedListener: " + backStackEntryCount);

                if (backStackEntryCount > 0) {

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                    try {
                        Fragment fragment = fragmentManager.getFragments()
                                .get(backStackEntryCount - 1);
                        fragment.onResume();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        };
    }

    @Override
    public void onBackPressed() {

        int count = fragmentManager.getBackStackEntryCount();
        Log.d("onBackPressed", "onBackPressed: " + count);

        if (count == 1) {
            finish();
            //additional code
        } else {
            fragmentManager.popBackStack();
        }

    }

}
