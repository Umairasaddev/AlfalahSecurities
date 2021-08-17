package com.softech.bipldirect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.softech.bipldirect.Const.ConnectionDetector;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Models.Event;
import com.softech.bipldirect.Models.LoginModel.LoginResponse;
import com.softech.bipldirect.Models.MarketModel.MarketResponse;
import com.softech.bipldirect.Models.SymbolsModel.SymbolsResponse;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.Preferences;
import com.softech.bipldirect.Util.Util;
import net.orange_box.storebox.StoreBox;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    EditText etName;
    EditText etPass;
    Button login_btn, registermeBut;
    TextView forgotPassword;

    Context context = LoginActivity.this;
    private Preferences preferences;
    private String user, pas;
    String[] serverNameArray = new String[]{"Primary", "Secondary", "DR"};
    String[] serverUrlArray = new String[]{"terminal1.alfalahtrade.com", "terminal2.alfalahtrade.com", "terminal1.alfalahtrade.net"};
    String userEncoded;
    String passEncoded, passdecoded;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        preferences = StoreBox.create(this, Preferences.class);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getBoolean("discon")) {
                Alert.show(context, getString(R.string.app_name), extras.getString("message"));
            }
        }
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ForgetPasswordActivity.class));
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingloginservice(v);
            }
        });
        registermeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SignupActivity.class));
            }
        });

    }

    private void initViews() {
        etName = (EditText) findViewById(R.id.login_name);
        etPass = (EditText) findViewById(R.id.login_pass);
        forgotPassword = (TextView) findViewById(R.id.tv_forgotPwd);
        login_btn = (Button) findViewById(R.id.login_btn);
        registermeBut = (Button) findViewById(R.id.login_registerme);

    }

    public void callingloginservice(View view) {
        user = etName.getText().toString();
        pas = etPass.getText().toString();
        try {
            EnctyptionUtils enctyptionUtils = new EnctyptionUtils();
            userEncoded = enctyptionUtils.encrypt(user.trim());
            passEncoded = enctyptionUtils.encrypt(pas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user.length() > 0 && pas.length() > 0) {
            final JsonObject login_obj = new JsonObject();

            login_obj.addProperty("MSGTYPE", Constants.LOGIN_MESSAGE_IDENTIFIER);
            login_obj.addProperty("userId", userEncoded);
            login_obj.addProperty("pswd", passEncoded);
            // For encryption
            login_obj.addProperty("Ver", "1.7");
//For encryption
//            if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {
////
//                Constants.KASB_API_LOGIN.length();
//
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("userId", user);
//
//                    RestClient.postRequest("login",
//                            context,
//                            Constants.KASB_API_LOGIN,
//                            jsonObject,
//                            new OnRestClientCallback() {
//                                @Override
//                                public void onRestSuccess(JSONObject response, String action) {
//
////                                    Log.d("Call","response: "+response);
//                                    try {
//                                        if (response.getString("code").equals("200")) {
//
//                                            String ip = response.getString("ip");
//                                            String port = response.getString("port");
//                                            Constants.serverIpAddress = new String[]{ip};
//                                            if (port.contains(",")) {
//                                                String[] portsArray = port.split(",");
//                                                Constants.ports = new int[portsArray.length];
//                                                for (int i = 0; i < Constants.ports.length; i++) {
//                                                    Constants.ports[i] = Integer.parseInt(portsArray[i]);
//                                                }
//                                            } else
//                                                Constants.ports[0] = Integer.parseInt(port);
//
//
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                        HToast.showMsg(context, "Unable to connect to Trading Server please try later or check your network");
//                                    }
//                                }
//
//                                @Override
//                                public void onRestError(Exception e, String action) {
//
//                                    Alert.showErrorAlert(context);
//                                    Log.d("Call","onRestError: ");
//                                }
//                            }, false, "Fetching Server IP's");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Alert.showErrorAlert(context);
//                    Log.d("Call","JSONException: ");
//                }
//
//            } else {
//                try {
//                    HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            connectWithMessageServer(login_obj);


        } else {

            HSnackBar.showMsg(view, "Please enter username and password.");
        }

    }

    private void connectWithMessageServer(final JsonObject login_obj) {
        Log.d(TAG, "connectWithMessageServer");

        connectMessageServer();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<Integer, String> map = new HashMap<>();
                map.put(1, Constants.LOGIN_MESSAGE_IDENTIFIER);
                map.put(2, login_obj.toString());
                write(map, true);
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.logOut) {
            Util.logOut = false;
            Alert.show(context, getString(R.string.app_name), Util.logOutString);
        }

        enableReconnect = false;
    }

    @Override
    public void onMessageReceived(String action, String resp) {

        Gson gson = new Gson();

        JsonParser jsonParser = new JsonParser();

        try {
            JsonObject jsonObject = jsonParser.parse(resp).getAsJsonObject();

            String MSGTYPE = jsonObject.get("response").getAsJsonObject().get("MSGTYPE").getAsString();
            String error = jsonObject.get("error").getAsString();
            String code = jsonObject.get("code").getAsString();

            Log.d(TAG, "MSGTYPE: " + MSGTYPE);

            if (code.equals("200") && error.equals("")) {

                switch (MSGTYPE) {

                    case Constants.LOGIN_MESSAGE_RESPONSE: {

                        LoginResponse result = gson.fromJson(resp, LoginResponse.class);
                        //Log.e("Call","response result: "+result);


                        if (result != null) {

                            if (result.getCode().equals("200")) {

                                preferences.setLoginResult(gson.toJson(result));

                                preferences.setUsername(etName.getText().toString());
                                preferences.setPassword(etPass.getText().toString());
                                //      FOR ZAFAR SECURITIES

                                if (result.getResponse().isShowSecLevPswd() && result.getResponse().getUsertype() == 1) {
                                    try {
                                        String encodedPass = result.getResponse().getSecondLevelPassword();
                                        if (encodedPass.equals("")) {

                                            preferences.setDecryptedPassword(encodedPass);
                                            startActivity(new Intent(context, EncryptedPasswordActivity.class));
                                            finish();
                                        } else {
                                            EnctyptionUtils enctyptionUtils = new EnctyptionUtils();
                                            passdecoded = enctyptionUtils.decrypt(result.getResponse().getSecondLevelPassword());
                                            Log.d("passworddecoded", passdecoded);
                                            preferences.setDecryptedPassword(passdecoded);
                                            preferences.setClientCode(result.getResponse().getClient());
                                            startActivity(new Intent(context, EncryptedPasswordActivity.class));
                                            finish();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                } else if (result.getResponse().getChangePassword() !=null && result.getResponse().getChangePassword().equals("true")) {
                                    preferences.setUserId(result.getResponse().getUserId());
                                    startActivity(new Intent(LoginActivity.this, ChangePasswordActivity.class));
                                } else {

                                    //      FOR ZAFAR SECURITIES

                                    Log.e(TAG, "ClientID: "+ result.getResponse().getClient());
                                    Log.e(TAG, "UserId: "+ result.getResponse().getUserId());

                                    //logEventToFirebaseAnalytics(result.getResponse().getClient(), result.getResponse().getUserId());

                                    logEventToFirebaseAnalytics(result.getResponse().getClient(), result.getResponse().getUserId());
                                    getMarket();
                                }
                                preferences.removeEvents(R.string.key_events);

                                Event.add(context, new Event(System.currentTimeMillis(), result.getResponse().getUserId() + " logged in successfully."));


//                                getSymbolsFromServer();
                            } else {
                                Alert.show(LoginActivity.this, "", result.getError());
                            }
                        } else {
                            Log.d(TAG, "Response :: LoginResponse null ");
                        }

                    }
                    break;

                    case Constants.SYMBOL_MESSAGE_RESPONSE: {

                        SymbolsResponse result = gson.fromJson(resp, SymbolsResponse.class);

                        if (result != null) {

                            if (result.getCode().equals("200")) {

                                preferences.setSymbolResult(gson.toJson(result));

//                                getMarket();

                            } else {

                                Alert.show(context, "", result.getError());
                            }


                        } else {
                            Log.d(TAG, "Response :: SymbolsResponse null ");
                        }
                    }
                    break;

                    case Constants.SUBSCRIPTION_LIST_REQUEST_RESPONSE: {

                        MarketResponse result = gson.fromJson(resp, MarketResponse.class);

                        if (result != null) {

                            if (result.getCode().equals("200")) {
                                preferences.setMarketResult(gson.toJson(result));
                                startActivity(new Intent(context, MainActivity.class));
                                finish();

                            } else {

                                Alert.show(context, "", result.getError());
                            }


                        } else {
                            Log.d(TAG, "Response :: MarketResponse null ");
                        }
                    }
                    break;
                }

            } else {
                Alert.show(context, getString(R.string.app_name), error);
            }


        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Alert.showErrorAlert(context);


        }


    }

    private void getSymbolsFromServer() {

        JsonObject login_obj = new JsonObject();

        login_obj.addProperty("MSGTYPE", Constants.SYMBOL_MESSAGE_IDENTIFIER);
//        login_obj.addProperty("userId", user);
//        login_obj.addProperty("pswd", pas);
        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SYMBOL_MESSAGE_IDENTIFIER);
            map.put(2, login_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void getMarket() {

        Gson gson = new Gson();


        String clientcode = gson.fromJson(preferences.getLoginResult(), LoginResponse.class).getResponse().getClient();

        Log.d("clientcode", "clientcode: " + clientcode);

        JsonObject login_obj = new JsonObject();

        login_obj.addProperty("MSGTYPE", Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
        login_obj.addProperty("userId", user);
        login_obj.addProperty("client", clientcode);


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
            map.put(2, login_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void logEventToFirebaseAnalytics(String clientCode, String userId){
        Bundle params = new Bundle();
        params.putString("UserId", userId);
        params.putString("ClientCode", clientCode);
        mFirebaseAnalytics.logEvent("Login", params);

        //        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, userId);
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, clientCode);
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

}

