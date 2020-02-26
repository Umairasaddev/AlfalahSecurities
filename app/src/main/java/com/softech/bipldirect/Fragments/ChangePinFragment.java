package com.softech.bipldirect.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePinFragment extends Fragment {

    @BindView(R.id.edittext_new_pin)
    EditText edit_newPin;
    @BindView(R.id.edittext_old_pin)
    EditText edit_oldPin;
    @BindView(R.id.edittext_confirm_pin)
    EditText edit_confirmPin;

    public ChangePinFragment() {
        // Required empty public constructor
    }

    public static ChangePinFragment newInstance() {
        return new ChangePinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pin, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.button_change_pin)
    public void submit(View view) {


        String oldPin = edit_oldPin.getText().toString();
        String newPin = edit_newPin.getText().toString();
        String confirmPin = edit_confirmPin.getText().toString();


        if (!TextUtils.equals(oldPin, "")) {

            if (!TextUtils.equals(newPin, "") && newPin.length() == 4) {

                if (TextUtils.equals(newPin, confirmPin)) {

                    ((MainActivity) getActivity()).changePinRequest(oldPin, newPin);

                } else {
                    HToast.showMsg(getActivity(), "Pins do not match.");
                }
            } else {
                HToast.showMsg(getActivity(), "New Pin must be equal to 4 characters.");
            }

        } else {
            HToast.showMsg(getActivity(), "Please type your old pin.");
        }


    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Change Pin");
        }
        super.onResume();
    }

}
