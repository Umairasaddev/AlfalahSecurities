package com.softech.bipldirect;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.HSnackBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity   extends BaseActivity {
    private static final String TAG = "SignupActivity";
    @BindView(R.id.field_firstname)
    EditText etfirstName;
    @BindView(R.id.field_lastname)
    EditText etlastName;
    @BindView(R.id.field_username)
    EditText etUsername;
    @BindView(R.id.field_phoneno)
    EditText etPhone;
    @BindView(R.id.field_email)
    EditText etEmail;
    @BindView(R.id.signupButton)
    Button signupBut;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.";
    private String firstname, lastname, username, phoneno, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        signupBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstname = etfirstName.getText().toString();
                lastname = etlastName.getText().toString();
                email = etEmail.getText().toString();
                username = etUsername.getText().toString();
                phoneno = etPhone.getText().toString();

                if (etfirstName.length() > 0 && etlastName.length() > 0 && username.length()>0
                        && email.length()>0 && phoneno.length()>0) {
                    final JsonObject login_obj = new JsonObject();
                    if (!isValidEmail(email))
                    {
                        Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                        return;
                    }


                    login_obj.addProperty("MSGTYPE", Constants.SIGNUP_MESSAGE_IDENTIFIER);
                    login_obj.addProperty("firstName", firstname);
                    login_obj.addProperty("lastName", lastname);
                    login_obj.addProperty("userName", username);
                    login_obj.addProperty("email", email);
                    login_obj.addProperty("phone", phoneno);
                    if (ConnectionDetector.getInstance(SignupActivity.this).isConnectingToInternet()) {

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


            }
        });
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

            Log.d(TAG, "MSGTYPE: " + MSGTYPE);
            String alertMessage;
            if (code.equals("200") && error.equals("")) {

                switch (MSGTYPE) {

                    case Constants.SIGNUP_MESSAGE_RESPONSE:
                    {
                        alertMessage = jsonObject.get("response").getAsJsonObject().get("remarks").getAsString();
                        Log.e("Remarks",alertMessage);
                        Alert.show(SignupActivity.this, getString(R.string.app_name),alertMessage);

                    }
                }
            }
            else
            {
                alertMessage = jsonObject.get("response").getAsJsonObject().get("remarks").getAsString();
                Log.e("Remarks",alertMessage);
                Alert.show(SignupActivity.this, getString(R.string.app_name),alertMessage);

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Alert.showErrorAlert(SignupActivity.this);


        }
    }
}