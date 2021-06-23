package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softech.bipldirect.Models.Event;
import com.softech.bipldirect.R;

import java.util.List;

public class EventAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Event> arraylist;

    public EventAdapter(Context context) {
        mContext = context;

        this.arraylist = Event.getAllEvents(context);

        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Event getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_events_list, null);
            holder.dateTime = (TextView) view.findViewById(R.id.text1);
            holder.body = (TextView) view.findViewById(R.id.text2);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Event obj = arraylist.get(position);

        holder.dateTime.setText(obj.getDateTimeString());
        holder.body.setText(obj.getBody());


        return view;
    }


    public static class ViewHolder {
        TextView dateTime, body;

    }
}
