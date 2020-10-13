package com.softech.bipldirect.Util;

import com.softech.bipldirect.R;

import net.orange_box.storebox.annotations.method.KeyByResource;
import net.orange_box.storebox.annotations.method.RemoveMethod;

/**
 * Developed by Hasham.Tahir on 1/27/2016.
 */
//@SuppressWarnings("ALL")
public interface Preferences {


    @KeyByResource(R.string.key_login)
    String getLoginResult();

    @KeyByResource(R.string.key_login)
    void setLoginResult(String value);

    @RemoveMethod
    void removeLoginResult(int keyRes);

    @KeyByResource(R.string.key_symbols)
    String getSymbolResult();

    @KeyByResource(R.string.key_symbols)
    void setSymbolResult(String value);

    @RemoveMethod
    void removeSymbolResult(int keyRes);

    @KeyByResource(R.string.key_market)
    String getMarketResult();

    @KeyByResource(R.string.key_market)
    void setMarketResult(String value);

    @RemoveMethod
    void removeMarketResult(int keyRes);

    @KeyByResource(R.string.key_events)
    String getEvents();

    @KeyByResource(R.string.key_events)
    void setEvents(String value);

    @RemoveMethod
    void removeEvents(int keyRes);

    @KeyByResource(R.string.key_remember_pin)
    boolean getRememberPin();

    @KeyByResource(R.string.key_remember_pin)
    void setRememberPin(boolean value);

    @RemoveMethod
    void removeRememberPin(int keyRes);

    @KeyByResource(R.string.key_username)
    String getUsername();

    @KeyByResource(R.string.key_username)
    void setUsername(String value);

    @RemoveMethod
    void removeUsername(int keyRes);

    @KeyByResource(R.string.key_password)
    String getPassword();

    @KeyByResource(R.string.key_password)
    void setPassword(String value);

    @RemoveMethod
    void removePassword(int keyRes);

    @KeyByResource(R.string.key_decrypted_password)
    String getDecryptedPassword();

    @KeyByResource(R.string.key_decrypted_password)
    void setDecryptedPassword(String value);

    @RemoveMethod
    void removeDecryptedPassword(int keyRes);
    @KeyByResource(R.string.key_client)
    String getClientCode();

    @KeyByResource(R.string.key_client)
    void setClientCode(String value);

    @KeyByResource(R.string.key_userId)
    String getUserId();

    @KeyByResource(R.string.key_userId)
    void setUserId(String value);

}
