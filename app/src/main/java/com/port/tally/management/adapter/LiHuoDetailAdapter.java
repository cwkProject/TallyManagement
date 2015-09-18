package com.port.tally.management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.port.tally.management.R;
import com.port.tally.management.bean.LiHuoWeiTuo;

import java.util.ArrayList;
import java.util.List;

public class LiHuoDetailAdapter extends BaseAdapter {

	//private List<Map<String, Object>> data;
	private List<String> data;
	private Context context;
	private LayoutInflater inflater;
	List<LiHuoWeiTuo> persons=new ArrayList<LiHuoWeiTuo>();

	public LiHuoDetailAdapter(Context context, List<String> data) {
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
	public String getItem(int position) {
		if (data != null && data.size() != 0) {
			return data.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String item = getItem(position);
		final int mPosition = position;
		final Hand hand;
		 
		if (convertView == null) {
			hand = new Hand();
			convertView = inflater.inflate(R.layout.plan_item, null);
			hand.bowei2 = (TextView) convertView.findViewById(R.id.bowei_2);
//			 
//			hand.guocheng2 = (TextView) convertView.findViewById(R.id.guocheng_2);
//			hand.start2 = (TextView) convertView.findViewById(R.id.start_2);
//			hand.end2 = (TextView) convertView.findViewById(R.id.end_2);
//			hand.huoming2 = (TextView) convertView.findViewById(R.id.huoming_2);
			hand.bowei2.setText(item);
		} else {
			hand = (Hand) convertView.getTag();
		}
		
 		
// 		hand.guocheng2.setText((CharSequence) item.get("address"));
// 		hand.start2.setText((CharSequence) item.get("areadcode"));
// 		hand.end2.setText((CharSequence) item.get("gender"));
// 		hand.start2.setText((CharSequence) item.get("areadcode"));
// 		hand.huoming2.setText((CharSequence) item.get("gender"));
		
		// hand.photo
		convertView.setTag(hand);
		return convertView;
	}

	private class Hand {

		// ������ƺͽ����ı�
		TextView bowei2,guocheng2,start2,end2,huoming2;

	}
}