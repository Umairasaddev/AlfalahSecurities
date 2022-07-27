package com.softech.bipldirect.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.softech.bipldirect.Adapters.ExchangeAdapter;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.ExchangeModel.Exchange;
import com.softech.bipldirect.Models.ExchangeModel.ExchangeResponse;
import com.softech.bipldirect.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ExchangeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExchangeAdapter exchangesAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private BroadcastReceiver mFeedReceiver;
    private List<Exchange> exchangeList = new ArrayList<>();

    public ExchangeFragment() {
        // Required empty public constructor
    }


    public static ExchangeFragment newInstance() {
        return new ExchangeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_exchange, container, false);

        linearLayoutManager = new LinearLayoutManager(getContext());
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        exchangesAdapter = new ExchangeAdapter(getActivity(), exchangeList, linearLayoutManager, this);

        recyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(exchangesAdapter);


        return recyclerView;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Exchanges");
        }
        super.onResume();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFeedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("response");

                if (message != null) {
                    onFeedReceived(message);
                } else {
                    try {
                        ((MainActivity) getActivity()).showFeedDisconnectAlert();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mFeedReceiver,
                new IntentFilter(Constants.FEED_SERVER_BROADCAST));
        ((MainActivity) getActivity()).exchangesRequest();

    }

    @Override
    public void onDetach() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mFeedReceiver);
        super.onDetach();
    }

    public void setResult(ExchangeResponse result) {

        if (result != null) {

            exchangeList.clear();
            exchangeList.addAll(result.getResponse().getExchanges());

            exchangesAdapter.notifyDataSetChanged();


        }
    }


    private void onFeedReceived(String resp) {

        Log.d("ExchangesFrag", "resp: " + resp);
        try {
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(resp).getAsJsonObject();

            if (json.get("code").getAsString().equals("200")) {

                JsonObject response = json.getAsJsonObject("response");

                JsonArray exchangesArr = response.getAsJsonArray("exchanges");

                if (exchangesArr.size() > 0) {

                    Type listType = new TypeToken<List<Exchange>>() {
                    }.getType();

                    List<Exchange> exchangeList = new Gson().fromJson(exchangesArr, listType);

//                    Log.d("feed exchanges", "" + new Gson().toJson(exchangeList, listType));

                    if (exchangeList != null && exchangeList.size() > 0) {

                        try {
                            exchangesAdapter.updateFeed(exchangeList);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
