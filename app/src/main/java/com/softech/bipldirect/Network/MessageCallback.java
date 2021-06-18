package com.softech.bipldirect.Network;

public interface MessageCallback {

    void onMessageReceived(String action, String resp);
}