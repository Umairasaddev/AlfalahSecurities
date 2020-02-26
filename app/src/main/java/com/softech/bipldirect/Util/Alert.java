package com.softech.bipldirect.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.softech.bipldirect.LoginActivity;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */
public class Alert {

    public static void show(final Context context, final String title, final String msg) {

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                handler.removeCallbacks(this);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener()

                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }

                );
                builder.setTitle(title);
                builder.setMessage(msg);
                try {
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void showToLogin(final Context context, final String title, final String msg) {

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {

                handler.removeCallbacks(this);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setNeutralButton("OK", new DialogInterface.OnClickListener()

                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Activity) context).finish();
                                context.startActivity(new Intent(context, LoginActivity.class));

                            }
                        }

                );
                builder.setTitle(title);
                builder.setMessage(msg);
                try {
                    builder.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static void showErrorAlert(Context context) {

        Alert.show(context, "ERROR", "Error occurred please try again.");
    }


}
