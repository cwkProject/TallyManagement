package com.port.tally.management.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.util.Log;
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
        Log.i("item的值", "" + item);
        final Hand hand;
        if (convertView == null) {
            hand = new Hand();
            convertView = inflater.inflate(R.layout.tally_item, null);
            hand.tv_consignor = (TextView) convertView.findViewById(R.id.tv_consignor);
            hand.tv_Entrust = (TextView) convertView.findViewById(R.id.tv_Entrust);
            hand.tv_cargo = (TextView) convertView.findViewById(R.id.tv_cargo);
            hand.tv_planwork = (TextView) convertView.findViewById(R.id.tv_planwork);
            hand.tv_sourcevector = (TextView) convertView.findViewById(R.id.tv_sourcevector);
            hand.tv_sourcecargo = (TextView) convertView.findViewById(R.id.tv_sourcecargo);
            hand.tv_purposecargo = (TextView) convertView.findViewById(R.id.tv_purposecargo);
            hand.tv_operationprocess= (TextView) convertView.findViewById(R.id.tv_operationprocess);
            hand.tv_starttime = (TextView) convertView.findViewById(R.id.tv_starttime);

            hand.tv_destinationvector= (TextView) convertView.findViewById(R.id.tv_destinationvector);

        } else {
            hand = (Hand) convertView.getTag();
        }
        if(!item.get("tv_consignor").equals("")){
            hand.tv_consignor.setText((CharSequence)item.get("tv_consignor"));}
        if(!item.get("taskno").equals("")){
            TextPaint tp = hand.tv_Entrust.getPaint();
            tp.setFakeBoldText(true);
            hand.tv_Entrust.setText((CharSequence)item.get("taskno"));}
        if(!item.get("tv_cargo").equals("")){
            TextPaint tp =  hand.tv_cargo.getPaint();
            tp.setFakeBoldText(true);
            hand.tv_cargo.setText((CharSequence)item.get("tv_cargo"));}

        if(!item.get("tv_planwork").equals("")){
            TextPaint tp =  hand.tv_planwork.getPaint();
            tp.setFakeBoldText(true);
           hand.tv_planwork.setText((CharSequence) item.get("tv_planwork"));
        }
            if(!item.get("tv_sourcevector").equals("")){
                TextPaint tp =   hand.tv_sourcevector.getPaint();
                tp.setFakeBoldText(true);
           hand.tv_sourcevector.setText((CharSequence) item.get("tv_sourcevector"));}
                if(!item.get("tv_sourcecargo").equals("")){
           hand.tv_sourcecargo.setText((CharSequence) item.get("tv_sourcecargo"));}
            if(!item.get("tv_purposecargo").equals("")){
                        TextPaint tp =   hand.tv_purposecargo.getPaint();
                        tp.setFakeBoldText(true);
        hand.tv_purposecargo.setText((CharSequence) item.get("tv_purposecargo"));}
    if(!item.get("tv_operationprocess").equals("")){
        hand.tv_operationprocess.setText((CharSequence) item.get("tv_operationprocess"));}
    if(!item.get("tv_starttime").equals("")){
        hand.tv_starttime.setText(item.get("tv_starttime").toString()+"/"+item.get("tv_terminaltime").toString());}

    if(!item.get("tv_destinationvector").equals("")){
        TextPaint tp =   hand.tv_destinationvector.getPaint();
        tp.setFakeBoldText(true);
        hand.tv_destinationvector.setText((CharSequence) item.get("tv_destinationvector"));}
        convertView.setTag(hand);
        return convertView;
    }

    private class Hand {



        TextView tv_Entrust,tv_consignor,tv_cargo,tv_planwork,tv_sourcevector,tv_sourcecargo,tv_purposecargo,tv_operationprocess, tv_starttime, tv_terminaltime,tv_destinationvector;

    }
}
