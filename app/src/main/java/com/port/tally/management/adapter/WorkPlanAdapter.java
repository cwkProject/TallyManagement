package com.port.tally.management.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.port.tally.management.R;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/10/10.
 */
public class WorkPlanAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater inflater;

    public WorkPlanAdapter(Context context, List<Map<String, Object>> data) {
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
            convertView = inflater.inflate(R.layout.workplan_item, null);
            hand.tv_cargoname = (TextView) convertView.findViewById(R.id.tv_cargoname);
            hand.tv_starttime = (TextView) convertView.findViewById(R.id.tv_starttime);
            hand.tv_end = (TextView) convertView.findViewById(R.id.tv_end);
            hand.tv_process = (TextView) convertView.findViewById(R.id.tv_process);
            hand.tv_trustno = (TextView) convertView.findViewById(R.id.tv_trustno);
            hand.tv_bowei = (TextView) convertView.findViewById(R.id.tv_bowei);


        } else {
            hand = (Hand) convertView.getTag();
        }
        if(!item.get("tv_cargoname").equals("")){
            hand.tv_cargoname.setText((CharSequence)item.get("tv_cargoname"));}
        if(!item.get("tv_starttime").equals("")){
            hand.tv_starttime.setText((CharSequence) item.get("tv_starttime"));
        }

        if(!item.get("tv_cargoname").equals("")){
            hand.tv_cargoname.setText((CharSequence) item.get("tv_cargoname"));}
        if(!item.get("tv_trustno").equals("")){
            hand.tv_trustno.setText((CharSequence) item.get("tv_trustno"));}
        if(!item.get("tv_starttime").equals("")){
            hand.tv_trustno.setText((CharSequence) item.get("tv_starttime"));}
        if(!item.get("tv_end").equals("")){
            hand.tv_end.setText((CharSequence) item.get("tv_end"));}
        if(!item.get("tv_bowei").equals("")){
            hand.tv_bowei.setText((CharSequence) item.get("tv_bowei"));}
        convertView.setTag(hand);
        return convertView;
    }

    private class Hand {

        TextView tv_cargoname,tv_starttime, tv_process, tv_trustno,tv_end, tv_bowei;

    }
}

