package com.softech.bipldirect;

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
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.HToast;

import org.json.JSONException;
import org.json.JSONObject;

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
        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callingloginservice(v);
            /*    if (email.getText().toString().length() > 0 && userID.getText().toString().length() > 0) {
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
*/
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

            Log.d("testingsignup", "MSGTYPE: " + MSGTYPE);
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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void callingloginservice(View view) {
        String name = userID.getText().toString();
        String email_str = email.getText().toString();

        if (name.length() > 0 && email.length() > 0 && email_str.length() > 0) {
            if (!isValidEmail(email_str)) {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                return;
            }
            final JsonObject login_obj = new JsonObject();
            login_obj.addProperty("MSGTYPE", Constants.ForgotPasswordRequest);
            login_obj.addProperty("email", email_str);
            login_obj.addProperty("userId", name);

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
//                                            Log.i("testingsignup", "Ip: " + ip);
                                            connectWithMessageServer(login_obj);

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        HToast.showMsg(ForgetPasswordActivity.this, "Unable to connect to Trading Server please try later or check your network");
                                    }
                                }

                                @Override
                                public void onRestError(Exception e, String action) {

                                    Alert.showErrorAlert(ForgetPasswordActivity.this);
                                    Log.i("testingsignup", "Exception: " + e.getMessage());
                                }
                            }, false, "Please wait...");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Alert.showErrorAlert(ForgetPasswordActivity.this);
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
