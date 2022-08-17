package com.softech.bipldirect;

import android.content.Context;

import androidx.multidex.BuildConfig;
import androidx.multidex.MultiDexApplication;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(
        formUri = "http://locallylahore.com/evolve_crash_log/", // will not be used
        mailTo = "sifyanali636@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text, formKey = "")

public class MyApplication extends MultiDexApplication {

    private static Context mAppContext;

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context mAppContext) {
        MyApplication.mAppContext = mAppContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (BuildConfig.DEBUG) {
            ACRA.init(this);
        } else {
            // do nothing.
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        ACRA.init(this);

        setAppContext(this);

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/helvetica.otf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

    }


}
