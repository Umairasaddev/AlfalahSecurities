package com.softech.bipldirect.Adapters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.Fragments.ExchangeFragment;
import com.softech.bipldirect.Models.ExchangeModel.Exchange;
import com.softech.bipldirect.R;

import java.util.List;


public class ExchangeAdapter extends RecyclerView.Adapter<ExchangeAdapter.ViewHolder> {

    private final List<Exchange> mValues;
    private final int colorRed, colorGreen;
    private RecyclerView.LayoutManager linearLayoutManager;
    private long animDuration = 1600;
    private Context mContext;

    public ExchangeAdapter(Context context, List<Exchange> items, RecyclerView.LayoutManager linearLayoutManager, ExchangeFragment listener) {
        mValues = items;
        this.linearLayoutManager = linearLayoutManager;
        mContext = context;
        colorRed = ContextCompat.getColor(mContext, R.color.blinkRed);
        colorGreen = ContextCompat.getColor(mContext, R.color.blinkGreen);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_exchanges, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.position = position;
        holder.mItem = mValues.get(position);

        holder.exchangeName.setText(holder.mItem.getSymbol());

        final SpannableStringBuilder sbLast = new SpannableStringBuilder("Last: " + holder.mItem.getLastIndex());
        final SpannableStringBuilder sbVol = new SpannableStringBuilder("Val: " + holder.mItem.getMonitoryVolume());
        final SpannableStringBuilder sbLow = new SpannableStringBuilder("Low: " + holder.mItem.getLowIndex());
        final SpannableStringBuilder sbHigh = new SpannableStringBuilder("High: " + holder.mItem.getHighIndex());

        final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#555555"));

        sbLast.setSpan(fcs, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sbVol.setSpan(fcs, 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sbLow.setSpan(fcs, 0, 3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sbHigh.setSpan(fcs, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        holder.last.setText(sbLast);
        holder.volume.setText(sbVol);
        holder.current.setText(holder.mItem.getCurrent());
        holder.low.setText(sbLow);
        holder.high.setText(sbHigh);
        holder.change.setText(holder.mItem.getChange());
        double percentage=0;
        String changeStr  = holder.mItem.getChange().replace(",", "");
        String lastStr   = holder.mItem.getCurrent().replace(",", "");
        Double chnge = Double.parseDouble(changeStr);
        double lst = Double.parseDouble(lastStr);
        double open = lst - chnge;
        //if (last > 0 && open > 0) {
        percentage = chnge * 100 / open;
        Log.d("Perc " ,String.valueOf(percentage));
        String perc=String.format("%.2f", percentage);
        holder.changePer.setText(perc.concat("%"));
        holder.turnOver.setText(holder.mItem.getTurnOver());

        try {
            float change = Float.parseFloat(holder.mItem.getChange().replace(",", ""));

            if (change < 0) {
                holder.change.setTextColor(ContextCompat.getColor(mContext, R.color.blinkRed));
                holder.changePer.setTextColor(ContextCompat.getColor(mContext, R.color.blinkRed));
            } else if (change > 0) {
                holder.change.setTextColor(ContextCompat.getColor(mContext, R.color.blinkGreen));
                holder.changePer.setTextColor(ContextCompat.getColor(mContext, R.color.blinkGreen));
            } else {
                holder.change.setTextColor(Color.BLACK);
                holder.changePer.setTextColor(Color.BLACK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            float current = Float.parseFloat(holder.mItem.getCurrent().replace(",", ""));
            float last = Float.parseFloat(holder.mItem.getLastIndex().replace(",", ""));


            if (current > last) {
                Log.d("ExchangeCheck", "current: " + current + " > last:" + last);
                holder.current.setTextColor(ContextCompat.getColor(mContext, R.color.blinkGreen));
            } else if (current < last) {
                Log.d("ExchangeCheck", "current: " + current + " < last:" + last);
                holder.current.setTextColor(ContextCompat.getColor(mContext, R.color.blinkRed));
            } else if (current == last) {
                Log.d("ExchangeCheck", "current: " + current + " == last:" + last);
                holder.current.setTextColor(Color.BLACK);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void remove(int position) {
        mValues.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Exchange item) {

        mValues.add(item);
        notifyItemInserted(mValues.size() - 1);
        notifyItemRangeChanged(mValues.size() - 1, mValues.size());
        notifyDataSetChanged();
    }

    public void updateFeed(List<Exchange> exchangeList) {

        for (int i = 0; i < exchangeList.size(); i++) {

            Exchange symToCompare = exchangeList.get(i);

            for (int j = 0; j < mValues.size(); j++) {

                Exchange existingSym = mValues.get(j);

                if (symToCompare.getSymbol().equals(existingSym.getSymbol())) {

                    blink(j, existingSym, symToCompare);
                    Log.d("blink", "blink");
                }

            }

        }
    }

    private void blink(int position, Exchange existingSym, Exchange symToCompare) {

        try {
            View view = linearLayoutManager.findViewByPosition(position);

            if (view != null) {

                float exChange = Float.parseFloat(symToCompare.getChange());
//                float newChange = Float.parseFloat(symToCompare.getChange());

                float current = Float.parseFloat(symToCompare.getCurrent().replace(",", ""));
                float last = Float.parseFloat(symToCompare.getLastIndex().replace(",", ""));


                mValues.set(position, symToCompare);
//                mValues.get(position).setChange(symToCompare.getChange());
//                mValues.get(position).setTurnOver(symToCompare.getTurnOver());
//                mValues.get(position).setIndicator(symToCompare.getIndicator());
//                mValues.get(position).setExchangeCode(symToCompare.getExchangeCode());

                notifyDataSetChanged();

                if (exChange < 0) {

                    blinkAnimationBgText(view.findViewById(R.id.change), colorRed, Color.WHITE);
                    blinkAnimationBgText(view.findViewById(R.id.change_per), colorRed, Color.WHITE);

                } else if (exChange > 0) {

                    blinkAnimationBgText(view.findViewById(R.id.change), colorGreen, Color.WHITE);
                    blinkAnimationBgText(view.findViewById(R.id.change_per), colorGreen, Color.WHITE);
                }

                if (current > last) {
                    blinkAnimationText(view.findViewById(R.id.current), colorGreen);
                } else if (current < last) {
                    blinkAnimationText(view.findViewById(R.id.current), colorRed);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void blinkAnimationText(final View view, final int colorFrom) {

        final TextView textView = ((TextView) view);

        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, textView.getCurrentTextColor());
        animator.setDuration(animDuration); // milliseconds
        animator.setRepeatCount(1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((int) animator.getAnimatedValue());
            }
        });

        animator.start();
    }

    private void blinkAnimationBgText(final View view, final int colorFrom, final int colorTo) {

        final TextView textView = ((TextView) view);

        ValueAnimator animatorText = ValueAnimator.ofObject(new ArgbEvaluator(), colorTo, textView.getCurrentTextColor());
        animatorText.setDuration(animDuration); // milliseconds
        animatorText.setRepeatCount(1);
        animatorText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                ((TextView) view).setTextColor((int) animator.getAnimatedValue());
            }
        });

        ValueAnimator animatorBg = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        animatorBg.setDuration(animDuration); // milliseconds
        animatorBg.setRepeatCount(1);
        animatorBg.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });

        animatorBg.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                view.setBackgroundColor(colorTo);
//                if (view.getId() == R.id.change||view.getI) {
                ((TextView) view).setTextColor(colorFrom);
//                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(animatorText, animatorBg);
        animatorSet.start();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public Exchange mItem;
        int position;
        private TextView exchangeName;
        private TextView last;
        private TextView volume;
        private TextView current;
        private TextView low;
        private TextView high;
        private TextView change;
        private TextView changePer;
        private TextView turnOver;

        public ViewHolder(View view) {
            super(view);


            mView = view.findViewById(R.id.front);
            exchangeName = (TextView) view.findViewById(R.id.exchangeName);
            last = (TextView) view.findViewById(R.id.last);
            volume = (TextView) view.findViewById(R.id.volume);
            current = (TextView) view.findViewById(R.id.current);
            low = (TextView) view.findViewById(R.id.low);
            high = (TextView) view.findViewById(R.id.high);
            change = (TextView) view.findViewById(R.id.change);
            changePer = (TextView) view.findViewById(R.id.change_per);
            turnOver = (TextView) view.findViewById(R.id.turn_over);

//            mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onMarketItemClick(v, mItem, position);
//                }
//            });
        }
    }
}
