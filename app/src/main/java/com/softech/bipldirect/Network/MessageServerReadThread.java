package com.softech.bipldirect.Network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.softech.bipldirect.BaseActivity;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.MainActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import static com.softech.bipldirect.Network.FeedSocket.TAG;

public class MessageServerReadThread extends HandlerThread {

    private static MessageServerReadThread instance;
    private final Context context;
    public Handler mHandler;
    volatile MessageSocket socket;
    String action = null;
    int retries = 1;

    public MessageServerReadThread(Context context) {
        super("MessageServerReadThread", HandlerThread.MAX_PRIORITY);
        this.context = context;
        Log.d("MessageRead", "started");
        start();
        onLooperPrepared();
    }

    public static void kill() {

        if (instance != null) {
            instance.quit();
            instance = null;
        }
    }

    public static MessageServerReadThread getInstance(Context context) {
        if (instance == null) {
            instance = new MessageServerReadThread(context);
        }
        return instance;
    }

    public static void LogResponse(String tag, String response) {
        int maxLogSize = 1000;
        for (int i = 0; i <= response.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > response.length() ? response.length() : end;
            Log.d(tag, response.substring(start, end));
        }
    }


    @SuppressLint("HandlerLeak")
    @Override
    public void run() {

        Log.d("MessageSocket", "run called");

        try {

            if (socket == null) {
                MessageSocket.context = context;
                socket = MessageSocket.getInstance();
                Log.d("MessageSocket", "read connected3");
            }

            if (socket != null && socket.isConnected()) {

                Log.d("MessageSocket", "socket.isConnected()3");

                BufferedReader streamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String inputStr = null;

//                while ((inputStr = streamReader.readLine()) != null) {

                while (socket.isConnected()) {

//                    Log.d("while", "while");

                    inputStr = streamReader.readLine();


                    if (inputStr != null) {

                        inputStr = inputStr.replace("<END>", "");
                        Log.d("MessageSocket", "inputStr: "+inputStr);
                        try {

                            JsonObject jsonObject = new JsonParser().parse(inputStr).getAsJsonObject();
                            String MSGTYPE = jsonObject.get("response").getAsJsonObject().get("MSGTYPE").getAsString();

                            broadcastResponse(MSGTYPE, inputStr);
                            LogResponse("MessageSocket", "read: " + inputStr);
                        } catch (Exception e) {
                            e.printStackTrace();
                            checkException(e);
                            LogResponse("MessageSocket", "read error: " + inputStr);
                        }
                    }


                }


            }


        } catch (Exception e) {
            e.printStackTrace();

            checkException(e);


        }


    }

    private void checkException(Exception e) {
        Log.d(TAG, "checkException: inside check exception");
        if (e instanceof java.net.SocketException) {

            if (BaseActivity.enableReconnect) {

                broadcastResponse(BaseActivity.STATE_DISCONNECTED, null);

                try {
                    MessageSocket.closeConnection();
                    FeedSocket.closeConnection();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                reconnectServers();
                
            }

        } else {
            broadcastResponse(null, null);
        }
    }

    private void reconnectServers() {

        final Timer mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (retries < 30) {
                    Log.d("MessageServerReadThread", "retry " + retries);
                    try {
                        socket = MessageSocket.getInstance();

                        if (socket.isConnected()) {
                            Log.d("MessageServerReadThread", "retry connected");
                            mTimer.cancel();
                            retries = 1;

                            MessageServerReadThread.kill();
                            MessageServerThread.kill();

//                            synchronized (this) {
//                                instance = new MessageServerReadThread(context.getApplicationContext());
//                                new MessageServerThread(context.getApplicationContext());
//                            }

                            Log.d("MessageReadThr", "should reconnect");
                            broadcastResponse(BaseActivity.STATE_RECONNECTED, null);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        retries++;
                        Log.d("MessageServerReadThread", "retries: " + retries);

                    }
                } else {
                    mTimer.cancel();
                    retries = 1;
                    broadcastResponse(BaseActivity.STATE_CONNECT_FAILURE, null);
                }
            }
        }, 0, 1000 * 10);

    }


    private void broadcastResponse(String msgType, String response) {
        Intent intent = new Intent(Constants.MSG_SERVER_BROADCAST);
        intent.putExtra("msgType", msgType);
        intent.putExtra("response", response);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}