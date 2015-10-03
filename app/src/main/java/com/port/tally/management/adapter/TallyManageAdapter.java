package com.port.tally.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.port.tally.management.R;


import java.util.List;
import java.util.Map;

public class TallyManageAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater inflater;

    public TallyManageAdapter(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    private boolean mBusy = false;

    public void setFlagBusy(boolean busy) {
        this.mBusy = busy;
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Map<String, Object> getItem(int position) {
        if (data != null && data.size() != 0) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Map<String, Object> item = getItem(position);
        final int mPosition = position;
        final Hand hand;
        if (convertView == null) {
            hand = new Hand();
            convertView = inflater.inflate(R.layout.plan_item, null);
            hand.tv_entrust = (TextView) convertView.findViewById(R.id.tv_entrust);
            hand.bowei2 = (TextView) convertView.findViewById(R.id.bowei_2);
            hand.tv_process = (TextView) convertView.findViewById(R.id.tv_process);
            hand.tv_start = (TextView) convertView.findViewById(R.id.tv_start);
            hand.tv_end = (TextView) convertView.findViewById(R.id.tv_end);
            hand.tv_cargoname = (TextView) convertView.findViewById(R.id.tv_cargoname);


        } else {
            hand = (Hand) convertView.getTag();
        }
           hand.tv_entrust.setText((CharSequence)item.get("entrust"));
           hand.bowei2.setText((CharSequence) item.get("bowei"));
           hand.tv_process.setText((CharSequence) item.get("process"));
           hand.tv_start.setText((CharSequence) item.get("start"));
           hand.tv_end.setText((CharSequence) item.get("end"));
           hand.tv_cargoname.setText((CharSequence) item.get("cargoname"));
           convertView.setTag(hand);
        return convertView;
    }

    private class Hand {

        // 锟斤拷锟斤拷锟斤拷坪徒锟斤拷锟斤拷谋锟�
        TextView tv_entrust,bowei2, tv_process, tv_start,tv_end, tv_cargoname;

    }
}
