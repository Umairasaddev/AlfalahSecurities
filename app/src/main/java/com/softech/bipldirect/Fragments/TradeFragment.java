package com.softech.bipldirect.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.multidex.BuildConfig;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.softech.bipldirect.Adapters.SearchClientListAdapter;
import com.softech.bipldirect.Adapters.SearchListAdapter;
import com.softech.bipldirect.Const.Constants;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.LoginModel.OrdProp;
import com.softech.bipldirect.Models.LoginModel.OrdType;
import com.softech.bipldirect.Models.MarketModel.MarketSymbol;
import com.softech.bipldirect.Models.SymbolsModel.Symbol;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.Util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TradeFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    public boolean sendMessage = true;

    private EditText clientcode;
    private EditText symbol;
    private EditText volume;
    private EditText price;
    private TextView limit_tv;
    private TextView buyvol;
    private TextView sellvol;
    private ListView listSearch;
    private ListView listSearch1;

    private LinearLayout listSearch_view;
    private LinearLayout listSearch_view1;
    List<Symbol> searchKeywordsList;
    MarketSymbol marketSymbol = null;
    int buyFlag, sellFlag, shortFlag = 0;
    int tab1Selected = 1;
    String orderSide = "B";
    String orderType = "4";
    private RadioGroup radioGroup;
    private RadioButton radiobuy;
    private RadioButton radiosell;
    private TextView textViewOrderType;
    private TextView textViewOrderProp;
    private TextView textViewOrderValue;
    private EditText etTriggerPrice;
    private EditText etDiscVolume;
    private EditText etOrderReference;
    private SearchListAdapter searchAdapter;
    private SearchClientListAdapter searchClientListAdapter;
    private MarketSymbol values;
    private boolean textChangeByUser = true;
    private List<OrdType> ordTypes;
    private List<OrdProp> ordProps;
    ArrayList<String> clientlist;
    String client;
    boolean isSetInitialText = false;

    private String action = "BUY";
    private final String[] months = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG",
            "SEP", "OCT", "NOV", "DEC"};
    private View mCancelSearch;
    private View mCancelSearch1;
    private View mTradebutton;
    private View mTextViewOrderType;
    private View mTextViewOrderProp;

    public TradeFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String param1) {
        TradeFragment fragment = new TradeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            String mParam1 = getArguments().getString(ARG_PARAM1);
            marketSymbol = new Gson().fromJson(mParam1, MarketSymbol.class);
        }
//        clientlist=new ArrayList<String>(Arrays.asList(MainActivity.loginResponse.getResponse().getClientlist().split("\\s*,\\s*")));

        searchKeywordsList = new ArrayList<>(MainActivity.symbolsResponse.getResponse().getSymbols());
        clientlist = new ArrayList<String>(MainActivity.loginResponse.getResponse().getClientlist());
        searchAdapter = new SearchListAdapter(getActivity(), searchKeywordsList);
        searchClientListAdapter = new SearchClientListAdapter(getActivity(), clientlist);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trade, container, false);
        bindView(view);
        if (MainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MainActivity.loginResponse.getResponse().getClient());
            client = clientcode.getText().toString();
            clientcode.setEnabled(false);
        } else if (MainActivity.loginResponse.getResponse().getUsertype() == 0 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 3) {


        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listSearch.setAdapter(searchAdapter);
        listSearch1.setAdapter(searchClientListAdapter);
        clientcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isSetInitialText) {
                    isSetInitialText = false;
                    listSearch_view1.setVisibility(View.GONE);
                } else {
                    if (s.length() > 0) {

                        listSearch_view1.setVisibility(View.VISIBLE);

                        String text = clientcode.getText().toString();
                        searchClientListAdapter.filter(text);
                    } else {
                        listSearch_view1.setVisibility(View.GONE);
                    }
                }


            }
        });

        symbol.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() >= 2) {

                    listSearch_view.setVisibility(View.VISIBLE);

                    String text = symbol.getText().toString();
                    searchAdapter.filter(text);
                } else {
                    listSearch_view.setVisibility(View.GONE);
                }
            }
        });

        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listSearch_view.setVisibility(View.GONE);
                Symbol symbol = searchKeywordsList.get(position);
                ((MainActivity) requireActivity()).tradeSymbolRequest(symbol);

            }
        });
        listSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listSearch_view1.setVisibility(View.GONE);
                client = clientlist.get(position);
                isSetInitialText = true;
                clientcode.setText(clientlist.get(position));


            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if (i == R.id.radioButtonBuy) {
                    tab1Selected = 1;
                    action = "BUY";
                    orderSide = "B";
                    radiobuy.setBackgroundResource(R.drawable.selector_radio_button);
                    radiosell.setBackgroundResource(R.drawable.selector_radio_button);

                    if (textViewOrderType.getText().toString().equals("SHORT SELL") || textViewOrderType.getText().toString().equals("LS")) {
                        textViewOrderType.setText(ordTypes.get(0).getName());
                        textViewOrderType.setTag(ordTypes.get(0).getCode());
                        etTriggerPrice.setText("");
                        etTriggerPrice.setEnabled(false);
                        price.setEnabled(true);

                        if (marketSymbol != null) {

                            if (tab1Selected == 1) {//Buy
                                price.setText(marketSymbol.getSellPrice());
                            } else {
                                price.setText(marketSymbol.getBuyPrice());
                            }
                        }
                        orderType = "4";
                    }

                } else {
                    radiosell.setBackgroundColor(getResources().getColor(R.color.blinkRed));

                    action = "SELL";
                    if (textViewOrderType.getText().toString().equals("MFB") || textViewOrderType.getText().toString().equals("LB")) {
                        textViewOrderType.setText(ordTypes.get(0).getName());
                        textViewOrderType.setTag(ordTypes.get(0).getCode());
                        etTriggerPrice.setText("");
                        etTriggerPrice.setEnabled(false);
                        price.setEnabled(true);
                        orderType = "4";

                    }

                    tab1Selected = 2;
                    orderSide = "S";
                }

                if (marketSymbol != null) {

                    if (tab1Selected == 1) {//Buy

                        price.setText(marketSymbol.getSellPrice());
                    } else {
                        price.setText(marketSymbol.getBuyPrice());
                    }
                }

                Log.d("onCheckedChanged", "tab1Selected= " + tab1Selected + " , orderSide= " + orderSide);
            }
        });

        String TrnCodes = MainActivity.loginResponse.getResponse().getTrnCodes();
        ordTypes = MainActivity.loginResponse.getResponse().getOrdTypes();


        try {
            for (int i = 0; i < ordTypes.size(); i++) {
                if (!(TrnCodes.contains("OM25")) && ordTypes.get(i).getName().equals("LEVERAGE BUY")) {
                    ordTypes.remove(i);
                }
            }
            for (int i = 0; i < ordTypes.size(); i++) {
                if (!(TrnCodes.contains("OM26")) && ordTypes.get(i).getName().equals("MFB")) {
                    ordTypes.remove(i);
                }
            }
            for (int i = 0; i < ordTypes.size(); i++) {
                if (!(TrnCodes.contains("OM27")) && ordTypes.get(i).getName().equals("LEVERAGE SELL")) {
                    ordTypes.remove(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ordProps = MainActivity.loginResponse.getResponse().getOrdProps();

        if (ordTypes != null && ordTypes.size() > 0) {
            textViewOrderType.setText(ordTypes.get(0).getName());
            textViewOrderType.setTag(ordTypes.get(0).getCode());
        }

        if (ordProps != null && ordProps.size() > 0) {
            textViewOrderProp.setText(ordProps.get(0).getName());
            textViewOrderProp.setTag(ordProps.get(0).getCode());
        }

        if (TrnCodes.contains("OM06")) {
            buyFlag = 1;
        }
        if (TrnCodes.contains("OM12")) {
            sellFlag = 1;
        }

        if (marketSymbol != null && TrnCodes.contains("OM12") && marketSymbol.getMarket().equals("FUT")) {
            shortFlag = 1;
//            Log.d("getExchangeCode", marketSymbol.getMarket());
        }


        if (marketSymbol != null) {
            setValues(marketSymbol, false);
        }

        volume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                updateOrderValue();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                updateOrderValue();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Trade");
        }

        listSearch_view.setVisibility(View.GONE);

        super.onResume();
    }

    private void cancelSearch(View view) {
        if (view.getId() == R.id.cancel_search) {
            listSearch_view.setVisibility(View.GONE);
        } else {
            listSearch_view1.setVisibility(View.GONE);
        }
    }

    private void showPickerOrderType(final View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setAdapter(new OrdTypeAdapter(getActivity(), android.R.layout.select_dialog_item, ordTypes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ((TextView) view).setText(ordTypes.get(i).getName());
                view.setTag(ordTypes.get(i).getCode());
                try {
                    checkOrderTypeLogicAndShit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("showPickerOrderType", "onCancel");
                onPickerOrderTypeCancel();

            }
        });

        builder.create().show();

    }

    private void onPickerOrderTypeCancel() {

        textViewOrderType.setText(ordTypes.get(0).getName());
        textViewOrderType.setTag(ordTypes.get(0).getCode());

        orderSide = "B";
        orderType = "4";

        ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

        etTriggerPrice.setText("");
        etTriggerPrice.setEnabled(false);
    }

    private void showPickerOrderProp(final View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setAdapter(new OrdTypeAdapter(getActivity(), android.R.layout.select_dialog_item, ordProps), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ((TextView) view).setText(ordProps.get(i).getName());
                view.setTag(ordProps.get(i).getCode());
                checkOrderPropLogicAndShit();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("showPickerOrderProp", "onCancel");
                onPickerOrderPropCancel();
            }
        });

        builder.create().show();

    }

    private void onPickerOrderPropCancel() {

        textViewOrderProp.setText(ordProps.get(0).getName());
        textViewOrderProp.setTag(ordProps.get(0).getCode());

    }

    public void setValues(MarketSymbol sym, boolean isLoaded) {
        this.marketSymbol = sym;

//        Log.d("setValues", "marketSymbol: " + new Gson().toJson(sym, MarketSymbol.class));

        textChangeByUser = false;

        symbol.setText(sym.getSymbol()/* + "-" + sym.getMarket() + "-" + sym.getExchangeCode()*/);

        limit_tv.setText(sym.getLowerLimit() + "-" + sym.getUpperLimit());
        buyvol.setText(sym.getBuyPrice() + "(" + sym.getBuyVolume() + ")");
        sellvol.setText(sym.getSellPrice() + "(" + sym.getSellVolume() + ")");

//        if (isLoaded){
//
//            //noinspection ConstantConditions
//            resetButtonView(ButterKnife.findById(getView(),R.id.buybutton), tab1Views);
//
//            tab1Selected = 1;
//        }


        setPriceValue(sym);

        listSearch_view.setVisibility(View.GONE);
        Util.hideKeyboard(getActivity());

        textViewOrderType.setText(ordTypes.get(0).getName());
        textViewOrderType.setTag(ordTypes.get(0).getCode());
        etTriggerPrice.setText("");
        etTriggerPrice.setEnabled(false);
        price.setEnabled(true);

        if (marketSymbol != null) {

            if (tab1Selected == 1) {//Buy

                price.setText(marketSymbol.getSellPrice());
            } else {
                price.setText(marketSymbol.getBuyPrice());
            }
        }
        orderType = "4";


    }

    private void setPriceValue(MarketSymbol sym) {

        if (sym != null) {

            switch (tab1Selected) {

                case 1: {
                    price.setText(sym.getSellPrice());
                }
                break;
                case 2: {
                    price.setText(sym.getBuyPrice());
                }
                break;
                case 3: {
                    price.setText("0.00");
                }
                break;
            }

        }
    }

    private void proceedTradePopup() {

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_input_pin, null);
        final EditText editText = (EditText) dialogView.findViewById(R.id.editText2);
        final CheckBox checkBox = (CheckBox) dialogView.findViewById(R.id.checkBox);
        final TextView actionField = dialogView.findViewById(R.id.action_field);
        final TextView symbolField = dialogView.findViewById(R.id.symbol_field);
        final TextView volumeField = dialogView.findViewById(R.id.volume_field);
        final TextView priceField = dialogView.findViewById(R.id.price_field);
        final TextView valueField = dialogView.findViewById(R.id.value_field);

        if(radiobuy.isSelected()) actionField.setText("BUY");
        else actionField.setText("SELL");
        actionField.setText(action);

        symbolField.setText(symbol.getText().toString());
        volumeField.setText(volume.getText().toString());
        priceField.setText(price.getText().toString());
        valueField.setText(textViewOrderValue.getText().toString());

        if (MainActivity.preferences.getRememberPin()) {

            editText.setText(MainActivity.loginResponse.getResponse().getPinCode());
            checkBox.setChecked(true);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("TRADE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Util.hideKeyboard(getActivity());

                        if (checkBox.isChecked()) {
                            MainActivity.preferences.setRememberPin(true);
                        } else {
                            MainActivity.preferences.setRememberPin(false);
                        }

                        if (editText.getText().toString().equals(MainActivity.loginResponse.getResponse().getPinCode())) {

                            proceedToTrade();


                        } else {
                            HToast.showMsg(getActivity(), "Invalid Pin Code.");
                        }


                        dialog.dismiss();


                    }
                }

        );
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }

        );
        builder.setTitle(getString(R.string.app_name));
//        builder.setMessage("Please provide your pin code.");
        builder.setView(dialogView);
        try {
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void proceedToOrder() {

        Util.hideKeyboard(getActivity());

        JsonObject request_obj = new JsonObject();

        request_obj.addProperty("MSGTYPE", Constants.ORDER_REQUEST_IDENTIFIER);

        if (orderType.equals("3")) {//market
            request_obj.addProperty("orderPrice", "");

        } else if (orderType.equals("4")) {//limit
            request_obj.addProperty("orderPrice", price.getText().toString());
        }

        request_obj.addProperty("orderMarket", marketSymbol.getMarket());
        request_obj.addProperty("orderExchange", marketSymbol.getExchangeCode());
        request_obj.addProperty("orderVolume", volume.getText().toString());
        request_obj.addProperty("orderSide", orderSide);
        request_obj.addProperty("orderAction", "NEW");
        request_obj.addProperty("orderRemVolume", "0");
        request_obj.addProperty("orderSymbol", marketSymbol.getSymbol());
        request_obj.addProperty("orderNumber", "0");
        request_obj.addProperty("client", clientcode.getText().toString());


        if (tab1Selected == 3 && orderSide.equals("S")) {//short sell
            request_obj.addProperty("orderType", "8");
        } else {
            request_obj.addProperty("orderType", orderType);
        }


        ((MainActivity) getActivity()).tradeOrderRequest(request_obj);

        volume.setText("");
        price.setText("");

    }

    private boolean checkOrderTypeLogicAndShit() {

        if (marketSymbol.getMarket().equals("FUT") && tab1Selected == 1 && textViewOrderType.getText().equals("SHORT SELL")) {

            Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.shortSellNotAllowed));

            textViewOrderType.setText(ordTypes.get(0).getName());
            textViewOrderType.setTag(ordTypes.get(0).getCode());

            orderSide = "B";
            orderType = "4";

            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

            etTriggerPrice.setText("");
            etTriggerPrice.setEnabled(false);

            return false;
        }

        if (textViewOrderType.getText().equals("LIMIT")) {

            etTriggerPrice.setText("");
            etTriggerPrice.setEnabled(false);

            price.setEnabled(true);
            orderType = "4";

            if (tab1Selected == 1) {//Buy

                price.setText(marketSymbol.getSellPrice());
            } else {
                price.setText(marketSymbol.getBuyPrice());
            }

            return false;
        }

        if (textViewOrderType.getText().equals("STOP LOSS") || textViewOrderType.getText().equals("MIT")) {

            price.setEnabled(true);
            etTriggerPrice.setEnabled(true);

            if (marketSymbol != null) {

                if (tab1Selected == 1) {//Buy

                    price.setText(marketSymbol.getSellPrice());
                } else {
                    price.setText(marketSymbol.getBuyPrice());
                }
            }

            return false;
        }
        if (textViewOrderType.getText().equals("LS") && tab1Selected == 1) {
            Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.LSnotAllowedForBuy));
            textViewOrderType.setText(ordTypes.get(0).getName());
            textViewOrderType.setTag(ordTypes.get(0).getCode());

            orderSide = "B";
            orderType = "4";

            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

            etTriggerPrice.setText("");
            etTriggerPrice.setEnabled(false);

            return false;

        }
        if (textViewOrderType.getText().equals("MFB") && tab1Selected == 2) {
            Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.MFBNotAllowedForSell));

            textViewOrderType.setText(ordTypes.get(0).getName());
            textViewOrderType.setTag(ordTypes.get(0).getCode());

            orderSide = "B";
            orderType = "4";

            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

            etTriggerPrice.setText("");
            etTriggerPrice.setEnabled(false);

            return false;

        }
        if (textViewOrderType.getText().equals("LB") && tab1Selected == 2) {
            Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.LBNotAllowedForSell));

            textViewOrderType.setText(ordTypes.get(0).getName());
            textViewOrderType.setTag(ordTypes.get(0).getCode());

            orderSide = "B";
            orderType = "4";

            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);

            etTriggerPrice.setText("");
            etTriggerPrice.setEnabled(false);

            return false;

        }
        if (textViewOrderType.getText().equals("MARKET")) {

            etTriggerPrice.setText("");
            etTriggerPrice.setEnabled(false);
            price.setText("");
            price.setEnabled(false);

            return false;
        }

        if (textViewOrderType.getText().equals("SHORT SELL")) {

            Log.d("flavor", BuildConfig.FLAVOR);

            if (marketSymbol != null && tab1Selected == 2 && BuildConfig.FLAVOR.equals("bipl")) {

               /* String monthInSymbol = marketSymbol.getSymbol().substring(
                        marketSymbol.getSymbol().length() - 3, marketSymbol.getSymbol().length());

                for (String month : months) {

                    if (month.equals(monthInSymbol)) {
                        return false;
                    }
                }*/
               if (!marketSymbol.getSymbol().contains("-")){
                   Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.shortSellOnlyAllowed));

                   textViewOrderType.setText(ordTypes.get(0).getName());
                   textViewOrderType.setTag(ordTypes.get(0).getCode());
                   etTriggerPrice.setText("");
                   etTriggerPrice.setEnabled(false);
                   price.setEnabled(true);

                   if (marketSymbol != null) {

                       if (tab1Selected == 1) {//Buy

                           price.setText(marketSymbol.getSellPrice());
                       } else {
                           price.setText(marketSymbol.getBuyPrice());
                       }
                   }
                   orderType = "4";
               }



            } else if (marketSymbol != null && (!(marketSymbol.getMarket().equals("FUT")))) {
                Log.i("testingfut", "Market Symbol: " + marketSymbol.getMarket());
                Log.i("testingfut", "Market Symbol: " + marketSymbol.getSymbol());

                Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.shortSellOnlyAllowed));

                textViewOrderType.setText(ordTypes.get(0).getName());
                textViewOrderType.setTag(ordTypes.get(0).getCode());
                etTriggerPrice.setText("");
                etTriggerPrice.setEnabled(false);
                price.setEnabled(true);

                if (marketSymbol != null) {

                    if (tab1Selected == 1) {//Buy

                        price.setText(marketSymbol.getSellPrice());
                    } else {
                        price.setText(marketSymbol.getBuyPrice());
                    }
                }
                orderType = "4";

            } else {
                orderType = (String) textViewOrderType.getTag();
                etTriggerPrice.setEnabled(true);
                price.setEnabled(true);

                if (marketSymbol != null) {

                    if (tab1Selected == 1) {//Buy

                        price.setText(marketSymbol.getSellPrice());
                    } else {
                        price.setText(marketSymbol.getBuyPrice());
                    }
                }
            }

            return false;
        }

        if (textViewOrderType.getText().equals("LB")) {

            if (marketSymbol != null && (!(marketSymbol.getMarket().equals("REG"))) || tab1Selected == 2) {

                Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.leverageBuyOnlyAllowed));

                textViewOrderType.setText(ordTypes.get(0).getName());
                textViewOrderType.setTag(ordTypes.get(0).getCode());
                etTriggerPrice.setText("");
                etTriggerPrice.setEnabled(false);
                price.setEnabled(true);

                if (marketSymbol != null) {

                    if (tab1Selected == 1) {//Buy

                        price.setText(marketSymbol.getSellPrice());
                    } else {
                        price.setText(marketSymbol.getBuyPrice());
                    }
                }
                orderType = "4";

            } else {

                etTriggerPrice.setEnabled(true);
                etTriggerPrice.setText("");
                price.setEnabled(true);

                if (marketSymbol != null) {

                    if (tab1Selected == 1) {//Buy

                        price.setText(marketSymbol.getSellPrice());
                    } else {
                        price.setText(marketSymbol.getBuyPrice());
                    }
                }
                orderType = (String) textViewOrderType.getTag();
            }

            return false;
        }

        return true;
    }

    private boolean checkOrderPropLogicAndShit() {

        if (textViewOrderType.getText().equals("LEVERAGE BUY") && textViewOrderProp.getText().equals("AON")) {

            Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.orderNatureAON));

            textViewOrderProp.setText(ordProps.get(0).getName());
            textViewOrderProp.setTag(ordProps.get(0).getCode());

        }
        return true;
    }

    private boolean checkTradeLogic() {

        String enteredVolume = volume.getText().toString();
        String enteredDiscloseVolume = etDiscVolume.getText().toString();
        String enteredPrice = price.getText().toString();
        String enteredTriggerPrice = etTriggerPrice.getText().toString();
        if (TextUtils.equals(clientcode.getText().toString(), "")) {
            Alert.show(getActivity(), getString(R.string.app_name), "Please Select Client.");
            return false;
        } else if (enteredVolume.length() <= 0) {
            Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.pleaseEnterVolume));
            return false;
        }

        switch (textViewOrderType.getText().toString()) {

            case "MARKET": {

                if (enteredDiscloseVolume.length() > 0) {

                    int volumeInt = Integer.parseInt(enteredVolume);
                    int disclosedVolumeInt = Integer.parseInt(enteredVolume);

                    if (disclosedVolumeInt < volumeInt) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.discloseVolumeMustBe));
                        return false;
                    }
                }

                return true;
            }

            case "LIMIT": {

                if (enteredPrice.length() <= 0) {

                    Alert.show(getActivity(), getString(R.string.app_name), "Please enter price/rate.");
                    return false;
                }

                enteredPrice = enteredPrice.replace(",", "");
                Log.d("Price", enteredPrice);
                float priceFloat = Float.parseFloat(enteredPrice);

                if (priceFloat <= 0) {
                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBeGreaterThan));
                    return false;
                }

//                if (!enteredPrice.contains(".")) {
//                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBe));
//                    return false;
//                }


                if (enteredDiscloseVolume.length() > 0) {

                    int volumeInt = Integer.parseInt(enteredVolume);
                    int disclosedVolumeInt = Integer.parseInt(enteredVolume);

                    if (disclosedVolumeInt < volumeInt) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.discloseVolumeMustBe));
                        return false;
                    }
                }

                return true;

            }

            case "STOP LOSS": {

                if (enteredPrice.length() <= 0 || enteredTriggerPrice.length() <= 0) {

                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.providePriceRateTriggerPrice));
                    return false;
                }

                float priceFloat = Float.parseFloat(enteredPrice);
                float triggerPriceFloat = Float.parseFloat(enteredTriggerPrice);

                if (priceFloat <= 0) {
                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBeGreaterThan));
                    return false;
                }

//                if (!enteredPrice.contains(".")) {
//                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBe));
//                    return false;
//                }

                if (triggerPriceFloat <= 0) {
                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.triggerPriceMustBeGreaterThan));
                    return false;
                }

//                if (!enteredTriggerPrice.contains(".")) {
//                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.triggerPriceMustBe));
//                    return false;
//                }

                if (enteredDiscloseVolume.length() > 0) {

                    int volumeInt = Integer.parseInt(enteredVolume);
                    int disclosedVolumeInt = Integer.parseInt(enteredVolume);

                    if (disclosedVolumeInt < volumeInt) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.discloseVolumeMustBe));
                        return false;
                    }
                }

                if (tab1Selected == 1) {//Buy

                    if (triggerPriceFloat > priceFloat) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.triggerPriceMustBeLess));
                        return false;
                    }
                }

                if (tab1Selected == 2) {//Sell

                    if (priceFloat > triggerPriceFloat) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.triggerPriceMustBeGreater));
                        return false;
                    }
                }

                if (enteredDiscloseVolume.length() > 0) {

                    int volumeInt = Integer.parseInt(enteredVolume);
                    int disclosedVolumeInt = Integer.parseInt(enteredVolume);

                    if (disclosedVolumeInt < volumeInt) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.discloseVolumeMustBe));
                        return false;
                    }
                }

                return true;
            }

            case "MIT": {

                if (enteredPrice.length() <= 0 || enteredTriggerPrice.length() <= 0) {

                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.providePriceRateTriggerPriceMIT));
                    return false;
                }

                float priceFloat = Float.parseFloat(enteredPrice);
                float triggerPriceFloat = Float.parseFloat(enteredTriggerPrice);

                if (priceFloat <= 0) {
                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBeGreaterThan));
                    return false;
                }

//                if (!enteredPrice.contains(".")) {
//                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBe));
//                    return false;
//                }

//                if (!enteredTriggerPrice.contains(".")) {
//                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.triggerPriceMustBe));
//                    return false;
//                }

                if (triggerPriceFloat <= 0) {
                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.triggerPriceMustBeGreaterThan));
                    return false;
                }

                if (tab1Selected == 1) {//Buy

                    if (priceFloat > triggerPriceFloat) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.triggerPriceMustBeGreater));
                        return false;
                    }
                }

                if (tab1Selected == 2) {//Sell

                    if (triggerPriceFloat <= priceFloat) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.triggerPriceMustBeGreaterThanPrice));
                        return false;
                    }
                }

                return true;
            }

            case "LEVERAGE BUY": {

//                if (marketSymbol.getMarket().equals("REG") || tab1Selected == 1) {
//                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.leverageBuyOnlyAllowed));
//                    return false;
//                }

                if (!marketSymbol.getMarket().equals("REG") || tab1Selected == 2) {
                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.leverageBuyOnlyAllowed));
                    return false;
                }

                if (marketSymbol.getMarket().equals("FUT")) {
                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.leverageBuyOnlyAllowedFUT));
                    return false;
                }

                if (enteredPrice.length() <= 0) {

                    Alert.show(getActivity(), getString(R.string.app_name), "Please enter price/rate.");
                    return false;
                }

                float priceFloat = Float.parseFloat(enteredPrice);

                if (priceFloat <= 0) {
                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBeGreaterThan));
                    return false;
                }

//                if (!enteredPrice.contains(".")) {
//                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBe));
//                    return false;
//                }

                if (enteredDiscloseVolume.length() > 0) {

                    int volumeInt = Integer.parseInt(enteredVolume);
                    int disclosedVolumeInt = Integer.parseInt(enteredVolume);

                    if (disclosedVolumeInt < volumeInt) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.discloseVolumeMustBe));
                        return false;
                    }
                }

                return true;
            }

            case "SHORT SELL": {

                if (tab1Selected == 2) {

                    if (marketSymbol != null && !marketSymbol.getMarket().equals("FUT") && !BuildConfig.FLAVOR.equals("bipl")) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.shortSellOnlyAllowed));
                        return false;
                    }

                    if (marketSymbol != null && !marketSymbol.getSymbol().contains("-") && BuildConfig.FLAVOR.equals("bipl")) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.shortSellOnlyAllowed));
                        return false;
                    }

                    if (enteredPrice.length() <= 0) {

                        Alert.show(getActivity(), getString(R.string.app_name), "Please enter price/rate.");
                        return false;
                    }

                    float priceFloat = Float.parseFloat(enteredPrice);

                    if (priceFloat <= 0) {
                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBeGreaterThan));
                        return false;
                    }

//                    if (!enteredPrice.contains(".")) {
//                        Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.priceMustBe));
//                        return false;
//                    }

                    if (enteredDiscloseVolume.length() > 0) {

                        int volumeInt = Integer.parseInt(enteredVolume);
                        int disclosedVolumeInt = Integer.parseInt(enteredVolume);

                        if (disclosedVolumeInt < volumeInt) {
                            Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.discloseVolumeMustBe));
                            return false;
                        }
                    }
                    if (marketSymbol != null && BuildConfig.FLAVOR.equals("bipl")) {
                        String [] arr = marketSymbol.getSymbol().split("-");
                        if(arr.length > 0) {
                            String futPeriod = arr[1];
                            futPeriod = futPeriod.substring(0,3);
//                            String monthInSymbol = marketSymbol.getSymbol().substring(
//                                    marketSymbol.getSymbol().length() - 3, marketSymbol.getSymbol().length());
//                            Alert.show(getActivity(), getString(R.string.app_name), " fuT SYMBOL : " + marketSymbol.getSymbol() + " MONTH " + futPeriod);
                            for (String month : months) {

                                if (month.equals(futPeriod)) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }

                    return true;

                } else if (tab1Selected == 1 && marketSymbol.getMarket().equals("FUT")) {

                    Alert.show(getActivity(), getString(R.string.app_name), getString(R.string.shortSellOnlyAllowedSell));
                    return false;
                }

                return true;
            }
        }


        return true;
    }

    private void updateOrderValue() {

        String vol = volume.getText().toString();
        String pri = price.getText().toString();

        if (vol.length() > 0 && pri.length() > 0) {

            try {
                double orderVal = Double.parseDouble(vol) * Double.parseDouble(pri);

                textViewOrderValue.setText(String.format(Locale.UK, "%.2f", orderVal));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            textViewOrderValue.setText("");
        }

    }

    private boolean proceedToTrade() {

        String enteredDisclosedVolume = etDiscVolume.getText().toString();
        String enteredOrderReference = etOrderReference.getText().toString();
        String enteredVolume = volume.getText().toString();
        String enteredPrice = price.getText().toString();
        String enteredTriggerPrice = etTriggerPrice.getText().toString();

        JsonObject request_obj = new JsonObject();

        switch (textViewOrderType.getText().toString()) {

            case "MARKET": {
                request_obj.addProperty("orderPrice", "");
                request_obj.addProperty("triggerPrice", "");
                break;
            }
            case "LIMIT":
            case "LEVERAGE BUY":
            case "LEVERAGE SELL":
            case "MFB": {
                request_obj.addProperty("orderPrice", enteredPrice);
                request_obj.addProperty("triggerPrice", "");
                break;
            }
            case "STOP LOSS":
            case "MIT":
            case "SHORT SELL": {
                request_obj.addProperty("orderPrice", enteredPrice);
                request_obj.addProperty("triggerPrice", enteredTriggerPrice);
                break;
            }
        }

        request_obj.addProperty("MSGTYPE", Constants.ORDER_REQUEST_IDENTIFIER);
        request_obj.addProperty("orderRemVolume", "0");
        request_obj.addProperty("orderNumber", "0");
        request_obj.addProperty("orderMarket", marketSymbol.getMarket());
        request_obj.addProperty("orderExchange", marketSymbol.getExchangeCode());
        request_obj.addProperty("orderVolume", enteredVolume);
        request_obj.addProperty("orderSide", orderSide);
        request_obj.addProperty("orderAction", "NEW");
        request_obj.addProperty("orderRemVolume", "0");
        request_obj.addProperty("orderSymbol", marketSymbol.getSymbol());
        request_obj.addProperty("orderType", textViewOrderType.getTag().toString());
        request_obj.addProperty("orderNumber", "0");
        request_obj.addProperty("orderProp", textViewOrderProp.getTag().toString());
        request_obj.addProperty("ordReference", enteredOrderReference);
        request_obj.addProperty("discVol", enteredDisclosedVolume);
        request_obj.addProperty("client", client);

//        Log.d("orderTrade", "" + request_obj.toString());

        ((MainActivity) requireActivity()).tradeOrderRequest(request_obj);
        Util.hideKeyboard(requireActivity());

        return true;
    }

    private void bindView(View bindSource) {
        clientcode = bindSource.findViewById(R.id.etclientcode);
        symbol = bindSource.findViewById(R.id.etSymbol);
        volume = bindSource.findViewById(R.id.etVolume);
        price = bindSource.findViewById(R.id.etPrice);
        limit_tv = bindSource.findViewById(R.id.tvlimit);
        buyvol = bindSource.findViewById(R.id.tvbuyvol);
        sellvol = bindSource.findViewById(R.id.tvsellvol);
        listSearch = bindSource.findViewById(R.id.search_list);
        listSearch1 = bindSource.findViewById(R.id.search_list1);
        listSearch_view = bindSource.findViewById(R.id.search_list_view);
        listSearch_view1 = bindSource.findViewById(R.id.search_list_view1);
        radioGroup = bindSource.findViewById(R.id.radioGroup);
        radiobuy = bindSource.findViewById(R.id.radioButtonBuy);
        radiosell = bindSource.findViewById(R.id.radioButtonSell);
        textViewOrderType = bindSource.findViewById(R.id.textViewOrderType);
        textViewOrderProp = bindSource.findViewById(R.id.textViewOrderProp);
        textViewOrderValue = bindSource.findViewById(R.id.textViewOrderValue);
        etTriggerPrice = bindSource.findViewById(R.id.etTriggerPrice);
        etDiscVolume = bindSource.findViewById(R.id.etDiscVolume);
        etOrderReference = bindSource.findViewById(R.id.etOrderReference);
        mCancelSearch = bindSource.findViewById(R.id.cancel_search);
        mCancelSearch1 = bindSource.findViewById(R.id.cancel_search1);
        mTradebutton = bindSource.findViewById(R.id.tradebutton);
        mTextViewOrderType = bindSource.findViewById(R.id.textViewOrderType);
        mTextViewOrderProp = bindSource.findViewById(R.id.textViewOrderProp);
        mCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch(v);
            }
        });
        mCancelSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch(v);
            }
        });


        mTradebutton.setOnClickListener(this);
        mTextViewOrderType.setOnClickListener(this);
        mTextViewOrderProp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tradebutton: {
                if (checkTradeLogic()) {
                    proceedTradePopup();
                }
            }
            break;
            case R.id.textViewOrderType: {

                if (ordTypes != null && ordTypes.size() > 0) {
                    showPickerOrderType(v);
                }
            }
            break;
            case R.id.textViewOrderProp: {

                if (ordProps != null && ordProps.size() > 0) {
                    showPickerOrderProp(v);
                }
            }
            break;
        }
    }

    private class OrdTypeAdapter<T> extends ArrayAdapter<T> {

        private ViewHolder holder;

        OrdTypeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(
                        android.R.layout.select_dialog_item, null);

                holder = new ViewHolder();
                holder.title = (TextView) convertView
                        .findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                // view already defined, retrieve view holder
                holder = (ViewHolder) convertView.getTag();
            }

            String name = "";

            if (getItem(position) != null) {

                if (getItem(position) instanceof OrdType) {
                    name = ((OrdType) getItem(position)).getName();
                }

                if (getItem(position) instanceof OrdProp) {
                    name = ((OrdProp) getItem(position)).getName();
                }
            }

            holder.title.setText(name);


            return convertView;
        }

        class ViewHolder {
            TextView title;
        }
    }

}
