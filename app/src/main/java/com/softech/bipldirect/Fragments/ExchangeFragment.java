package com.softech.bipldirect.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.softech.bipldirect.Adapters.ExchangeAdapter;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.ExchangeModel.Exchange;
import com.softech.bipldirect.Models.ExchangeModel.ExchangeResponse;
import com.softech.bipldirect.Network.FeedCallback;
import com.softech.bipldirect.Network.FeedServer;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.DividerItemDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.softech.bipldirect.MainActivity.loginResponse;

public class ExchangeFragment extends Fragment implements FeedCallback {

    private RecyclerView recyclerView;
    private ExchangeAdapter exchangesAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private BroadcastReceiver mFeedReceiver;
    private List<Exchange> exchangeList = new ArrayList<>();
    View view;
    private static final String TAG = "Exchange";

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
        view =  inflater.inflate(R.layout.fragment_exchange, container, false);
        recyclerView=view.findViewById(R.id.exchange_list);

        linearLayoutManager = new LinearLayoutManager(getContext());
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(linearLayoutManager);
        }

        exchangesAdapter = new ExchangeAdapter(getActivity(), exchangeList, linearLayoutManager, this);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setAdapter(exchangesAdapter);
        broadCastStart();


        return view;
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

        JsonObject feed_obj = new JsonObject();
        String action = Constants.FEED_LOGIN_MESSAGE_IDENTIFIER;
        String user = loginResponse.getResponse().getUserId();
        feed_obj.addProperty("MSGTYPE", action);
        feed_obj.addProperty("userId", user);
        new FeedServer(getActivity(), this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, feed_obj.toString());


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
    private BroadcastReceiver mMessageReceiver;

    private void broadCastStart() {
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                try {
//                    loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String message = intent.getStringExtra("response");
                String msgType = intent.getStringExtra("msgType");

                if (message != null && msgType != null) {
                    if (msgType.equalsIgnoreCase(Constants.EXC_REQ_RESPONSE)) {
                        exchangeResponse(message);
                    }
                }
            }
        };

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.MSG_SERVER_BROADCAST));
    }



    private void exchangeResponse(String resp) {
        if (!(resp.equals("null"))) {

            Gson gson = new Gson();

            JsonParser jsonParser = new JsonParser();

            try {

                JsonObject jsonObject = jsonParser.parse(resp).getAsJsonObject();

                String MSGTYPE = jsonObject.get("response").getAsJsonObject().get("MSGTYPE").getAsString();
                String error = jsonObject.get("error").getAsString();
                String code = jsonObject.get("code").getAsString();

                Log.e(TAG, "MSGTYPE: " + MSGTYPE);

                if (!(MSGTYPE.equals(Constants.EXC_REQ_RESPONSE))) {
//                    callingExchangeService();
                    return;
                }

                if (code.equals("200") && error.equals("")) {
                    final ExchangeResponse result = gson.fromJson(resp, ExchangeResponse.class);

                    if (result != null) {

                        if (result.getCode().equals("200")) {
                            try{
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (result.getResponse().getExchanges().size() == 0) {

                                            Alert.show(getActivity(), getString(R.string.app_name), "Market is Closed.Please Open Market First");
                                            return;
                                        } else {
                                            exchangeList.clear();
                                            exchangeList.addAll(result.getResponse().getExchanges());
                                            exchangesAdapter = new ExchangeAdapter(getActivity(),exchangeList, linearLayoutManager, ExchangeFragment.this);
                                            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                                            recyclerView.setAdapter(exchangesAdapter);

                                        }

                                        if (result != null) {
                                        }
                                    }
                                });
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }



                        }

                        else {

                            Alert.show(getActivity(), "", result.getError());
                        }


                    } else {
                        Log.e(TAG, "Response :: SymbolsResponse null ");
                    }
                }
            } catch (JsonSyntaxException e) {

                e.printStackTrace();
                Alert.showErrorAlert(getActivity());

            }
        } else if (resp.equals("null")) {
//            Alert.showSessionExpAlert(getActivity(), getString(R.string.app_name), "Your session has been expired.Please Relogin");


        }
    }


    public void onFeedReceived(String resp) {

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
