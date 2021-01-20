package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.Models.CashBookModel.CashDatum;
import com.softech.bipldirect.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Developed by Hasham.Tahir on 1/13/2017.
 */

public class CashBookAdapter extends RecyclerView.Adapter<CashBookAdapter.ViewHolder> {

    private final ArrayList<CashDatum> arraylist;
    private Context context;

    public CashBookAdapter(Context context, ArrayList<CashDatum> items) {
        arraylist = items;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_cash_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (position % 2 == 1) {
            holder.mView.setBackgroundColor(ContextCompat.getColor(context, R.color.greyBar));
        } else {
            holder.mView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }

        CashDatum obj = arraylist.get(position);

        if (obj != null) {

            holder.mItem = obj;

            holder.textView1.setText(obj.getDescription());
            holder.textView2.setText(obj.getTransDate());

            double debit = Double.parseDouble(obj.getDebit().replace(",", ""));
            double credit = Double.parseDouble(obj.getCredit().replace(",", ""));

            double runningBalance = Double.parseDouble(obj.getRunningBalance().replace(",", ""));

            if (runningBalance < 0) {
                holder.textView4.setTextColor(ContextCompat.getColor(context, R.color.blinkRed));
                holder.textView4.setText(obj.getRunningBalance().replace("-", ""));
            } else {
                holder.textView4.setTextColor(ContextCompat.getColor(context, R.color.blinkGreen));
                holder.textView4.setText(obj.getRunningBalance());
            }

            if (obj.getIndex() != 0) {
                if (credit > 0) {
                    holder.textView3.setTextColor(ContextCompat.getColor(context, R.color.blinkGreen));
                    holder.textView3.setText(obj.getCredit());
                } else if (debit > 0) {
                    holder.textView3.setTextColor(ContextCompat.getColor(context, R.color.blinkRed));
                    holder.textView3.setText(String.format("(%s)", obj.getDebit()));
                }
            }

//            CalligraphyUtils.applyFontToTextView(context, holder.textView4, "fonts/helvetica.otf");
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/helvetica_bold.ttf");

            holder.textView4.setTypeface(typeface);


        } else {
            holder.textView3.setTextColor(Color.BLACK);
            holder.textView3.setText(context.getString(R.string.balance));

            String runningBalanceText = arraylist.get(arraylist.size() - 2).getRunningBalance();

            double runningBalance = Double.parseDouble(runningBalanceText.replace(",", ""));

//            CalligraphyUtils.applyFontToTextView(context, holder.textView4, "fonts/Helvetica Bold.otf");
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/helvetica_bold.ttf");

            holder.textView4.setTypeface(typeface);
            if (runningBalance < 0) {
                holder.textView4.setTextColor(ContextCompat.getColor(context, R.color.blinkRed));
                holder.textView4.setText(runningBalanceText.replace("-", ""));
            } else {
                holder.textView4.setTextColor(ContextCompat.getColor(context, R.color.blinkGreen));
                holder.textView4.setText(runningBalanceText);
            }
        }
    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CashDatum mItem;
        View mView;
        @BindView(R.id.textView1)
        TextView textView1;
        @BindView(R.id.textView2)
        TextView textView2;
        @BindView(R.id.textView3)
        TextView textView3;
        @BindView(R.id.textView4)
        TextView textView4;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.mView = view;
        }
    }
}
