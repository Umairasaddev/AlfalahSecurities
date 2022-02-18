package com.softech.bipldirect.Adapters;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softech.bipldirect.Models.PortfolioModel.Portfolio;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioFooter;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;
import com.softech.bipldirect.R;

import java.text.DecimalFormat;
import java.util.List;


public class PortfolioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "PortfolioAdapter";

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 1;
    Context mContext;
    LayoutInflater inflater;
    private List<Portfolio> arrayList;
    private OnPortofolioClickListner portofolioClickListner;

    public PortfolioAdapter(Context context, List<Portfolio> arrayList, OnPortofolioClickListner portofolioClickListner) {
        mContext = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        this.portofolioClickListner = portofolioClickListner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_NORMAL: {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.portfolio_list_item, viewGroup, false);
                viewHolder = new ViewHolder(v) {};

            }
            break;
            case TYPE_FOOTER: {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.acc_list_item_footer, viewGroup, false);
                viewHolder = new FooterViewHolder(v);
            }
            break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        AccountAdapter.ViewHolder holder;
        switch (getItemViewType(position)) {
            case TYPE_NORMAL: {
                if (position % 2 == 1) {
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.greyBar));
                } else {
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }
                final PortfolioSymbol obj = (PortfolioSymbol) arrayList.get(position);
                final ViewHolder viewHolder1 = (ViewHolder) viewHolder;
                viewHolder1.sym.setText(obj.getSymbol());
                viewHolder1.portfolio_market_price.setText(obj.getCurrentPrice());

                viewHolder1.volume.setText(obj.getVolume());
                viewHolder1.portfolio_cost_price.setText(obj.getCostPerUnit());

                double profitLoss=Double.parseDouble(obj.getCapGainLoss().replace(",", ""));
                if(profitLoss>0){
                    viewHolder1.ivArrow.setBackground(ContextCompat.getDrawable(mContext, R.drawable.arrow_green));
                    viewHolder1.profitloss.setTextColor(ContextCompat.getColor(mContext, R.color.darkGreen));
                }else{
                    viewHolder1.ivArrow.setBackground(ContextCompat.getDrawable(mContext, R.drawable.arrow_red));
                    viewHolder1.profitloss.setTextColor(ContextCompat.getColor(mContext, R.color.blinkRed));
                }
                viewHolder1.profitloss.setText(obj.getCapGainLoss());


                viewHolder1.portfolio_total.setText(obj.getTotalCost());

                viewHolder1.llItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        portofolioClickListner.onPortfolioClick(obj);
                    }
                });
            }
            break;
            case TYPE_FOOTER: {
                FooterViewHolder footerView = (FooterViewHolder) viewHolder;
                PortfolioFooter portfolioFooter = (PortfolioFooter) arrayList.get(arrayList.size() - 1);

                DecimalFormat formatter = new DecimalFormat("#,###,###.##");
                String totalInvestmentFormatted = formatter.format(portfolioFooter.getTotalInvestment());

                if(portfolioFooter.getTotalInvestment()>0){
                    footerView.totalInvestment.setTextColor(ContextCompat.getColor(mContext, R.color.darkGreen));
                }else{
                    footerView.totalInvestment.setTextColor(ContextCompat.getColor(mContext, R.color.blinkRed));
                }
                footerView.totalInvestment.setText(totalInvestmentFormatted);


                double totalGainLoss=Double.parseDouble(portfolioFooter.getTotalProfitLoss().replace(",", ""));
                if(totalGainLoss>0){
                    footerView.totalProfitLoss.setTextColor(ContextCompat.getColor(mContext, R.color.darkGreen));
                }else{
                    footerView.totalProfitLoss.setTextColor(ContextCompat.getColor(mContext, R.color.blinkRed));
                }
                footerView.totalProfitLoss.setText(portfolioFooter.getTotalProfitLoss());
            }
            break;
        }

    }

    public interface OnPortofolioClickListner {
        void onPortfolioClick(PortfolioSymbol mItem);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == arrayList.size() - 1) {

            return TYPE_FOOTER;


        } else {
            return TYPE_NORMAL;

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sym;
        TextView volume;
        TextView profitloss;
        LinearLayout llItem;

        ImageView ivArrow;
        TextView portfolio_market_price, portfolio_cost_price, portfolio_total;

        public ViewHolder(View view) {
            super(view);

            sym=view.findViewById(R.id.portfolio_symbol);
            volume=view.findViewById(R.id.portfolio_volume);
            profitloss=view.findViewById(R.id.portfolio_profitloss);
            llItem=view.findViewById(R.id.llItem);
            ivArrow=view.findViewById(R.id.ivArrow);
            portfolio_market_price=view.findViewById(R.id.portfolio_market_price);
            portfolio_cost_price=view.findViewById(R.id.portfolio_cost_price);
            portfolio_total=view.findViewById(R.id.portfolio_total);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView totalInvestment;
        TextView totalProfitLoss;

        public FooterViewHolder(View view) {
            super(view);
            totalInvestment=view.findViewById(R.id.acc_ammount);
            totalProfitLoss=view.findViewById(R.id.acc_totalPortValue);
        }
    }
}
