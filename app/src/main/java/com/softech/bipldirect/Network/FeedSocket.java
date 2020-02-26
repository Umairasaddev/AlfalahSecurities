package com.softech.bipldirect.Network;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Developed by Hasham.Tahir on 1/28/2016.
 */
public class FeedSocket extends Socket {

    public static final String TAG = "FeedSocketDebug";
    private static FeedSocket instance = null;


    public FeedSocket(InetAddress dstAddress, int dstPort) throws IOException {
        super(dstAddress, dstPort);
    }

    public static FeedSocket getInstance(InetAddress dstAddress, int dstPort) throws IOException {
        if (instance == null || instance.isClosed()) {
            instance = new FeedSocket(dstAddress, dstPort);
        }
        return instance;
    }

    public static void closeConnection() throws IOException {
        Log.d(TAG, "closeConnection: ");
        if (instance != null) {
            instance.close();
            instance = null;
        }
    }


    boolean isFeedSocketConnected() {
        if(instance == null)
            return false;

        return instance.isConnected();
    }
}
