package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.R;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

public class PLSummaryAdapter extends RecyclerView.Adapter<PLSummaryAdapter.ViewHolder> {

    private final ArrayList<String> arraylist;
    private Context context;

    public PLSummaryAdapter(Context context, ArrayList<String> items) {
        arraylist = items;
        this.context = context;

    }

    @Override
    public PLSummaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.summary_items, parent, false);
        return new PLSummaryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PLSummaryAdapter.ViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        View mView;
        @BindView(R.id.profit_lossText)
        TextView profitLoss;
        @BindView(R.id.company_nameText)
        TextView companyName;
        @BindView(R.id.volume)
        TextView volume;
        @BindView(R.id.avg_text)
        TextView avg;
        @BindView(R.id.total_cost_text)
        TextView totalCost;
        @BindView(R.id.mkt_value)
        TextView mktValue;
        @BindView(R.id.payout_value)
        TextView payoutValue;
        @BindView(R.id.ann_dateValue)
        TextView annDate;

        public ViewHolder(View view) {
            super(view);
//            ButterKnife.bind(this, view);
//            this.mView = view;
        }
    }
}