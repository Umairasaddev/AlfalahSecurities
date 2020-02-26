package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.Models.NetShareModel.NetShareCustody;
import com.softech.bipldirect.R;

import java.util.List;

import static com.softech.bipldirect.Network.MessageSocket.context;

/**
 * Developed by hasham on 4/20/17.
 */

public class NetShareCustodyAdapter extends RecyclerView.Adapter<NetShareCustodyAdapter.ViewHolder> {

    private final List<NetShareCustody> mValues;
    private Context mContext;
    private OnNetShareClickListener clickListener;

    public NetShareCustodyAdapter(Context context, List<NetShareCustody> items,OnNetShareClickListener clickListener) {
        mValues = items;
        mContext = context;
        this.clickListener=clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_net_shares, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position % 2 == 1) {
            holder.mView.setBackgroundColor(ContextCompat.getColor(context, R.color.greyBar));
        } else {
            holder.mView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        holder.position = holder.getAdapterPosition();
        holder.mItem = mValues.get(holder.getAdapterPosition());

        holder.symbol.setText(holder.mItem.getSymbol());
        holder.reg.setText(holder.mItem.getRegular());
        holder.fut.setText(holder.mItem.getForward());
        holder.cdc.setText(holder.mItem.getCdcTradable());
        holder.net.setText(holder.mItem.getNet());
        holder.rate.setText(holder.mItem.getCloseRate());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        NetShareCustody mItem;
        int position;
        private TextView symbol;
        private TextView reg;
        private TextView fut;
        private TextView cdc;
        private TextView net;
        private TextView rate;
        private View mView;

        public ViewHolder(View view) {
            super(view);

            symbol = (TextView) view.findViewById(R.id.textView_symbol);
            reg = (TextView) view.findViewById(R.id.textView_reg);
            fut = (TextView) view.findViewById(R.id.textView_forward);
            cdc = (TextView) view.findViewById(R.id.textView_cdc);
            net = (TextView) view.findViewById(R.id.textView_net);
            rate = (TextView) view.findViewById(R.id.textView_rate);
            mView =  view.findViewById(R.id.list_item);


            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onNetShareClick(mItem);
                }
            });
        }
    }

    public interface OnNetShareClickListener{
        void onNetShareClick(NetShareCustody mItem);
    }
}
