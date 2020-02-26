package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softech.bipldirect.Models.SymbolsModel.Symbol;
import com.softech.bipldirect.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Developed by Hasham.Tahir on 1/29/2016.
 */
public class SearchListAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Symbol> symbolsList = null;
    private ArrayList<Symbol> arraylist;

    public SearchListAdapter(Context context, List<Symbol> symbolsList) {
        mContext = context;
        this.symbolsList = symbolsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(symbolsList);
        Log.d("search_debug", "Constructor: suggestion size: " + symbolsList.size());

    }

    @Override
    public int getCount() {
        return symbolsList.size();
    }

    @Override
    public Symbol getItem(int position) {
        return symbolsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_search_list, null);
            holder.label = (TextView) view.findViewById(R.id.text1);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        Symbol obj = symbolsList.get(position);
        holder.label.setText(obj.getSymbol() + "-" + obj.getMarket() + "-" + obj.getExchangeCode());

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        Log.d("search_debug", "filter: " + charText);
        Log.d("search_debug", "filter: filteredList size: " + symbolsList.size() + " allListSize: " + arraylist.size());

        charText = charText.toUpperCase();

        symbolsList.clear();
        if (charText.length() == 0) {
            symbolsList.addAll(arraylist);
        } else {
            for (Symbol wp : arraylist) {

                if (wp.getSymbol().startsWith(charText)) {
                    symbolsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView label;
    }

}
