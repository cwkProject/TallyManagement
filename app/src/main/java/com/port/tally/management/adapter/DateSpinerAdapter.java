//package com.port.tally.management.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.port.tally.management.R;
//
//import java.util.List;
//
//public class DateSpinerAdapter extends BaseAdapter {
//
////    private List<LiHuoWeiTuo> mList;
//    private Context mContext;
//
//    public DateSpinerAdapter(Context pContext, List<LiHuoWeiTuo> pList) {
//        this.mContext = pContext;
//        this.mList = pList;
//    }
//
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return mList.size();
//    }
//
//    @Override
//    public Object getItem(int arg0) {
//        // TODO Auto-generated method stub
//        return mList.get(arg0);
//    }
//
//    @Override
//    public long getItemId(int arg0) {
//        // TODO Auto-generated method stub
//        return arg0;
//    }
//
//    @Override
//    public View getView(int arg0, View arg1, ViewGroup arg2) {
//        // TODO Auto-generated method stub
//        LayoutInflater lyFlater = LayoutInflater.from(mContext);
//        arg1 = lyFlater.inflate(R.layout.lihuodetail_item, null);
//        if (arg1 != null) {
//            TextView lihuodetail_tv1 = (TextView) arg1.findViewById(R.id.lihuodetail_tv1);
//            TextView lihuodetail_tv2 = (TextView) arg1.findViewById(R.id.lihuodetail_tv2);
//            lihuodetail_tv1.setText(mList.get(arg0).getWeituo1());
//            lihuodetail_tv2.setText(mList.get(arg0).getWeituo2());
//        }
//        return arg1;
//
//    }
//
//}
