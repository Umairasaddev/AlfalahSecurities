package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softech.bipldirect.Models.SymbolsModel.Symbol;
import com.softech.bipldirect.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by saimshafqat on 05/10/2017.
 */


public class SearchClientListAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;

    ArrayList<String> symbolsList ;
    private ArrayList<String> arraylist;

    public SearchClientListAdapter(Context context, ArrayList<String> symbolsList) {
        mContext = context;

        inflater = LayoutInflater.from(mContext);
        this.symbolsList =symbolsList;
        arraylist=new ArrayList<>();
        arraylist.addAll(this.symbolsList);
    }

    @Override
    public int getCount() {
        return symbolsList.size();
    }

    @Override
    public String getItem(int position) {
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

        holder.label.setText(symbolsList.get(position));
        return view;
    }

    // Filter Class
    public void filter(String charText) {

        charText = charText.toUpperCase();

        symbolsList.clear();
        if (charText.length() == 0) {
            symbolsList.addAll(arraylist);
        } else {
            for (String string : arraylist) {

                if (string.startsWith(charText)) {
                    symbolsList.add(string);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        TextView label;

    }

}
