//package com.port.tally.management.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.port.tally.management.R;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by song on 2015/10/23.
// */
//public class TallyTeamAdapter extends BaseAdapter {
//
//    private List<Map<String, Object>> data;
//    private Context context;
//    private LayoutInflater inflater;
//    public TallyTeamAdapter(Context context, List<Map<String, Object>> data) {
//        this.data = data;
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//
//    }
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
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        Map<String, Object> item = getItem(position);
//        ViewHolder  holder = null;
//        Log.i("item的值", "" + item);
//
//        if (convertView == null) {
//
//            convertView = inflater.inflate(R.layout.team_item, null);
//            holder = new  ViewHolder();
//            holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
//            holder.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);
//            holder.tv_start = (TextView) convertView.findViewById(R.id.tv_start);
//            holder.tv_end = (TextView) convertView.findViewById(R.id.tv_end);
//            holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
//            holder.tv_macpeo = (TextView) convertView.findViewById(R.id.tv_macpeo);
//
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView,
//                                         boolean isChecked) {
//                if (isChecked) {
//                    data.get(position).put("select", "1");
//                } else {
//
//                    data.get(position).put("select", "0");
//                }
//            }
//        });
//        if(item.get("select").equals("1")){
//
//            holder.cb.setChecked(true);}
//        if(item.get("select").equals("0")){
//
//            holder.cb.setChecked(false);}
//        if(!item.get("machine").equals("")){
//
//            holder.tv_mac.setText((CharSequence) item.get("machine"));
//        } if(!item.get("name").equals("")){
//
//            holder.tv_macpeo.setText((CharSequence) item.get("name"));
//        }
//        if(!item.get("amount").equals("")){
//
//            holder.tv_count.setText((CharSequence) item.get("amount"));
//        }
//        convertView.setTag(holder);
//        return convertView;
//    }
//    private class ViewHolder {
//         CheckBox cb;
//        TextView tv_mac,tv_start,tv_end,tv_count,tv_macpeo;
//    }
//}
