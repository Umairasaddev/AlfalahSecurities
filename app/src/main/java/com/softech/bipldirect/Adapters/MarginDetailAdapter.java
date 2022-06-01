package com.softech.bipldirect.Adapters;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softech.bipldirect.Models.MarginModel.CustodyHeader;
import com.softech.bipldirect.Models.MarginModel.CustodyList;
import com.softech.bipldirect.Models.MarginModel.MarginDetail;
import com.softech.bipldirect.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class MarginDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = "MarginDetailAdapter";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;

    double totalAmount=0;
    double totalPortfolio=0;
    Context context;
    private ArrayList<MarginDetail> allData;

    public MarginDetailAdapter(Context context, ArrayList<MarginDetail> data, double totalAmount, double totalPortfolio) {
        this.context = context;
        this.allData = data;
        this.totalAmount=totalAmount;
        this.totalPortfolio=totalPortfolio;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {

            return TYPE_HEADER;


        } else if (position > 0 && allData.get(position) != null) {

            return TYPE_NORMAL;

        }

        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_NORMAL: {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.symbols_list_item, viewGroup, false);
                viewHolder = new ItemViewHolder(v);

            }
            break;
            case TYPE_HEADER: {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_margin_header, viewGroup, false);
                viewHolder = new HeaderViewHolder(v);
            }
            break;
        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_NORMAL: {
                if (position % 2 == 1) {
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.greyBar));
                } else {
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                }

                CustodyList obj = (CustodyList) allData.get(position);
                ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
                itemViewHolder.symbol_symbol.setText(obj.getSymbol());
                itemViewHolder.symbol_market.setText(obj.getBalance());
                itemViewHolder.symbol_exchange.setText(obj.getAmount());


                if(position==(allData.size()-1)){

                    DecimalFormat formatter = new DecimalFormat("#############.##");
                    String totalAmountCommaSeparated = new DecimalFormat("#,###.##").format(Double.parseDouble(formatter.format(totalAmount)));
                    String totalPortfolioCommaSeparated = new DecimalFormat("#,###.##").format(Double.parseDouble(formatter.format(totalPortfolio)));

                    ((ItemViewHolder) viewHolder).tvTotalHoldings.setText(totalAmountCommaSeparated);
                    ((ItemViewHolder) viewHolder).tvTotalPortfolio.setText(totalPortfolioCommaSeparated);

                    if(allData.size()%2==0){
                        ((ItemViewHolder) viewHolder).llTotalHoldings.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                        ((ItemViewHolder) viewHolder).llTotalPortfolio.setBackgroundColor(ContextCompat.getColor(context, R.color.greyBar));
                    }else {
                        ((ItemViewHolder) viewHolder).llTotalHoldings.setBackgroundColor(ContextCompat.getColor(context, R.color.greyBar));
                        ((ItemViewHolder) viewHolder).llTotalPortfolio.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    }
                    ((ItemViewHolder) viewHolder).llTotalHoldings.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) viewHolder).llTotalPortfolio.setVisibility(View.VISIBLE);
                }else{
                    ((ItemViewHolder) viewHolder).llTotalHoldings.setVisibility(View.GONE);
                    ((ItemViewHolder) viewHolder).llTotalPortfolio.setVisibility(View.GONE);
                }

            }
            break;
            case TYPE_HEADER: {
                HeaderViewHolder headerView = (HeaderViewHolder) viewHolder;
                CustodyHeader header = (CustodyHeader) allData.get(0);
                Log.d("header",header.getClientcode());
                headerView.usertext.setText(header.getClientcode());
                headerView.textViewDateTime.setText(header.getDate());
                headerView.cashtext.setText(header.getEquityCashBalance());
                headerView.worthText.setText(header.getTotalWorth());
                headerView.custodyText.setText(header.getEquityReducedValue());
                headerView.mtmText.setText(header.getEquityProfitLos());
                headerView.blockedMtmText.setText(header.getEquityBlockedMTMProfit());
                headerView.marginText.setText(header.getNetLiquidityEquity());
                headerView.exposureText.setText(header.getOpenPositionEquity());
                headerView.marginRequiredText.setText(header.getEquityMarginRequired());
                headerView.cashRequiredText.setText(header.getEquityMarginRequiredAsCash());
                headerView.availableMarginText.setText(header.getEquityFreeMargin());
                headerView.currentMarginText.setText(header.getEquityMarginPerc());
                headerView.cashWithdrawalText.setText(header.getEquityCashWithdrawalinProcess());
                headerView.cashWithdrawalLimitText.setText(header.getEquityCashWithdrawal());
                headerView.marginCallText.setText(header.getEquityCashMarginCall());
                headerView.cashCallText.setText(header.getEquityNetMarginCall());
                headerView.availableCashText.setText(header.getEquityFreeCash());
            }
            break;
        }
    }

    @Override
    public int getItemCount() {
        return allData.size();

    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView symbol_symbol;
        TextView symbol_market;
        TextView symbol_exchange;

        LinearLayout llTotalHoldings;
        LinearLayout llTotalPortfolio;
        TextView tvTotalHoldings;
        TextView tvTotalPortfolio;


        public ItemViewHolder(View itemView) {
            super(itemView);
            symbol_symbol=itemView.findViewById(R.id.symbol_symbol);
            symbol_market=itemView.findViewById(R.id.symbol_market);
            symbol_exchange=itemView.findViewById(R.id.symbol_exchange);

            llTotalHoldings=itemView.findViewById(R.id.llTotalHoldings);
            llTotalPortfolio=itemView.findViewById(R.id.llTotalPortfolio);
            tvTotalHoldings=itemView.findViewById(R.id.tvTotalHoldings);
            tvTotalPortfolio=itemView.findViewById(R.id.tvTotalPortfolio);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView textViewDateTime;
        TextView usertext;
        TextView cashtext;
        TextView worthText;

        TextView custodyText;
        TextView mtmText;
        TextView blockedMtmText;
        TextView marginText;

        TextView exposureText;
        TextView marginRequiredText;
        TextView cashRequiredText;
        TextView availableMarginText;

        TextView currentMarginText;
        TextView cashWithdrawalText;
        TextView cashWithdrawalLimitText;
        TextView marginCallText;
        TextView cashCallText;
        TextView availableCashText;

        public HeaderViewHolder(View view) {
            super(view);
            textViewDateTime=itemView.findViewById(R.id.textViewDateTime);
            usertext=itemView.findViewById(R.id.usertext);
            cashtext=itemView.findViewById(R.id.cashtext);
            worthText=itemView.findViewById(R.id.worthText);

            custodyText=itemView.findViewById(R.id.custodyText);
            mtmText=itemView.findViewById(R.id.mtmText);
            blockedMtmText=itemView.findViewById(R.id.blockedMtmText);
            marginText=itemView.findViewById(R.id.marginText);

            exposureText=itemView.findViewById(R.id.exposureText);
            marginRequiredText=itemView.findViewById(R.id.marginRequiredText);
            cashRequiredText=itemView.findViewById(R.id.cashRequiredText);
            availableMarginText=itemView.findViewById(R.id.availableMarginText);

            currentMarginText=itemView.findViewById(R.id.currentMarginText);
            cashWithdrawalText=itemView.findViewById(R.id.cashWithdrawalText);
            cashWithdrawalLimitText=itemView.findViewById(R.id.cashWithdrawalLimitText);
            marginCallText=itemView.findViewById(R.id.marginCallText);
            cashCallText=itemView.findViewById(R.id.cashCallText);
            availableCashText=itemView.findViewById(R.id.availableCashText);

        }
    }
}

