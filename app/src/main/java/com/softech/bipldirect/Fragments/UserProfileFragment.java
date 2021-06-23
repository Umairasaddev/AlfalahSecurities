package com.softech.bipldirect.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softech.bipldirect.Adapters.SearchClientListAdapter;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.ProfileModel.ProfileResponse;
import com.softech.bipldirect.Models.ProfileModel.Response;
import com.softech.bipldirect.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView clientName;
    private TextView clientCode;
    private TextView tradingAccount;
    private TextView email;
    private TextView address;
    private TextView smsService;
    private TextView accountOpenDate;
    private TextView nicExpiryDate;
    private TextView cdcAccount;
    private TextView branchName;
    private TextView traderName;
    private TextView traderCode;
    private TextView traderMobile;
    private TextView traderEmail;
    private TextView zakatStatus;
    private TextView cityName;
    private TextView nic;
    private TextView nominee;
    private TextView jointHolders;
    private TextView bankName;
    private TextView accountTitle;
    private TextView accountNo;
    private TextView accountBranch;
    private TextView accountCity;
    private TextView dividend;
    private String mParam1;
    private String mParam2;
    private EditText clientcode;
    private ListView listSearch1;
    private LinearLayout listSearch_view1;
    boolean isSetInitialText = false;
    private SearchClientListAdapter searchClientListAdapter;
    ArrayList<String> clientlist;
    private View mCancelSearch1;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            Log.d("mParam1", mParam1);
            Log.d("mParam2", mParam2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        bindView(view);
        if (MainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MainActivity.loginResponse.getResponse().getClient());
            clientcode.setEnabled(false);
            ((MainActivity) getActivity()).profileRequest(clientcode.getText().toString());
        } else if (MainActivity.loginResponse.getResponse().getUsertype() == 0 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 3) {

            clientlist = new ArrayList<String>(MainActivity.loginResponse.getResponse().getClientlist());
            searchClientListAdapter = new SearchClientListAdapter(getActivity(), clientlist);
        }
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Profile");
        }
        super.onResume();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        listSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listSearch_view1.setVisibility(View.GONE);
                isSetInitialText = true;
                clientcode.setText(clientlist.get(position));
                ((MainActivity) getActivity()).profileRequest(clientlist.get(position));
            }
        });

    }

    private void cancelSearch(View view) {
        listSearch_view1.setVisibility(View.GONE);
    }

    public void setResult(ProfileResponse result) {

        if (result != null) {

            Response response = result.getResponse();

            clientName.setText(response.getClientName());
            clientCode.setText(response.getClientCode());
            tradingAccount.setText(response.getTradingAccountNo());
            email.setText(response.getEmail());
            address.setText(response.getAddress());
            smsService.setText(response.getSmsService());
            accountOpenDate.setText(response.getAccountOpenDate());
            nicExpiryDate.setText(response.getNICExpiryDate());
            cdcAccount.setText(response.getCdcAccountNo());
            branchName.setText(response.getBranchName());
            traderName.setText(response.getTraderName());
            traderCode.setText(response.getTraderCode());
            traderMobile.setText(response.getTraderMobile());
            traderEmail.setText(response.getTraderEmail());
            zakatStatus.setText(response.getZakatStatus());
            cityName.setText(response.getCityName());
            nic.setText(response.getNIC());
            nominee.setText(response.getNominee());
            jointHolders.setText(response.getJointHolders());
            bankName.setText(response.getBankName());
            accountTitle.setText(response.getAccountTitle());
            accountNo.setText(response.getAccountNo());
            accountBranch.setText(response.getBranchName());
            accountCity.setText(response.getAccountCity());
            dividend.setText(response.getDividend());
        }


    }

    private void bindView(View bindSource) {
        clientName = bindSource.findViewById(R.id.clientName);
        clientCode = bindSource.findViewById(R.id.clientCode);
        tradingAccount = bindSource.findViewById(R.id.tradingAccount);
        email = bindSource.findViewById(R.id.email);
        address = bindSource.findViewById(R.id.address);
        smsService = bindSource.findViewById(R.id.smsService);
        accountOpenDate = bindSource.findViewById(R.id.accountOpenDate);
        nicExpiryDate = bindSource.findViewById(R.id.nicExpiryDate);
        cdcAccount = bindSource.findViewById(R.id.cdcAccount);
        branchName = bindSource.findViewById(R.id.branchName);
        traderName = bindSource.findViewById(R.id.traderName);
        traderCode = bindSource.findViewById(R.id.traderCode);
        traderMobile = bindSource.findViewById(R.id.traderMobile);
        traderEmail = bindSource.findViewById(R.id.traderEmail);
        zakatStatus = bindSource.findViewById(R.id.zakatStatus);
        cityName = bindSource.findViewById(R.id.cityName);
        nic = bindSource.findViewById(R.id.nic);
        nominee = bindSource.findViewById(R.id.nominee);
        jointHolders = bindSource.findViewById(R.id.jointHolders);
        bankName = bindSource.findViewById(R.id.bankName);
        accountTitle = bindSource.findViewById(R.id.accountTitle);
        accountNo = bindSource.findViewById(R.id.accountNo);
        accountBranch = bindSource.findViewById(R.id.accountBranch);
        accountCity = bindSource.findViewById(R.id.accountCity);
        dividend = bindSource.findViewById(R.id.dividend);
        clientcode = bindSource.findViewById(R.id.etclientcode);
        listSearch1 = bindSource.findViewById(R.id.search_list1);
        listSearch_view1 = bindSource.findViewById(R.id.search_list_view1);
        mCancelSearch1 = bindSource.findViewById(R.id.cancel_search1);
        mCancelSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch(v);
            }
        });
    }
}
