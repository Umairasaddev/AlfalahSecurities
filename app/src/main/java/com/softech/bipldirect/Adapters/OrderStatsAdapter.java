package com.softech.bipldirect.Adapters;

import android.content.Context;
import androidx.core.content.ContextCompat;
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

import com.softech.bipldirect.Models.OrderStatsModel.OrdersList;
import com.softech.bipldirect.R;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class OrderStatsAdapter extends RecyclerView.Adapter<OrderStatsAdapter.ViewHolder> {

    private final ArrayList<OrdersList> arraylist;
    OnListItemClickListener listener;
    private Context context;
    DecimalFormat decimalFormat;
    public OrderStatsAdapter(Context context, ArrayList<OrdersList> items, OnListItemClickListener listener) {
        arraylist = items;
        this.listener = listener;
        this.context = context;
        decimalFormat=new DecimalFormat();
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setMinimumIntegerDigits(1);
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_status_list_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        OrdersList obj = arraylist.get(position);
        holder.exc_mar.setText(obj.getExchange() + "-" + obj.getMarket());
        holder.s_symbol.setText(obj.getSymbol());
        holder.datetime.setText(obj.getDate());
        String orderType = obj.getOrderType();
        holder.clientCode.setText(obj.getClient());

        String sideLabelText = "";

        if (obj.getSide().equals("B") || obj.getSide().equals("Buy")) {
            sideLabelText = "Buy";
            holder.side.setText(sideLabelText);
            holder.side.setTextColor(ContextCompat.getColor(context, R.color.blinkGreen));
            holder.image.setBackgroundColor(ContextCompat.getColor(context, R.color.blinkGreen));
        } else {
            sideLabelText = "Sell";
            holder.side.setText(sideLabelText);
            holder.side.setTextColor(ContextCompat.getColor(context, R.color.blinkRed));
            holder.image.setBackgroundColor(ContextCompat.getColor(context, R.color.blinkRed));

        }
        switch (orderType) {
            case "3":
                holder.side.setText(sideLabelText + "(MKT)");
                break;
            case "4":
                holder.side.setText(sideLabelText + "(LIMIT)");
                break;
            case "5":
                holder.side.setText(sideLabelText + "(STP-LOS)");
                break;
            case "6":
                holder.side.setText(sideLabelText + "(MIT)");
                break;
            case "7":
                holder.side.setText(sideLabelText + "(UNP)");
                break;
            case "8":
                holder.side.setText(sideLabelText + "(SHS)");
                break;
            case "9":
                holder.side.setText(sideLabelText + "(LB)");
                break;
            case "10":
                holder.side.setText(sideLabelText + "(STLMKT)");
                break;
        }
//        String price=obj.getPrice().replace(",","");
        holder.price.setText(obj.getPrice());

        holder.volume.setText("" + obj.getVolume());

        if (obj.getIdentifier().equals("ORD")) {
            holder.textView26.setText(String.format("Executed %s", obj.getExecVolume()));
            holder.qued.setText("Queued");

        }

        if (obj.getIdentifier().equals("TRD")) {

            holder.textView26.setText("");
            holder.qued.setText("" + obj.getValue());

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onListItemClick(v, obj, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public void removeAt(int position) {
        arraylist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arraylist.size());
    }

    public interface OnListItemClickListener {
        void onListItemClick(View caller, OrdersList mItem, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View image;
        TextView exc_mar;
        TextView s_symbol;
        TextView datetime;
        TextView side;
        TextView price;
        TextView qued;
        TextView volume;
        TextView textView26;
        TextView clientCode;

        public ViewHolder(View view, final OnListItemClickListener listener) {
            super(view);
            initViews(view);
        }

        private void initViews(View view) {


            image=view.findViewById(R.id.orderlistimage);
            exc_mar=view.findViewById(R.id.ecx_mar);
            s_symbol=view.findViewById(R.id.symbol);
            datetime=view.findViewById(R.id.datetime);


            side=view.findViewById(R.id.side);
            price=view.findViewById(R.id.price);
            qued=view.findViewById(R.id.qued);
            volume=view.findViewById(R.id.volume);

            textView26=view.findViewById(R.id.textView26);
            clientCode=view.findViewById(R.id.clientCode);
        }
    }
}
