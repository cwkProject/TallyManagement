package com.port.tally.management.activity;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

import com.port.tally.management.R;

public class QiYunZuoYe extends Activity {

	/**
	 * @param args
	 */
	     private PopupWindow datePopupWindow,datePopupWindow1;
	     private EditText date1,date2;
	     private ImageView imgLeft;
	 	private TextView title;
	 	  // 定义5个记录当前时间的变量  
	    private int year,year1;  
	    private int month,month1;  
	    private int day,day1;  
	    private int hour,hour1;  
	    private int minute,minute1; 
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.qichezuoye);
			
			 Init();
			 }
		
		public void Init(){
			title = (TextView) findViewById(R.id.title);
			imgLeft= (ImageView)findViewById(R.id.left);
			date1 = (EditText) findViewById(R.id.date_1);
			date2 = (EditText) findViewById(R.id.date_2);
			title.setText("汽运作业");
			date1.setText("选择时间");
			title.setVisibility(View.VISIBLE);
			imgLeft.setVisibility(View.VISIBLE);
			View popupView = getLayoutInflater().inflate(R.layout.datepopupwin, null);
			View popupView1 = getLayoutInflater().inflate(R.layout.datepopupwin1, null);
	        datePopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
	        datePopupWindow1 = new PopupWindow(popupView1, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
	        datePopupWindow.setTouchable(true);
	        datePopupWindow.setOutsideTouchable(true);
	        datePopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
	      
	        datePopupWindow1.setTouchable(true);
	        datePopupWindow1.setOutsideTouchable(true);
	        datePopupWindow1.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
	        imgLeft.setOnClickListener(new OnClickListener() {
	       	 
//				@Override
	 			public void onClick(View arg0) {
	 				finish();
	 			}
	 		});
	        date2.setOnClickListener(new OnClickListener() {
		            public void onClick(View v) {
		            	datePopupWindow1.showAsDropDown(v);   
		            }
		        });
	        date1.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	datePopupWindow.showAsDropDown(v);   
	            }
	        });
	        DatePicker datePicker = (DatePicker)popupView.findViewById(R.id.datePicker);
	        TimePicker timePicker = (TimePicker)popupView.findViewById(R.id.timePicker);
	        DatePicker datePicker1 = (DatePicker)popupView1.findViewById(R.id.datePicker1);
	        TimePicker timePicker1 = (TimePicker)popupView1.findViewById(R.id.timePicker1);
	            // 获取当前的年、月、日、小时、分钟  
	            Calendar c = Calendar.getInstance();
	            year = c.get(Calendar.YEAR);
	            month = c.get(Calendar.MONTH);
	            day = c.get(Calendar.DAY_OF_MONTH);
	            hour = c.get(Calendar.HOUR);
	            minute = c.get(Calendar.MINUTE);
	          
	    
	            Calendar c1 = Calendar.getInstance();
	            year1 = c1.get(Calendar.YEAR);
	            month1 = c1.get(Calendar.MONTH);
	            day1 = c1.get(Calendar.DAY_OF_MONTH);
	            hour1 = c1.get(Calendar.HOUR);
	            minute1 = c1.get(Calendar.MINUTE);
	            datePicker1.init(year1, month1, day1, new OnDateChangedListener()
	            {   
	                public void onDateChanged(DatePicker arg0, int year
	                        , int month, int day)  
	                {  
	                	QiYunZuoYe.this.year1 = year;  
	                	QiYunZuoYe.this.month1 = month;  
	                	QiYunZuoYe.this.day1 = day;  
	                    // 显示当前日期、时间  
 	                    showDate2(year, month, day, hour1, minute1);  
	                }  
	            }); 
	            // 为TimePicker指定监听器  
	            timePicker.setOnTimeChangedListener(new OnTimeChangedListener()
	            {  
	      
	                @Override
	                public void onTimeChanged(TimePicker view
	                        , int hourOfDay, int minute)  
	                {  
	                	QiYunZuoYe.this.hour = hourOfDay;  
	                	QiYunZuoYe.this.minute = minute; 
	                	showDate(year, month, day, hour, minute);
	                	  
	                }  
	            }); 
	            timePicker1.setOnTimeChangedListener(new OnTimeChangedListener()
	            {  
	      
	                @Override
	                public void onTimeChanged(TimePicker view
	                        , int hourOfDay, int minute)  
	                {  
	                	QiYunZuoYe.this.hour1 = hourOfDay;  
	                	QiYunZuoYe.this.minute1 = minute;  
	                	 showDate2(year, month, day, hour1, minute1);
	                }  
	            }); 
	            // 初始化DatePicker组件，初始化时指定监听器  
	            datePicker.init(year, month, day, new OnDateChangedListener()
	            {   
	                public void onDateChanged(DatePicker arg0, int year
	                        , int month, int day)  
	                {  
	                	QiYunZuoYe.this.year = year;  
	                	QiYunZuoYe.this.month = month;  
	                	QiYunZuoYe.this.day = day;  
	                    // 显示当前日期、时间  
 	                    showDate(year, month, day, hour, minute);  
	                }  
	            });
	          
		}

		   // 定义在EditText中显示当前日期、时间的方法  
	    private void showDate(int year, int month , int day, int hour, int minute)  
	    {  
	    	date1.setText(+year + "年"   
	                + (month + 1) + "月" + day + "日  "  
	                + hour + "时" + minute + "分");  
	    }
	    private void showDate2(int year, int month  
	            , int day, int hour, int minute)  
	    {   
	    	date2.setText(+year + "年"   
	                + (month + 1) + "月" + day + "日  "  
	                + hour + "时" + minute + "分");  
	    }
 
}
