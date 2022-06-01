package com.softech.bipldirect.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.softech.bipldirect.Adapters.OrderStatsAdapter;
import com.softech.bipldirect.MainActivity;
import com.softech.bipldirect.Models.OrderStatsModel.OrderStatsResponse;
import com.softech.bipldirect.Models.OrderStatsModel.OrdersList;
import com.softech.bipldirect.R;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.Util.Util;
import com.softech.bipldirect.callBack.OnOrderDeleteRequest;

import java.util.ArrayList;
import java.util.List;


public class OrderStatsFragment extends Fragment implements OrderStatsAdapter.OnListItemClickListener, OnOrderDeleteRequest {

    private static int postionToRemove = -1;
    private RecyclerView order_status_list;
    private Button order;
    private Button trade;
    private TextView textViewError;
    OrderStatsResponse result;
    ArrayList<OrdersList> OrdersList = new ArrayList();
    ArrayList<OrdersList> TradeList = new ArrayList();
    int tabSelected = 1;
    private OrderStatsAdapter adapter;
    private View mOrderbtn;
    private View mTradebtn;
    OnOrderDeleteRequest onOrderDeleteRequest;

    public OrderStatsFragment() {
        // Required empty public constructor
    }

    public static OrderStatsFragment newInstance() {
        return new OrderStatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onOrderDeleteRequest = this;
    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Order Status");
        }

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status, container, false);
        bindView(view);

        return view;
    }

    private void orderPressed() {
        trade.setBackgroundColor(requireActivity().getResources().getColor(R.color.greyDarkBar));
        order.setBackgroundColor(requireActivity().getResources().getColor(R.color.colorPrimary));
        tabSelected = 1;
        setAdapter();
    }

    private void tradePressed() {
        trade.setBackgroundResource(R.drawable.selected);
        order.setBackgroundResource(R.drawable.unselected);
        order.setBackgroundColor(requireActivity().getResources().getColor(R.color.greyDarkBar));
        trade.setBackgroundColor(requireActivity().getResources().getColor(R.color.colorPrimary));
        tabSelected = 2;
        setAdapter();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        order_status_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        order_status_list.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));

        ((MainActivity) requireActivity()).orderStatusRequest();

        textViewError.setText("No orders or trades available to display.");

    }

    public void setResult(OrderStatsResponse result) {

        this.result = result;

        if (result != null) {

            List<OrdersList> listMain = result.getResponse().getOrdersList();

            if (listMain != null && listMain.size() > 0) {


                OrdersList.clear();
                TradeList.clear();


                for (OrdersList obj : listMain) {

                    if (obj.getIdentifier().equals("TRD")) {
                        TradeList.add(obj);
                    } else if (obj.getIdentifier().equals("ORD")) {
                        OrdersList.add(obj);
                    }
                }

                setAdapter();

            } else {
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText("No orders available to display.");
            }
        }

    }

    public void setAdapter(){
        textViewError.setVisibility(View.GONE);

        if (tabSelected == 1 && !(OrdersList.size() > 0)) {

            textViewError.setVisibility(View.VISIBLE);
            textViewError.setText("There is no outstanding Order.");

        }
        else if (tabSelected == 1 && !(OrdersList.size() > 0) && !(TradeList.size() > 0)) {

            textViewError.setVisibility(View.VISIBLE);
            textViewError.setText("No orders or trades available to display.");

        }
        else if (tabSelected == 2 && !(TradeList.size() > 0)) {

            textViewError.setVisibility(View.VISIBLE);
            textViewError.setText("There is no Trade");
        }

        if (tabSelected == 1) {
            adapter = new OrderStatsAdapter(requireActivity(), OrdersList, this);
        } else {
            adapter = new OrderStatsAdapter(requireActivity(), TradeList, this);
        }

        order_status_list.setAdapter(adapter);

    }

    @Override
    public void onListItemClick(View caller, final OrdersList mItem, final int position) {

        if (mItem.getIdentifier().equals("ORD")) {

            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getActivity());
            alert.setTitle("Cancel");
            alert.setMessage("Do you want to cancel order for " + mItem.getSymbol() + "?");
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    proceedTradePopup(mItem);
                    postionToRemove = position;


                }
            });

            alert.show();

        }
    }

    private void proceedTradePopup(final OrdersList mItem) {

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_input_pin, null);
        final EditText editText = (EditText) dialogView.findViewById(R.id.editText2);
        final CheckBox checkBox = (CheckBox) dialogView.findViewById(R.id.checkBox);

        if (MainActivity.preferences.getRememberPin()) {

            editText.setText(MainActivity.loginResponse.getResponse().getPinCode());
            checkBox.setChecked(true);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("PROCEED", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if (checkBox.isChecked()) {
                            MainActivity.preferences.setRememberPin(true);
                        } else {
                            MainActivity.preferences.setRememberPin(false);
                        }

                        if (editText.getText().toString().equals(MainActivity.loginResponse.getResponse().getPinCode())) {
                            ((MainActivity) requireActivity()).cancelOrderRequest(mItem, onOrderDeleteRequest);
                        } else {
                            HToast.showMsg(getActivity(), "Invalid Pin Code.");
                        }

                        Util.hideKeyboard(requireActivity());
                        dialog.dismiss();


                    }
                }

        );
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }

        );
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("Please provide your pin code.");
        builder.setView(dialogView);
        try {
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindView(View bindSource) {
        order_status_list = bindSource.findViewById(R.id.order_status_list);
        order = bindSource.findViewById(R.id.orderbtn);
        trade = bindSource.findViewById(R.id.tradebtn);
        textViewError = bindSource.findViewById(R.id.textView8);
        mOrderbtn = bindSource.findViewById(R.id.orderbtn);
        mTradebtn = bindSource.findViewById(R.id.tradebtn);
        mOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderPressed();
            }
        });
        mTradebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tradePressed();
            }
        });
    }

    @Override
    public void onOrderDeleteRequestResponse() {
        if (postionToRemove > -1) {
            adapter.removeAt(postionToRemove);
        }
    }
}
