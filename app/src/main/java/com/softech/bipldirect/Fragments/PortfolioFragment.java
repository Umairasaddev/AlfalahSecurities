package com.softech.bipldirect.Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.softech.bipldirect.Adapters.SearchClientListAdapter;

import com.softech.bipldirect.BuildConfig;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.PortfolioModel.*;
import com.softech.bipldirect.Adapters.PortfolioAdapter;
import com.softech.bipldirect.Models.LoginModel.LoginResponse;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.Alert;
import com.softech.bipldirect.Util.Preferences;

import net.orange_box.storebox.StoreBox;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;


public class PortfolioFragment extends Fragment implements PortfolioAdapter.OnPortofolioClickListner {

    public static Preferences preferences;
    public static LoginResponse loginResponse;
    private static int[] COLORS = new int[]{Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN};
    private EditText clientcode;
    private RecyclerView portfolio_list;
    private ListView listSearch1;
    private LinearLayout listSearch_view1;
    private PieChart pieChart;
    private BarChart barchart;
    private int maxCount = 0;
    private float maxWeight = 0f;
    ArrayList<String> clientlist;

    public static final int[] chartColors = {
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53), Color.rgb(207, 248, 246),
            Color.rgb(148, 212, 212), Color.rgb(136, 180, 187), Color.rgb(118, 174, 175),
            Color.rgb(42, 109, 130), Color.rgb(217, 80, 138), Color.rgb(254, 149, 7),
            Color.rgb(254, 247, 120), Color.rgb(106, 167, 134), Color.rgb(53, 194, 209),
            Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
            Color.rgb(191, 134, 134), Color.rgb(179, 48, 80), Color.rgb(192, 255, 140),
            Color.rgb(255, 247, 140), Color.rgb(255, 208, 140), Color.rgb(140, 234, 255),
            Color.rgb(255, 140, 157),
            Color.rgb(128, 0, 0),
            Color.rgb(165, 42, 42),
            Color.rgb(220, 20, 60),
            Color.rgb(255, 99, 71),
            Color.rgb(255, 127, 80),
            Color.rgb(240, 128, 128),
            Color.rgb(233, 150, 122),
            Color.rgb(255, 160, 122),
            Color.rgb(255, 140, 0),
            Color.rgb(184, 134, 11),
            Color.rgb(218, 165, 32),
            Color.rgb(128, 128, 0),
            Color.rgb(85, 107, 47),
            Color.rgb(107, 142, 35),
            Color.rgb(143, 188, 143),
            Color.rgb(47, 79, 79),
            Color.rgb(95, 158, 160),
            Color.rgb(72, 61, 139),
            Color.rgb(139, 69, 19),
            Color.rgb(210, 105, 30),
            Color.rgb(188, 143, 143),
            Color.rgb(112, 128, 144)
    };


    private SearchClientListAdapter searchClientListAdapter;
    List<PortfolioSymbol> values1;
    View v;
    boolean isSetInitialText = false;
    private View mCancelSearch1;

    public PortfolioFragment() {
    }

    public static PortfolioFragment newInstance() {
        return new PortfolioFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Portfolio Summary");
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio, container, false);
        bindView(view);
        portfolio_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        preferences = StoreBox.create(getActivity(), Preferences.class);
        if (MainActivity.loginResponse.getResponse().getUsertype() == 1 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 2) {

            clientcode.setText(MainActivity.loginResponse.getResponse().getClient());
            clientcode.setEnabled(false);
            ((MainActivity) requireActivity()).portfolioRequestRequest(clientcode.getText().toString());
        } else if (MainActivity.loginResponse.getResponse().getUsertype() == 0 ||
                MainActivity.loginResponse.getResponse().getUsertype() == 3) {

            clientlist = new ArrayList<String>(MainActivity.loginResponse.getResponse().getClientlist());
            searchClientListAdapter = new SearchClientListAdapter(getActivity(), clientlist);
        }

        v = view;

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listSearch1.setAdapter(searchClientListAdapter);
        clientcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isSetInitialText) {
                    isSetInitialText = false;
                    listSearch_view1.setVisibility(View.GONE);
                } else {
                    if (s.length() > 0) {

                        listSearch_view1.setVisibility(View.VISIBLE);

                        String text = clientcode.getText().toString();
                        searchClientListAdapter.filter(text);
                    } else {
                        listSearch_view1.setVisibility(View.GONE);
                    }
                }

            }
        });
        listSearch1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listSearch_view1.setVisibility(View.GONE);
                ((MainActivity) getActivity()).portfolioRequestRequest(clientlist.get(position));
                isSetInitialText = true;
                clientcode.setText(clientlist.get(position));

            }
        });

    }

    private void cancelSearch(View view) {
        listSearch_view1.setVisibility(View.GONE);
    }

    public void setValues(final List<PortfolioSymbol> values) {


        if (values.size() > 0) {

            values1 = values;
            Collections.sort(values1, (portfolioSymbol, t1) -> {
                String pfWeight1 = portfolioSymbol.getPfWeight();
                String pfWeight2 = t1.getPfWeight();

                return pfWeight1.compareTo(pfWeight2);

            });

            double totalValue = 0.00;
            double totalInvestment = 0.00;
            for (PortfolioSymbol obj : values) {
                try {
                    double amountVal = Double.parseDouble(obj.getCapGainLoss().replace(",", ""));
                    totalValue = totalValue + amountVal;

                    double totalCost = Double.parseDouble(obj.getTotalCost().replace(",", ""));
                    totalInvestment = totalInvestment + totalCost;


                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            PortfolioFooter portfolioFooter = new PortfolioFooter();
            portfolioFooter.setTotalInvestment(totalInvestment);
            String yourFormattedString = NumberFormat.getNumberInstance(Locale.UK).format(totalValue);
            portfolioFooter.setTotalProfitLoss(yourFormattedString);

            ArrayList<Portfolio> arrayMain = new ArrayList<>();
            arrayMain.addAll(values);
            arrayMain.add(portfolioFooter);
            PortfolioAdapter portfolioAdapter = new PortfolioAdapter(getActivity(), arrayMain, this);
            portfolio_list.setAdapter(portfolioAdapter);
            portfolioAdapter.notifyDataSetChanged();
            setUpPieChart();


        } else {
            Alert.show(getActivity(), getString(R.string.app_name), "You do not have any portfolio yet.");
        }
    }

    private void setUpPieChart() {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(false);
        pieChart.setDrawEntryLabels(false);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        if (values1 != null) {

            Toast.makeText(getContext(), ""+BuildConfig.FLAVOR , Toast.LENGTH_LONG).show();
           if (BuildConfig.FLAVOR.equals("Vector")){
               setYAxisRange(values1);
               setUpBarChart2(values1);
           }else {
               ArrayList<PieEntry> entries = new ArrayList<>();

               List<LegendEntry> legendEntries = new ArrayList<>();

               for (int i = 0; i < values1.size(); i++) {

                   float percent = Float.valueOf(values1.get(i).getPfWeight());

                   if (percent > 0) {

                       entries.add(new PieEntry(percent, values1.get(i).getSymbol()));
                   }
               }

               for (int i = 0; i < entries.size(); i++) {
                   legendEntries.add(new LegendEntry(entries.get(i).getLabel(), Legend.LegendForm.CIRCLE,
                           Float.NaN, Float.NaN, null, chartColors[i]));
               }

               PieDataSet dataSet = new PieDataSet(entries, "Allocation by Funds");
               dataSet.setDrawIcons(false);
               dataSet.setSliceSpace(3f);
               dataSet.setIconsOffset(new MPPointF(0, 40));
               dataSet.setSelectionShift(5f);
               dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
               dataSet.setValueTextColor(Color.BLACK);
               dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
               dataSet.setValueLinePart1OffsetPercentage(80.f);
               dataSet.setValueLinePart1Length(0.2f);
               dataSet.setValueLinePart2Length(0.4f);

               dataSet.setColors(chartColors);
               dataSet.setSelectionShift(4f);
               PieData data = new PieData(dataSet);
               data.setValueFormatter(new PercentFormatter());
               data.setValueTextSize(11f);
               data.setValueTextColor(Color.BLACK);
               pieChart.setData(data);

               // undo all highlights
               pieChart.highlightValues(null);

               pieChart.invalidate();

               Legend l = pieChart.getLegend();
               l.setCustom(legendEntries);
               l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
               l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
               l.setOrientation(Legend.LegendOrientation.VERTICAL);
               l.setDrawInside(false);
           }



        }

    }


    private void setUpBarChart2(List<PortfolioSymbol> portfolioSymbols) {


        List<BarEntry> entries = new ArrayList<>();
        List<String> labelName = new ArrayList<>();
        List<PortfolioSymbol> mListSymbols = new ArrayList<>(portfolioSymbols);

        for (int i = 0; i < mListSymbols.size(); i++) {
            labelName.add(mListSymbols.get(i).getSymbol());
            entries.add(new BarEntry((float) i, Float.parseFloat(mListSymbols.get(i).getPfWeight())));
        }

        BarDataSet barDataSet = new BarDataSet(entries, null);
        barDataSet.setColors(chartColors);
        barDataSet.setDrawValues(true);

//        //Description
        Description description = new Description();
        description.setText("My Description");
        barchart.setDescription(description);

        //Bar Data
        BarData barData = new BarData(barDataSet);
        barData.setValueTextColor(ContextCompat.getColor(requireActivity(), R.color.colorPrimary));

        //Set bar values text size
        //val textSize = requireActivity().resources.getDimensionPixelSize(R.dimen._2ssp)
        barData.setValueTextSize(10F);

        barData.setValueFormatter(new PercentFormatter());


        barData.setBarWidth(0.5f);
        barchart.setDrawBarShadow(false);
        barchart.setDrawValueAboveBar(true);
        barchart.getDescription().setEnabled(false);
        barchart.setPinchZoom(false);
        barchart.setDrawGridBackground(false);
        barchart.setData(barData);
        barchart.setVisibleXRangeMaximum(7F); // allow 10 values to be displayed at once on the x-axis, not more

        //X-axis values formatter
        XAxis xAxis = barchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelName));

        //Set position of X-axis labels
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setGranularity(1f);
        xAxis.setSpaceMin(10f);
        //Maximum 10 bars will be drawn on chart
        xAxis.setLabelCount(entries.size());


        //Y-axis values formatter
        YAxis leftAxis = barchart.getAxisLeft();

        leftAxis.setLabelCount(maxCount, true);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(2f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(maxWeight);


        //Y-axis values formatter
        YAxis rightAxis = barchart.getAxisRight();
        rightAxis.setEnabled(false);


        Legend l2 = barchart.getLegend();
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l2.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l2.setDrawInside(false);
        l2.setForm(Legend.LegendForm.NONE);
        l2.setFormSize(9f);
        l2.setTextSize(11f);
        l2.setXEntrySpace(4f);
        barchart.animateY(2000);
        barchart.invalidate();
    }

    private void setYAxisRange(List<PortfolioSymbol> mListSymbols) {


        Collections.sort(mListSymbols, (o1, o2) -> o2.getPfWeight().compareToIgnoreCase(o1.getPfWeight()));
        float highestWeight = Float.parseFloat(mListSymbols.get(0).getPfWeight());
        if (highestWeight <= 5) {
            maxWeight = 6f;
            maxCount = 6;
        } else if (highestWeight <= 10) {
            maxWeight = 12f;
            maxCount = 6;
        } else if (highestWeight <= 15) {
            maxWeight = 20f;
            maxCount = 5;
        } else if (highestWeight <= 20) {
            maxWeight = 24f;
            maxCount = 6;
        } else if (highestWeight <= 25) {
            maxWeight = 30f;
            maxCount = 6;
        } else if (highestWeight <= 30) {
            maxWeight = 35f;
            maxCount = 7;
        } else if (highestWeight <= 35) {
            maxWeight = 40f;
            maxCount = 8;
        } else if (highestWeight <= 40) {
            maxWeight = 45f;
            maxCount = 9;
        } else if (highestWeight <= 45) {
            maxWeight = 50f;
            maxCount = 10;
        } else if (highestWeight <= 50) {
            maxWeight = 54f;
            maxCount = 9;
        } else if (highestWeight <= 55) {
            maxWeight = 60f;
            maxCount = 10;
        } else if (highestWeight <= 60) {
            maxWeight = 64f;
            maxCount = 8;
        } else if (highestWeight <= 65) {
            maxWeight = 70f;
            maxCount = 7;
        } else if (highestWeight <= 70) {
            maxWeight = 72f;
            maxCount = 9;
        } else if (highestWeight <= 75) {
            maxWeight = 80f;
            maxCount = 8;
        } else if (highestWeight <= 80) {
            maxWeight = 84f;
            maxCount = 7;
        } else {
            maxWeight = 100f;
            maxCount = 10;
        }
    }


    @Override
    public void onPortfolioClick(PortfolioSymbol mItem) {
        ((MainActivity) getActivity()).showPortFolioDetail(mItem);
    }

    private void bindView(View bindSource) {
        clientcode = bindSource.findViewById(R.id.etclientcode);
        portfolio_list = bindSource.findViewById(R.id.portfolio_list);
        listSearch1 = bindSource.findViewById(R.id.search_list1);
        listSearch_view1 = bindSource.findViewById(R.id.search_list_view1);
        pieChart = bindSource.findViewById(R.id.piechart);
        barchart = bindSource.findViewById(R.id.barchart);
        if (BuildConfig.FLAVOR.equals("Vector")){
            barchart.setVisibility(View.VISIBLE);
        }else {
            barchart.setVisibility(View.GONE);
            pieChart.setVisibility(View.VISIBLE);
        }

        mCancelSearch1 = bindSource.findViewById(R.id.cancel_search1);
        mCancelSearch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSearch(v);
            }
        });
    }


}
