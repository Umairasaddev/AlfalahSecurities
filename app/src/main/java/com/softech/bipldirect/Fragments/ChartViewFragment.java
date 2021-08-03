package com.softech.bipldirect.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.infragistics.controls.AbsoluteVolumeOscillatorIndicator;
import com.infragistics.controls.AccumulationDistributionIndicator;
import com.infragistics.controls.AverageTrueRangeIndicator;
import com.infragistics.controls.BollingerBandWidthIndicator;
import com.infragistics.controls.BollingerBandsOverlay;
import com.infragistics.controls.CategorySeries;
import com.infragistics.controls.CategoryXAxis;
import com.infragistics.controls.ChaikinVolatilityIndicator;
import com.infragistics.controls.ChartDataContext;
import com.infragistics.controls.ChartToolTipAdapter;
import com.infragistics.controls.ColumnSeries;
import com.infragistics.controls.CustomContentUpdateInfo;
import com.infragistics.controls.DataChartView;
import com.infragistics.controls.FinancialPriceSeries;
import com.infragistics.controls.FinancialSeries;
import com.infragistics.controls.ForceIndexIndicator;
import com.infragistics.controls.IndicatorDisplayType;
import com.infragistics.controls.LineSeries;
import com.infragistics.controls.MedianPriceIndicator;
import com.infragistics.controls.MovingAverageConvergenceDivergenceIndicator;
import com.infragistics.controls.NumericYAxis;
import com.infragistics.controls.OnBalanceVolumeIndicator;
import com.infragistics.controls.PercentagePriceOscillatorIndicator;
import com.infragistics.controls.PercentageVolumeOscillatorIndicator;
import com.infragistics.controls.PriceChannelOverlay;
import com.infragistics.controls.PriceDisplayType;
import com.infragistics.controls.RateOfChangeAndMomentumIndicator;
import com.infragistics.controls.RelativeStrengthIndexIndicator;
import com.infragistics.controls.StandardDeviationIndicator;
import com.infragistics.controls.TrendLineType;
import com.infragistics.controls.TypicalPriceIndicator;
import com.infragistics.graphics.SolidColorBrush;
import com.softech.bipldirect.Models.ChartsModel.ChartDataHelper.CategoryDataPoint;
import com.softech.bipldirect.Models.ChartsModel.ChartsResponse;
import com.softech.bipldirect.Models.ChartsModel.ChartDataHelper.CategoryDataSample;
import com.softech.bipldirect.Models.ChartsModel.ChartDataHelper.StockPriceData;
import com.softech.bipldirect.Models.ChartsModel.ChartDataHelper.StockPriceItem;
import com.softech.bipldirect.R;

import java.text.DecimalFormat;


public class ChartViewFragment extends Fragment {

    private TextView textViewSymbolName;
    private FrameLayout chartContainer1;
    private FrameLayout chartContainer2;
    private OnChartViewListener mListener;
    private ChartTypeFragment.Type chartType;
    private String symbolName;
    private ChartsResponse result;
    private View mIndicatorBtn;
    private View mOverlaysBtn;

    public ChartViewFragment() {
        // Required empty public constructor
    }

    public static ChartViewFragment newInstance(ChartTypeFragment.Type type, String symbol) {

        ChartViewFragment fragment = new ChartViewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("type", type);
        bundle.putString("symbol", symbol);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        chartType = (ChartTypeFragment.Type) bundle.getSerializable("type");
        symbolName = bundle.getString("symbol");
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Chart");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart_view, container, false);
        bindView(view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (symbolName != null) {
            textViewSymbolName.setText(symbolName);
        }

        if (mListener != null) {
            mListener.onChartViewInteraction(0);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChartViewListener) {
            mListener = (OnChartViewListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChartDurationListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnChartViewListener) {
            mListener = (OnChartViewListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChartViewListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setResult(ChartsResponse result) {

        this.result = result;

        chartContainer1.removeAllViews();
        chartContainer2.removeAllViews();

        if (chartType != null) {

            switch (chartType) {

                case CHART_LINE: {

                    addLineChart(result);

                }
                break;

                case CHART_BAR: {

                    addBarChart(result);

                }
                break;

                case CHART_CANDLE_STICK: {

                    addCandleStickChart(result);

                }
                break;
//
//                case CHART_FORCE_INDEX: {
//
//                    addForceIndexChart(result);
//
//                }
//                break;
//
//                case CHART_BOLLINGER: {
//
//                    addBollingerOverlayChart(result);
//
//                }
//                break;
//
//                case CHART_MEDIAN_PRICE: {
//
//                    addMedianPriceChart(result);
//
//                }
//                break;
//
//                case CHART_TRUE_RANGE: {
//
//                    addTrueRangeChart(result);
//
//                }
//                break;
//
//                case CHART_BOLLINGER_WIDTH: {
//
//                    addBollingerWidthChart(result);
//
//                }
//                break;

                case CHART_HYBRID: {

                    addHybridChart1(result);

                    addHybridChart2(result);

                }
                break;
            }

        }

    }

    private void addHybridChart1(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        yAxis.setMinimumValue(-75);
        yAxis.setMaximumValue(425);
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.OHLC);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.getAxisAt(0).setLabelsVisible(false);

        chart.addSeries(series);
        chartContainer1.addView(chart);


    }

    private void addBollingerWidthChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        BollingerBandWidthIndicator indicator = new BollingerBandWidthIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        indicator.getXAxis().setLabelsVisible(true);

        indicator.setIgnoreFirst(10);
        indicator.setTransitionDuration(1);

        SolidColorBrush brushNegative = new SolidColorBrush();
        brushNegative.setColor(Color.RED);
        indicator.setNegativeBrush(brushNegative);
        indicator.setBrush(brushNegative);

        SolidColorBrush brushXAxis = new SolidColorBrush();
        brushXAxis.setColor(Color.parseColor("#323232"));

        indicator.getXAxis().setMajorStroke(brushXAxis);
        indicator.getYAxis().setMajorStroke(brushXAxis);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addTrueRangeChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.OHLC);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        AverageTrueRangeIndicator indicator = new AverageTrueRangeIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setTrendLineThickness(2.0);
        indicator.setTrendLineType(TrendLineType.EXPONENTIAL_AVERAGE);
        indicator.setTrendLinePeriod(5);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addMedianPriceChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        MedianPriceIndicator indicator = new MedianPriceIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addBollingerOverlayChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        BollingerBandsOverlay indicator = new BollingerBandsOverlay();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        indicator.getXAxis().setLabelsVisible(true);

        indicator.setIgnoreFirst(10);
        indicator.setTransitionDuration(1);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addPriceChannelOverlayChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        PriceChannelOverlay indicator = new PriceChannelOverlay();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        indicator.getXAxis().setLabelsVisible(true);

//        indicator.setIgnoreFirst(10);
//        indicator.setTransitionDuration(1);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addForceIndexChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        ForceIndexIndicator indicator = new ForceIndexIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        indicator.getXAxis().setLabelsVisible(true);

        indicator.setIgnoreFirst(10);
        indicator.setTransitionDuration(1);

        SolidColorBrush brushNegative = new SolidColorBrush();
        brushNegative.setColor(Color.RED);
        indicator.setNegativeBrush(brushNegative);
        indicator.setBrush(brushNegative);

        SolidColorBrush brushXAxis = new SolidColorBrush();
        brushXAxis.setColor(Color.parseColor("#323232"));

        indicator.getXAxis().setMajorStroke(brushXAxis);
        indicator.getYAxis().setMajorStroke(brushXAxis);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addCandleStickChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chartContainer1.addView(chart);
    }

    private void addBarChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        CategoryDataSample data =
                new CategoryDataSample(result.getResponse().getCloses(), result.getResponse().getDates());

        NumericYAxis yAxis = new NumericYAxis();
        yAxis.setLabelTextSize(10.0f);

        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Category");
        xAxis.setLabelTextSize(10.0f);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        ColumnSeries series = new ColumnSeries();
        series.setDataSource(data);
        series.setValueMemberPath("Value");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chartContainer1.addView(chart);
    }

    private void addLineChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        CategoryDataSample data =
                new CategoryDataSample(result.getResponse().getCloses(), result.getResponse().getDates());

        NumericYAxis yAxis = new NumericYAxis();
        yAxis.setLabelTextSize(10.0f);

        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Category");
        xAxis.setLabelTextSize(10.0f);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        LineSeries series = new LineSeries();
        series.setDataSource(data);
        series.setValueMemberPath("Value");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chartContainer1.addView(chart);
    }

    private void addHybridChart2(ChartsResponse result) {

        chartContainer2.setVisibility(View.VISIBLE);

        DataChartView chart = new DataChartView(chartContainer2.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        yAxis.setMinimumValue(-75);
        yAxis.setMaximumValue(425);
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        BollingerBandsOverlay indicator = new BollingerBandsOverlay();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        indicator.getXAxis().setLabelsVisible(true);
        indicator.getXAxis().setLabelExtent(45);
        indicator.getXAxis().setLabelAngle(45);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(indicator);

        chart.addSeries(indicator);
        chartContainer2.addView(chart);


    }

    private void addAccumulationIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        AccumulationDistributionIndicator indicator = new AccumulationDistributionIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        indicator.setTrendLineType(TrendLineType.MODIFIED_AVERAGE);
        indicator.setTrendLinePeriod(5);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addAbsoluteVolumeChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        AbsoluteVolumeOscillatorIndicator indicator = new AbsoluteVolumeOscillatorIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addChaikinVolatilityChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        ChaikinVolatilityIndicator indicator = new ChaikinVolatilityIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addMovingAverageConvergenceDivergenceIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        MovingAverageConvergenceDivergenceIndicator indicator = new MovingAverageConvergenceDivergenceIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.AREA);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addPercentagePriceOscillatorIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        PercentagePriceOscillatorIndicator indicator = new PercentagePriceOscillatorIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.LINE);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addPercentageVolumeOscillatorIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        PercentageVolumeOscillatorIndicator indicator = new PercentageVolumeOscillatorIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.LINE);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addPositiveVolumeIndexIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        PercentageVolumeOscillatorIndicator indicator = new PercentageVolumeOscillatorIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.LINE);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addStandardDeviationIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        StandardDeviationIndicator indicator = new StandardDeviationIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.LINE);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addRateOfChangeAndMomentumIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        RateOfChangeAndMomentumIndicator indicator = new RateOfChangeAndMomentumIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.LINE);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addOnBalanceVolumeIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        OnBalanceVolumeIndicator indicator = new OnBalanceVolumeIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.LINE);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addTypicalPriceIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        TypicalPriceIndicator indicator = new TypicalPriceIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.LINE);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }

    private void addRelativeStrengthIndexIndicatorChart(ChartsResponse result) {

        DataChartView chart = new DataChartView(chartContainer1.getContext());
        chart.setHorizontalZoomable(true);
        chart.setVerticalZoomable(false);

        StockPriceData data = new StockPriceData(result.getResponse().getDates()
                , result.getResponse().getCloses(), result.getResponse().getHighs()
                , result.getResponse().getLows(), result.getResponse().getOpens()
                , result.getResponse().getVolumes());

        NumericYAxis yAxis = new NumericYAxis();
        CategoryXAxis xAxis = new CategoryXAxis();
        xAxis.setDataSource(data);
        xAxis.setLabel("Date");

        FinancialPriceSeries series = new FinancialPriceSeries();
        series.setDisplayType(PriceDisplayType.CANDLESTICK);
        series.setDataSource(data);
        series.setOpenMemberPath("Open");
        series.setHighMemberPath("High");
        series.setLowMemberPath("Low");
        series.setCloseMemberPath("Close");
        series.setVolumeMemberPath("Volume");
        series.setXAxis(xAxis);
        series.setYAxis(yAxis);

        RelativeStrengthIndexIndicator indicator = new RelativeStrengthIndexIndicator();
        indicator.setXAxis(xAxis);
        indicator.setYAxis(yAxis);
        indicator.setDataSource(data);
        indicator.setThickness(2.0);
        indicator.setOpenMemberPath("Open");
        indicator.setHighMemberPath("High");
        indicator.setLowMemberPath("Low");
        indicator.setCloseMemberPath("Close");
        indicator.setVolumeMemberPath("Volume");
        indicator.setDisplayType(IndicatorDisplayType.AREA);

        chart.addAxis(xAxis);
        chart.addAxis(yAxis);

        setToolTipOnChart(series);

        chart.addSeries(series);
        chart.addSeries(indicator);
        chartContainer1.addView(chart);
    }


    private void setToolTipOnChart(CategorySeries series) {

        final Activity activity = getActivity();

        ChartToolTipAdapter tip = new ChartToolTipAdapter(activity) {
            @SuppressLint("NewApi")
            @Override
            public View getView(ChartDataContext chartContext, View existingView, CustomContentUpdateInfo updateInfo) {
                TextView tv;
                LinearLayout lin = (LinearLayout) existingView;
                if (lin == null) {
                    lin = new LinearLayout(activity);
                    lin.setBackgroundColor(Color.WHITE);
                    existingView = lin;
                    tv = new TextView(activity);
                    tv.setPadding(4, 4, 4, 4);

                    lin.addView(tv);
                    //existingView = tv;
                } else {
                    tv = (TextView) ((LinearLayout) existingView).getChildAt(0);
                }

                String valueText = "";
                CategoryDataPoint it = (CategoryDataPoint) chartContext.getItem();
                if (it != null) {
                    String val = new DecimalFormat("#.00").format(it.getValue());
                    if (val == null) {
                        val = "NaN";
                    }
                    tv.setText(val);
                }

                return existingView;
            }
        };

        series.setToolTip(tip);
    }

    private void setToolTipOnChart(FinancialSeries series) {

        final Activity activity = getActivity();

        ChartToolTipAdapter tip = new ChartToolTipAdapter(activity) {
            @SuppressLint("NewApi")
            @Override
            public View getView(ChartDataContext chartContext, View existingView, CustomContentUpdateInfo updateInfo) {

                TextView tvOpen, tvClose, tvHigh, tvLow, tvVolume;
                LinearLayout lin = (LinearLayout) existingView;
                if (lin == null) {
                    lin = new LinearLayout(activity);
                    lin.setOrientation(LinearLayout.VERTICAL);
                    lin.setBackgroundColor(Color.WHITE);
                    existingView = lin;
                    tvOpen = new TextView(activity);
                    tvClose = new TextView(activity);
                    tvHigh = new TextView(activity);
                    tvLow = new TextView(activity);
                    tvVolume = new TextView(activity);
                    tvOpen.setPadding(4, 4, 4, 4);
                    tvClose.setPadding(4, 4, 4, 4);
                    tvHigh.setPadding(4, 4, 4, 4);
                    tvLow.setPadding(4, 4, 4, 4);
                    tvVolume.setPadding(4, 4, 4, 4);

                    lin.addView(tvOpen);
                    lin.addView(tvClose);
                    lin.addView(tvHigh);
                    lin.addView(tvLow);
                    lin.addView(tvVolume);
                } else {
                    tvOpen = (TextView) ((LinearLayout) existingView).getChildAt(0);
                    tvClose = (TextView) ((LinearLayout) existingView).getChildAt(1);
                    tvHigh = (TextView) ((LinearLayout) existingView).getChildAt(2);
                    tvLow = (TextView) ((LinearLayout) existingView).getChildAt(3);
                    tvVolume = (TextView) ((LinearLayout) existingView).getChildAt(4);
                }

                StockPriceItem it = (StockPriceItem) chartContext.getItem();
                if (it != null) {
                    String open = new DecimalFormat("#.00").format(it.getOpen());
                    if (open == null) {
                        open = "NaN";
                    }
                    String close = new DecimalFormat("#.00").format(it.getClose());
                    if (close == null) {
                        close = "NaN";
                    }
                    String high = new DecimalFormat("#.00").format(it.getHigh());
                    if (high == null) {
                        high = "NaN";
                    }
                    String low = new DecimalFormat("#.00").format(it.getLow());
                    if (low == null) {
                        low = "NaN";
                    }
                    String volume = new DecimalFormat("#.00").format(it.getVolume());
                    if (volume == null) {
                        volume = "NaN";
                    }

                    tvOpen.setText("Open: " + open);
                    tvClose.setText("Close: " + close);
                    tvHigh.setText("High: " + high);
                    tvLow.setText("Low: " + low);
                    tvVolume.setText("Volume: " + volume);
                }

                return existingView;
            }
        };

        series.setToolTip(tip);
    }

    private void showIndicatorsChooser() {

        final String[] indicatorNames = {
                "Accumulation Distribution Indicator", "Absolute Volume Indicator",
                "Chaikin Volatility Indicator", "Moving Average Convergence Divergence Indicator",
                "Percentage Price Oscillator Indicator", "Percentage Volume Oscillator Indicator",
                "Positive Volume Index Indicator", "Standard Deviation Indicator", "Rate Of Change And Momentum",
                "On Balance Volume indicator", "Typical Price Indicator", "Relative Strength Index Indicator",
                "Force Index Indicator", "Median Price Indicator", "Average Two Range Indicator",
                "Bollinger Band Width Indicator"};

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Edit Graph");
        alert.setItems(indicatorNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (result != null) {

                    chartContainer1.removeAllViews();
                    chartContainer2.removeAllViews();

                    Log.d("indicatorNames", "indicatorNames: " + indicatorNames[which]);

                    switch (which) {

                        case 0: {
                            addAccumulationIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addAccumulationIndicatorChart");
                        }
                        break;
                        case 1: {
                            addAbsoluteVolumeChart(result);
                            Log.d("indicatorNames", "chart Added: addAbsoluteVolumeChart");
                        }
                        break;
                        case 2: {
                            addChaikinVolatilityChart(result);
                            Log.d("indicatorNames", "chart Added: addChaikinVolatilityChart");
                        }
                        break;
                        case 3: {
                            addMovingAverageConvergenceDivergenceIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addMovingAverageConvergenceDivergenceIndicatorChart");
                        }
                        break;
                        case 4: {
                            addPercentagePriceOscillatorIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addPercentagePriceOscillatorIndicatorChart");
                        }
                        break;
                        case 5: {
                            addPercentageVolumeOscillatorIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addPercentageVolumeOscillatorIndicatorChart");
                        }
                        break;
                        case 6: {
                            addPositiveVolumeIndexIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addPositiveVolumeIndexIndicatorChart");
                        }
                        break;
                        case 7: {
                            addStandardDeviationIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addStandardDeviationIndicatorChart");
                        }
                        break;
                        case 8: {
                            addRateOfChangeAndMomentumIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addRateOfChangeAndMomentumIndicatorChart");
                        }
                        break;
                        case 9: {
                            addOnBalanceVolumeIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addOnBalanceVolumeIndicatorChart");
                        }
                        break;
                        case 10: {
                            addTypicalPriceIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addTypicalPriceIndicatorChart");
                        }
                        break;
                        case 11: {
                            addRelativeStrengthIndexIndicatorChart(result);
                            Log.d("indicatorNames", "chart Added: addRelativeStrengthIndexIndicatorChart");
                        }
                        break;
                        case 12: {
                            addForceIndexChart(result);
                            Log.d("indicatorNames", "chart Added: addForceIndexChart");
                        }
                        break;
                        case 13: {
                            addMedianPriceChart(result);
                            Log.d("indicatorNames", "chart Added: addMedianPriceChart");
                        }
                        break;
                        case 14: {
                            addTrueRangeChart(result);
                            Log.d("indicatorNames", "chart Added: addTrueRangeChart");
                        }
                        break;
                        case 15: {
                            addBollingerWidthChart(result);
                            Log.d("indicatorNames", "chart Added: addBollingerWidthChart");
                        }
                        break;
                    }

                }
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }

    private void showOverlaysChooser() {

        final String[] overlayNames = {"Bollinger Band Overlay", "Price Channel Overlay"};

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Edit Graph");
        alert.setItems(overlayNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (result != null) {

                    chartContainer1.removeAllViews();
                    chartContainer2.removeAllViews();

                    Log.d("overlayNames", "overlayNames: " + overlayNames[which]);

                    switch (which) {

                        case 0: {
                            addBollingerOverlayChart(result);
                            Log.d("overlayNames", "chart Added: addAccumulationIndicatorChart");
                        }
                        break;
                        case 1: {
                            addPriceChannelOverlayChart(result);
                            Log.d("overlayNames", "chart Added: addPriceChannelOverlayChart");
                        }
                        break;
                    }

                }
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();

    }

    private void bindView(View bindSource) {
        textViewSymbolName = bindSource.findViewById(R.id.symbolName);
        chartContainer1 = bindSource.findViewById(R.id.chart_container1);
        chartContainer2 = bindSource.findViewById(R.id.chart_container2);
        mIndicatorBtn = bindSource.findViewById(R.id.indicator_btn);
        mOverlaysBtn = bindSource.findViewById(R.id.overlays_btn);
        mIndicatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIndicatorsChooser();
            }
        });
        mOverlaysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOverlaysChooser();
            }
        });
    }

    public interface OnChartViewListener {
        void onChartViewInteraction(int action);
    }
}
