package com.softech.bipldirect.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.softech.bipldirect.Adapters.SearchClientListAdapter;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.PaymentModel.PaymentResponse;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.Alert;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class PaymentFragment extends Fragment {

    private final String TAG = "PaymentFragment";

    private EditText clientcode;
    private EditText edit_exchange;
    private EditText edit_amount;
    private RadioGroup radioGroup;
    private TextView cashBalance;
    private TextView withdrawalLimit;
    private TextView pendingWithdrawal;
    private TextView remainingAmount;
    private LinearLayout listSearch_view1;
    private ListView listSearch1;
    ArrayList<String> clientlist;
    SearchClientListAdapter searchClientListAdapter;
    String client;
    String selectedVal = null; //B bank, C cheque
    boolean isSetInitialText = false;
    private View mButtonPaymentRequest;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        bindView(view);
        if (MainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MainActivity.loginResponse.getResponse().getClient());
            client = clientcode.getText().toString();
            clientcode.setEnabled(false);
            ((MainActivity) getActivity()).getPaymentRequest(clientcode.getText().toString());
        }
        else if (MainActivity.loginResponse.getResponse().getUsertype() == 0 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 3) {
            clientlist = new ArrayList<String>(MainActivity.loginResponse.getResponse().getClientlist());
            searchClientListAdapter = new SearchClientListAdapter(getActivity(), clientlist);
            listSearch1.setAdapter(searchClientListAdapter);

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //      edit_username.setText(MainActivity.loginResponse.getResponse().getUserId());
        edit_exchange.setText(MainActivity.loginResponse.getResponse().getServerCode());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Log.e("checkedId", "checkedId: " + checkedId);

                if (checkedId == R.id.radio1) {

                    selectedVal = "B";

                } else if (checkedId == R.id.radio2) {

                    selectedVal = "C";

                } else {

                    selectedVal = null;
                }
            }
        });
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
        listSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listSearch_view1.setVisibility(View.GONE);
                isSetInitialText = true;
                clientcode.setText(clientlist.get(position));
                ((MainActivity) getActivity()).getPaymentRequest(clientcode.getText().toString());

            }
        });


    }

    private void submit(View view) {

        String amount = edit_amount.getText().toString();
        String client = clientcode.getText().toString();
        if (TextUtils.equals(client, "")) {
            Alert.show(getActivity(), getString(R.string.app_name), "Please Select Client.");
        }
        else if (TextUtils.equals(amount, "")) {

            Alert.show(getActivity(), getString(R.string.app_name), "Please enter amount.");

        } else if (selectedVal == null) {

            Alert.show(getActivity(), getString(R.string.app_name), "Please select Bank Transfer or Cheque Delivery.");
        } else {

            try {
                int amountVal = Integer.parseInt(amount);

                ((MainActivity) getActivity()).paymentRequest(amountVal, selectedVal,clientcode.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
                Alert.show(getActivity(), getString(R.string.app_name), "Invalid amount entered.");
            }
        }


//        String amount= edit_amount.gettex.toString();
//        String newPin= edit_newPin.getText().toString();
//        String confirmPin= edit_confirmPin.getText().toString();
//
//
//        if (!TextUtils.equals(oldPin, "")){
//
//            if(!TextUtils.equals(newPin,"")&&newPin.length()>=8){
//
//                if(TextUtils.equals(newPin,confirmPin)){
//
//                    ((MainActivity)getActivity()).changePinRequest(oldPin, newPin);
//
//                }else{
//                    HToast.showMsg(getActivity(), "Pins do not match.");
//                }
//            }else{
//                HToast.showMsg(getActivity(),"New Pin must be equal to 4 characters.");
//            }
//
//        }else {
//            HToast.showMsg(getActivity(),"Please type your old pin.");
//        }


    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Payment Request");
        }
        super.onResume();
    }

    public void setResult(JsonObject json) {

        final PaymentResponse paymentResponse = new Gson().fromJson(json, PaymentResponse.class);

        if (paymentResponse != null) {

            final String cash = paymentResponse.getResponse().getCashBalance();
            final String withDrawal = paymentResponse.getResponse().getWithdrawalLimit();
            String pending = paymentResponse.getResponse().getPendingWithdrawal();

            cashBalance.setText(cash);
            withdrawalLimit.setText(withDrawal);
            pendingWithdrawal.setText(pending);

            edit_amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    try {
                        double amountEntered = Double.parseDouble(s.toString());
                        double withDrawalLimit = Double.parseDouble(paymentResponse.getResponse().getWithdrawalLimit().replace(",", ""));

                        String remaining = String.valueOf(withDrawalLimit - amountEntered);

                        double amount = Double.parseDouble(remaining);
                        DecimalFormat formatter = new DecimalFormat("#,###.00");


                        remainingAmount.setText(formatter.format(amount));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (s.length() == 0) {
                        remainingAmount.setText("");
                    }

                }
            });


        }


    }

    private void bindView(View bindSource) {
        clientcode = bindSource.findViewById(R.id.edittext_clientcode);
        edit_exchange = bindSource.findViewById(R.id.edittext_exchange);
        edit_amount = bindSource.findViewById(R.id.edittext_amount);
        radioGroup = bindSource.findViewById(R.id.radioGroup);
        cashBalance = bindSource.findViewById(R.id.cashBalance);
        withdrawalLimit = bindSource.findViewById(R.id.withdrawalLimit);
        pendingWithdrawal = bindSource.findViewById(R.id.pendingWithdrawal);
        remainingAmount = bindSource.findViewById(R.id.remainingAmount);
        listSearch_view1 = bindSource.findViewById(R.id.search_list_view1);
        listSearch1 = bindSource.findViewById(R.id.search_list1);
        mButtonPaymentRequest = bindSource.findViewById(R.id.button_payment_request);
        mButtonPaymentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(v);
            }
        });
    }
}
