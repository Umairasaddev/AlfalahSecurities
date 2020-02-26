package com.softech.bipldirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.google.gson.JsonObject;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Network.MessageServerReadThread;
import com.softech.bipldirect.Network.MessageServerThread;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.Loading;
import com.softech.bipldirect.Util.Preferences;

import net.orange_box.storebox.StoreBox;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Developed by Hasham.Tahir on 7/25/2016.
 */
public class BaseActivity extends AppCompatActivity {

    public static final String STATE_CONNECT_EXCEPTION = "ConnectException";
    public static final String STATE_DISCONNECTED = "Disconnected";
    public static final String STATE_RECONNECTED = "Reconnected";
    public static final String STATE_CONNECT_FAILURE = "ConnectFailure";
    public static boolean enableReconnect = false;
    public Context context = BaseActivity.this;
    public MessageServerThread messageServerThread;
    public MessageServerReadThread messageServerReadThread;
    private Loading loading;
    private BroadcastReceiver mMessageReceiver;
    private Preferences preferences;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = StoreBox.create(this, Preferences.class);

        loading = new Loading(context, "Please wait...");

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                try {
                    loading.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String message = intent.getStringExtra("response");
                String msgType = intent.getStringExtra("msgType");

                if (message == null && msgType == null) {
                    Alert.showErrorAlert(BaseActivity.this);

                } else if (msgType.equals(STATE_CONNECT_EXCEPTION)) {

                    //    Alert.show(BaseActivity.this, getString(R.string.app_name), "Unable to connect to Server. Please try later or check your network.");

                } else if (msgType.equals(STATE_DISCONNECTED)) {

                    //     Alert.show(BaseActivity.this, getString(R.string.app_name), "Unable to connect to Server. Please wait while we try to re connect to the Server");

                } else if (msgType.equals(STATE_RECONNECTED)) {

                    connectMessageServer();

                    final JsonObject login_obj = new JsonObject();

                    login_obj.addProperty("MSGTYPE", Constants.LOGIN_MESSAGE_IDENTIFIER);
                    login_obj.addProperty("userId", preferences.getUsername());
                    login_obj.addProperty("pswd", preferences.getPassword());

                    Map<Integer, String> map = new HashMap<>();
                    map.put(1, Constants.LOGIN_MESSAGE_IDENTIFIER);
                    map.put(2, login_obj.toString());

                    write(map, false);

                    //   Alert.show(BaseActivity.this, getString(R.string.app_name), "App is connect to Server. Please continue");

                } else if (msgType.equals(STATE_CONNECT_FAILURE)) {

                    // Alert.show(BaseActivity.this, getString(R.string.app_name), "Unable to connect to Server. Please wait while we try to reconnect to Server.");

                } else {
                    onMessageReceived(msgType, message);
                }
            }
        };

        LocalBroadcastManager.getInstance(context).registerReceiver(mMessageReceiver,
                new IntentFilter(Constants.MSG_SERVER_BROADCAST));


    }

    public void connectMessageServer() {
       // synchronized (this) {
            messageServerReadThread = MessageServerReadThread.getInstance(getApplicationContext());
            messageServerThread = MessageServerThread.getInstance(getApplicationContext());

//        }

    }

    public void onMessageReceived(String msgType, String message) {
        try {
            loading.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    public void write(final Map<Integer, String> map, boolean showLoading) {

        if (messageServerThread != null && messageServerReadThread != null) {

//            if (messageServerThread.isAlive() && messageServerReadThread.isAlive()) {

                Log.d("MessageServerThread", "message server thread running. writing now..");
                Log.d("messageServerReadThread", "message server read thread running");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("tesingbipl", "runOnUiThread: " + Calendar.getInstance().getTime());
                        messageServerThread.write(map);
                    }
                });
                if (showLoading) {
                    try {
                        loading.show();
                        // Hide after some seconds
                        final Handler handler = new Handler();
                        final Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
//                                loading.dismiss();
                                handler.removeCallbacks(this);
                            }
                        };

                        handler.postDelayed(runnable, 500);
                        Log.d("tesingbipl", "postDelayed");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//            }
        }

        if (messageServerThread == null) {
            Log.d("MessageServerThread", "is null..");
        }

        if (messageServerReadThread == null) {
            Log.d("messageServerReadThread", "is null..");
        }


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}