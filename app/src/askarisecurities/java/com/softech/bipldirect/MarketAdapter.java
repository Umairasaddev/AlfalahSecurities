package com.softech.bipldirect.Adapters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softech.bipldirect.Models.MarketModel.MarketSymbol;
import com.softech.bipldirect.R;

import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {

    private final List<MarketSymbol> mValues;
    private final int colorRed, colorGreen, colorReallyGreen,colorBlack;
    OnMarketItemClickListener listener;
    private Context context;
    private RecyclerView.LayoutManager linearLayoutManager;
    private long animDuration = 2000;
    private ValueAnimator blinkAnimationText;
    private AnimatorSet animatorSet;

    public MarketAdapter(Context context, List<MarketSymbol> items, RecyclerView.LayoutManager linearLayoutManager, OnMarketItemClickListener listener) {
        mValues = items;
        this.linearLayoutManager = linearLayoutManager;
        this.context = context;
        this.listener = listener;
        colorRed = ContextCompat.getColor(context, R.color.blinkRed);
        colorGreen = ContextCompat.getColor(context, R.color.blinkGreen);
        colorBlack = ContextCompat.getColor(context, R.color.black);
        colorReallyGreen = ContextCompat.getColor(context, R.color.ledGreen);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.market_list_row, parent, false);

        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if ((position % 2) == 0) {
            holder.mView.setBackgroundResource(R.color.white);
        } else {
            holder.mView.setBackgroundResource(R.color.greyBar);
        }

        holder.position = position;
        holder.mItem = mValues.get(position);

        holder.symbol.setText(holder.mItem.getSymbol());
        holder.market.setText(holder.mItem.getMarket());
        holder.name.setText(holder.mItem.getName());
        holder.current.setText(holder.mItem.getCurrent());
        holder.buy_price.setText(holder.mItem.getBuyPrice());

        holder.buy_volume.setText(holder.mItem.getBuyVolume());
        holder.sell_price.setText(holder.mItem.getSellPrice());
        holder.sell_voulme.setText(holder.mItem.getSellVolume());
        holder.low_price.setText(holder.mItem.getLowPrice());
        holder.high_price.setText(holder.mItem.getHighPrice());

        holder.change.setText(holder.mItem.getChange());
        double percentage = 0;
        String changeStr = holder.mItem.getChange().replace(",", "");
        String lastStr = holder.mItem.getCurrent().replace(",", "");
        Double change = Double.parseDouble(changeStr);
        //   Log.d("Low ",holder.mItem.getLowerLimit());
        //  Log.d("High ",holder.mItem.getUpperLimit());
        //   Log.d("Symbol High Low",holder.mItem.getSymbol() + holder.mItem.getLowPrice()+holder.mItem.getHighPrice());
        //   Log.d("last : ",lastStr);
        //   Log.d("change : ",changeStr);

        double last = Double.parseDouble(lastStr);
        double open = last - change;

        if (open != 0) {
            percentage = change * 100 / open;
            Log.d("Perc ", String.valueOf(percentage));
            String perc = String.format("%.2f", percentage);
            holder.change_per.setText(perc.concat("%"));
        } else {
            holder.change_per.setText(holder.mItem.getChangePer().concat("%"));

        }


        holder.turn_over.setText(holder.mItem.getTurnOver());

        float exChange = Float.parseFloat(holder.mItem.getChange().replace(",", ""));

        if (exChange < 0) {
            holder.change.setTextColor(colorRed);
            holder.change_per.setTextColor(colorRed);
            holder.ivArrow.setBackgroundResource(R.drawable.arrow_red);
        } else if (exChange > 0) {
            holder.change.setTextColor(colorGreen);
            holder.change_per.setTextColor(colorGreen);
            holder.ivArrow.setBackgroundResource(R.drawable.arrow_green);
        } else if (exChange == 0) {
            holder.change.setTextColor(colorBlack);
            holder.change_per.setTextColor(colorBlack);
            holder.ivArrow.setBackgroundResource(R.drawable.arrow_grey);
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

    public void addItem(MarketSymbol item) {

        mValues.add(item);
        notifyItemInserted(mValues.size() - 1);
        notifyItemRangeChanged(mValues.size() - 1, mValues.size());
        notifyDataSetChanged();
    }

    public void updateFeed(List<MarketSymbol> symbolList) {

        for (int i = 0; i < symbolList.size(); i++) {

            MarketSymbol symToCompare = symbolList.get(i);

            for (int j = 0; j < mValues.size(); j++) {

                MarketSymbol existingSym = mValues.get(j);

                if (symToCompare.getSymbol().equals(existingSym.getSymbol())
                        && symToCompare.getMarket().equals(existingSym.getMarket())
                        && symToCompare.getExchangeCode().equals(existingSym.getExchangeCode())) {


                    blink(j, existingSym, symToCompare);

                }

            }

        }


    }

    private void blink(int position, MarketSymbol existingSym, MarketSymbol symToCompare) {

        try {

            final View row = linearLayoutManager.findViewByPosition(position);

            if (row != null) {

                final View parent = row.findViewById(R.id.front);

                float exBuyPrice = Float.parseFloat(existingSym.getBuyPrice().replace(",", ""));
                float newBuyPrice = Float.parseFloat(symToCompare.getBuyPrice().replace(",", ""));

                float exSellPrice = Float.parseFloat(existingSym.getSellPrice().replace(",", ""));
                float newSellPrice = Float.parseFloat(symToCompare.getSellPrice().replace(",", ""));

                float exBuyVolume = Float.parseFloat(existingSym.getBuyVolume().replace(",", ""));
                float newBuyVolume = Float.parseFloat(symToCompare.getBuyVolume().replace(",", ""));

                float exSellVolume = Float.parseFloat(existingSym.getSellVolume().replace(",", ""));
                float newSellVolume = Float.parseFloat(symToCompare.getSellVolume().replace(",", ""));

                float exChange = Float.parseFloat(existingSym.getChange().replace(",", ""));
                float newChange = Float.parseFloat(symToCompare.getChange().replace(",", ""));

                //  mValues.set(position,symToCompare);

                MarketSymbol oldMarketSymbol = mValues.get(position);
//
                oldMarketSymbol.setChange(symToCompare.getChange());
                oldMarketSymbol.setBuyPrice(symToCompare.getBuyPrice());
                oldMarketSymbol.setBuyVolume(symToCompare.getBuyVolume());
                oldMarketSymbol.setSellPrice(symToCompare.getSellPrice());
                oldMarketSymbol.setSellVolume(symToCompare.getSellVolume());
                oldMarketSymbol.setTurnOver(symToCompare.getTurnOver());
                oldMarketSymbol.setIndicator(symToCompare.getIndicator());
                oldMarketSymbol.setCurrent(symToCompare.getCurrent());
                oldMarketSymbol.setLowPrice(symToCompare.getLowPrice());
                oldMarketSymbol.setHighPrice(symToCompare.getHighPrice());
                //Log.d("Low high",oldMarketSymbol.getLowPrice()+oldMarketSymbol.getHighPrice());

                notifyItemChanged(position);

                if (exBuyPrice > newBuyPrice) {

                    blinkAnimationText(parent.findViewById(R.id.buy_price), colorRed);

                } else if (exBuyPrice < newBuyPrice) {

                    blinkAnimationText(parent.findViewById(R.id.buy_price), colorReallyGreen);

                } else {
                    ((TextView) parent.findViewById(R.id.buy_price)).setTextColor(Color.BLACK);
                }

                if (exSellPrice > newSellPrice) {

                    blinkAnimationText(parent.findViewById(R.id.sell_price), colorRed);

                } else if (exSellPrice < newSellPrice) {

                    blinkAnimationText(parent.findViewById(R.id.sell_price), colorReallyGreen);

                } else {
                    ((TextView) parent.findViewById(R.id.sell_price)).setTextColor(Color.BLACK);
                }

                if (exBuyVolume > newBuyVolume) {

                    blinkAnimationText(parent.findViewById(R.id.buy_volume), colorRed);

                } else if (exBuyVolume < newBuyVolume) {

                    blinkAnimationText(parent.findViewById(R.id.buy_volume), colorReallyGreen);

                } else {
                    ((TextView) parent.findViewById(R.id.buy_volume)).setTextColor(Color.BLACK);
                }

                if (exSellVolume > newSellVolume) {

                    blinkAnimationText(parent.findViewById(R.id.sell_volume), colorRed);

                } else if (exSellVolume < newSellVolume) {

                    blinkAnimationText(parent.findViewById(R.id.sell_volume), colorReallyGreen);

                } else {
                    ((TextView) parent.findViewById(R.id.sell_volume)).setTextColor(Color.BLACK);
                }

                ///////////////////////////////////////////////////////////////////

                if (newChange == 0.0 || newChange == 0.00) {

                    ((TextView) parent.findViewById(R.id.change)).setTextColor(Color.BLACK);
                    ((TextView) parent.findViewById(R.id.change_per)).setTextColor(Color.BLACK);

                } else if (exChange < newChange) {

                    blinkAnimationBgText(parent.findViewById(R.id.change), colorGreen, Color.WHITE, position, newChange);
                    blinkAnimationBgText(parent.findViewById(R.id.change_per), colorGreen, Color.WHITE, position, newChange);

                } else if (exChange > newChange) {

                    blinkAnimationBgText(parent.findViewById(R.id.change), colorRed, Color.WHITE, position, newChange);
                    blinkAnimationBgText(parent.findViewById(R.id.change_per), colorRed, Color.WHITE, position, newChange);

                } else if (exChange == newChange) {
                    //to nothing
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void blinkAnimationText(final View view, final int colorFrom) {

        final TextView textView = ((TextView) view);

        blinkAnimationText = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, textView.getCurrentTextColor());
        blinkAnimationText.setDuration(animDuration); // milliseconds
        blinkAnimationText.setRepeatCount(1);
        blinkAnimationText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((int) animator.getAnimatedValue());
            }
        });
        if (!blinkAnimationText.isRunning()) {
            blinkAnimationText.start();
        }
    }

    private void blinkAnimationBgText(final View view, final int colorFrom, final int colorTo, final int position, final float newChange) {

        final TextView textView = ((TextView) view);
//        ((TextView) view).setTextColor(colorFrom);

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


//                if (view.getId() == R.id.change || view.getId() == R.id.change_per) {
                view.setBackgroundColor(view.getDrawingCacheBackgroundColor());
//                } else {
//                ((TextView) view).setTextColor(colorFrom);
//                }

//                notifyItemChanged(position);

                if (newChange > 0) {

                    ((TextView) view).setTextColor(colorGreen);

                } else if (newChange < 0) {

                    ((TextView) view).setTextColor(colorRed);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet = new AnimatorSet();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                notifyItemChanged(position);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        if (!animatorSet.isRunning()) {
            animatorSet.playTogether(animatorText, animatorBg);
            animatorSet.start();
        }

    }

    public interface OnMarketItemClickListener {
        void onMarketItemClick(View caller, MarketSymbol mItem, int position, boolean openTrade);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public MarketSymbol mItem;
        int position;
        private ImageView ivArrow;
        private TextView symbol, market, name, current, buy_price, sell_price, buy_volume, sell_voulme, low_price, high_price, change, change_per, turn_over;
        private LinearLayout linearLayout1, linearLayout2, linearLayout3;

        public ViewHolder(View view, final OnMarketItemClickListener listener) {
            super(view);

            mView = view.findViewById(R.id.front);
            symbol = (TextView) view.findViewById(R.id.symbol);
            market = (TextView) view.findViewById(R.id.market);
            name = (TextView) view.findViewById(R.id.name);
            current = (TextView) view.findViewById(R.id.current);
            buy_price = (TextView) view.findViewById(R.id.buy_price);
            sell_price = (TextView) view.findViewById(R.id.sell_price);
            buy_volume = (TextView) view.findViewById(R.id.buy_volume);
            sell_voulme = (TextView) view.findViewById(R.id.sell_volume);
            low_price = (TextView) view.findViewById(R.id.low_price);
            high_price = (TextView) view.findViewById(R.id.high_price);
            change = (TextView) view.findViewById(R.id.change);
            change_per = (TextView) view.findViewById(R.id.change_per);
            turn_over = (TextView) view.findViewById(R.id.turn_over);
            linearLayout1 = (LinearLayout) view.findViewById(R.id.linearLayout1);
            linearLayout2 = (LinearLayout) view.findViewById(R.id.linearLayout2);
            linearLayout3 = (LinearLayout) view.findViewById(R.id.linearLayout3);
            ivArrow = view.findViewById(R.id.ivArrow);


            linearLayout1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Test", new Gson().toJson(mItem, MarketSymbol.class));

                    listener.onMarketItemClick(v, mItem, position, false);
                }
            });

            linearLayout3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Test", new Gson().toJson(mItem, MarketSymbol.class));
                    listener.onMarketItemClick(v, mItem, position, false);
                }
            });

            linearLayout2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Test", new Gson().toJson(mItem, MarketSymbol.class));
                    listener.onMarketItemClick(v, mItem, position, true);
                }
            });
        }
    }
}
