package com.softech.bipldirect.Models.ChartsModel.ChartDataHelper;

/**
 * Developed by Hasham.Tahir on 11/4/2016.
 */

public class StockPriceItem {
    private double _volume;
    private double _open;
    private double _close;
    private double _high;
    private double _low;
    private String _date;

    public double getVolume() {
        return _volume;
    }

    public double setVolume(double value) {
        _volume = value;
        return _volume;
    }

    public double getOpen() {
        return _open;
    }

    public double setOpen(double value) {
        _open = value;
        return _open;
    }

    public double getClose() {
        return _close;
    }

    public double setClose(double value) {
        _close = value;
        return _close;
    }

    public double getHigh() {
        return _high;
    }

    public double setHigh(double value) {
        _high = value;
        return _high;
    }

    public double getLow() {
        return _low;
    }

    public double setLow(double value) {
        _low = value;
        return _low;
    }

    public String getDate() {
        return _date;
    }

    public String setDate(String value) {
        _date = value;
        return _date;
    }
}
