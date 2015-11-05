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
 * Created by song on 2015/11/3.
 */
public class Trust2Adapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater inflater;


    public Trust2Adapter(List<Map<String, Object>> data, Context context) {
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
        View view;
        ViewHolder holder;
        Map<String, Object> item = getItem(position);
        Log.i("Trust2Adapter的item值是","Trust2Adapter的item值是"+item);
        if(convertView==null){
            view = View.inflate(context, R.layout.tallydetailtrust_item, null);
            holder = new ViewHolder();
            holder.tv_3 = (TextView) view.findViewById(R.id.tv_3);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        if(!item.get("tv5").equals("")){
            holder.tv_3.setText(item.get("tv5").toString());}

        return view;
    }
    static class ViewHolder{
        public TextView tv_3;

    }
}
