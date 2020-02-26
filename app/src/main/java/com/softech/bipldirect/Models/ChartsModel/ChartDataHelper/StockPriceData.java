package com.softech.bipldirect.Models.ChartsModel.ChartDataHelper;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Developed by Hasham.Tahir on 11/4/2016.
 */

public class StockPriceData extends StockPriceList {


    private Random rand = new Random();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMM d", Locale.UK);
    public StockPriceData(List<String> dates, List<Double> closes, List<Double> highs,
                          List<Double> lows, List<Double> opens, List<String> volumes) {

        for (int i = 0; i < dates.size(); i++) {

            double volume = 0;
            try {
                volume = Double.parseDouble(volumes.get(i).replace(",", ""));

                StockPriceItem item = new StockPriceItem();
                item.setDate(dates.get(i));
                item.setClose(closes.get(i));
                item.setHigh(highs.get(i));
                item.setLow(lows.get(i));
                item.setOpen(opens.get(i));
                item.setVolume(volume);

                this.add(item);

                Log.d("StockPriceData", "volume: " + item.getVolume()
                        + " open: " + item.getOpen()
                        + " close: " + item.getClose()
                        + " high: " + item.getHigh()
                        + " low: " + item.getLow()
                        + " date: " + item.getDate());


            } catch (NumberFormatException e) {
                e.printStackTrace();
            }


        }
    }

    public StockPriceData() {
        double open = 500, close, low, high, mod;
        double volume = 10000;
        int total = 100;
        int range = 5;
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.add(Calendar.DATE, -total);

        for (int i = 0; i < total; i++) {
            low = open - (rand.nextDouble() * range);
            high = open + (rand.nextDouble() * range);
            mod = rand.nextDouble() - 0.4;
            close = open + (mod * range);
            StockPriceItem item = new StockPriceItem();
            item.setVolume(volume);
            item.setOpen(open);
            item.setClose(close);
            item.setHigh(high);
            item.setLow(low);

            String dateString = sdf.format(date.getTime());

            item.setDate(dateString);
            this.add(item);
            open = open + (mod * range * 2);
            volume = volume + (mod * range * 100);
            date.add(Calendar.DATE, 1);
        }
    }
}