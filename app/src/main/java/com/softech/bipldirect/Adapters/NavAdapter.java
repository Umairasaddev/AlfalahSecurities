package com.softech.bipldirect.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softech.bipldirect.Models.Menu;
import com.softech.bipldirect.R;

import java.util.List;

public class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder> {

    private final List<Menu> mValues;
    private final OnMenuInteractionListener mListener;

    public NavAdapter(List<Menu> items, OnMenuInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.drawer_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());
        holder.icon.setImageResource(mValues.get(position).getIc_resource());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onMenuInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView icon;
        public final TextView title;
        public final TextView count;
        public Menu mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            icon = (ImageView) view.findViewById(R.id.icon);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.counter);
        }


    }

    public interface OnMenuInteractionListener {
        void onMenuInteraction(Menu item);
    }
}
