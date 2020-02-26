package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softech.bipldirect.Models.TopSymModel.TopSymbol;
import com.softech.bipldirect.R;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Developed by Hasham.Tahir on 2/3/2016.
 */
public class TopSymbolAdapter extends BaseAdapter {

    private static final String TAG = "TopSymbolAdapterDebug";
    Context mContext;
    LayoutInflater inflater;
    private List<TopSymbol> arrayList;
    private int m_flag = 1;

    public TopSymbolAdapter(Context context, List<TopSymbol> arrayList) {
        mContext = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public TopSymbol getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(R.layout.top_sym_list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        if (position % 2 == 1) {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.greyBar));
        } else {
            view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

        TopSymbol obj = arrayList.get(position);


        holder.sym.setText(obj.getSymbol());
        holder.chng.setText(obj.getChange());
        holder.lasttrade.setText(obj.getLastTradePrice());
        double turnover = Double.parseDouble(obj.getTurnover());
        DecimalFormat formatter = new DecimalFormat("#,###");
        holder.turnover.setText(formatter.format(turnover));

        Log.d(TAG, "getView: postion: " + position + " turnover: " + formatter.format(turnover));


        return view;
    }


    static class ViewHolder {
        @BindView(R.id.top_symbol)
        TextView sym;
        @BindView(R.id.top_change)
        TextView chng;
        @BindView(R.id.top_lasttrade)
        TextView lasttrade;
        @BindView(R.id.top_turnover)
        TextView turnover;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
