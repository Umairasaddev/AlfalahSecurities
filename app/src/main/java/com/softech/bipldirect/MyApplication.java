package com.softech.bipldirect;

import android.content.Context;
import android.support.multidex.MultiDexApplication;


import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */
@ReportsCrashes(
        formUri = "http://locallylahore.com/evolve_crash_log/", // will not be used
        mailTo = "waqarmustafa18@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text, formKey = "")
/*@ReportsCrashes(formKey = "", // will not be used
        mailTo = "hasham.tahir@softech.com.pk", mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)*/
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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Helvetica.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }


}
