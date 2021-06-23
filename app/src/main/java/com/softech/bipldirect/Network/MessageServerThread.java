package com.softech.bipldirect.Network;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.softech.bipldirect.BaseActivity;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.MainActivity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Developed by Hasham.Tahir on 7/25/2016.
 */
public class MessageServerThread extends HandlerThread {

    private static MessageServerThread instance;
    private final Context context;
    public Handler mHandler;
    MessageSocket socket;
    String action = null;

    public MessageServerThread(Context context) {
        super("MessageServerThread", HandlerThread.MAX_PRIORITY);
        this.context = context;

        start();
    }

    public static void kill() {

        if (instance != null) {
            instance.quit();
            instance = null;
        }
    }

    public static MessageServerThread getInstance(Context context) {
        if (instance == null) {
            instance = new MessageServerThread(context);
        }
        return instance;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        mHandler = new Handler(getLooper()) {
            @Override
            public void handleMessage(Message msg) {

                try {

                    if (socket == null) {
                        MessageSocket.context = context;
                        socket = MessageSocket.getInstance();
                        Log.d("MessageSocket", "write connected");
                    }

                    if (socket != null && socket.isConnected()) {
                        Log.d("tesingbipl", "handleMessage: " + Calendar.getInstance().getTime());
                        writeToSocket(msg, socket);
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    if (e instanceof java.net.ConnectException) {

                        broadcastResponse(BaseActivity.STATE_CONNECT_EXCEPTION, null);

                    } else {
                        broadcastResponse(null, null);
                    }


                }

            }
        };
    }


    public void write(Map<Integer, String> map) {
        try {
            if (mHandler == null) {
                //  Log.d("testCrash", "handler is null");
                onLooperPrepared();
            }
            Message msg = mHandler.obtainMessage();
            msg.obj = map;

            mHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void writeToSocket(Message msg, MessageSocket socket) throws IOException {

        HashMap<Integer, String> map = (HashMap<Integer, String>) msg.obj;

        PrintWriter mOut;

        mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                true);
//        Log.d("tesingbipl", "on success writeToSocket: " + Calendar.getInstance().getTime());
        String final_str = map.get(2) + "<END>";
        mOut.println(final_str);
        Log.i("printLogTime", "write to socket: " + msg);
        Log.d("MessageSocket", "write: " + final_str);

        action = map.get(1);
    }

    private void broadcastResponse(String msgType, String response) {
        Intent intent = new Intent(Constants.MSG_SERVER_BROADCAST);
        intent.putExtra("msgType", msgType);
        intent.putExtra("response", response);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
