package com.softech.bipldirect;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.softech.bipldirect.Const.ConnectionDetector;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Network.MessageServerReadThread;
import com.softech.bipldirect.Network.MessageServerThread;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.Util.Preferences;

import net.orange_box.storebox.StoreBox;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity {
    private static final String TAG = "ChangePasswordActivity";
    @BindView(R.id.edittext_new_pass)
    EditText edit_newPass;
    @BindView(R.id.edittext_old_pass)
    EditText edit_oldPass;
    @BindView(R.id.edittext_confirm_pass)
    EditText edit_confirmPass;
    String newpassEncoded, oldPassEncoded, useridEncoded;
    private Preferences preferences;
    EnctyptionUtils enctyptionUtils = new EnctyptionUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary, this.getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        preferences = StoreBox.create(this, Preferences.class);

    }

    @OnClick(R.id.button_change_pass)
    public void submit(View view) {
        try {
            newpassEncoded = enctyptionUtils.encrypt(edit_newPass.getText().toString());
            oldPassEncoded = enctyptionUtils.encrypt(edit_oldPass.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String oldPassword = edit_oldPass.getText().toString();
        String newPassword = edit_newPass.getText().toString();
        String confirmPassword = edit_confirmPass.getText().toString();


        if (!TextUtils.equals(oldPassword, "")) {

            if (!TextUtils.equals(newPassword, "") && newPassword.length() >= 8) {

                if (TextUtils.equals(newPassword, confirmPassword)) {

                    changePasswordRequest(oldPassEncoded, newpassEncoded);

                } else {
                    HToast.showMsg(this, "Passwords do not match.");
                }
            } else {
                HToast.showMsg(this, "New password must be equal to at least 8 characters.");
            }

        } else {
            HToast.showMsg(this, "Please type your old password.");
        }


    }

    public void changePasswordRequest(String oldPassword, String newPassword) {

        JsonObject request_obj = new JsonObject();
        EnctyptionUtils enctyptionUtils = new EnctyptionUtils();
        try {
            useridEncoded = enctyptionUtils.encrypt(preferences.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        request_obj.addProperty("MSGTYPE", Constants.CHANGE_PASSWORD_REQ_IDENTIFIER);
        request_obj.addProperty("userName", useridEncoded);
        request_obj.addProperty("oldPassword", oldPassword);
        request_obj.addProperty("newPassword", newPassword);


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

           connectWithMessageServer(request_obj);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.CHANGE_PASSWORD_REQ_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void connectWithMessageServer(final JsonObject login_obj) {

        connectMessageServer();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Map<Integer, String> map = new HashMap<>();
                map.put(1, Constants.CHANGE_PASSWORD_REQ_IDENTIFIER);
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
            String respAction = jsonObject.get("response").getAsJsonObject().get("action").getAsString();

            String error = jsonObject.get("error").getAsString();
            String code = jsonObject.get("code").getAsString();

            Log.d(TAG, "MSGTYPE: " + MSGTYPE);
            String alertMessage;
            if (code.equals("200") && error.equals("") && !respAction.equals("REJT")) {

                switch (MSGTYPE) {

                    case Constants.CHANGE_PASSWORD_REQ_RESPONSE: {
                        alertMessage = jsonObject.get("response").getAsJsonObject().get("remarks").getAsString();
                        Log.e("Remarks", alertMessage);

                        Alert.showToLogin(ChangePasswordActivity.this, getString(R.string.app_name), alertMessage);

                    }
                }
            } else {
                alertMessage = jsonObject.get("response").getAsJsonObject().get("remarks").getAsString();
                Log.e("Remarks", alertMessage);
                Alert.show(ChangePasswordActivity.this, getString(R.string.app_name), alertMessage);

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Alert.showErrorAlert(ChangePasswordActivity.this);


        }
    }
}
