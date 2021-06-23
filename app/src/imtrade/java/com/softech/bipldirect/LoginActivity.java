package com.softech.bipldirect;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.softech.bipldirect.Const.ConnectionDetector;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Models.Event;
import com.softech.bipldirect.Models.LoginModel.LoginResponse;
import com.softech.bipldirect.Models.MarketModel.MarketResponse;
import com.softech.bipldirect.Models.SymbolsModel.SymbolsResponse;
import com.softech.bipldirect.Network.OnRestClientCallback;
import com.softech.bipldirect.Network.RestClient;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.FingerprintHandler;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.Util.Loading;
import com.softech.bipldirect.Util.Preferences;
import com.softech.bipldirect.Util.Util;

import net.orange_box.storebox.StoreBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */


@RequiresApi(api = Build.VERSION_CODES.M)
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    public static boolean check = false;
    @BindView(R.id.login_name)
    EditText etName;
    @BindView(R.id.login_pass)
    EditText etPass;
    //    @BindView(R.id.login_registerme)
//    Button registermeBut;
    @BindView(R.id.tv_forgotPwd)
    TextView forgotPassword;

    //        @BindView(R.id.login_server)
//    TextView etServer;
    Context context = LoginActivity.this;
    private Preferences preferences;
    private String user, pas;
    String[] serverNameArray = new String[]{"Primary", "Secondary", "DR"};
    String[] serverUrlArray = new String[]{"terminal1.alfalahtrade.com", "terminal2.alfalahtrade.com", "terminal1.alfalahtrade.net"};
    String userEncoded;
    String passEncoded, passdecoded;
    Button login_btn;
    private Loading loading;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        login_btn = (Button) findViewById(R.id.login_btn);
        loading = new Loading(context, "Please wait...");

//        if (BuildConfig.FLAVOR=="alfalahsec") {
//            TextView etServer = (TextView) findViewById(R.id.login_server);
//        }
//        etName.setText("act01315");
//        etPass.setText("123456");
//        etName.setText("00022249");
//        etPass.setText("bipl1234");
//        etName.setText("00024639");
//        etPass.setText("pakipower1");


//        etName.setText("RMS01");
//        etPass.setText("123456");

        etName.setText("TESTING");
        etPass.setText("12345678");
//        etName.setText("25394");
//        etPass.setText("12345678");

        preferences = StoreBox.create(this, Preferences.class);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getBoolean("discon")) {
                Alert.show(context, getString(R.string.app_name), extras.getString("message"));
            }
        }
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ForgetPasswordActivity.class));
            }
        });
//        registermeBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(context, SignupActivity.class));
//            }
//        });
//        etServer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (BuildConfig.FLAVOR.equals("alfalahsec")) {
//                    etServer.setText("Primary");
//                    final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
//                    alert.setItems(serverNameArray, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            etServer.setText(serverNameArray[which]);
//                            Constants.serverIpAddress = new String[]{serverUrlArray[which]};
//
//
//                        }
//                    });
//                    alert.show();
//                }
//            }
//        });




    }

    public void checkFingerPrint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Get an instance of KeyguardManager and FingerprintManager//
            keyguardManager =
                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            //Check whether the device has a fingerprint sensor//
            if (!fingerprintManager.isHardwareDetected()) {
                // If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
                Toast.makeText(context, "Your device doesn't support fingerprint authentication", Toast.LENGTH_SHORT).show();
            }
            //Check whether the user has granted your app the USE_FINGERPRINT permission//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC)
//                    != PackageManager.PERMISSION_GRANTED) {
//                // If your app doesn't have this permission, then display the following text//
//                Toast.makeText(context, "Please enable the fingerprint permission", Toast.LENGTH_SHORT).show();
//
//            }

            //Check that the user has registered at least one fingerprint//
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                // If the user hasn’t configured any fingerprints, then display the following message//
                Toast.makeText(context, "No fingerprint configured. Please register at least one fingerprint in your device's Settings", Toast.LENGTH_SHORT).show();

            }

            //Check that the lockscreen is secured//
            if (!keyguardManager.isKeyguardSecure()) {
                // If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
                Toast.makeText(context, "Please enable lockscreen security in your device's Settings", Toast.LENGTH_SHORT).show();

            } else {
                try {
                    generateKey();

                } catch (Exception e) {

                }

                if (initCipher()) {
                    //If the cipher is initialized successfully, then create a CryptoObject instance//
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);

                    // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
                    // for starting the authentication process (via the startAuth method) and processing the authentication process events//
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }
            }
        }
    }

    private void generateKey() throws FingerprintException {
        try {
            // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            //Generate the key//
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            //Initialize an empty KeyStore//
            keyStore.load(null);

            //Initialize the KeyGenerator//
            keyGenerator.init(new

                    //Specify the operation(s) this key can be used for//
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                    //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            //Generate the key//
            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }
    }


    public boolean initCipher() {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Return true if the cipher has been initialized successfully//
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {

            //Return false if cipher initialization failed//
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }

    public void callingloginservice(View view) {
        user = etName.getText().toString();
        pas = etPass.getText().toString();
        try {
            EnctyptionUtils enctyptionUtils = new EnctyptionUtils();
            userEncoded = enctyptionUtils.encrypt(user.trim());
            passEncoded = enctyptionUtils.encrypt(pas);
        } catch (Exception e) {
            login_btn.setEnabled(true);
            e.printStackTrace();
        }
        if (user.length() > 0 && pas.length() > 0) {
            final JsonObject login_obj = new JsonObject();

            login_obj.addProperty("MSGTYPE", Constants.LOGIN_MESSAGE_IDENTIFIER);
            login_obj.addProperty("userId", userEncoded);
            login_obj.addProperty("pswd", passEncoded);
            // For encryption
            login_obj.addProperty("Ver", "1.7");
//For encryption
            if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {
                login_btn.setEnabled(false);
                loading.show();
                connectWithMessageServer(login_obj);
            } else {
                login_btn.setEnabled(true);

                try {
                    HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            login_btn.setEnabled(true);

            HSnackBar.showMsg(view, "Please enter username and password.");
        }

    }

    @OnClick(R.id.login_btn)
    public void submit(final View view) {
        callingloginservice(view);
//        checkFingerPrint();
    }

    private void connectWithMessageServer(final JsonObject login_obj) {
        Log.d(TAG, "connectWithMessageServer");

        connectMessageServer();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<Integer, String> map = new HashMap<>();
                map.put(1, Constants.LOGIN_MESSAGE_IDENTIFIER);
                map.put(2, login_obj.toString());
                write(map, false);
            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Util.logOut) {
            Util.logOut = false;
            Alert.show(context, getString(R.string.app_name), Util.logOutString);
        }

        enableReconnect = false;
    }

    @Override
    public void onMessageReceived(String action, String resp) {

        Gson gson = new Gson();
        check = true;
        JsonParser jsonParser = new JsonParser();

        try {
            JsonObject jsonObject = jsonParser.parse(resp).getAsJsonObject();

            String MSGTYPE = jsonObject.get("response").getAsJsonObject().get("MSGTYPE").getAsString();
            String error = jsonObject.get("error").getAsString();
            String code = jsonObject.get("code").getAsString();

            Log.d(TAG, "MSGTYPE: " + MSGTYPE);

            if (code.equals("200") && error.equals("")) {

                switch (MSGTYPE) {

                    case Constants.LOGIN_MESSAGE_RESPONSE: {

                        LoginResponse result = gson.fromJson(resp, LoginResponse.class);
                        Log.d("Call", "response result: " + result);


                        if (result != null) {

                            if (result.getCode().equals("200")) {

                                preferences.setLoginResult(gson.toJson(result));

                                preferences.setUsername(etName.getText().toString());
                                preferences.setPassword(etPass.getText().toString());
                                //      FOR ZAFAR SECURITIES

                                if (result.getResponse().isShowSecLevPswd() && result.getResponse().getUsertype() == 1) {

                                    try {
                                        String encodedPass = result.getResponse().getSecondLevelPassword();


                                        if (encodedPass.equals("")) {
                                            loading.dismiss();
                                            preferences.setDecryptedPassword(encodedPass);
                                            check = false;
                                            startActivity(new Intent(context, EncryptedPasswordActivity.class));
                                            finish();
                                        } else {
                                            loading.dismiss();
                                            check = false;
                                            EnctyptionUtils enctyptionUtils = new EnctyptionUtils();
                                            passdecoded = enctyptionUtils.decrypt(result.getResponse().getSecondLevelPassword());
                                            Log.d("passworddecoded", passdecoded);
                                            preferences.setDecryptedPassword(passdecoded);
                                            preferences.setClientCode(result.getResponse().getClient());
                                            startActivity(new Intent(context, EncryptedPasswordActivity.class));
                                            finish();
                                        }

                                    } catch (Exception e) {
                                        loading.dismiss();
                                        login_btn.setEnabled(true);
                                        e.printStackTrace();

                                    }
                                } else if (result.getResponse().getChangePassword() != null && result.getResponse().getChangePassword().equals("true")) {
                                    loading.dismiss();

                                    preferences.setUserId(result.getResponse().getUserId());
                                    startActivity(new Intent(LoginActivity.this, ChangePasswordActivity.class));
                                } else {
//      FOR ZAFAR SECURITIES

                                    getMarket();
                                }
                                preferences.removeEvents(R.string.key_events);

                                Event.add(context, new Event(System.currentTimeMillis(), result.getResponse().getUserId() + " logged in successfully."));


//                                getSymbolsFromServer();
                            } else {

                                loading.dismiss();
                                login_btn.setEnabled(true);
                                Alert.show(LoginActivity.this, "", result.getError());
                            }


                        } else {
                            loading.dismiss();
                            login_btn.setEnabled(true);
                            Log.d(TAG, "Response :: LoginResponse null ");
                        }

                    }
                    break;


                    case Constants.SYMBOL_MESSAGE_RESPONSE: {

                        SymbolsResponse result = gson.fromJson(resp, SymbolsResponse.class);

                        if (result != null) {

                            if (result.getCode().equals("200")) {

                                preferences.setSymbolResult(gson.toJson(result));

//                                getMarket();

                            } else {
                                loading.dismiss();
                                login_btn.setEnabled(true);

                                Alert.show(context, "", result.getError());
                            }


                        } else {
                            loading.dismiss();
                            login_btn.setEnabled(true);

                            Log.d(TAG, "Response :: SymbolsResponse null ");
                        }
                    }
                    break;

                    case Constants.SUBSCRIPTION_LIST_REQUEST_RESPONSE: {

                        MarketResponse result = gson.fromJson(resp, MarketResponse.class);

                        if (result != null) {


                            if (result.getCode().equals("200")) {

                                preferences.setMarketResult(gson.toJson(result));
                                loading.dismiss();
                                startActivity(new Intent(context, MainActivity.class));
                                finish();

                            } else {
                                loading.dismiss();
                                login_btn.setEnabled(true);

                                Alert.show(context, "", result.getError());
                            }


                        } else {
                            loading.dismiss();
                            login_btn.setEnabled(true);

                            Log.d(TAG, "Response :: MarketResponse null ");
                        }
                    }
                    break;
                }

            } else {
                loading.dismiss();
                login_btn.setEnabled(true);
                Alert.show(context, getString(R.string.app_name), error);
            }


        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            loading.dismiss();
            login_btn.setEnabled(true);
            Alert.showErrorAlert(context);

        }


    }

    private void getSymbolsFromServer() {

        JsonObject login_obj = new JsonObject();

        login_obj.addProperty("MSGTYPE", Constants.SYMBOL_MESSAGE_IDENTIFIER);
//        login_obj.addProperty("userId", user);
//        login_obj.addProperty("pswd", pas);
        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SYMBOL_MESSAGE_IDENTIFIER);
            map.put(2, login_obj.toString());

            write(map, false);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void getMarket() {

        Gson gson = new Gson();


        String clientcode = gson.fromJson(preferences.getLoginResult(), LoginResponse.class).getResponse().getClient();

        Log.d("clientcode", "clientcode: " + clientcode);

        JsonObject login_obj = new JsonObject();

        login_obj.addProperty("MSGTYPE", Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
        login_obj.addProperty("userId", user);
        login_obj.addProperty("client", clientcode);


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
            map.put(2, login_obj.toString());

            write(map, false);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

