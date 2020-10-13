package com.softech.bipldirect.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.softech.bipldirect.Adapters.AccountAdapter;
import com.softech.bipldirect.Adapters.SearchClientListAdapter;
import com.softech.bipldirect.Models.AccountModel.AccountDetail;
import com.softech.bipldirect.Models.AccountModel.AccountFooter;
import com.softech.bipldirect.Models.AccountModel.AccountResponse;
import com.softech.bipldirect.Models.AccountModel.OrdersList;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.MarginModel.MarginDetail;
import com.softech.bipldirect.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountFragment extends Fragment {

    @BindView(R.id.cashtext)
    TextView cash;
    @BindView(R.id.freetext)
    TextView freecash;
    @BindView(R.id.blockedtext)
    TextView blockedcash;
    @BindView(R.id.holdingstext)
    TextView holding;
    @BindView(R.id.margintext)
    TextView margin;
    @BindView(R.id.usertext)
    TextView user;
    @BindView(R.id.acc_listView)
    RecyclerView acc_list;
    @BindView(R.id.etclientcode)
    EditText clientcode;
    @BindView(R.id.search_list1)
    ListView listSearch1;
    @BindView(R.id.search_list_view1)
    LinearLayout listSearch_view1;
    private SearchClientListAdapter searchClientListAdapter;
    ArrayList<String> clientlist;
    private AccountResponse values;
    boolean isSetInitialText = false;
    AccountAdapter accountAdapter;

    public AccountFragment() {
    }

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        acc_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (MainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MainActivity.loginResponse.getResponse().getClient());
            clientcode.setEnabled(false);
            ((MainActivity) getActivity()).accountRequest(clientcode.getText().toString());
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
            toolbar.setTitle("Account");
        }
        super.onResume();
    }

    @OnClick(R.id.cancel_search1)
    public void cancelSearch(View view) {
        listSearch_view1.setVisibility(View.GONE);
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
                ((MainActivity) getActivity()).accountRequest(clientlist.get(position));
                isSetInitialText = true;
                clientcode.setText(clientlist.get(position));

            }
        });


    }

    public void setValues(AccountResponse values) {
        this.values = values;

        if (values != null) {

            cash.setText(values.getResponse().getCashBalance());
            freecash.setText(values.getResponse().getCashUnBlocked());
            blockedcash.setText(values.getResponse().getCashBlocked());
            holding.setText(values.getResponse().getCustody());
            margin.setText(values.getResponse().getAvailableMargin());
            user.setText(clientcode.getText().toString());
            ArrayList<OrdersList> ordersList = (ArrayList<OrdersList>) values.getResponse().getOrdersList();
            View footerView = LayoutInflater.from(getActivity()).inflate(R.layout.acc_list_item_footer, acc_list, false);
//            TextView sym = (TextView) footerView.findViewById(R.id.acc_sym);
//            TextView qty = (TextView) footerView.findViewById(R.id.acc_qty);
//            TextView amount = (TextView) footerView.findViewById(R.id.acc_ammount);
//            TextView totalPortfolio = (TextView) footerView.findViewById(R.id.acc_totalPort);
//            TextView totalPortfolioValue = (TextView) footerView.findViewById(R.id.acc_totalPortValue);
//            footerView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.greyDarkBar));
//            Log.d("FooterCount", String.valueOf(acc_list.getFooterViewsCount()));
//            if (acc_list.getFooterViewsCount() > 0) {
//
//            } else {
//                acc_list.addFooterView(footerView);
//                sym.setText("");
//                qty.setText("Total Holdings");
//                totalPortfolio.setText("Total Portfolio");
//
//            }
            double totalValue = 0;
            for (OrdersList obj : ordersList) {


                try {
                    double amountVal = Double.parseDouble(obj.getAmount().replace(",", ""));

                    totalValue = totalValue + amountVal;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


            }
            AccountFooter accountFooter = new AccountFooter();

            double cashvalue = Double.parseDouble(freecash.getText().toString().replace(",", ""));
            Double totportfoliosum = totalValue + cashvalue;

            totalValue = Math.round(totalValue);

            String sum = String.format("%.0f", totalValue);
            String portsum = String.format("%.0f", totportfoliosum);
            double portfoliosum = Double.parseDouble(portsum);
            DecimalFormat formatter = new DecimalFormat("#,###,###,###.00");

            String yourFormattedString = NumberFormat.getNumberInstance(Locale.UK).format(totalValue);

            accountFooter.setTotalHoldings(yourFormattedString);
            accountFooter.setTotalPortfolio(formatter.format(portfoliosum));
            //  Log.d("totalportfolio", amount.getText().toString());
//            accountAdapter = new AccountAdapter(getActivity(), ordersList);
            ArrayList<AccountDetail> arrayMain = new ArrayList<>();
            arrayMain.addAll(values.getResponse().getOrdersList());
            arrayMain.add(accountFooter);
            accountAdapter = new AccountAdapter(getActivity(), arrayMain);
            acc_list.setAdapter(accountAdapter);
            accountAdapter.notifyDataSetChanged();
        }
    }
}
