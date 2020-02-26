package com.softech.bipldirect.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Developed by Hasham.Tahir on 2/2/2016.
 */
public class HToast {

    public static void showMsg(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
