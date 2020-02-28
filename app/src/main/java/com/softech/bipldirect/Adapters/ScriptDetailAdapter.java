package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.Models.PortfolioModel.Portfolio;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;
import com.softech.bipldirect.Models.PortfolioWatch.Cash;
import com.softech.bipldirect.R;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ScriptDetailAdapter extends RecyclerView.Adapter<ScriptDetailAdapter.ViewHolder> {

    private final ArrayList<Portfolio> arraylist;
    private Context context;

    public ScriptDetailAdapter(Context context, ArrayList<Portfolio> items) {
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

        final PortfolioSymbol obj = (PortfolioSymbol) arraylist.get(position);

        holder.companyName.setText(obj.getSymbol());
        holder.profitLoss.setText(obj.getCapGainLoss());
        holder.volume.setText(obj.getVolume());
        holder.totalCost.setText(obj.getTotalCost());
        holder.avg.setText(obj.getCostPerUnit());
        holder.mktValue.setText(obj.getCurrentPrice());
        holder.payoutValue.setText(obj.getAnnouncementValue());
        holder.annDate.setText(obj.getAnnouncementDate());

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
            profitLoss = itemView.findViewById(R.id.profit_lossText);
            companyName = itemView.findViewById(R.id.company_nameText);
            volume = itemView.findViewById(R.id.volume);
            avg = itemView.findViewById(R.id.avg_text);
            totalCost = itemView.findViewById(R.id.total_cost_text);
            mktValue = itemView.findViewById(R.id.mkt_value);
            payoutValue = itemView.findViewById(R.id.payout_value);
            annDate = itemView.findViewById(R.id.ann_dateValue);
        }

    }
}