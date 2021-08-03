package com.softech.bipldirect;

import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.softech.bipldirect.Network.OnRestClientCallback;
import com.softech.bipldirect.Network.RestClient;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.HToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends BaseActivity {
    private static final String TAG = "SignupActivity";

    EditText etfirstName;
    EditText etPhone;
    EditText etEmail;
    Button signupBut;

    private String firstname, phoneno, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.green, this.getTheme()));
        setContentView(R.layout.activity_signup);
        initViews();
        signupBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingloginservice(v);
            }
        });
    }

    private void initViews() {

        etfirstName = (EditText) findViewById(R.id.field_firstname);
        etPhone = (EditText) findViewById(R.id.field_phoneno);
        etEmail = (EditText) findViewById(R.id.field_email);
        signupBut = (Button) findViewById(R.id.signupButton);

    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void connectWithMessageServer(final JsonObject login_obj) {

        connectMessageServer();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Map<Integer, String> map = new HashMap<>();
                map.put(1, Constants.SIGNUP_MESSAGE_IDENTIFIER);
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

            Log.d("testingsignup", "MSGTYPE: " + MSGTYPE);
            String alertMessage;
            if (code.equals("200") && error.equals("")) {

                if (Constants.SIGNUP_MESSAGE_RESPONSE.equals(MSGTYPE)) {
                    alertMessage = jsonObject.get("response").getAsJsonObject().get("remarks").getAsString();
                    Log.e("Remarks", alertMessage);
                    Alert.show(SignupActivity.this, getString(R.string.app_name), alertMessage);
                }
            } else {
                alertMessage = jsonObject.get("response").getAsJsonObject().get("remarks").getAsString();
                Log.e("Remarks", alertMessage);
                Alert.show(SignupActivity.this, getString(R.string.app_name), alertMessage);

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Alert.showErrorAlert(SignupActivity.this);
        }
    }

    public void callingloginservice(View view) {
        firstname = etfirstName.getText().toString();
        phoneno = etPhone.getText().toString();
        email = etEmail.getText().toString();

        if (firstname.length() > 0 && email.length() > 0 && phoneno.length() > 0) {
            if (!isValidEmail(email)) {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            final JsonObject login_obj = new JsonObject();

            login_obj.addProperty("MSGTYPE", Constants.SIGNUP_MESSAGE_IDENTIFIER);
            login_obj.addProperty("name", firstname);
            login_obj.addProperty("phone", phoneno);
            login_obj.addProperty("email", email);

            if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {
                Constants.KASB_API_LOGIN.length();

                final JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userId", "SIGNUP");

                    RestClient.postRequest("login",
                            this,
                            Constants.KASB_API_LOGIN,
                            jsonObject,
                            new OnRestClientCallback() {
                                @Override
                                public void onRestSuccess(JSONObject response, String action) {

//                                    Log.d("Call","response: "+response);
                                    try {
                                        if (response.getString("code").equals("200")) {

                                            String ip = response.getString("ip");
                                            String port = response.getString("port");
                                            Constants.serverIpAddress = new String[]{ip};
                                            if (port.contains(",")) {
                                                String[] portsArray = port.split(",");
                                                Constants.ports = new int[portsArray.length];
                                                for (int i = 0; i < Constants.ports.length; i++) {
                                                    Constants.ports[i] = Integer.parseInt(portsArray[i]);
                                                }
                                            } else
                                                Constants.ports[0] = Integer.parseInt(port);
                                            Log.i("testingsignup", "Ip: " + ip);
                                            connectWithMessageServer(login_obj);

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        HToast.showMsg(SignupActivity.this, "Unable to connect to Trading Server please try later or check your network");
                                    }
                                }

                                @Override
                                public void onRestError(Exception e, String action) {

                                    Alert.showErrorAlert(SignupActivity.this);
                                    Log.i("testingsignup", "Exception: " + e.getMessage());
                                }
                            }, false, "Please wait...");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Alert.showErrorAlert(SignupActivity.this);
                    Log.d("Call", "JSONException: ");
                }

            } else {
                try {
                    HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } else {
            HSnackBar.showMsg(view, "Please fill all fields.");
        }
    }
}