package com.softech.bipldirect.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.softech.bipldirect.Const.ConnectionDetector;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Network.OnRestClientCallback;
import com.softech.bipldirect.Network.RestClient;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.Util.Loading;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

import static com.softech.bipldirect.Network.MessageSocket.context;

public class ResearchPortalFragment extends Fragment {

    private static final String TAG = "ResearchPortalDebug";
    private WebView webView;
    private Loading loading;
    private static final String KEY_SYMBOL ="key_symbol";
    private String symbolName =null;

    public ResearchPortalFragment() {
        // Required empty public constructor
    }

    public static ResearchPortalFragment newInstance(String symbolName) {
        Bundle extras = new Bundle();
        extras.putString(KEY_SYMBOL,symbolName);
        ResearchPortalFragment fragment = new ResearchPortalFragment();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        symbolName = getArguments().getString(KEY_SYMBOL);
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        loading = new Loading(getActivity(),"Loading..");

        webView = (WebView) inflater.inflate(R.layout.fragment_reseach_portal, container, false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new AppWebViewClient());
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(s));
                startActivity(i);
            }
        });
        return webView;
    }

    @Override
    public void onResume() {
        ActionBar toolbar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle("Research Portal");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Constants.RESEARCH_PORTAL_URL.length() > 0) {
            String url=Constants.RESEARCH_PORTAL_URL;
                callingResearchPortalService(Constants.RESEARCH_PORTAL_URL);
        }
    }

    public void callingResearchPortalService(String url) {

        if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet()) {

            String encryptedUserName= "";
            try {
                encryptedUserName = EnctyptionUtils.encrypt(MainActivity.loginResponse.getResponse().getUserId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", "rashid.irshad");
                jsonObject.put("client", Constants.RESESRCH_PORTAL_CLIENT);
                jsonObject.put("password", "rashid123");
                jsonObject.put("ip", Constants.RESESRCH_PORTAL_IP);
                jsonObject.put("brokerUserCode", encryptedUserName);
                jsonObject.put("isDemoUser", false);

                RestClient.postRequest("research_portal", getActivity(), url, jsonObject,
                        new OnRestClientCallback() {
                            @Override
                            public void onRestSuccess(JSONObject response, String action) {

                                try {
                                    if (response.getString("response").equals("success")) {

                                        String url = response.getString("link");
                                        Log.e(TAG, "url: "+url);

                                        if (symbolName!=null){
                                            url=url+ "&symbol=" + symbolName;
                                            Log.e("PortalUrl", url);

                                        }
                                        webView.loadUrl(url);
                                    } else {
                                        Alert.show(getActivity() , getActivity().getString(R.string.app_name),response.getString("message"));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    HToast.showMsg(context, "Unable to connect to Trading Server please try later or check your network");
                                }
                            }

                            @Override
                            public void onRestError(Exception e, String action) {
                                Log.d(TAG, "onRestError: exception: " + e.getMessage() + " action: " + action);
                                Alert.showErrorAlert(getActivity());
                            }
                        }, false, "Please wait..");
            } catch (JSONException e) {
                e.printStackTrace();
                Alert.showErrorAlert(context);
            }
        }
    }

    private class AppWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loading.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loading.cancel();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            loading.cancel();
        }
    }
}