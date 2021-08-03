package com.softech.bipldirect.Fragments;


import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.R;


public class SettingFragment extends Fragment {

    private TextView tvChangePass;
    private TextView tvChangePin;
    private View mTextChangePass;
    private View mTextChangePin;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        bindView(view);
        return view;
    }

    private void pickSetting(View view) {
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

    private void bindView(View bindSource) {
        tvChangePass = bindSource.findViewById(R.id.text_change_pass);
        tvChangePin = bindSource.findViewById(R.id.text_change_pin);
        mTextChangePass = bindSource.findViewById(R.id.text_change_pass);
        mTextChangePin = bindSource.findViewById(R.id.text_change_pin);
        mTextChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickSetting(v);
            }
        });
        mTextChangePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickSetting(v);
            }
        });
    }
}
