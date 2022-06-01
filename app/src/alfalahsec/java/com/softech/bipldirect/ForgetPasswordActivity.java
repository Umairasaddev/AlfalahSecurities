package com.softech.bipldirect;

import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.softech.bipldirect.Const.ConnectionDetector;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Network.MessageServerReadThread;
import com.softech.bipldirect.Network.MessageServerThread;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.HSnackBar;

import java.util.HashMap;
import java.util.Map;


public class ForgetPasswordActivity extends BaseActivity {
    private static final String TAG = "ForgetPasswordActivity";

    EditText userID;
    EditText email;
    Button forgetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initViews();
        forgetBtn.setOnClickListener(v -> {
            if (email.getText().toString().length() > 0 && userID.getText().toString().length() > 0) {
                final JsonObject login_obj = new JsonObject();
                login_obj.addProperty("MSGTYPE", Constants.ForgotPasswordRequest);
                login_obj.addProperty("email", email.getText().toString());
                login_obj.addProperty("userId", userID.getText().toString());
                if (ConnectionDetector.getInstance(ForgetPasswordActivity.this).isConnectingToInternet()) {

                    connectWithMessageServer(login_obj);
//                }

                } else {
                    try {
                        HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } else {

                HSnackBar.showMsg(v, "Please Fill All Fields.");
            }

        });
    }

    private void initViews() {
         userID = findViewById(R.id.useridField);
         email = findViewById(R.id.emailField);
         forgetBtn = findViewById(R.id.forgetBtn);

    }

    private void connectWithMessageServer(final JsonObject login_obj) {

        connectMessageServer();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Map<Integer, String> map = new HashMap<>();
                map.put(1, Constants.ForgotPasswordRequest);
                map.put(2, login_obj.toString());

                write(map, true);
            }
        }, 1000);


    }

    public void connectMessageServer() {
        synchronized (this) {
            messageServerThread = MessageServerThread.getInstance(getApplicationContext());
            messageServerReadThread = MessageServerReadThread.getInstance(getApplicationContext());
        }

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
            String alertMessage;
            if (code.equals("200") && error.equals("")) {

                switch (MSGTYPE) {

                    case Constants.ForgotPasswordResponse: {
                        alertMessage = jsonObject.get("response").getAsJsonObject().get("remarks").getAsString();
                        Log.e("Remarks", alertMessage);
                        Alert.show(ForgetPasswordActivity.this, getString(R.string.app_name), alertMessage);

                    }
                }
            } else {
                alertMessage = jsonObject.get("response").getAsJsonObject().get("remarks").getAsString();
                Log.e("Remarks", alertMessage);
                Alert.show(ForgetPasswordActivity.this, getString(R.string.app_name), alertMessage);

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Alert.showErrorAlert(ForgetPasswordActivity.this);


        }
    }
}
