package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softech.bipldirect.Fragments.ChartTypeFragment;
import com.softech.bipldirect.R;

/**
 * Developed by Hasham.Tahir on 10/27/2016.
 */

public class ChartTypeAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private ChartTypeFragment.Charts[] arr;

    public ChartTypeAdapter(Context context, ChartTypeFragment.Charts[] arr) {
        mContext = context;
        this.arr = arr;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public ChartTypeFragment.Charts getItem(int position) {
        return arr[position];
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

        ChartTypeFragment.Charts obj = arr[position];
        holder.label.setText(obj.getTitle());

        return view;
    }

    public class ViewHolder {
        TextView label;

    }
}
