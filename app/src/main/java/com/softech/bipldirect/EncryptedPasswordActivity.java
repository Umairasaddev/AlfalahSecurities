package com.softech.bipldirect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.softech.bipldirect.Const.ConnectionDetector;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Models.MarketModel.MarketResponse;
import com.softech.bipldirect.Models.SecondLevelPassword.SecondLevelPassword;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.Preferences;


import net.orange_box.storebox.StoreBox;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EncryptedPasswordActivity extends BaseActivity {
    private Preferences preferences;
    @BindView(R.id.btn_registerme)
    Button loginBtn;
    @BindView(R.id.field_password)
    EditText passwordField;
    @BindView(R.id.btn_generatePass)
    Button generatePasswordBtn;
    String generatedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypted_password);
        ButterKnife.bind(this);
        connectMessageServer();
        preferences = StoreBox.create(this, Preferences.class);
//        if (preferences.getDecryptedPassword().equals(""))
//        {
//
//            generatePasswordBtn.setVisibility(View.VISIBLE);
//        }
//        else
//            generatePasswordBtn.setVisibility(View.GONE);

        passwordField.setText("UWJ9YZ");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordField.getText().length() > 0) {
                    Log.d("decryptedpass",preferences.getDecryptedPassword());
                    if (passwordField.getText().toString().equals(preferences.getDecryptedPassword()))
                    {
                    getMarket();
                    }
                    else
                        HSnackBar.showMsg(findViewById(android.R.id.content), "Passowrd Does Not Match.");

                }
                else
                    {
                        HSnackBar.showMsg(findViewById(android.R.id.content), "Please enter Second Level password.");
                    }
            }
        });
        generatePasswordBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                callPasswordService();


            }
        });
    }
private void callPasswordService()
{
    Gson gson = new Gson();



    JsonObject login_obj = new JsonObject();

    login_obj.addProperty("MSGTYPE", Constants.SECOND_LEVEL_PASSWORD_REQUEST);
    login_obj.addProperty("userName", preferences.getUsername());


    if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

        Map<Integer, String> map = new HashMap<>();
        map.put(1, Constants.SECOND_LEVEL_PASSWORD_REQUEST);
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



        JsonObject login_obj = new JsonObject();

        login_obj.addProperty("MSGTYPE", Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
        login_obj.addProperty("userId", preferences.getUsername());
        login_obj.addProperty("clientCode", preferences.getClientCode());


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
    public void onMessageReceived(String action, String resp) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        try {
            JsonObject jsonObject = jsonParser.parse(resp).getAsJsonObject();
            String MSGTYPE = jsonObject.get("response").getAsJsonObject().get("MSGTYPE").getAsString();
            String error = jsonObject.get("error").getAsString();
            String code = jsonObject.get("code").getAsString();

//            Log.d(TAG, "MSGTYPE: " + MSGTYPE);

            if (code.equals("200") && error.equals("")) {

                switch (MSGTYPE) {
                    case Constants.SUBSCRIPTION_LIST_REQUEST_RESPONSE: {

                        MarketResponse result = gson.fromJson(resp, MarketResponse.class);

                        if (result != null) {


                            if (result.getCode().equals("200")) {

                                preferences.setMarketResult(gson.toJson(result));

                                startActivity(new Intent(this, MainActivity.class));
                                finish();

                            } else {

                                Alert.show(EncryptedPasswordActivity.this, "", result.getError());
                            }


                        } else {
                            //              Log.d(TAG, "Response :: MarketResponse null ");
                        }
                    }
                    break;
                    case Constants.SECOND_LEVEL_PASSWORD_RESPONSE: {
                        SecondLevelPassword result=gson.fromJson(resp,SecondLevelPassword.class);
                        if (result.getCode().equals("200")) {

                            Alert.show(EncryptedPasswordActivity.this, "", result.getResponse().getRemarks());
                            EnctyptionUtils enctyptionUtils=new EnctyptionUtils();
                            try {
                                generatedPassword=enctyptionUtils.decrypt(result.getResponse().getSecondLevPwd());
                                Log.d("generatedPass",generatedPassword);
                                preferences.setDecryptedPassword(generatedPassword);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {

                            Alert.show(EncryptedPasswordActivity.this, "", result.getError());
                        }




                    }
                    break;
                }

            } else {
                Alert.show(EncryptedPasswordActivity.this, getString(R.string.app_name), error);
            }


        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Alert.showErrorAlert(EncryptedPasswordActivity.this);


        }


    }
}
