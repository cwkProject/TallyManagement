package com.port.tally.management.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.adapter.LiHuoWeiTuoAdapter;
import com.port.tally.management.bean.LiHuoWeiTuo;


import java.util.ArrayList;
import java.util.List;

public class LiHuoDetail extends Activity {

	/**
	 * @param args
	 */
	private ImageView imgLeft;
	private TextView title;
	private Spinner weiTuoSp;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lihuodetail);
		init();
	}
	 
		
	 
	private void init(){
		title = (TextView) findViewById(R.id.title);
		imgLeft= (ImageView)findViewById(R.id.left);
		title.setText("作业票生成");
		title.setVisibility(View.VISIBLE);
		imgLeft.setVisibility(View.VISIBLE);
		weiTuoSp = (Spinner) findViewById(R.id.weituo_spinner1);
		// 建立数据源  
		List<LiHuoWeiTuo> persons=new ArrayList<LiHuoWeiTuo>();
		persons.add(new LiHuoWeiTuo("张三", "上海 "));  
		persons.add(new LiHuoWeiTuo("李四", "上海 "));  
		persons.add(new LiHuoWeiTuo("王五", "北京" ));  
		persons.add(new LiHuoWeiTuo("赵六", "广州 "));  
		//  建立Adapter绑定数据源  
		LiHuoWeiTuoAdapter LiHuoWeiTuoAdapter=new LiHuoWeiTuoAdapter(this, persons);
		//绑定Adapter  
		weiTuoSp.setAdapter(LiHuoWeiTuoAdapter); 
		imgLeft.setOnClickListener(new OnClickListener() {
			 
//			@Override
 			public void onClick(View arg0) {
 				finish();
 			}
 		});
	
	}
}
