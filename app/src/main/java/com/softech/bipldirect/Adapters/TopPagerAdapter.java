package com.softech.bipldirect.Adapters;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.softech.bipldirect.Models.MarketModel.Exchange;
import com.softech.bipldirect.R;

import java.util.List;

/**
 * Developed by Hasham.Tahir on 1/28/2016.
 */
public class TopPagerAdapter extends PagerAdapter {

    private final int colorRed, colorGreen;
    List<Exchange> exchanges;
    ViewPager pager;
    TextView exc_sym, exc_curr, exc_chng, exc_turnover;
    private Context mContext;
    private long animDuration = 1600;

    public TopPagerAdapter(Context context, List<Exchange> exchanges, ViewPager pager) {
        mContext = context;
        this.exchanges = exchanges;
        this.pager = pager;
        colorRed = ContextCompat.getColor(mContext, R.color.blinkRed);
        colorGreen = ContextCompat.getColor(mContext, R.color.blinkGreen);


    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.item_top_slider, collection, false);
        collection.addView(layout);

        exc_sym = (TextView) layout.findViewById(R.id.exc_symbol);
        exc_curr = (TextView) layout.findViewById(R.id.exc_curr);
        exc_chng = (TextView) layout.findViewById(R.id.exc_change);
        exc_turnover = (TextView) layout.findViewById(R.id.exc_turnover);

        exc_sym.setText(exchanges.get(position).getSymbol());
        exc_curr.setText(exchanges.get(position).getCurrent());
        exc_chng.setText(exchanges.get(position).getChange());
        exc_turnover.setText(exchanges.get(position).getTurnOver());

        float exChange = Float.parseFloat(exchanges.get(position).getChange().replace(",", ""));

        if (exChange < 0) {

            exc_chng.setTextColor(colorRed);

        } else if (exChange > 0) {


            exc_chng.setTextColor(colorGreen);

        } else {

            exc_chng.setTextColor(Color.BLACK);
        }

        layout.setTag(position);

        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        pager.removeView((View) view);
    }

    @Override
    public int getCount() {
        return exchanges.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return null;
    }


    public void updateFeed(List<Exchange> exchangeList) {

        for (int i = 0; i < exchangeList.size(); i++) {

            Exchange symToCompare = exchangeList.get(i);

            for (int j = 0; j < exchanges.size(); j++) {

                Exchange existingSym = exchanges.get(j);

                if (symToCompare.getSymbol().equals(existingSym.getSymbol())
                    //    && symToCompare.getExchangeCode().equals(existingSym.getExchangeCode())
                        ) {


                    blink(j, existingSym, symToCompare);

                }

            }

        }
    }

    private void blink(int position, Exchange existingSym, Exchange symToCompare) {

        try {
            View view = pager.findViewWithTag(position);


            if (view != null) {

                float exChange = Float.parseFloat(existingSym.getChange().replace(",", ""));
                float newChange = Float.parseFloat(symToCompare.getChange().replace(",", ""));

                float exCurrent = Float.parseFloat(existingSym.getCurrent().replace(",", ""));
                float newCurrent = Float.parseFloat(symToCompare.getCurrent().replace(",", ""));

                exchanges.set(position, symToCompare);

                if (newChange == 0.00 || newChange == 0.00) {

                    ((TextView) view.findViewById(R.id.exc_change)).setTextColor(Color.WHITE);

                } else if (exChange < newChange) {

                    blinkAnimationBgText(view.findViewById(R.id.exc_change), colorGreen, Color.WHITE);

                } else if (exChange > newChange) {

                    blinkAnimationBgText(view.findViewById(R.id.exc_change), colorRed, Color.WHITE);

                } else if (exChange == newChange) {
                    //to nothing
                }

                if (exCurrent < newCurrent) {

                    blinkAnimationText(view.findViewById(R.id.exc_curr), colorGreen);

                } else if (exCurrent > newCurrent) {

                    blinkAnimationText(view.findViewById(R.id.exc_curr), colorRed);

                } else if (exChange == newChange) {
                    //to nothing
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
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

                notifyDataSetChanged();
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
}
