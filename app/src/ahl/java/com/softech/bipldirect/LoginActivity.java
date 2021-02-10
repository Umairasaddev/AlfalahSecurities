package com.softech.bipldirect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.BuildConfig;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import com.softech.bipldirect.Network.OnRestClientCallback;
import com.softech.bipldirect.Network.RestClient;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.Util.Preferences;
import com.softech.bipldirect.Util.Util;

import net.orange_box.storebox.StoreBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */


public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.login_name)
    EditText etName;
    @BindView(R.id.login_pass)
    EditText etPass;
    //    @BindView(R.id.login_registerme)
//    Button registermeBut;
    @BindView(R.id.tv_forgotPwd)
    TextView forgotPassword;

//    TextView etServer;
    Context context = LoginActivity.this;
    private Preferences preferences;
    private String user, pas;
    String[] serverNameArray = new String[]{"Primary", "Secondary", "DR"};
    String[] serverUrlArray = new String[]{"terminal1.alfalahtrade.com", "terminal2.alfalahtrade.com", "terminal1.alfalahtrade.net"};
    String userEncoded;
    String passEncoded, passdecoded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (BuildConfig.FLAVOR=="alfalahsec") {
        }

        etName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
//        etName.setText("act01315");
//        etPass.setText("123456");
//        etName.setText("00022249");
//        etPass.setText("bipl1234");
//        etName.setText("00024639");
//        etPass.setText("pakipower1");


//
//        etName.setText("Softech");
//        etPass.setText("afs987");

//        etName.setText("Demo320");
//        etPass.setText("Demo123");

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
//        registermeBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, SignupActivity.class));
//            }
//        });
//        etServer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    etServer.setText("Primary");
//                    final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
//                    alert.setItems(serverNameArray, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            etServer.setText(serverNameArray[which]);
//                            Constants.serverIpAddress = new String[]{serverUrlArray[which]};
//
//
//                        }
//                    });
//                    alert.show();
//
//            }
//        });
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
            if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {
//
                Constants.KASB_API_LOGIN.length();

                JSONObject jsonObject = new JSONObject();


                    connectWithMessageServer(login_obj);



            } else {
                try {
                    HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } else {

            HSnackBar.showMsg(view, "Please enter username and password.");
        }

    }

    @OnClick(R.id.login_btn)
    public void submit(final View view) {
        callingloginservice(view);
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
                        Log.d("Call","response result: "+result);


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


}

