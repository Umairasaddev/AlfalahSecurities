package com.softech.bipldirect.Adapters;

import android.content.Context;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.Models.PortfolioModel.Portfolio;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;
import com.softech.bipldirect.R;

import java.util.ArrayList;


import butterknife.BindView;

public class PLSummaryAdapter extends RecyclerView.Adapter<PLSummaryAdapter.ViewHolder> {

    private final ArrayList<Portfolio> arraylist;
    private Context context;

    public PLSummaryAdapter(Context context, ArrayList<Portfolio> items) {
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

        final PortfolioSymbol obj = (PortfolioSymbol) arraylist.get(position);

        final ViewHolder viewHolder1 = (ViewHolder) holder;
        viewHolder1.companyName.setText(obj.getSymbol());
        viewHolder1.profitLoss.setText(obj.getCapGainLoss());
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView profitLoss;
        TextView companyName;
        TextView volume;
        TextView avg;
        TextView totalCost;
        TextView mktValue;
        TextView payoutValue;
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