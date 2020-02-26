package com.softech.bipldirect.Models.ChartsModel.ChartDataHelper;

import java.util.List;

/**
 * Developed by Hasham.Tahir on 10/28/2016.
 */

public class CategoryDataSample extends CategoryDataCollection {

    public CategoryDataSample(List<Double> closes, List<String> dates) {

        for (int i = 0; i < dates.size(); i++) {

            this.add(new CategoryDataPoint(dates.get(i), closes.get(i)));
        }

        int i = 0;
        for (CategoryDataPoint dataPoint : this) {
            dataPoint.setIndex(i++);
        }
    }
}
