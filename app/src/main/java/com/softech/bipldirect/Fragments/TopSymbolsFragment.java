package com.softech.bipldirect.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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


public class TopSymbolsFragment extends Fragment {

    private ListView top_symbol_list;

    Button gainers;
    Button losers;
    Button volleader;

    private List<TopSymbol> values;

    public TopSymbolsFragment() {
    }

    public static Fragment newInstance() {
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
        bindView(view);
        return view;
    }

    private void gainers(View view) {

        setValues(values, "1");
        resetButtonViews(1);
    }

    private void losers(View view) {

        setValues(values, "2");
        resetButtonViews(2);
    }

    private void leaders(View view) {

        setValues(values, "3");
        resetButtonViews(3);
    }


    private void resetButtonViews(int position) {
        if(position==1){
            gainers.setBackgroundResource(R.drawable.selected);
            losers.setBackgroundResource(R.drawable.unselected);
            volleader.setBackgroundResource(R.drawable.unselected);
        }
        else if(position==2){
            losers.setBackgroundResource(R.drawable.selected);
            gainers.setBackgroundResource(R.drawable.unselected);
            volleader.setBackgroundResource(R.drawable.unselected);

        }
        else{
            volleader.setBackgroundResource(R.drawable.selected);
            gainers.setBackgroundResource(R.drawable.unselected);
            losers.setBackgroundResource(R.drawable.unselected);
        }
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

    private void bindView(View bindSource) {
        top_symbol_list = bindSource.findViewById(R.id.top_symbol_list);
        gainers = bindSource.findViewById(R.id.gainers);
        losers = bindSource.findViewById(R.id.losers);
        volleader = bindSource.findViewById(R.id.volleader);
        gainers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gainers(v);
            }
        });
        losers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                losers(v);
            }
        });
        volleader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaders(v);
            }
        });
    }
}
