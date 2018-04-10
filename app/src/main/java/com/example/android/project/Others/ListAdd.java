package com.example.android.project.Others;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.project.R;

import java.util.ArrayList;

public class ListAdd extends BaseAdapter {
    private Context context;
    private ArrayList<Integer> ID;
    private ArrayList<String> source;
    private ArrayList<String> dest;
    private ArrayList<Integer> cost;
    private ArrayList<String> transtime;


    public ListAdd(Context context,
            ArrayList<Integer> ID,
            ArrayList<String> source,
            ArrayList<String> dest,
            ArrayList<Integer> cost,
            ArrayList<String> transtime){
        this.ID=ID;
        this.context=context;
        this.source=source;
        this.dest=dest;
        this.cost=cost;
        this.transtime=transtime;
    }
    @Override
    public int getCount() {
        return source.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder;

        LayoutInflater layoutInflater;

        if (view == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            try{view = layoutInflater.inflate(R.layout.items, null);}
            catch(NullPointerException e)
            {
                Log.d("layout inflater",e.toString());
            }

            holder = new Holder();

            holder.tvID = (TextView) view.findViewById(R.id.tv_ID);
            holder.tvSource = (TextView) view.findViewById(R.id.tv_source);
            holder.tvDest = (TextView) view.findViewById(R.id.tv_dest);
            holder.tvCost = (TextView) view.findViewById(R.id.tv_cost);
            holder.tvTranstime = (TextView) view.findViewById(R.id.tv_tramstime);

            view.setTag(holder);

        } else {

            holder = (Holder) view.getTag();
        }
        holder.tvID.setText(String.valueOf(ID.get(position)));
        holder.tvSource.setText(String.valueOf(source.get(position)));
        holder.tvDest.setText(String.valueOf(dest.get(position)));
        holder.tvCost.setText(String.valueOf(cost.get(position)));
        holder.tvTranstime.setText(String.valueOf(transtime.get(position)));

        return view;
    }

    public class Holder {

        TextView tvID;
        TextView tvSource;
        TextView tvDest;
        TextView tvCost;
        TextView tvTranstime;
    }
}
