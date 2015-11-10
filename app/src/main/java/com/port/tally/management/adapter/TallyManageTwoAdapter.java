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

/**
 * Created by song on 2015/11/7.
 */
public class TallyManageTwoAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater inflater;

    public TallyManageTwoAdapter(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.tallytwo_item, null);
            hand.tv_waitflag = (TextView) convertView.findViewById(R.id.tv_waitflag);
            hand.tv_yuantrust = (TextView) convertView.findViewById(R.id.tv_yuantrust);
            hand.tv_muditrust = (TextView) convertView.findViewById(R.id.tv_muditrust);
            hand.tv_yuanzaiti = (TextView) convertView.findViewById(R.id.tv_yuanzaiti);
            hand.tv_trustno = (TextView) convertView.findViewById(R.id.tv_trustno);
            hand.tv_mudizaiti = (TextView) convertView.findViewById(R.id.tv_mudizaiti);
            hand.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
        } else {
            hand = (Hand) convertView.getTag();
        }
        if(!item.get("MarkFinish").equals("0")){
            hand.tv_waitflag.setText("提交成功");}
        if(!item.get("MarkFinish").equals("1")){
            hand.tv_waitflag.setText("等待提交");}
        if(!item.get("Gbdisplay").equals("")){
//            TextPaint tp = hand.tv_yuantrust.getPaint();
//            tp.setFakeBoldText(true);
            hand.tv_yuantrust.setText(ToDBC(item.get("Gbdisplay").toString()));}
        if(!item.get("GbdisplayLast").equals("")){
//            TextPaint tp =  hand.tv_muditrust.getPaint();
//            tp.setFakeBoldText(true);
            hand.tv_muditrust.setText(ToDBC(item.get("GbdisplayLast").toString()));}
        if(!item.get("Carrier1").equals("")){
//            TextPaint tp =  hand.tv_yuanzaiti.getPaint();
//            tp.setFakeBoldText(true);
            hand.tv_yuanzaiti.setText(ToDBC(item.get("Carrier1").toString()));
        }
        if(!item.get("Taskno").equals("")){
//            TextPaint tp =   hand.tv_trustno.getPaint();
//            tp.setFakeBoldText(true);
            hand.tv_trustno.setText(ToDBC(item.get("Taskno").toString()));}
        if(!item.get("Carrier2").equals("")){
            hand.tv_mudizaiti.setText(ToDBC(item.get("Carrier2").toString()));}
        if(!item.get("Count").equals("")){
//            TextPaint tp =   hand.tv_count.getPaint();
//            tp.setFakeBoldText(true);
            hand.tv_count.setText(ToDBC(item.get("Count").toString()));}
        convertView.setTag(hand);
        return convertView;
    }
    /**
     * 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
    private class Hand {
        TextView tv_waitflag,tv_yuantrust,tv_muditrust,tv_yuanzaiti,tv_trustno,tv_mudizaiti,
                tv_count;
    }
}
