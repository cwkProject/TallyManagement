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
		title.setText("��ҵƱ����");
		title.setVisibility(View.VISIBLE);
		imgLeft.setVisibility(View.VISIBLE);
		weiTuoSp = (Spinner) findViewById(R.id.weituo_spinner1);
		// ��������Դ  
		List<LiHuoWeiTuo> persons=new ArrayList<LiHuoWeiTuo>();
		persons.add(new LiHuoWeiTuo("����", "�Ϻ� "));  
		persons.add(new LiHuoWeiTuo("����", "�Ϻ� "));  
		persons.add(new LiHuoWeiTuo("����", "����" ));  
		persons.add(new LiHuoWeiTuo("����", "���� "));  
		//  ����Adapter������Դ  
		LiHuoWeiTuoAdapter LiHuoWeiTuoAdapter=new LiHuoWeiTuoAdapter(this, persons);
		//��Adapter  
		weiTuoSp.setAdapter(LiHuoWeiTuoAdapter); 
		imgLeft.setOnClickListener(new OnClickListener() {
			 
//			@Override
 			public void onClick(View arg0) {
 				finish();
 			}
 		});
	
	}
}
