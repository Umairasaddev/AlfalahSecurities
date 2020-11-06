package com.softech.bipldirect;


import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.softech.bipldirect.Adapters.NavAdapter;
import com.softech.bipldirect.Const.ConnectionDetector;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.Fragments.AccountFragment;
import com.softech.bipldirect.Fragments.CashBookFragment;
import com.softech.bipldirect.Fragments.EventsFragment;
import com.softech.bipldirect.Fragments.ExchangeFragment;
import com.softech.bipldirect.Fragments.LinksFragment;
import com.softech.bipldirect.Fragments.MarginDetailFragment;
import com.softech.bipldirect.Fragments.MarketFragment;
import com.softech.bipldirect.Fragments.NetShareDetailFragment;
import com.softech.bipldirect.Fragments.NetSharesFragment;
import com.softech.bipldirect.Fragments.OrderStatsFragment;
import com.softech.bipldirect.Fragments.PaymentFragment;
import com.softech.bipldirect.Fragments.PortfolioDetail;
import com.softech.bipldirect.Fragments.PortfolioFragment;
import com.softech.bipldirect.Fragments.QuotesFragment;
import com.softech.bipldirect.Fragments.ResearchPortalFragment;
import com.softech.bipldirect.Fragments.SettingFragment;
import com.softech.bipldirect.Fragments.SymbolsFragment;
import com.softech.bipldirect.Fragments.TopSymbolsFragment;
import com.softech.bipldirect.Fragments.TradeFragment;
import com.softech.bipldirect.Fragments.UserProfileFragment;
import com.softech.bipldirect.Models.AccountModel.AccountResponse;
import com.softech.bipldirect.Models.CashBookModel.CashBookResponse;
import com.softech.bipldirect.Models.Event;
import com.softech.bipldirect.Models.ExchangeModel.ExchangeResponse;
import com.softech.bipldirect.Models.LinksModel.LinksResponse;
import com.softech.bipldirect.Models.LoginModel.LoginResponse;
import com.softech.bipldirect.Models.MarginModel.MarginResponse;
import com.softech.bipldirect.Models.MarketModel.MarketResponse;
import com.softech.bipldirect.Models.MarketModel.MarketSymbol;
import com.softech.bipldirect.Models.Menu;
import com.softech.bipldirect.Models.NetShareModel.NetShareCustody;
import com.softech.bipldirect.Models.NetShareModel.NetShareResponse;
import com.softech.bipldirect.Models.OrderStatsModel.OrderStatsResponse;
import com.softech.bipldirect.Models.OrderStatsModel.OrdersList;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioResponse;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;
import com.softech.bipldirect.Models.ProfileModel.ProfileResponse;
import com.softech.bipldirect.Models.SymbolsModel.Symbol;
import com.softech.bipldirect.Models.SymbolsModel.SymbolsResponse;
import com.softech.bipldirect.Models.TopSymModel.TopSymResponse;
import com.softech.bipldirect.Models.TopSymModel.TopSymbol;
import com.softech.bipldirect.Network.FeedServer;
import com.softech.bipldirect.Network.FeedSocket;
import com.softech.bipldirect.Network.MessageSocket;
import com.softech.bipldirect.Network.OnRestClientCallback;
import com.softech.bipldirect.Network.RestClient;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.DividerItemDecoration;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.Util.Preferences;
import com.softech.bipldirect.Util.Util;

import net.orange_box.storebox.StoreBox;
import net.orange_box.storebox.adapters.StoreType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.softech.bipldirect.Network.MessageSocket.context;

public class MainActivity extends BaseActivity implements NavAdapter.OnMenuInteractionListener,
        MarketFragment.OnMarketFragmentListener, MarketFragment.OnSymbolRequest,
        OrderStatsFragment.OnOrderDeleteRequest, QuotesFragment.OnQoutesFragmentListener {

    public static LoginResponse loginResponse;
    public static MarketResponse marketResponse;
    public static SymbolsResponse symbolsResponse;
    public static Preferences preferences;
    public static ArrayList<String> optionItems = new ArrayList<>();
    final String TAG = "MainActivity";
    Context context = MainActivity.this;
    List<Menu> navMenuList = new ArrayList<>();
    FragmentManager fragmentManager;
    MarketFragment marketFragment = MarketFragment.newInstance();
    OrderStatsFragment orderStatsFragment = OrderStatsFragment.newInstance();
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private boolean isActive = true;
    private AtomicInteger notificationID = new AtomicInteger(0);
    String useridEncoded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectMessageServer();

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        preferences = StoreBox.create(this, Preferences.class);

        initSessionData();

        try {
            initNavMenu();

        } catch (NullPointerException e) {
            e.printStackTrace();
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }

        getSupportFragmentManager().addOnBackStackChangedListener(getListener());

        fragmentManager = getSupportFragmentManager();

        replaceFragment(marketFragment, true, false);
        getSymbolsFromServer();
        connectFeed();
    }
    private void getSymbolsFromServer() {

        JsonObject login_obj = new JsonObject();

        login_obj.addProperty("MSGTYPE", Constants.SYMBOL_MESSAGE_IDENTIFIER);

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SYMBOL_MESSAGE_IDENTIFIER);
            map.put(2, login_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void portfolioWatchRequest(String clientcode){
        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.PORTFOLIO_CASH_REQUEST_IDENTIFIER);
        request_obj.addProperty("exchange", loginResponse.getResponse().getExchange());
        request_obj.addProperty("client", clientcode);


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.PORTFOLIO_CASH_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void connectFeed() {

        try{
            JsonObject feed_obj = new JsonObject();

            String action = Constants.FEED_LOGIN_MESSAGE_IDENTIFIER;
            String user = MainActivity.loginResponse.getResponse().getUserId();

            feed_obj.addProperty("MSGTYPE", action);
            feed_obj.addProperty("userId", user);

            new FeedServer(context).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, feed_obj.toString());
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        enableReconnect = true;
        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    @Override
    protected void onDestroy() {
        deleteAll();
        super.onDestroy();
    }


    private void initNavMenu() {

        RecyclerView navigationView = (RecyclerView) findViewById(R.id.menu_list);

        String TrnCodes = loginResponse.getResponse().getTrnCodes();
        Log.d("TrnCodes", "TrnCodes: " + TrnCodes);

        optionItems.clear();

        if (TrnCodes.length() > 0) {

            if (TrnCodes.contains("OM01")) {
                navMenuList.add(new Menu("Market", R.drawable.iconmarket2x, false));
            }
            if (TrnCodes.contains("OM19")) {
             //   navMenuList.add(new Menu("Exchange", R.drawable.marketicon2x, false));
                navMenuList.add(new Menu("Index Watch", R.drawable.marketicon2x, false));
            }
            if (TrnCodes.contains("OM24")) {
                navMenuList.add(new Menu("Research Portal", R.drawable.research2x, false));
            }
            if (TrnCodes.contains("OM13")) {
                navMenuList.add(new Menu("Message Board", R.drawable.events2x, false));
            }
            if (TrnCodes.contains("OM03")) {
                navMenuList.add(new Menu("Order Status", R.drawable.orderstatus2x, false));
            }
            if (TrnCodes.contains("OM06") || TrnCodes.contains("OM012")) {
                navMenuList.add(new Menu("Order", R.drawable.trade2x, false));
            }
            if (TrnCodes.contains("OM07")) {
                navMenuList.add(new Menu("Quotes", R.drawable.quotes2x, false));
            }
            if (TrnCodes.contains("OM15")) {
                navMenuList.add(new Menu("Symbols", R.drawable.symbols2x, false));
            }
            if (TrnCodes.contains("OM09")) {
                navMenuList.add(new Menu("Top Symbols", R.drawable.topsymbols2x, false));
            }
            if (TrnCodes.contains("OM04")) {
                navMenuList.add(new Menu("Portfolio Summary", R.drawable.portfoliosummary2x, false));
            }
            if (TrnCodes.contains("OM21")) {
                navMenuList.add(new Menu("Cash Book", R.drawable.cashbook2x, false));
            }
            if (TrnCodes.contains("OM14")) {
                navMenuList.add(new Menu("Payment Request", R.drawable.paymentrequest2x, false));
            }
            if (TrnCodes.contains("OM18")) {
                navMenuList.add(new Menu("Profile", R.drawable.profileicon2x, false));
            }

            if (TrnCodes.contains("OM02")) {
                navMenuList.add(new Menu("Account Status", R.drawable.account2x, false));
            }
            if (TrnCodes.contains("OM10")) {
                navMenuList.add(new Menu("Settings", R.drawable.setting2x, false));
            }
            if (TrnCodes.contains("OM17")) {
                navMenuList.add(new Menu("Links", R.drawable.linkicon2x, false));
            }


            if (TrnCodes.contains("OM07")) {
                optionItems.add("Summary");
            }
            if (TrnCodes.contains("OM20"))
            {
                optionItems.add("Charts");
            }

            if ((TrnCodes.contains("OM06") || TrnCodes.contains("OM012"))) {
                optionItems.add("Trade");
            }


            if (TrnCodes.contains("OM16")) {
                navMenuList.add(new Menu("Margin Detail", R.drawable.margin2x, false));
            }
            if (TrnCodes.contains("OM22")) {
                navMenuList.add(new Menu("Net Shares", R.drawable.netcustody2x, false));
            }


        } else {
            navMenuList.add(new Menu("Market", R.drawable.iconmarket2x, false));

       //     navMenuList.add(new Menu("Exchanges", R.drawable.marketicon2x, false));
                 navMenuList.add(new Menu("Market Indices", R.drawable.marketicon2x, false));
        }
        if (TrnCodes.contains("OM24")) {
            optionItems.add("Research");
        }
        optionItems.add("Delete");

        navMenuList.add(new Menu("Logout", R.drawable.logout2x, false));


        navigationView.setLayoutManager(new LinearLayoutManager(context));
        navigationView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));

        navigationView.setAdapter(new NavAdapter(navMenuList, this));

    }

    private void initSessionData() {

        Gson gson = new Gson();

        loginResponse = gson.fromJson(preferences.getLoginResult(), LoginResponse.class);
        marketResponse = gson.fromJson(preferences.getMarketResult(), MarketResponse.class);
        symbolsResponse = gson.fromJson(preferences.getSymbolResult(), SymbolsResponse.class);


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = fragmentManager.getBackStackEntryCount();
            if (count == 1) {

                if (!marketFragment.isVisible()) {

                    if (!drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.openDrawer(GravityCompat.START);
                    }


                } else {

                    logoutAlert();
                }


            } else {
                fragmentManager.popBackStack();
            }

        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_home:
                int count = fragmentManager.getBackStackEntryCount();
                Log.d("action_home", "count: " + count);

                if (count == 2) {
                    fragmentManager.popBackStackImmediate();
                } else if (count == 1) {
                    replaceFragment(marketFragment, true, false);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMenuInteraction(Menu item) {

        switch (item.getIc_resource()) {


            case R.drawable.iconmarket2x: {
                replaceFragment(marketFragment, true, false);
            }
            break;

            case R.drawable.orderstatus2x: {
                replaceFragment(orderStatsFragment, true, false);
            }
            break;

            case R.drawable.logout2x: {

                logoutAlert();
            }
            break;

            case R.drawable.events2x: {

                replaceFragment(EventsFragment.newInstance(), true, false);

            }
            break;

            case R.drawable.linkicon2x: {

                replaceFragment(LinksFragment.newInstance(), true, false);

            }
            break;

            case R.drawable.margin2x: {

                replaceFragment(MarginDetailFragment.newInstance(loginResponse.getResponse().getClient()), true, false);

            }
            break;

            case R.drawable.marketicon2x: {

                replaceFragment(ExchangeFragment.newInstance(), true, false);

            }
            break;

            case R.drawable.quotes2x: {

                replaceFragment(QuotesFragment.newInstance(null), true, false);
            }
            break;

            case R.drawable.topsymbols2x: {

                replaceFragment(TopSymbolsFragment.newInstance(), true, false);

            }
            break;

            case R.drawable.portfoliosummary2x: {

                replaceFragment(PortfolioFragment.newInstance(), true, false);

            }
            break;

            case R.drawable.symbols2x: {

                replaceFragment(SymbolsFragment.newInstance(), true, false);
            }
            break;

            case R.drawable.profileicon2x: {

                replaceFragment(UserProfileFragment.newInstance(loginResponse.getResponse().getClient()
                        , loginResponse.getResponse().getExchange()), true, false);
            }
            break;

            case R.drawable.account2x: {

                replaceFragment(AccountFragment.newInstance(), true, false);
            }
            break;

            case R.drawable.setting2x: {

                replaceFragment(SettingFragment.newInstance(), true, false);
            }
            break;

            case R.drawable.paymentrequest2x: {

                replaceFragment(PaymentFragment.newInstance(), true, false);
            }
            break;

            case R.drawable.trade2x: {
                if (isTrnCodeAvailable("OM06") || isTrnCodeAvailable("OM012")) {
                    replaceFragment(TradeFragment.newInstance(null), true, false);
                }
            }
            break;

            case R.drawable.cashbook2x: {

                replaceFragment(CashBookFragment.newInstance(), true, false);
            }
            break;
            case R.drawable.netcustody2x: {

                replaceFragment(NetSharesFragment.newInstance(), true, false);
            }
            break;
            case R.drawable.research2x: {
                replaceFragment(ResearchPortalFragment.newInstance(null), true, false);

            }
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void logoutAlert() {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Logout");
        alert.setMessage("Do you want to logout and exit?");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                logoutRequest();


            }
        });

        alert.show();
    }


    public void replaceFragment(Fragment fragment, boolean popStack, boolean isChild) {

        String backStateName = fragment.getClass().getName();

        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (isChild) {
            ft.add(R.id.fragment_container, fragment, backStateName);
        } else {
            fragmentManager.popBackStackImmediate();
            ft.replace(R.id.fragment_container, fragment, backStateName);
        }



        ft.addToBackStack(backStateName);
        ft.commit();

    }

    private void logoutRequest() {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.LOGOUT_MESSAGE_REQUEST_IDENTIFIER);
        request_obj.addProperty("userId", loginResponse.getResponse().getUserId());

        if (ConnectionDetector.getInstance(context).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.LOGOUT_MESSAGE_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, MainActivity.this, true).executeOnExecutor(
//                    AsyncTask.THREAD_POOL_EXECUTOR,
//                    Constants.LOGOUT_MESSAGE_REQUEST_IDENTIFIER, request_obj.toString());

            deleteAll();
            startActivity(new Intent(context, LoginActivity.class));
            finish();

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private FragmentManager.OnBackStackChangedListener getListener() {
        return new FragmentManager.OnBackStackChangedListener() {
            @SuppressWarnings("ConstantConditions")
            public void onBackStackChanged() {

                Util.hideKeyboard(MainActivity.this);

                int backStackEntryCount = fragmentManager.getBackStackEntryCount();
                if (backStackEntryCount > 1) {

                    getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                } else {
                    //show hamburger
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    toggle.syncState();
                    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            drawer.openDrawer(GravityCompat.START);
                            Util.hideKeyboard(MainActivity.this);
                        }
                    });
                }


                if (backStackEntryCount > 0) {
                    try {
                        Fragment fragment = fragmentManager.getFragments()
                                .get(backStackEntryCount - 1);
                        fragment.onResume();

                        try {
                            final NetSharesFragment frag =
                                    (NetSharesFragment) fragmentManager
                                            .findFragmentByTag(NetSharesFragment.class.getName());

                            if (frag != null) {
                                //   frag.setAsOnDate(null);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }


    public void orderStatusRequest() {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.ORDER_STATUS_REQUEST_IDENTIFIER);
        request_obj.addProperty("exchange", loginResponse.getResponse().getExchange());

        if (ConnectionDetector.getInstance(context).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.ORDER_STATUS_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, MainActivity.this, true).executeOnExecutor(
//                    AsyncTask.THREAD_POOL_EXECUTOR,
//                    Constants.ORDER_STATUS_REQUEST_IDENTIFIER, request_obj.toString());


        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteAll() {

        try {
            MessageSocket.closeConnection();
            FeedSocket.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        preferences.removeLoginResult(R.string.key_login);
        preferences.removeMarketResult(R.string.key_symbols);
        preferences.removeSymbolResult(R.string.key_market);
        preferences.removeEvents(R.string.key_events);
        preferences.removeRememberPin(R.string.key_remember_pin);
        preferences.removeUsername(R.string.key_username);
        preferences.removePassword(R.string.key_password);

    }

    @Override
    public void onMarketFragmentListener(int which, MarketSymbol sym) {

        switch (optionItems.get(which)) {

            case "Summary": { //summary
                Log.d("Summary", new Gson().toJson(sym, MarketSymbol.class));
                QuotesFragment fragment = QuotesFragment.newInstance(new Gson().toJson(sym, MarketSymbol.class));
                replaceFragment(fragment, true, true);
            }
            break;

            case "Trade": { //trade
                goToTrade(sym);
            }
            break;

            case "Charts": { //charts
                goToCharts(sym);

            }
            break;
            case "Research":
                ResearchPortalFragment fragment = ResearchPortalFragment.newInstance(sym.getSymbol());
                replaceFragment(fragment, true, true);
               // callingResearchPortalService(Constants.RESEARCH_PORTAL_URL);
                break;
        }
    }

    public void callingResearchPortalService(String url) {

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", "rashid.irshad");
                jsonObject.put("client", Constants.RESESRCH_PORTAL_CLIENT);
                jsonObject.put("password", "rashid123");
                jsonObject.put("ip", Constants.RESESRCH_PORTAL_IP);

                RestClient.postRequest("research_portal",
                        this,
                        url,
                        jsonObject,
                        new OnRestClientCallback() {
                            @Override
                            public void onRestSuccess(JSONObject response, String action) {

                                try {
                                    if (response.getString("response").equals("success")) {

                                        String url = response.getString("link");
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
                                        startActivity(browserIntent);

                                        //  webView.loadUrl(url);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    HToast.showMsg(context, "Unable to connect to Trading Server please try later or check your network");
                                }
                            }

                            @Override
                            public void onRestError(Exception e, String action) {

                                Alert.showErrorAlert(MainActivity.this);
                            }
                        }, false, "Please wait..");
            } catch (JSONException e) {
                e.printStackTrace();
                Alert.showErrorAlert(context);
            }
        }
    }

    @Override
    public void onSymbolAddRequest(Symbol symbol) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.SUBSCRIPTION_REQUEST_IDENTIFIER);
        request_obj.addProperty("symbol", symbol.getSymbol());
        request_obj.addProperty("market", symbol.getMarket());
        request_obj.addProperty("exchange", symbol.getExchangeCode());
        request_obj.addProperty("requestType", "ADD");


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {


            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SUBSCRIPTION_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.SUBSCRIPTION_REQUEST_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSymbolDeleteRequest(MarketSymbol symbol) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.SUBSCRIPTION_REQUEST_IDENTIFIER);
        request_obj.addProperty("symbol", symbol.getSymbol());
        request_obj.addProperty("market", symbol.getMarket());
        request_obj.addProperty("exchange", symbol.getExchangeCode());
        request_obj.addProperty("requestType", "REM");


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {


            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SUBSCRIPTION_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.SUBSCRIPTION_REQUEST_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessageReceived(String action, String resp) {

        if (isActive) {

            Gson gson = new Gson();

            JsonParser jsonParser = new JsonParser();

            try {
                JsonObject jsonObject = jsonParser.parse(resp).getAsJsonObject();

                JsonObject response = jsonObject.get("response").getAsJsonObject();
                String MSGTYPE = response.get("MSGTYPE").getAsString();
                String error = jsonObject.get("error").getAsString();
                String code = jsonObject.get("code").getAsString();

                Log.d(TAG, "MSGTYPE: " + MSGTYPE);
                Log.d(TAG, "action: " + action);

                if (code.equals("200") && error.equals("")) {

                    switch (MSGTYPE) {

                        case Constants.LOGIN_MESSAGE_RESPONSE: {

                            preferences.setLoginResult(resp);
                            loginResponse = gson.fromJson(preferences.getLoginResult(), LoginResponse.class);
                            connectFeed();

                        }
                        break;
                        case Constants.SYMBOL_MESSAGE_RESPONSE: {

                            SymbolsResponse result = gson.fromJson(resp, SymbolsResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    preferences.setSymbolResult(gson.toJson(result));
                                    symbolsResponse = gson.fromJson(preferences.getSymbolResult(), SymbolsResponse.class);
                                    marketFragment.setSearchSymbols();

                                } else {

                                    Alert.show(MainActivity.this, "", result.getError());
                                }

                            }
                        }
                        break;
                        case Constants.ORDER_STATUS_REQUEST_RESPONSE: {

                            final OrderStatsResponse result = gson.fromJson(resp, OrderStatsResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            orderStatsFragment.setResult(result);
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: SymbolsResponse null ");
                            }

                        }
                        break;

                        case Constants.SUBSCRIPTION_REQUEST_RESPONSE: {

                            Log.d("test", "SUBSCRIPTION_REQUEST_RESPONSE: " + Constants.SUBSCRIPTION_REQUEST_RESPONSE);

                            String responseType = response.get("responseType").getAsString();
                            String requestType = response.get("requestType").getAsString();

                            if (responseType.equals("ACPT") && requestType.equals("REM")) { //delete request

                                try {
                                    marketFragment.removeMarket();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else if (responseType.equals("ACPT") && requestType.equals("ADD")) { //add request

                                MarketSymbol sym = new Gson().fromJson(response.getAsJsonObject("symbols"), MarketSymbol.class);

                                if (sym.getSymbol() != null) {
//                                switch (action) {
//                                    case Constants.ACTION_FROM_SYMBOLS: { //for symbols screen add request
//                                        Alert.show(context, "", sym.getSymbol() + " Successfully added.");
//                                        marketFragment.shouldReload = true;
//                                    }
//                                    break;
//                                    default: { //for market screen add request
                                    try {
                                        marketFragment.addMarketItem(sym);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Alert.show(context, "", sym.getSymbol() + " Successfully added.");
                                        marketFragment.shouldReload = true;
                                    }
//                                    }
//                                    break;
//                                }
                                } else {
                                    Alert.showErrorAlert(context);
                                    Alert.show(context, getString(R.string.app_name), "No symbol found.");

                                }
                            } else {
                                Alert.show(context, getString(R.string.app_name), "No symbol found.");
                            }
                        }
                        break;

                        case Constants.SUBSCRIPTION_LIST_REQUEST_RESPONSE: {

                            MarketResponse result = gson.fromJson(resp, MarketResponse.class);

                            if (result != null) {


                                if (result.getCode().equals("200")) {

                                    preferences.removeMarketResult(R.string.key_market);

                                    preferences.setMarketResult(gson.toJson(result));

                                    marketResponse = null;

                                    marketResponse = gson.fromJson(preferences.getMarketResult(), MarketResponse.class);

                                    if (marketResponse != null) {

                                        marketFragment.isReloaded = true;
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                marketFragment.onResume();
                                            }
                                        });
                                    }

                                } else {

                                    Alert.show(context, "", result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: MarketResponse null ");
                            }
                        }
                        break;

                        case Constants.OUOTES_SECURITIES_REQUEST_RESPONSE: {

                            JsonArray symbolsArr = response.getAsJsonArray("symbols");

                            if (symbolsArr.size() > 0) {

                                final MarketSymbol marketSymbol = gson.fromJson(symbolsArr.get(0), MarketSymbol.class);


//                            switch (action) {
//                                case Constants.ACTION_FROM_TRADE: {

                                final TradeFragment frag = (TradeFragment) fragmentManager.findFragmentByTag(TradeFragment.class.getName());

                                if (frag != null) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            frag.setValues(marketSymbol, true);
                                        }
                                    });
                                } else {
                                    Log.d("TradeFragment", "TradeFragment is null");
//                                    }
//                                }
//                                break;
//
//                                default: {

                                    final QuotesFragment frag2 = (QuotesFragment) fragmentManager.findFragmentByTag(QuotesFragment.class.getName());

                                    if (frag2 != null) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                frag2.setValues(marketSymbol);
                                            }
                                        });
                                    } else {
                                        Log.d("QuotesFragment", "QuotesFragment is null");
                                    }
//                                }
//                                break;

                                }
                            } else {

                                Alert.show(context, getString(R.string.app_name), "No symbol found.");
                            }
                        }
                        break;

                        case Constants.TOP_SYMBOLS_REQUEST_RESPONSE: {

                            final TopSymResponse result = gson.fromJson(resp, TopSymResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    final List<TopSymbol> topSymbolList = result.getResponse().getSymbols();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final TopSymbolsFragment frag =
                                                    (TopSymbolsFragment) fragmentManager
                                                            .findFragmentByTag(TopSymbolsFragment.class.getName());

                                            if (frag != null) {
                                                frag.setValues(topSymbolList, "1");

                                            } else {
                                                Log.d("QuotesFragment", "QuotesFragment is null");
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: TopSymResponse null ");
                            }
                        }
                        break;

                        case Constants.NET_CUSTODY_REQ_RESPONSE: {

                            final NetShareResponse result = gson.fromJson(resp, NetShareResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    final List<NetShareCustody> netShareCustodies = result.getResponse().getNetShareCustody();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final NetSharesFragment frag =
                                                    (NetSharesFragment) fragmentManager
                                                            .findFragmentByTag(NetSharesFragment.class.getName());

                                            if (frag != null) {
                                                frag.setValues(netShareCustodies);

                                            } else {
                                                Log.d("NetSharesFragment", "NetSharesFragment is null");
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: TopSymResponse null ");
                            }
                        }
                        break;

                        case Constants.PORTFOLIO_REQUEST_RESPONSE: {

                            final PortfolioResponse result = gson.fromJson(resp, PortfolioResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    final List<PortfolioSymbol> portfolioSymbols = result.getResponse().getSymbols();

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final PortfolioFragment frag =
                                                    (PortfolioFragment) fragmentManager
                                                            .findFragmentByTag(PortfolioFragment.class.getName());

                                            if (frag != null) {


                                                frag.setValues(portfolioSymbols);

                                            } else {
                                                Log.d("PortfolioResponse", "PortfolioResponse is null");
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: PortfolioResponse null ");
                            }
                        }
                        break;

                        case Constants.MARGIN_REQUEST_RESPONSE: {

                            final AccountResponse result = gson.fromJson(resp, AccountResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final AccountFragment frag =
                                                    (AccountFragment) fragmentManager
                                                            .findFragmentByTag(AccountFragment.class.getName());

                                            if (frag != null) {
                                                frag.setValues(result);

                                            } else {
                                                Log.d("AccountFragment", "AccountFragment is null");
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }

                            } else {
                                Log.d(TAG, "Response :: PortfolioResponse null ");
                            }
                        }
                        break;

                        case Constants.MARGIN_DETAIL_REQ_RESPONSE: {

                            final MarginResponse result = gson.fromJson(resp, MarginResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    final MarginDetailFragment frag = (MarginDetailFragment)
                                            fragmentManager.findFragmentByTag(MarginDetailFragment.class.getName());


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (frag != null) {
                                                frag.setResult(result);
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: SymbolsResponse null ");
                            }

                        }
                        break;

                        case Constants.PROFILE_REQ_RESPONSE: {

                            final ProfileResponse result = gson.fromJson(resp, ProfileResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    final UserProfileFragment frag = (UserProfileFragment)
                                            fragmentManager.findFragmentByTag(UserProfileFragment.class.getName());


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (frag != null) {
                                                frag.setResult(result);
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: SymbolsResponse null ");
                            }

                        }
                        break;

                        case Constants.EXC_REQ_RESPONSE: {

                            final ExchangeResponse result = gson.fromJson(resp, ExchangeResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    final ExchangeFragment frag = (ExchangeFragment)
                                            fragmentManager.findFragmentByTag(ExchangeFragment.class.getName());

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (frag != null) {
                                                frag.setResult(result);
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: SymbolsResponse null ");
                            }

                        }
                        break;

                        case Constants.LINKS_REQUEST_RESPONSE: {

                            final LinksResponse result = gson.fromJson(resp, LinksResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    final LinksFragment frag = (LinksFragment)
                                            fragmentManager.findFragmentByTag(LinksFragment.class.getName());


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (frag != null) {
                                                frag.setResult(result);
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }


                            } else {
                                Log.d(TAG, "Response :: SymbolsResponse null ");
                            }

                        }
                        break;

                        case Constants.CHANGE_PASSWORD_REQ_RESPONSE: {

                            Alert.show(context, getString(R.string.app_name), response.get("remarks").getAsString());

                            if (response.get("action").getAsString().equals("ACPT")) {

                                Event.add(context, new Event(System.currentTimeMillis(), response.get("remarks").getAsString()));

                            }

                        }
                        break;

                        case Constants.CHANGE_PIN_REQ_RESPONSE: {

                            Alert.show(context, getString(R.string.app_name), response.get("remarks").getAsString());

                            if (response.get("action").getAsString().equals("ACPT")) {
                                Event.add(context, new Event(System.currentTimeMillis(), response.get("remarks").getAsString()));
                            }
                        }
                        break;

                        case Constants.TRADE_CONFIRMATION_MESG: {

//                        Alert.show(context, "Trade Confirmation", response.get("remarks").getAsString());
                            if (loginResponse.getResponse().getUsertype() == 0 ||
                                    loginResponse.getResponse().getUsertype() == 3) {
                                if (loginResponse.getResponse().getUserId().equals(response.get("UserId").getAsString()))
                                {
                                    sendNotification("Trade Confirmation", response.get("remarks").getAsString());

                                }


                            }
                            else
                            {
                                sendNotification("Trade Confirmation", response.get("remarks").getAsString());

                            }


                            Event.add(context, new Event(System.currentTimeMillis(), response.get("remarks").getAsString()));

                        }
                        break;

                        case Constants.MSG_TYPE_TEXT: {

                            if (response.get("message").getAsString()
                                    .equals("You have been disconnected because you logged in somewhere else.")) {

                                deleteAll();
                                startActivity(new Intent(context, LoginActivity.class)
                                        .putExtra("discon", true)
                                        .putExtra("message", response.get("message").getAsString()));
                                finish();
                            } else {
                                Alert.show(context, getString(R.string.app_name), response.get("message").getAsString());
                            }


                        }
                        break;

                        case Constants.MARKET_STAT_RESPONSE: {

                            Alert.show(context, getString(R.string.app_name), response.get("message").getAsString());
                            Event.add(context, new Event(System.currentTimeMillis(), response.get("message").getAsString()));

                        }
                        break;

                        case Constants.LOGOUT_MESSAGE_RESPONSE: {

                            deleteAll();
                            startActivity(new Intent(context, LoginActivity.class)
                                    .putExtra("discon", true)
                                    .putExtra("message", response.get("remarks").getAsString()));
                            finish();
                        }
                        break;

                        case Constants.PAYMENT_REQ_RESPONSE: {

                            String responseAction = response.get("action").getAsString();

                            if (responseAction.equals("1") || responseAction.equals("2")) {

                                Alert.show(context, getString(R.string.app_name), response.get("remarks").getAsString());
                                Event.add(context, new Event(System.currentTimeMillis(), response.get("remarks").getAsString()));

                            }


                        }
                        break;

                        case "OCNF": {

                            JsonParser parser = new JsonParser();
                            JsonObject json = parser.parse(resp).getAsJsonObject();

                            if (json.get("code").getAsString().equals("200")) {
                                if (loginResponse.getResponse().getUsertype() == 0 ||
                                        loginResponse.getResponse().getUsertype() == 3) {
                                    if (loginResponse.getResponse().getUserId().equals(response.get("UserId").getAsString()))
                                    {
                                        Alert.show(MainActivity.this, "Order Confirmation", response.get("orderRemarks").getAsString());

                                    }


                                }
                                else
                                {
                                    Alert.show(MainActivity.this, "Order Confirmation", response.get("orderRemarks").getAsString());

                                }

                                Event.add(context, new Event(System.currentTimeMillis(), response.get("orderRemarks").getAsString()));

                                if (response.get("confType").getAsString().equals("QUEUE") && response.get("MSGTYPE").getAsString().equals("OCNF")) {

                                    final OrderStatsFragment frag =
                                            (OrderStatsFragment) fragmentManager
                                                    .findFragmentByTag(OrderStatsFragment.class.getName());

                                    if (frag != null) {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                frag.removeItem();
                                            }
                                        });

                                    } else {
                                        Log.d("OrderStatsFragment", "OrderStatsFragment is null");
                                    }

                                }
                            } else {

                                if (json.get("error").getAsString().equals("")) {
                                    Alert.showErrorAlert(context);
                                } else {
                                    Alert.show(context, getString(R.string.app_name), json.get("error").getAsString());
                                }
                            }

                            if (!json.get("error").getAsString().equals("")) {

                                Event.add(context, new Event(System.currentTimeMillis(), json.get("error").getAsString()));

                            }
                        }
                        break;

                        case Constants.PAYMENT_REQ_RESPONSE1: {

                            JsonParser parser = new JsonParser();
                            final JsonObject json = parser.parse(resp).getAsJsonObject();

                            if (json.get("code").getAsString().equals("200")) {

                                final PaymentFragment frag =
                                        (PaymentFragment) fragmentManager
                                                .findFragmentByTag(PaymentFragment.class.getName());

                                if (frag != null) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            frag.setResult(json);
                                        }
                                    });

                                } else {
                                    Log.d("OrderStatsFragment", "OrderStatsFragment is null");
                                }

                            } else {

                                if (json.get("error").getAsString().equals("")) {
                                    Alert.showErrorAlert(context);
                                } else {
                                    Alert.show(context, getString(R.string.app_name), json.get("error").getAsString());
                                }
                            }
                        }
                        break;

                        case Constants.CASHBOOK_REQ_RESPONSE: {

                            final CashBookResponse result = gson.fromJson(resp, CashBookResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final CashBookFragment frag =
                                                    (CashBookFragment) fragmentManager
                                                            .findFragmentByTag(CashBookFragment.class.getName());

                                            if (frag != null) {
                                                frag.setValues(result);

                                            } else {
                                                Log.d("CashBookFragment", "CashBookFragment is null");
                                            }
                                        }
                                    });

                                } else {

                                    Alert.show(context, getString(R.string.app_name), result.getError());
                                }

                            } else {
                                Log.d(TAG, "Response :: PortfolioResponse null ");
                            }
                        }
                        break;


                    }


                } else {
                    Alert.show(context, getString(R.string.app_name), error);
                    try {
                        if (MSGTYPE.equals(Constants.ORDER_STATUS_REQUEST_RESPONSE)) {
                            final OrderStatsResponse result = gson.fromJson(resp, OrderStatsResponse.class);

                            if (result != null) {

                                if (result.getCode().equals("200")) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                orderStatsFragment.setResult(result);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
             //   Alert.showErrorAlert(context);


            }

//        switch (action) {
//
//
//            case Constants.ACTION_ORDER_FROM_TRADE: {
//
//                JsonParser parser = new JsonParser();
//                JsonObject json = parser.parse(resp).getAsJsonObject();
//
//                JsonObject response = json.getAsJsonObject("response");
//
//                if (json.get("code").getAsString().equals("200")) {
//
//                    final TradeFragment frag = (TradeFragment) fragmentManager.findFragmentByTag(TradeFragment.class.getName());
//
//
//                    if (response.get("orderAction").getAsString().equals("ACPT")) {
//                        if (frag != null) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//
//
//                                    if (frag.sendMessage) {
//                                        frag.proceedToOrder();
//                                        frag.sendMessage = false;
//                                    }
//                                }
//                            });
//                        } else {
//                            Log.d("TradeFragment", "TradeFragment is null");
//                        }
//                    }
//
//                    Alert.show(context, getString(R.string.app_name), response.get("orderRemarks").getAsString());
//
//                    Event.add(context, new Event(System.currentTimeMillis(), response.get("orderRemarks").getAsString()));
//
//                } else {
//
//                    if (json.get("error").getAsString().equals("")) {
//                        Alert.showErrorAlert(context);
//                    } else {
//                        Alert.show(context, getString(R.string.app_name), json.get("error").getAsString());
//                    }
//                }
//
//
//                if (!json.get("error").getAsString().equals("")) {
//
//                    Event.add(context, new Event(System.currentTimeMillis(), json.get("error").getAsString()));
//
//                }
//            }
//            break;
//
//
//
//        }
        }
    }

    @Override
    public void onOrderDeleteRequest(OrdersList order) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.ORDER_REQUEST_IDENTIFIER);
        request_obj.addProperty("orderPrice", order.getPrice() + "");
        request_obj.addProperty("orderMarket", order.getMarket());
        request_obj.addProperty("orderExchange", loginResponse.getResponse().getExchange());
        request_obj.addProperty("orderVolume", order.getVolume() + "");
        request_obj.addProperty("client",order.getClient());

        if (order.getSide().equals("B") || order.getSide().equals("Buy")) {
            request_obj.addProperty("orderSide", "B");
        } else {
            request_obj.addProperty("orderSide", "S");
        }


        request_obj.addProperty("orderAction", "CNL");

        int remVolume = order.getVolume() - order.getExecVolume();

        request_obj.addProperty("orderRemVolume", remVolume + "");
        request_obj.addProperty("orderSymbol", order.getSymbol());
        request_obj.addProperty("orderType", order.getOrderType());
        request_obj.addProperty("orderNumber", order.getOrderNo());
        request_obj.addProperty("triggerPrice", order.getTriggerPrice());
        request_obj.addProperty("discVol", order.getDiscVol());
        request_obj.addProperty("orderProp", order.getOrderProp());


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.ORDER_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);
//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
//                    Constants.ORDER_REQUEST_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onOnQoutesFragmentListener(Symbol symbol) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.OUOTES_SECURITIES_REQUEST_IDENTIFIER);
        request_obj.addProperty("symbol", symbol.getSymbol());
        request_obj.addProperty("market", symbol.getMarket());
        request_obj.addProperty("exchange", symbol.getExchangeCode());


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.OUOTES_SECURITIES_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
//                    Constants.OUOTES_SECURITIES_REQUEST_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void goToTrade(MarketSymbol marketSymbol) {
        if (isTrnCodeAvailable("OM06") || isTrnCodeAvailable("OM012")) {
            replaceFragment(TradeFragment.newInstance(new Gson().toJson(marketSymbol, MarketSymbol.class)), false, true);
        }
    }

    public void goToNetShareDetail(NetShareCustody custody) {

        replaceFragment(NetShareDetailFragment.newInstance(new Gson().toJson(custody, NetShareCustody.class)), false, true);
    }

    public void goToCharts(MarketSymbol marketSymbol) {

        Bundle bundle = new Bundle();
        bundle.putString("sym", new Gson().toJson(marketSymbol, MarketSymbol.class));
        bundle.putString("exchange", MainActivity.loginResponse.getResponse().getExchange());

        startActivity(new Intent(context, ChartsActivity.class).putExtras(bundle));

    }

    public void topSymbolRequest() {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.TOP_SYMBOLS_REQUEST_IDENTIFIER);
        request_obj.addProperty("exchange", loginResponse.getResponse().getExchange());


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.TOP_SYMBOLS_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
//                    Constants.TOP_SYMBOLS_REQUEST_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void netSharesRequest(String date,String Client) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.NET_CUSTODY_REQ_IDENTIFIER);
        request_obj.addProperty("client", Client);
        request_obj.addProperty("date", date);

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.NET_CUSTODY_REQ_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void portfolioRequestRequest(String clientcode) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.PORTFOLIO_REQUEST_IDENTIFIER);
        request_obj.addProperty("exchange", loginResponse.getResponse().getExchange());
        request_obj.addProperty("client",clientcode);



        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.PORTFOLIO_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
//                    Constants.PORTFOLIO_REQUEST_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showPortFolioDetail(PortfolioSymbol portfolioSymbol) {

        PortfolioDetail fragment = PortfolioDetail.newInstance(new Gson().toJson(portfolioSymbol, PortfolioSymbol.class));

        replaceFragment(fragment, false, true);
    }

    public void addSymbolRequest(Symbol symbol) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.SUBSCRIPTION_REQUEST_IDENTIFIER);
        request_obj.addProperty("symbol", symbol.getSymbol());
        request_obj.addProperty("market", symbol.getMarket());
        request_obj.addProperty("exchange", symbol.getExchangeCode());
        request_obj.addProperty("requestType", "ADD");


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {


            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SUBSCRIPTION_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.ACTION_FROM_SYMBOLS, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void accountRequest(String clientcode) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.MARGIN_REQUEST_IDENTIFIER);
        request_obj.addProperty("client", clientcode);

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.MARGIN_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void linksRequest() {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.LINKS_REQUEST_IDENTIFIER);

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.LINKS_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.LINKS_REQUEST_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void marginRequest(String clientcode) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.MARGIN_DETAIL_REQ_IDENTIFIER);
        request_obj.addProperty("client", clientcode);

        Log.d("marginRequest", "clientCode: " + loginResponse.getResponse().getClient());

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.MARGIN_DETAIL_REQ_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.MARGIN_DETAIL_REQ_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void profileRequest(String clientcode) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.PROFILE_REQ_IDENTIFIER);
        request_obj.addProperty("client", clientcode);
        request_obj.addProperty("exchangeCode", loginResponse.getResponse().getExchange());

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {


            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.PROFILE_REQ_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);
//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.PROFILE_REQ_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void exchangesRequest() {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.EXC_REQ_IDENTIFIER);
        request_obj.addProperty("exchangeCode", loginResponse.getResponse().getExchange());

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.EXC_REQ_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);


//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.EXC_REQ_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changePasswordRequest(String oldPassword, String newPassword) {

        JsonObject request_obj = new JsonObject();
        EnctyptionUtils enctyptionUtils=new EnctyptionUtils();
        try {
            useridEncoded=enctyptionUtils.encrypt(loginResponse.getResponse().getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        request_obj.addProperty("MSGTYPE", Constants.CHANGE_PASSWORD_REQ_IDENTIFIER);
        request_obj.addProperty("userName", useridEncoded);
        request_obj.addProperty("oldPassword", oldPassword);
        request_obj.addProperty("newPassword", newPassword);


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.CHANGE_PASSWORD_REQ_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.CHANGE_PASSWORD_REQ_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changePinRequest(String oldPin, String newPin) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.CHANGE_PIN_REQ_IDENTIFIER);
        request_obj.addProperty("userName", loginResponse.getResponse().getUserId());
        request_obj.addProperty("oldPin", oldPin);
        request_obj.addProperty("newPin", newPin);


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.CHANGE_PIN_REQ_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.CHANGE_PIN_REQ_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void paymentRequest(int amountVal, String selectedVal,String clientcode) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.PAYMENT_REQ_IDENTIFIER);
        request_obj.addProperty("client", clientcode);
        request_obj.addProperty("amount", amountVal + "");
        request_obj.addProperty("operation", selectedVal);


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.PAYMENT_REQ_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Constants.PAYMENT_REQ_IDENTIFIER, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tradeSymbolRequest(Symbol symbol) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.OUOTES_SECURITIES_REQUEST_IDENTIFIER);
        request_obj.addProperty("symbol", symbol.getSymbol());
        request_obj.addProperty("market", symbol.getMarket());
        request_obj.addProperty("exchange", symbol.getExchangeCode());



        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.OUOTES_SECURITIES_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);


//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
//                    Constants.ACTION_FROM_TRADE, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void tradeOrderRequest(JsonObject request_obj) {

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.ACTION_ORDER_FROM_TRADE);
            map.put(2, request_obj.toString());

            write(map, true);


//            new MessageServer(context, this, true).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
//                    Constants.ACTION_ORDER_FROM_TRADE, request_obj.toString());
        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getMarket() {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
        request_obj.addProperty("userId", loginResponse.getResponse().getUserId());
        request_obj.addProperty("client", loginResponse.getResponse().getClient());


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

//            new MessageServer(context, this, true).executeOnExecutor(
//                    AsyncTask.THREAD_POOL_EXECUTOR, Constants.SUBSCRIPTION_LIST_REQUEST_IDENTIFIER,
//                    request_obj.toString());

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getPaymentRequest(String client) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.PAYMENT_REQ_IDENTIFIER1);
        request_obj.addProperty("exchangeCode", loginResponse.getResponse().getExchange());
        request_obj.addProperty("client",client);
        Log.d("paymentresponse",request_obj.toString());

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.PAYMENT_REQ_IDENTIFIER1);
            map.put(2, request_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showFeedDisconnectAlert() {

        // Alert.show(context, getString(R.string.app_name), "Unable to connect to Server. Please check your network or try later.");

    }

    public boolean isTrnCodeAvailable(String code) {

        String TrnCodes = loginResponse.getResponse().getTrnCodes();

        Log.d("isTrnCodeAvailable", "TrnCodes: " + TrnCodes);

        return TrnCodes.contains(code);
    }

    private void sendNotification(String title, String message) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setPriority(Notification.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(title)
                .setDefaults(Notification.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message));

        Log.d("sendNotification", "message: " + message);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);

        notificationManager.notify(notificationID.incrementAndGet(),
                notificationBuilder.build());
    }

    public void cashBookRequest(String clientcode) {

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.CASHBOOK_REQ_IDENTIFIER);
        request_obj.addProperty("client", clientcode);


        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {

            Map<Integer, String> map = new HashMap<>();
            map.put(1, Constants.CASHBOOK_REQ_IDENTIFIER);
            map.put(2, request_obj.toString());

            write(map, true);

        } else {

            try {
                HSnackBar.showMsg(findViewById(android.R.id.content), "No Internet Connection.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}