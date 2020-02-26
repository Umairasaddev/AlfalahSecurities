package com.softech.bipldirect.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingFragment extends Fragment {

    @BindView(R.id.text_change_pass)
    TextView tvChangePass;
    @BindView(R.id.text_change_pin)
    TextView tvChangePin;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.text_change_pass, R.id.text_change_pin})
    public void pickSetting(View view) {
        if (view.getId() == R.id.text_change_pass) {

            ((MainActivity) getActivity()).replaceFragment(ChangePassFragment.newInstance(), false, true);
        } else {
            ((MainActivity) getActivity()).replaceFragment(ChangePinFragment.newInstance(), false, true);
        }
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Settings");
        }
        super.onResume();
    }

}
