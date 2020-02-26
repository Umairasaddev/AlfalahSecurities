package com.softech.bipldirect.Util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */
public class HSnackBar {


    public static void showMsg(View view, String msg) {

        try {
            Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
