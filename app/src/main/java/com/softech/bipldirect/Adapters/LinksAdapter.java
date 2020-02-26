package com.softech.bipldirect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softech.bipldirect.Models.LinksModel.Link;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Developed by Hasham.Tahir on 7/15/2016.
 */
public class LinksAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Link> linkListLOL;

    public LinksAdapter(Context context, ArrayList<Link> linkListLOL) {
        mContext = context;

        this.linkListLOL = linkListLOL;

        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return linkListLOL.size();
    }

    @Override
    public Link getItem(int position) {
        return linkListLOL.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }


        Link obj = linkListLOL.get(position);

        holder.sym.setText(obj.getTitle());


        return view;
    }


    public class ViewHolder {

        @BindView(android.R.id.text1)
        TextView sym;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }
}
