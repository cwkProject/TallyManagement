package com.port.tally.management.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.port.tally.management.R;
import com.port.tally.management.xlistview.XListView;
import com.port.tally.management.adapter.LiHuoDetailAdapter;
import java.util.ArrayList;
import java.util.List;


public class LiHuoActivity extends Activity implements XListView.IXListViewListener {
	private XListView listView;
	private ImageView imgLeft;
	private TextView title;
	private ArrayAdapter<String> mAdapter;
	private LiHuoDetailAdapter adapter;
	private List<String> items = new ArrayList<String>();
	
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lihuomain);
		
		 Init();
		listView = (XListView) findViewById(R.id.xListView); 
		listView.setPullLoadEnable(true);
		adapter = new LiHuoDetailAdapter(LiHuoActivity.this, items);
		listView.setAdapter(adapter);
		listView.setXListViewListener(this); 
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
			
			 
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				Intent intent = new Intent();
				intent = new Intent(LiHuoActivity.this,LiHuoDetail.class);
				 
				startActivity(intent);
				

			}
			
		});
		mHandler = new Handler();
	}

	private void geneItems() {
		for (int i = 0; i != 20; ++i) {
			items.add("refresh cnt " + (++start));
		}
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime("上拉刷新");
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				items.clear();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				mAdapter = new ArrayAdapter<String>(LiHuoActivity.this, R.layout.plan_item, items);
				listView.setAdapter(adapter);
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}
	private void Init() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.title);
		imgLeft= (ImageView)findViewById(R.id.left);
		title.setText("理货作业票");
		title.setVisibility(View.VISIBLE);
		imgLeft.setVisibility(View.VISIBLE);
		geneItems();
		imgLeft.setOnClickListener(new OnClickListener() {
 
//			@Override
 			public void onClick(View arg0) {
 				finish();
 			}
 		});
	
	}
}