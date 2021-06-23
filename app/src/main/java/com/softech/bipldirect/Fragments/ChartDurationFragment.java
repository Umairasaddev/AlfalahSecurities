package com.softech.bipldirect.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.softech.bipldirect.Adapters.ChartDurationAdapter;
import com.softech.bipldirect.R;


public class ChartDurationFragment extends Fragment implements AdapterView.OnItemClickListener {

    private OnChartDurationListener mListener;

    public ChartDurationFragment() {
        // Required empty public constructor
    }

    public static ChartDurationFragment newInstance() {
        return new ChartDurationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Chart Duration");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_chart_duration, container, false);

        listView.setAdapter(new ChartDurationAdapter(getActivity(), ChartDuration.getDurations()));
        listView.setOnItemClickListener(this);
        return listView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChartDurationListener) {
            mListener = (OnChartDurationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChartDurationListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnChartDurationListener) {
            mListener = (OnChartDurationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnChartDurationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mListener != null) {
            ChartDuration[] arr = ChartDuration.getDurations();
            mListener.onChartDurationInteraction(arr[position].getValue());
        }
    }

    public interface OnChartDurationListener {
        void onChartDurationInteraction(String val);
    }

    public static class ChartDuration {

        private String title, value;

        ChartDuration(String title, String value) {
            this.title = title;
            this.value = value;
        }

        static ChartDuration[] getDurations() {

            ChartDuration[] durations = new ChartDuration[7];

            durations[0] = new ChartDuration("1 Day", "1D");
            durations[1] = new ChartDuration("1 Week", "1W");
            durations[2] = new ChartDuration("1 Month", "1M");
            durations[3] = new ChartDuration("3 Months", "3M");
            durations[4] = new ChartDuration("6 Months", "6M");
            durations[5] = new ChartDuration("1 Year", "1Y");
            durations[6] = new ChartDuration("2 Years", "2Y");

            return durations;
        }

        public String getTitle() {
            return title;
        }

        public String getValue() {
            return value;
        }
    }
}
