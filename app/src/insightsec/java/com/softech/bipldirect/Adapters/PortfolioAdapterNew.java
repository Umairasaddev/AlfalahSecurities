package com.softech.bipldirect.Adapters;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softech.bipldirect.Models.NetShareModel.NetShareCustody;
import com.softech.bipldirect.Models.PortfolioModel.Portfolio;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioFooter;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;
import com.softech.bipldirect.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Developed by Hasham.Tahir on 2/3/2016.
 */
public class PortfolioAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 1;
    Context mContext;
    LayoutInflater inflater;
    private List<Portfolio> arrayList;
    private int m_flag = 1;
    private OnPortofolioClickListner portofolioClickListner;
    private final int colorRed, colorGreen, colorReallyGreen, colorBlack;

    public PortfolioAdapterNew(Context context, List<Portfolio> arrayList,
                               OnPortofolioClickListner portofolioClickListner) {
        mContext = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(mContext);
        this.portofolioClickListner = portofolioClickListner;
        colorRed = ContextCompat.getColor(context, R.color.blinkRed);
        colorGreen = ContextCompat.getColor(context, R.color.blinkGreen);
        colorReallyGreen = ContextCompat.getColor(context, R.color.ledGreen);
        colorBlack = ContextCompat.getColor(context, R.color.black);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_NORMAL: {
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.portfolio_list_item, viewGroup, false);
                viewHolder = new ViewHolder(v) {
                };

            }
            break;
            case TYPE_FOOTER: {
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.acc_list_item_footer, viewGroup, false);
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
//                viewHolder1.mItem = obj;
                viewHolder1.sym.setText(obj.getSymbol());

                String volStr = obj.getVolume().replaceAll(",", "");
                if (volStr != null && !volStr.isEmpty()){
                    viewHolder1.volume.setText(getFormatedAmount(Double.parseDouble(volStr)));
                }
                viewHolder1.profitloss.setText(obj.getCapGainLoss());

                viewHolder1.marketRate.setText(obj.getCurrentPrice());
                viewHolder1.costRate.setText(obj.getCostPerUnit());
                viewHolder1.totalPortfolio.setText(obj.getTotalCost());

               Double exChange  = Double.parseDouble(obj.getCapGainLoss().replaceAll(",", ""));
                if (exChange < 0) {
                    viewHolder1.profitloss.setTextColor(colorRed);
                    viewHolder1.ivArrow.setBackgroundResource(R.drawable.arrow_red);
                } else if (exChange > 0) {
                    viewHolder1.profitloss.setTextColor(colorGreen);
                    viewHolder1.ivArrow.setBackgroundResource(R.drawable.arrow_green);
                } else if (exChange == 0) {
                    viewHolder1.profitloss.setTextColor(colorBlack);
                    viewHolder1.ivArrow.setBackgroundResource(R.drawable.arrow_grey);
                }

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
                footerView.sym.setText("");
                footerView.qty.setText("Total Profit/loss");
                Log.d("totalprofitloss", portfolioFooter.getTotalProfitloss());
                footerView.totalProfitloss.setText(portfolioFooter.getTotalProfitloss());

                if (Double.parseDouble(portfolioFooter.getTotalProfitloss().replaceAll(",", "")) >0){
                    footerView.qty.setTextColor(colorGreen);
                    footerView.totalProfitloss.setTextColor(colorGreen);
                }else{
                    footerView.qty.setTextColor(colorRed);
                    footerView.totalProfitloss.setTextColor(colorRed);
                }
            }
            break;
        }

    }

    private String getFormatedAmount(double amount){
        return NumberFormat.getNumberInstance(Locale.US).format(amount);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.portfolio_symbol)
        TextView sym;
        @BindView(R.id.portfolio_volume)
        TextView volume;
        @BindView(R.id.portfolio_market_price)
        TextView marketRate;
        @BindView(R.id.portfolio_cost_price)
        TextView costRate;
        @BindView(R.id.portfolio_total)
        TextView totalPortfolio;
        @BindView(R.id.portfolio_profitloss)
        TextView profitloss;
        @BindView(R.id.llItem)
        LinearLayout llItem;

        @BindView(R.id.ivArrow)
        ImageView ivArrow;

//        View mView;
//        PortfolioSymbol mItem;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.acc_sym)
        TextView sym;
        @BindView(R.id.acc_qty)
        TextView qty;
        @BindView(R.id.acc_ammount)
        TextView totalProfitloss;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
