package com.softech.bipldirect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.softech.bipldirect.Util.Alert;

/**
 * Developed by Hasham.Tahir on 7/14/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);

        if (BuildConfig.FLAVOR=="alfalahsec") {
            String username = "", password = "";
            String BANK_USER = "";
            Bundle extras = getIntent().getExtras();
            if (extras != null) {

                username = extras.getString("username");
                password = extras.getString("password");
                BANK_USER = extras.getString("BANK_USER");
            }

            final String finalUsername = username;
            final String finalPassword = password;
            final String finalBANK_USER = BANK_USER;


            if (finalBANK_USER.equals("BANK_USER")) {
//                Intent intent = new Intent(SplashActivity.this, ValidatingActivity.class);
//                intent.putExtra("username", finalUsername);
//                intent.putExtra("password", finalPassword);
//                intent.putExtra("BANK_USER", finalBANK_USER);
//                startActivity(intent);
//                finish();
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 5000);
            }
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 5000);
        }
    }


}
