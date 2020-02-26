package com.softech.bipldirect.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

import com.google.gson.Gson;
import com.softech.bipldirect.Adapters.SearchListAdapter;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.MarketModel.MarketSymbol;
import com.softech.bipldirect.Models.SymbolsModel.Symbol;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class
QuotesFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.symbol)
    TextView symbol;
    @BindView(R.id.textView16)
    TextView last;
    @BindView(R.id.bidtext)
    TextView bid_size;
    @BindView(R.id.offertext)
    TextView offersize;
    @BindView(R.id.changetext)
    TextView change;
    @BindView(R.id.lowtext)
    TextView lowhigh;
    @BindView(R.id.limittext)
    TextView limits;
    @BindView(R.id.turnovertext)
    TextView turnover;
    @BindView(R.id.exchangetext)
    TextView exchange;
    @BindView(R.id.markettext)
    TextView market;
    @BindView(R.id.lottext)
    TextView lotsize;
    @BindView(R.id.lowhighPrice30)
    TextView lowhighPrice30;
    @BindView(R.id.lowhighPrice90)
    TextView lowhighPrice90;
    @BindView(R.id.lowhighPrice180)
    TextView lowhighPrice180;
    @BindView(R.id.lowhighPrice52)
    TextView lowhighPrice52;
    @BindView(R.id.avgPrice30)
    TextView avgPrice30;
    @BindView(R.id.avgPrice90)
    TextView avgPrice90;
    @BindView(R.id.avgPrice180)
    TextView avgPrice180;
    @BindView(R.id.avgPrice52)
    TextView avgPrice52;
    @BindView(R.id.AvgVolume30)
    TextView AvgVolume30;
    @BindView(R.id.AvgVolume90)
    TextView AvgVolume90;
    @BindView(R.id.AvgVolume180)
    TextView AvgVolume180;
    @BindView(R.id.AvgVolume52)
    TextView AvgVolume52;


    @BindView(R.id.search_list)
    ListView listSearch;

    @BindView(R.id.search_list_view)
    LinearLayout listSearch_view;
    List<Symbol> searchKeywordsList;
    private SearchListAdapter searchAdapter;

    private MarketSymbol marketSymbol;

    private OnQoutesFragmentListener mListener;

    public QuotesFragment() {
        // Required empty public constructor
    }


    public static QuotesFragment newInstance(String param1) {
        QuotesFragment fragment = new QuotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
//        Log.d("Qoutes",param1);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            String json = getArguments().getString(ARG_PARAM1);
            marketSymbol = new Gson().fromJson(json, MarketSymbol.class);
        }

        searchKeywordsList = new ArrayList<>(MainActivity.symbolsResponse.getResponse().getSymbols());
        searchAdapter = new SearchListAdapter(getActivity(), searchKeywordsList);

    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Quotes");
        }

        listSearch_view.setVisibility(View.GONE);

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQoutesFragmentListener) {
            mListener = (OnQoutesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQoutesFragmentListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnQoutesFragmentListener) {
            mListener = (OnQoutesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQoutesFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listSearch.setAdapter(searchAdapter);
//        Util.hideKeyboard(getActivity());

        if (marketSymbol != null) {
            setValues(marketSymbol);
        }


        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listSearch_view.setVisibility(View.GONE);

                Symbol symbol = searchKeywordsList.get(position);


                if (mListener != null) {
                    mListener.onOnQoutesFragmentListener(symbol);
                } else {
                    Log.d("onItemClick", "mAddSymbol==null");
                }


            }
        });

        search.addTextChangedListener(new TextWatcher() {
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
                    String text = search.getText().toString();
                    searchAdapter.filter(text);
                } else {
                    listSearch_view.setVisibility(View.GONE);

                }
            }
        });
    }

    @OnClick(R.id.cancel_search)
    public void cancelSearch(View view) {
        listSearch_view.setVisibility(View.GONE);
    }

    @OnClick(R.id.trade_btn)
    public void tradeButton() {

        String TrnCodes = MainActivity.loginResponse.getResponse().getTrnCodes();


        int buyflag = 0, sellflag = 0;

        if (TrnCodes.contains("OM06")) {
            buyflag = 1;
            Log.d("TrnCodes", "buyflag: " + buyflag);
        }
        if (TrnCodes.contains("OM12")) {
            sellflag = 1;
            Log.d("TrnCodes", "sellflag: " + sellflag);
        }

        if (buyflag == 0 & sellflag == 0) {

            Alert.show(getActivity(), getString(R.string.app_name), "You cannot buy or sell shares.");
        } else {

            if (marketSymbol == null) {
                Alert.show(getActivity(), getString(R.string.app_name), "Please select a symbol first.");
            } else {
                ((MainActivity) getActivity()).goToTrade(marketSymbol);
            }
        }

    }

    public void setValues(MarketSymbol marketSymbol) {

        this.marketSymbol = marketSymbol;

        Util.hideKeyboard(getActivity());

        try {
            float ichange = Float.parseFloat(marketSymbol.getChange());

            name.setText(marketSymbol.getName());
            symbol.setText(marketSymbol.getSymbol() + "-" + marketSymbol.getMarket());
            last.setText(marketSymbol.getCurrent());
            bid_size.setText(marketSymbol.getBuyPrice() + "(" + marketSymbol.getBuyVolume() + ")");
            offersize.setText(marketSymbol.getSellPrice() + "(" + marketSymbol.getSellVolume() + ")");

            if (ichange > 0) {
                change.setTextColor(Color.GREEN);
            }
            if (ichange < 0) {
                change.setTextColor(Color.RED);
            }
            if (ichange == 0) {
                change.setTextColor(Color.GRAY);
            }
            double percentage = 0;
            String changeStr = marketSymbol.getChange().replace(",", "");
            String lastStr = marketSymbol.getCurrent().replace(",", "");
            Double chnge = Double.parseDouble(changeStr);
            double last = Double.parseDouble(lastStr);
            double open = last - chnge;
            //if (last > 0 && open > 0) {
            percentage = chnge * 100 / open;
            Log.d("Perc ", String.valueOf(percentage));
            String perc = String.format("%.2f", percentage);
            change.setText(marketSymbol.getChange() + "(" + perc + "%)");
            lowhigh.setText(marketSymbol.getLowPrice() + "-" + marketSymbol.getHighPrice());
            limits.setText(marketSymbol.getLowerLimit() + "-" + marketSymbol.getUpperLimit());
            turnover.setText(marketSymbol.getTurnOver());
            exchange.setText(marketSymbol.getExchangeCode());
            market.setText(marketSymbol.getMarket());
            lotsize.setText(marketSymbol.getLotSize());
            lowhighPrice30.setText(marketSymbol.getLowPrice30() + "-" + marketSymbol.getHighPrice30());
            lowhighPrice90.setText(marketSymbol.getLowPrice90() + "-" + marketSymbol.getHighPrice90());
            lowhighPrice180.setText(marketSymbol.getLowPrice180() + "-" + marketSymbol.getHighPrice180());
            lowhighPrice52.setText(marketSymbol.getWLowPrice52() + "-" + marketSymbol.getWHighPrice52());
            avgPrice30.setText(marketSymbol.getAvgPrice30());
            avgPrice52.setText(marketSymbol.getWAvgPrice52());
            avgPrice90.setText(marketSymbol.getAvgPrice90());
            avgPrice180.setText(marketSymbol.getAvgPrice180());
            AvgVolume30.setText(marketSymbol.getAvgVolume30());
            AvgVolume52.setText(marketSymbol.getWAvgVolume52());
            AvgVolume90.setText(marketSymbol.getAvgVolume90());
            AvgVolume180.setText(marketSymbol.getAvgVol180());


        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert.show(getActivity(), "ERROR", e.getLocalizedMessage());
        }
    }

    public interface OnQoutesFragmentListener {
        void onOnQoutesFragmentListener(Symbol symbol);
    }
}
