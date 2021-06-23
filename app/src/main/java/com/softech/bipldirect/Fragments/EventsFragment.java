package com.softech.bipldirect.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.softech.bipldirect.Adapters.EventAdapter;
import com.softech.bipldirect.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EventsFragment extends Fragment {

    private ListView eventsListView;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new EventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Message Board");
        }
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        bindView(view);

        eventsListView.setAdapter(new EventAdapter(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void bindView(View bindSource) {
        eventsListView = bindSource.findViewById(R.id.events_list);
    }
}
