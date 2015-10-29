package com.port.tally.management.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.port.tally.management.R;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/10/26.
 */
public class TallytTeam1Adapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater inflater;
    //    修改后的值
    private List<Map<String, Object>> updata;
    private Map<String,String> upmap;
    public TallytTeam1Adapter(Context context, List<Map<String, Object>> data) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        Map<String, Object> item = getItem(position);

        Log.i("item的值", "" + item);
        final Hand hand;
        if (convertView == null) {
            hand = new Hand();
            convertView = inflater.inflate(R.layout.team_item, null);
            hand.ck_mac = (CheckBox) convertView.findViewById(R.id.im_mac);
            hand.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);
            hand.tv_start = (TextView) convertView.findViewById(R.id.tv_start);
            hand.tv_end = (TextView) convertView.findViewById(R.id.tv_end);
            hand.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hand.tv_macpeo = (TextView) convertView.findViewById(R.id.tv_macpeo);

        } else {
            hand = (Hand) convertView.getTag();
        }

        hand.ck_mac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    data.get(position).put("select", "1");
                } else {

                    data.get(position).put("select", "0");
                }
            }
        });
        if(item.get("select").equals("1")){
            hand.ck_mac.setChecked(true);}
        if(item.get("select").equals("0")){
            hand.ck_mac.setChecked(false);}
        if(!item.get("machine").equals("")){
            hand.tv_mac.setText((CharSequence) item.get("machine"));
        } if(!item.get("name").equals("")){
            hand.tv_macpeo.setText((CharSequence) item.get("name"));
        }
        if(!item.get("amount").equals("")){
            hand.tv_count.setText((CharSequence) item.get("amount"));
        }
        convertView.setTag(hand);
        return convertView;
    }
    private class Hand {
        CheckBox ck_mac;
        TextView tv_mac,tv_start,tv_end,tv_count,tv_macpeo;
    }
}
