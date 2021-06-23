package com.softech.bipldirect.Const;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.core.content.ContextCompat;

import com.softech.bipldirect.MainActivity;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */
public class ConnectionDetector {


    private static ConnectionDetector instance = null;
    private Context _context;


    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public static ConnectionDetector getInstance(Context context) {
        if (instance == null) {
            instance = new ConnectionDetector(context);
        }
        return instance;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        } else {
            return false;
        }
    }
}
