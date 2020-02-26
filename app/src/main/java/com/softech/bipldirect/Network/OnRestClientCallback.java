package com.softech.bipldirect.Network;

import org.json.JSONObject;

/**
 * Developed by Hasham.Tahir on 1/15/2016.
 */
public interface OnRestClientCallback {

    void onRestSuccess(JSONObject response, String action);

    void onRestError(Exception e, String action);
}
