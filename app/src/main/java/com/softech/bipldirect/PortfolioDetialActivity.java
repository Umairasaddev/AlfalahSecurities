package com.softech.bipldirect;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.softech.bipldirect.Adapters.PLSummaryAdapter;
import com.softech.bipldirect.Adapters.ScriptDetailAdapter;
import com.softech.bipldirect.Models.PortfolioModel.Portfolio;
import com.softech.bipldirect.Models.PortfolioModel.PortfolioSymbol;
import com.softech.bipldirect.Models.PortfolioModel.Response;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.charts.DayAxisValueFormatter;
import com.softech.bipldirect.charts.DemoBase;
import com.softech.bipldirect.charts.Fill;
import com.softech.bipldirect.charts.MyValueFormatter;
import com.softech.bipldirect.charts.ValueFormatter;
import com.softech.bipldirect.charts.XYMarkerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class PortfolioDetialActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    @BindView(R.id.cash_text)
    TextView cash;
    @BindView(R.id.custody_text)
    TextView custody;
    @BindView(R.id.capital)
    TextView capital;
    @BindView(R.id.netProfit_btn)
    Button netProfitBtn;
    @BindView(R.id.piechart)
    PieChart pieChart;
    ScriptDetailAdapter scriptDetailAdapter;
    ArrayList<String> scriptList;
    PLSummaryAdapter plSummaryAdapter;
    ArrayList<String> plSummaryList;
    private RecyclerView summaryRecycler, scriptRecycler;
    private BarChart chart;
    private TextView tvX, tvY;
    List<PortfolioSymbol> values1;
    public static final int[] pieChartColors = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53), Color.rgb(207, 248, 246),
            Color.rgb(148, 212, 212), Color.rgb(136, 180, 187), Color.rgb(118, 174, 175),
            Color.rgb(42, 109, 130), Color.rgb(217, 80, 138), Color.rgb(254, 149, 7),
            Color.rgb(254, 247, 120), Color.rgb(106, 167, 134), Color.rgb(53, 194, 209),
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80), Color.rgb(192, 255, 140),
            Color.rgb(255, 247, 140), Color.rgb(255, 208, 140), Color.rgb(140, 234, 255),
            Color.rgb(255, 140, 157)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_detial);

        init();

        List<PortfolioSymbol> portfolios = (List<PortfolioSymbol>) getIntent().getSerializableExtra("symbols");
        Log.d("port", portfolios.toString());
        setupBarChart();
        setBarData(10, 20f);

        setValues(portfolios);

    }

    private void setupBarChart() {

        chart = (BarChart) findViewById(R.id.graphchart);
        chart.setOnChartValueSelectedListener(this);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);

        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(chart);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxis.setTypeface(tfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        ValueFormatter custom = new MyValueFormatter("$");

        YAxis leftAxis = chart.getAxisLeft();
        // leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        // rightAxis.setTypeface(tfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(chart); // For bounds control
        chart.setMarker(mv); // Set the marker to the chart
    }

    private void init() {
        scriptRecycler = (RecyclerView) findViewById(R.id.scrip_detail_recycler);
        summaryRecycler = (RecyclerView) findViewById(R.id.pl_recycler);
        pieChart = (PieChart) findViewById(R.id.piechart);

        scriptList = new ArrayList<>();
        scriptList.add("Test");
        scriptList.add("Test");
        scriptList.add("Test");

        scriptDetailAdapter = new ScriptDetailAdapter(this, scriptList);
        scriptRecycler.setLayoutManager(new LinearLayoutManager(this));
        scriptRecycler.setAdapter(scriptDetailAdapter);
        scriptDetailAdapter.notifyDataSetChanged();

        plSummaryList = new ArrayList<>();
        plSummaryList.add("Testing");
        plSummaryList.add("Testing");
        plSummaryList.add("Testing");

        plSummaryAdapter = new PLSummaryAdapter(this, scriptList);
        summaryRecycler.setLayoutManager(new LinearLayoutManager(this));
        summaryRecycler.setAdapter(plSummaryAdapter);
        plSummaryAdapter.notifyDataSetChanged();

        //  tvX = findViewById(R.id.tvXMax);
        //   tvY = findViewById(R.id.tvYMax);


    }

    private void setBarData(int count, float range) {

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            set1 = new BarDataSet(values, "The year 2017");

            set1.setDrawIcons(false);

            int startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(this, android.R.color.holo_blue_dark);
            int endColor2 = ContextCompat.getColor(this, android.R.color.holo_purple);
            int endColor3 = ContextCompat.getColor(this, android.R.color.holo_green_dark);
            int endColor4 = ContextCompat.getColor(this, android.R.color.holo_red_dark);
            int endColor5 = ContextCompat.getColor(this, android.R.color.holo_orange_dark);

            List<Fill> gradientFills = new ArrayList<>();
            gradientFills.add(new Fill(startColor1, endColor1));
            gradientFills.add(new Fill(startColor2, endColor2));
            gradientFills.add(new Fill(startColor3, endColor3));
            gradientFills.add(new Fill(startColor4, endColor4));
            gradientFills.add(new Fill(startColor5, endColor5));

            // set1.setFills(gradientFills);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);

            chart.setData(data);
        }
    }

    private final RectF onValueSelectedRectF = new RectF();

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = onValueSelectedRectF;
        chart.getBarBounds((BarEntry) e, bounds);
        MPPointF position = chart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + chart.getLowestVisibleX() + ", high: "
                        + chart.getHighestVisibleX());

        MPPointF.recycleInstance(position);
    }

    @Override
    public void onNothingSelected() {
    }

    public void setValues(final List<PortfolioSymbol> values) {


        if (values.size() > 0) {

            values1 = values;

            setUpPieChart();

        } else {
            Alert.show(this, getString(R.string.app_name), "You do not have any portfolio yet.");
        }
    }

    private void setUpPieChart() {

        // pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(60, 3, 20, 3);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawSliceText(false); // To remove slice text
        pieChart.setDrawMarkers(false); // To remove markers when click
        pieChart.setDrawEntryLabels(false); // To remove labels from piece of pie

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(70);

        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(70f);

        pieChart.setDrawCenterText(false);

        pieChart.setDrawEntryLabels(false);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false);

        // pieChart.setHighlightPerTapEnabled(true);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        if (values1 != null) {

            ArrayList<PieEntry> entries = new ArrayList<>();
            List<LegendEntry> legendEntries = new ArrayList<>();

            if (values1.size() > 0) {

                Collections.sort(values1, new Comparator<PortfolioSymbol>() {
                    @Override
                    public int compare(PortfolioSymbol t1, PortfolioSymbol t2) {

                        if (Float.parseFloat(t1.getPfWeight()) < Float.parseFloat(t2.getPfWeight())) {

                            return 1;
                        } else if (Float.parseFloat(t1.getPfWeight()) > Float.parseFloat(t2.getPfWeight())) {
                            return -1;
                        } else
                            return 0;

                    }
                });

                for (PortfolioSymbol p : values1) {
                    Log.d("VALUES", p.getPfWeight());

                }
            }


            if (values1.size() < 18) {
                for (int i = 0; i < values1.size(); i++) {

                    float percent = Float.valueOf(values1.get(i).getPfWeight());

                    if (percent > 0) {

                        entries.add(new PieEntry(percent, values1.get(i).getSymbol()));
                    }
                }

                for (int i = 0; i < entries.size(); i++) {

                    legendEntries.add(new LegendEntry(entries.get(i).getLabel(), Legend.LegendForm.SQUARE,
                            10f, 10f, null, pieChartColors[i]));
                }
            } else {
                for (int i = 0; i <= 12; i++) {

                    entries.add(new PieEntry(Float.parseFloat(values1.get(i).getPfWeight()), values1.get(i).getSymbol()));

                    legendEntries.add(new LegendEntry(entries.get(i).getLabel(), Legend.LegendForm.SQUARE,
                            10f, 10f, null, pieChartColors[i]));
                }

                float addedValues = 0;
                for (int i = 13; i < values1.size(); i++) {

                    addedValues += Float.valueOf(values1.get(i).getPfWeight());

                }

                entries.add(new PieEntry(addedValues, "others"));
                legendEntries.add(new LegendEntry("others", Legend.LegendForm.SQUARE,
                        10f, 10f, null, pieChartColors[13]));
            }

            PieDataSet dataSet = new PieDataSet(entries, "Allocation by Funds");
            dataSet.setDrawIcons(false);
            dataSet.setSliceSpace(2f);
            dataSet.setIconsOffset(new MPPointF(0, 40));
            dataSet.setSelectionShift(10f);
            //  dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            dataSet.setValueTextColor(Color.WHITE);
            //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            dataSet.setValueLinePart1OffsetPercentage(70.f);
            dataSet.setValueLinePart1Length(0.4f);
            dataSet.setValueLinePart2Length(0.7f);
            pieChart.setDrawEntryLabels(false);
            dataSet.setDrawValues(false);

            dataSet.setColors(pieChartColors);
            PieData data = new PieData(dataSet);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            pieChart.setData(data);

            // undo all highlights
            pieChart.highlightValues(null);

            pieChart.invalidate();


            Legend l = pieChart.getLegend();
            l.setCustom(legendEntries);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(false);

            l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
            l.setTextColor(Color.BLACK);
            l.setFormToTextSpace(5);
            l.setXEntrySpace(50.0f);
        }

    }
}


