package com.softech.bipldirect.Models.ChartsModel.ChartDataHelper;

/**
 * Developed by Hasham.Tahir on 10/28/2016.
 */

public class CategoryDataPoint {

    private int _index;
    private String _category;
    private double _value;
    private double _high;
    private double _low;

    public CategoryDataPoint(String category, double value, double high, double low) {
        _category = category;
        _value = value;
        _high = high;
        _low = low;
    }

    public CategoryDataPoint(String category, double value) {
        _category = category;
        _value = value;
    }

    public int getIndex() {
        return _index;
    }

    public int setIndex(int value) {
        _index = value;
        return _index;
    }

    public String getCategory() {
        return _category;
    }

    public String setCategory(String category) {
        _category = category;
        return category;
    }

    public double getValue() {
        return _value;
    }

    public double setValue(double value) {
        _value = value;
        return _value;
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
}
