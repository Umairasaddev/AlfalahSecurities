package com.softech.bipldirect.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.softech.bipldirect.Adapters.LinksAdapter;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.LinksModel.Link;
import com.softech.bipldirect.Models.LinksModel.LinksResponse;
import com.softech.bipldirect.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LinksFragment extends Fragment {

    @BindView(R.id.links_listView)
    ListView links_listView;

    public LinksFragment() {
        // Required empty public constructor
    }


    public static LinksFragment newInstance() {
        LinksFragment fragment = new LinksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_links, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Links");
        }
        super.onResume();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).linksRequest();
    }

    public void setResult(LinksResponse result) {

        if (result != null) {

            final ArrayList<Link> linkListLOL = (ArrayList<Link>) result.getResponse().getLinks();

            links_listView.setAdapter(new LinksAdapter(getActivity(), linkListLOL));

            links_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String url = linkListLOL.get(position).getLink();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }
    }
}
