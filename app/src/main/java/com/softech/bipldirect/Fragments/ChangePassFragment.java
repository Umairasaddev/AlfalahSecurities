package com.softech.bipldirect.Fragments;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Util.EnctyptionUtils;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChangePassFragment extends Fragment {

    private EditText edit_newPass;
    private EditText edit_oldPass;
    private EditText edit_confirmPass;
    String newpassEncoded,oldPassEncoded;
    EnctyptionUtils enctyptionUtils = new EnctyptionUtils();
    private View mButtonChangePass;

    public ChangePassFragment() {
        // Required empty public constructor
    }

    public static ChangePassFragment newInstance() {
        return new ChangePassFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        bindView(view);
        return view;
    }

    private void submit(View view) {
        try {
            newpassEncoded = enctyptionUtils.encrypt(edit_newPass.getText().toString());
            oldPassEncoded = enctyptionUtils.encrypt(edit_oldPass.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String oldPassword = edit_oldPass.getText().toString();
        String newPassword = edit_newPass.getText().toString();
        String confirmPassword = edit_confirmPass.getText().toString();


        if (!TextUtils.equals(oldPassword, "")) {

            if (!TextUtils.equals(newPassword, "") && newPassword.length() >= 8) {

                if (TextUtils.equals(newPassword, confirmPassword)) {

                    ((MainActivity) requireActivity()).changePasswordRequest(oldPassEncoded, newpassEncoded);

                } else {
                    HToast.showMsg(getActivity(), "Passwords do not match.");
                }
            } else {
                HToast.showMsg(getActivity(), "New password must be greater than 8 characters.");
            }

        } else {
            HToast.showMsg(getActivity(), "Please type your old password.");
        }


    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Change Password");
        }
        super.onResume();
    }

    private void bindView(View bindSource) {
        edit_newPass = bindSource.findViewById(R.id.edittext_new_pass);
        edit_oldPass = bindSource.findViewById(R.id.edittext_old_pass);
        edit_confirmPass = bindSource.findViewById(R.id.edittext_confirm_pass);
        mButtonChangePass = bindSource.findViewById(R.id.button_change_pass);
        mButtonChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit(v);
            }
        });
    }
}
