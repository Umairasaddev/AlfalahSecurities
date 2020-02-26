package com.softech.bipldirect.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.softech.bipldirect.Util.DividerItemDecoration;
import com.softech.bipldirect.Util.HSnackBar;
import com.softech.bipldirect.Util.HToast;
import com.softech.bipldirect.Util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Developed by Hasham.Tahir on 2/1/2016.
 */
public class OrderStatsFragment extends Fragment implements OrderStatsAdapter.OnListItemClickListener {

    private static int postionToRemove = -1;
    @BindView(R.id.order_status_list)
    RecyclerView order_status_list;
    @BindView(R.id.orderbtn)
    Button order;
    @BindView(R.id.tradebtn)
    Button trade;
    @BindView(R.id.textView8)
    TextView textViewError;
    OrderStatsResponse result;
    ArrayList<OrdersList> OrdersList, TradeList;
    OnOrderDeleteRequest deleteListener;
    int tabSelected = 1;
    private OrderStatsAdapter adapter;

    public OrderStatsFragment() {
        // Required empty public constructor
    }

    public static OrderStatsFragment newInstance() {
        return new OrderStatsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (toolbar != null) {
            toolbar.setTitle("Order Status");
        }

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_status, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @OnClick(R.id.orderbtn)
    public void orderPressed() {
        trade.setBackgroundResource(R.drawable.unselected);
        order.setBackgroundResource(R.drawable.selected);

        tabSelected = 1;
        setResult(result);
    }

    @OnClick(R.id.tradebtn)
    public void tradePressed() {
        trade.setBackgroundResource(R.drawable.selected);
        order.setBackgroundResource(R.drawable.unselected);

        tabSelected = 2;
        setResult(result);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        order_status_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        order_status_list.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        ((MainActivity) getActivity()).orderStatusRequest();

        textViewError.setText("No orders or trades available to display.");

    }

    public void setResult(OrderStatsResponse result) {

        this.result = result;

        if (result != null) {

            List<OrdersList> listMain = result.getResponse().getOrdersList();

            if (listMain != null && listMain.size() > 0) {


                OrdersList = new ArrayList<>();
                TradeList = new ArrayList<>();


                for (OrdersList obj : listMain) {

                    if (obj.getIdentifier().equals("TRD")) {
                        TradeList.add(obj);
                    } else if (obj.getIdentifier().equals("ORD")) {
                        OrdersList.add(obj);
                    }
                }
                textViewError.setVisibility(View.GONE);

                if (tabSelected == 1 && !(OrdersList.size() > 0)) {

                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("There is no outstanding Order.");

                } else if (tabSelected == 1 && !(OrdersList.size() > 0) && !(TradeList.size() > 0)) {

                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("No orders or trades available to display.");

                } else if (tabSelected == 2 && !(TradeList.size() > 0)) {

                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("There is no Trade");
                }


                if (tabSelected == 1) {
                    adapter = new OrderStatsAdapter(getActivity(), OrdersList, this);
                    order_status_list.setAdapter(adapter);

                } else {
                    adapter = new OrderStatsAdapter(getActivity(), TradeList, this);
                    order_status_list.setAdapter(adapter);

                }
            } else {
                textViewError.setVisibility(View.VISIBLE);
                textViewError.setText("No orders available to display.");
            }
        }

//        adapter.notifyDataSetChanged();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOrderDeleteRequest) {
            deleteListener = (OnOrderDeleteRequest) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderDeleteRequest");
        }

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnOrderDeleteRequest) {
            deleteListener = (OnOrderDeleteRequest) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderDeleteRequest");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        deleteListener = null;
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


                            if (deleteListener != null) {

                                deleteListener.onOrderDeleteRequest(mItem);

                            }

                        } else {
                            HToast.showMsg(getActivity(), "Invalid Pin Code.");
                        }

                        Util.hideKeyboard(getActivity());
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

    public void removeItem() {
        if (postionToRemove > -1) {

            OrdersList.remove(postionToRemove);

            adapter = new OrderStatsAdapter(getActivity(), OrdersList, this);

            order_status_list.setAdapter(adapter);

            HSnackBar.showMsg(order_status_list, "Successfully removed.");

            ((MainActivity) getActivity()).orderStatusRequest();
        }
    }

    public interface OnOrderDeleteRequest {
        void onOrderDeleteRequest(OrdersList order);
    }
}
