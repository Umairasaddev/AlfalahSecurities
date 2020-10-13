package com.softech.bipldirect.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.softech.bipldirect.Models.TopSymModel.TopSymbol;
import com.softech.bipldirect.Adapters.TopSymbolAdapter;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.Alert;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TopSymbolsFragment extends Fragment {

    @BindView(R.id.top_symbol_list)
    ListView top_symbol_list;

    @BindViews({R.id.gainers, R.id.losers, R.id.volleader})
    List<Button> buttonViews;

    private List<TopSymbol> values;

    public TopSymbolsFragment() {
    }

    public static TopSymbolsFragment newInstance() {
        return new TopSymbolsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_symbols, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.gainers)
    public void gainers(View view) {

        setValues(values, "1");

        resetButtonView(view);
    }

    @OnClick(R.id.losers)
    public void losers(View view) {

        setValues(values, "2");

        resetButtonView(view);
    }

    @OnClick(R.id.volleader)
    public void leaders(View view) {

        setValues(values, "3");

        resetButtonView(view);
    }


    private void resetButtonView(View view) {

        for (Button button : buttonViews) {

            button.setBackgroundResource(R.drawable.unselected);
        }

        view.setBackgroundResource(R.drawable.selected);
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            // change1
            toolbar.setTitle("Market Performers");
        }
        super.onResume();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).topSymbolRequest();
    }

    public void setValues(List<TopSymbol> values, String dataType) {
        this.values = values;

        if (values != null && values.size() > 0) {

            List<TopSymbol> arrayToShow = new ArrayList<>();

            for (TopSymbol obj : values) {

                if (obj.getDataType().equals(dataType)) {

                    arrayToShow.add(obj);
                }
            }

            TopSymbolAdapter adapter = new TopSymbolAdapter(getActivity(), arrayToShow);
            top_symbol_list.setAdapter(adapter);

        } else {

            Alert.show(getActivity(), getString(R.string.app_name), "No Symbols to display, Try again later.");
        }
    }
}
