package com.softech.bipldirect.Network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.LoginActivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 * Edited by Robin Royal
 */

public class MessageSocket extends Socket {

    public static Context context;
    private static MessageSocket instance = null;


    private MessageSocket(InetSocketAddress dstAddress, int dstPort) throws Exception {
        connect(dstAddress, dstPort);
    }

    public static synchronized MessageSocket getInstance() throws Exception {


        if (instance == null) {
            outerLoop:
            for (int i = 0; i < Constants.ports.length; i++) {

                for (int j = 0; j < Constants.serverIpAddress.length; j++) {
                    InetAddress serverAddress = InetAddress.getByName(Constants.serverIpAddress[j]);
                    Log.d("MessageSocket", "trying on: " +
                            Constants.serverIpAddress[j] + ":" + Constants.ports[i]);

                    try {
                        instance = new MessageSocket(new InetSocketAddress(serverAddress, Constants.ports[i]), 6000);
                        if (instance.isConnected()) {
                            Log.d("MessageSocket", "connected to: " +
                                    Constants.serverIpAddress[j] + ":" + Constants.ports[i]);

                            break outerLoop;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();


                        if (i == Constants.serverIpAddress.length - 1 && j == Constants.ports.length - 1) {
                            if (e instanceof java.net.ConnectException) {
                            }
                        }

                    }

                }

            }
        }
        return instance;
    }

    private static void broadcastResponse(String msgType, String response) {
        Intent intent = new Intent(Constants.MSG_SERVER_BROADCAST);
        intent.putExtra("msgType", msgType);
        intent.putExtra("response", response);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


    public static void closeConnection() throws IOException {

        if (instance != null) {
//            instance.close();
            instance = null;
        }
    }


    private static void logInAferReconnect() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
