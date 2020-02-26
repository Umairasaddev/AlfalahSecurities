package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.R;

import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ScriptDetailAdapter extends RecyclerView.Adapter<ScriptDetailAdapter.ViewHolder> {

    private final ArrayList<String> arraylist;
    private Context context;

    public ScriptDetailAdapter(Context context, ArrayList<String> items) {
        arraylist = items;
        this.context = context;

    }

    @Override
    public ScriptDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.script_detail_items, parent, false);
        return new ScriptDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ScriptDetailAdapter.ViewHolder holder, int position) {


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

        public ViewHolder(View itemView) {
            super(itemView);
        }

//        public ViewHolder(View view) {
//            super(view);
//            ButterKnife.bind(this, view);
//            this.mView = view;
//        }
    }
}