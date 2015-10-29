package com.port.tally.management.adapter;

/**
 * Created by song on 2015/10/13.
 */
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;

        import android.content.Context;
        import android.util.Log;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.TextView;

        import com.port.tally.management.R;

public class EndWorkTeamAutoAdapter extends BaseAdapter implements Filterable {
    private ArrayFilter mFilter;
    private List<Map<String, Object>> mList;
    private Context context;
    private ArrayList<Map<String, Object>> mUnfilteredData;

    public EndWorkTeamAutoAdapter(List<Map<String, Object>> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {

        return mList==null ? 0:mList.size();
    }

    @Override
    public Map<String, Object>  getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        Map<String, Object> item = getItem(position);
        if(convertView==null){
            view = View.inflate(context, R.layout.endworkteam_item, null);

            holder = new ViewHolder();
//            holder.tv_1 = (TextView) view.findViewById(R.id.tv_1);
            holder.tv_2 = (TextView) view.findViewById(R.id.tv_2);
            holder.tv_3 = (TextView) view.findViewById(R.id.tv_3);

            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        if(!item.get("tv2").equals("")){
            holder.tv_2.setText((CharSequence)item.get("tv2"));}
        if(!item.get("tv3").equals("")){
            holder.tv_3.setText((CharSequence)item.get("tv3"));}

        return view;
    }

    static class ViewHolder{
//        public TextView tv_1;
        public TextView tv_2;
        public TextView tv_3;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<Map<String, Object>>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<Map<String, Object>> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<Map<String, Object>> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<Map<String, Object>> newValues = new ArrayList<Map<String, Object>>(count);

                for (int i = 0; i < count; i++) {
                    Map<String, Object> pc = unfilteredValues.get(i);
                    if (pc != null) {

                        if(pc.get("tv3")!=null && ((CharSequence)pc.get("tv3")).toString().startsWith(prefixString)){
                            Log.i("tv3的值",pc.toString());
                            newValues.add(pc);
                        }else if(pc.get("tv2")!=null && ((CharSequence)pc.get("tv2")).toString().startsWith(prefixString)){

                            newValues.add(pc);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            mList = (List<Map<String, Object>>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}