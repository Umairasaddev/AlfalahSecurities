package com.softech.bipldirect.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.softech.bipldirect.Adapters.CashBookAdapter;
import com.softech.bipldirect.Adapters.SearchClientListAdapter;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.CashBookModel.CashBookResponse;
import com.softech.bipldirect.Models.CashBookModel.CashDatum;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CashBookFragment extends Fragment {

    @BindView(R.id.recyclerView_cashBook)
    RecyclerView recyclerView_cashBook;
    @BindView(R.id.etclientcode)
    EditText clientcode;
    @BindView(R.id.search_list1)
    ListView listSearch1;
    @BindView(R.id.search_list_view1)
    LinearLayout listSearch_view1;
    private SearchClientListAdapter searchClientListAdapter;
    private CashBookResponse values;
    ArrayList<String> clientlist;
    boolean isSetInitialText = false;

    public CashBookFragment() {
        // Required empty public constructor
    }

    public static CashBookFragment newInstance() {
        return new CashBookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_book, container, false);
        ButterKnife.bind(this, view);
        if (MainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MainActivity.loginResponse.getResponse().getClient());
            clientcode.setEnabled(false);
            ((MainActivity) getActivity()).cashBookRequest(clientcode.getText().toString());
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
            toolbar.setTitle("Cash Book");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView_cashBook.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView_cashBook.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
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
                ((MainActivity) getActivity()).cashBookRequest(clientlist.get(position));
            }
        });
    }

    @OnClick(R.id.cancel_search1)
    public void cancelSearch(View view) {
        listSearch_view1.setVisibility(View.GONE);
    }

    public void setValues(CashBookResponse values) {

        this.values = values;

        if (values != null) {

            ArrayList<CashDatum> listMain = (ArrayList<CashDatum>) values.getResponse().getCashData();

            Collections.sort(listMain, new Comparator<CashDatum>() {
                public int compare(CashDatum o1, CashDatum o2) {
                    return o1.getIndex().compareTo(o2.getIndex());
                }
            });

            listMain.add(null);// last balance row in adapter

            CashBookAdapter adapter = new CashBookAdapter(getActivity(), listMain);
            recyclerView_cashBook.setAdapter(adapter);

        }
    }
}
