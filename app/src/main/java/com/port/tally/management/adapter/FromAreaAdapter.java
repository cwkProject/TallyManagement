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
 * Created by song on 2015/11/7.
 */
public class FromAreaAdapter extends BaseAdapter {

    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater inflater;


    public FromAreaAdapter(List<Map<String, Object>> data, Context context) {
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
        if(convertView==null){
            view = View.inflate(context, R.layout.fromarea_item, null);

            holder = new ViewHolder();
            holder.tv_1 = (TextView) view.findViewById(R.id.tv_1);
            holder.tv_2 = (TextView) view.findViewById(R.id.tv_2);


            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        if(!item.get("tv1").equals("")){
            holder.tv_1.setText((CharSequence)item.get("tv1"));
            Log.i("tv1的值", item.get("tv1").toString());}
        if(!item.get("tv2").equals("")){
            holder.tv_2.setText((CharSequence)item.get("tv2"));}


        return view;
    }
    static class ViewHolder{

        public TextView tv_2;
        public TextView tv_1;


    }
}

