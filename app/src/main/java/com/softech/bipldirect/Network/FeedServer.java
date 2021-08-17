package com.softech.bipldirect.Network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Models.LoginModel.LoginResponse;
import com.softech.bipldirect.Util.Preferences;

import net.orange_box.storebox.StoreBox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FeedServer extends AsyncTask<String, String, String> {

    Context context;

    String TAG = "FeedServer";
    List<String> serverIpAddress;
    String serverPort;
    public FeedSocket socket;

    public FeedServer(Context context) {

        this.context = context;

        Preferences preferences = StoreBox.create(context, Preferences.class);

        Gson gson = new Gson();
        LoginResponse loginResult = gson.fromJson(preferences.getLoginResult(), LoginResponse.class);

        serverPort = loginResult.getResponse().getFeedPort();
        String feedIP = loginResult.getResponse().getFeedIP();

        try {
            serverIpAddress = new ArrayList<>(Arrays.asList(feedIP.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "FeedIP: null");
        }

        Log.d(TAG, "FeedIP: " + loginResult.getResponse().getFeedIP());
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

    @Override
    protected String doInBackground(String... params) {

        try {
            InetAddress serverAddr = InetAddress.getByName(serverIpAddress.get(0));
            if (serverPort.contains(",") || serverPort.length() > 4) {

                String[] ports = serverPort.split(",");

                Random rand = new Random();
                for (int i = 0; i < ports.length; i++) {
                    int portPos = rand.nextInt(ports.length);
                    String port = ports[portPos];

                    //  for (String port : ports) {

                    try {
                        socket = FeedSocket.getInstance(serverAddr, Integer.parseInt(port));
                        if (socket.isConnected()) {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } else {
                socket = FeedSocket.getInstance(serverAddr, Integer.parseInt(serverPort));
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (serverIpAddress != null && serverIpAddress.size() > 1) {

                try {
                    InetAddress serverAddr = InetAddress.getByName(serverIpAddress.get(1));
                    socket = FeedSocket.getInstance(serverAddr, Integer.parseInt(serverPort));
                } catch (Exception e1) {
                    e1.printStackTrace();
                    sendMessage(null, false);
                }

            } else {
                sendMessage(null, false);
            }
        }

        if (socket != null && socket.isConnected()) {
            Log.d(TAG, "socket is connected");
            try {
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                String final_str = params[0] + "<END>";
                out.println(final_str);
                Log.d(TAG, "socket write..");
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (socket.isConnected()) {

                    String line = br.readLine();

                    if (line != null) {

                        if (line.contains("<END>")) {
                            line = line.replace("<END>", "");
                        }
                        publishProgress(line);


                    } else {
                        Log.d(TAG, "Response :: line null ");

                        break;
                    }
                }
                sendMessage(null, false);
            } catch (Exception e) {

                if (!socket.isConnected()) {
                    sendMessage(null, false);
                }
                sendMessage(null, false);
                e.printStackTrace();
            }


        } else {
            sendMessage(null, false);
            Log.d(TAG, "socket not connected");
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        sendMessage(values[0], true);

    }

    private void sendMessage(String response, boolean isConnected) {
        Intent intent = new Intent(Constants.FEED_SERVER_BROADCAST);
        intent.putExtra("response", response);
        intent.putExtra("isConnected", isConnected);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public boolean isFeedSocketConnected() {
        if (socket == null) return false;
        return socket.isFeedSocketConnected();
    }


}
