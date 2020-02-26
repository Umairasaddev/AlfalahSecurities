package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softech.bipldirect.Models.AccountModel.AccountDetail;
import com.softech.bipldirect.Models.AccountModel.AccountFooter;
import com.softech.bipldirect.Models.AccountModel.OrdersList;
import com.softech.bipldirect.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Developed by Hasham.Tahir on 2/8/2016.
 */
public class AccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_NORMAL = 1;
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<AccountDetail> ordersList;

    public AccountAdapter(Context context, ArrayList<AccountDetail> ordersList) {
        mContext = context;

        this.ordersList = ordersList;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_NORMAL: {
                View v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.acc_list_item, viewGroup, false);
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
        ViewHolder holder;
        switch (getItemViewType(position)) {
            case TYPE_NORMAL: {
                if (position % 2 == 1) {
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.greyBar));
                } else {
                    viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                }
                OrdersList obj = (OrdersList) ordersList.get(position);
                ViewHolder viewHolder1 = (ViewHolder)viewHolder;
                viewHolder1.sym.setText(obj.getSymbol());
                viewHolder1.qty.setText(obj.getBalance());
                viewHolder1.amount.setText(obj.getAmount());

            }
            break;
            case TYPE_FOOTER: {

                FooterViewHolder footerView = (FooterViewHolder) viewHolder;
                AccountFooter accountFooter = (AccountFooter) ordersList.get(ordersList.size() - 1);
                footerView.sym.setText("");
                footerView.qty.setText("Total Holdings");
                footerView.totalport.setText("Total Portfolio");
                footerView.totalportValue.setText(accountFooter.getTotalPortfolio());
                footerView.amount.setText(accountFooter.getTotalHoldings());

            }
            break;
        }


    }
    @Override
    public int getItemViewType(int position) {
        if (position == ordersList.size() - 1) {

            return TYPE_FOOTER;


        } else {
            return TYPE_NORMAL;

        }
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.acc_sym)
        TextView sym;
        @BindView(R.id.acc_qty)
        TextView qty;
        @BindView(R.id.acc_ammount)
        TextView amount;
        @BindView(R.id.acc_totalPort)
        TextView totalport;
        @BindView(R.id.acc_totalPortValue)
        TextView totalportValue;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.acc_sym)
        TextView sym;
        @BindView(R.id.acc_qty)
        TextView qty;
        @BindView(R.id.acc_ammount)
        TextView amount;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
