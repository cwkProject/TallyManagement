//package com.port.tally.management.adapter;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.text.TextPaint;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.port.tally.management.R;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by song on 2015/10/21.
// */
//public class TallyMachineAdapter extends BaseAdapter {
//
//    private List<Map<String, Object>> data;
//    private Context context;
//    private LayoutInflater inflater;
//
//    public TallyMachineAdapter(Context context, List<Map<String, Object>> data) {
//        this.data = data;
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getCount() {
//        if (data != null) {
//            return data.size();
//        }
//        return 0;
//    }
//
//    @Override
//    public Map<String, Object> getItem(int position) {
//        if (data != null && data.size() != 0) {
//            return data.get(position);
//        }
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Map<String, Object> item = getItem(position);
//        Log.i("item的值", "" + item);
//        final Hand hand;
//        if (convertView == null) {
//            hand = new Hand();
//            convertView = inflater.inflate(R.layout.machine_item, null);
//
//            hand.im_mac = (CheckBox) convertView.findViewById(R.id.im_mac);
//            hand.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);
//            hand.tv_start = (TextView) convertView.findViewById(R.id.tv_start);
//            hand.tv_end = (TextView) convertView.findViewById(R.id.tv_end);
//            hand.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
//            hand.tv_macpeo = (TextView) convertView.findViewById(R.id.tv_macpeo);
//            hand.im_mac.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if(event.getAction()==MotionEvent.ACTION_DOWN){
//                        hand.im_mac.setImageResource(R.drawable.check_x); ;
//
//                    }
//                    if(event.getAction()==MotionEvent.ACTION_UP){
//                        hand.im_mac.setImageResource(R.drawable.check_n); ;
//
//                    }
//                    return false;
//                }
//            });
//        } else {
//            hand = (Hand) convertView.getTag();
//        }
//        if(item.get("select").equals("1")){
//
//          hand.im_mac.setImageResource(R.drawable.check_x);}
//        if(item.get("select").equals("0")){
//
//            hand.im_mac.setImageResource(R.drawable.check_n);}
//        if(!item.get("machine").equals("")){
//
//            hand.tv_mac.setText((CharSequence) item.get("machine"));
//        } if(!item.get("name").equals("")){
//
//            hand.tv_macpeo.setText((CharSequence) item.get("name"));
//        }
//        if(!item.get("amount").equals("")){
//
//            hand.tv_count.setText((CharSequence) item.get("amount"));
//        }
//        convertView.setTag(hand);
//        return convertView;
//    }
//    private class Hand {
//        CheckBox im_mac;
//        TextView tv_mac,tv_start,tv_end,tv_count,tv_macpeo;
//    }
//}
