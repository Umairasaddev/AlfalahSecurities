package com.softech.bipldirect.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
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
    private EditText search;
    private TextView name;
    private TextView symbol;
    private TextView last;
    private TextView bid_size;
    private TextView offersize;
    private TextView change;
    private TextView lowhigh;
    private TextView limits;
    private TextView turnover;
    private TextView exchange;
    private TextView market;
    private TextView lotsize;
    private TextView lowhighPrice30;
    private TextView lowhighPrice90;
    private TextView lowhighPrice180;
    private TextView lowhighPrice52;
    private TextView avgPrice30;
    private TextView avgPrice90;
    private TextView avgPrice180;
    private TextView avgPrice52;
    private TextView AvgVolume30;
    private TextView AvgVolume90;
    private TextView AvgVolume180;
    private TextView AvgVolume52;


    private ListView listSearch;

    private LinearLayout listSearch_view;
    List<Symbol> searchKeywordsList;
    private SearchListAdapter searchAdapter;

    private MarketSymbol marketSymbol;

    private OnQoutesFragmentListener mListener;
    private View mCancelSearch;
    private View mTradeBtn;

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
        bindView(view);
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

    private void cancelSearch(View view) {
        listSearch_view.setVisibility(View.GONE);
    }

    private void tradeButton() {

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

    private void bindView(View bindSource) {
        search = bindSource.findViewById(R.id.search);
        name = bindSource.findViewById(R.id.name);
        symbol = bindSource.findViewById(R.id.symbol);
        last = bindSource.findViewById(R.id.textView16);
        bid_size = bindSource.findViewById(R.id.bidtext);
        offersize = bindSource.findViewById(R.id.offertext);
        change = bindSource.findViewById(R.id.changetext);
        lowhigh = bindSource.findViewById(R.id.lowtext);
        limits = bindSource.findViewById(R.id.limittext);
        turnover = bindSource.findViewById(R.id.turnovertext);
        exchange = bindSource.findViewById(R.id.exchangetext);
        market = bindSource.findViewById(R.id.markettext);
        lotsize = bindSource.findViewById(R.id.lottext);
        lowhighPrice30 = bindSource.findViewById(R.id.lowhighPrice30);
        lowhighPrice90 = bindSource.findViewById(R.id.lowhighPrice90);
        lowhighPrice180 = bindSource.findViewById(R.id.lowhighPrice180);
        lowhighPrice52 = bindSource.findViewById(R.id.lowhighPrice52);
        avgPrice30 = bindSource.findViewById(R.id.avgPrice30);
        avgPrice90 = bindSource.findViewById(R.id.avgPrice90);
        avgPrice180 = bindSource.findViewById(R.id.avgPrice180);
        avgPrice52 = bindSource.findViewById(R.id.avgPrice52);
        AvgVolume30 = bindSource.findViewById(R.id.AvgVolume30);
        AvgVolume90 = bindSource.findViewById(R.id.AvgVolume90);
        AvgVolume180 = bindSource.findViewById(R.id.AvgVolume180);
        AvgVolume52 = bindSource.findViewById(R.id.AvgVolume52);
        listSearch = bindSource.findViewById(R.id.search_list);
        listSearch_view = bindSource.findViewById(R.id.search_list_view);
        mCancelSearch = bindSource.findViewById(R.id.cancel_search);
        mTradeBtn = bindSource.findViewById(R.id.trade_btn);
        mCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch(v);
            }
        });
        mTradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tradeButton();
            }
        });
    }

    public interface OnQoutesFragmentListener {
        void onOnQoutesFragmentListener(Symbol symbol);
    }
}
