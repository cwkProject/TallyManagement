package com.port.tally.management.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.adapter.TallyManageAdapter;
import com.port.tally.management.work.GetDataAndWorkTeamWork;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.global.GlobalApplication;
import org.mobile.library.model.work.WorkBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TallyActivity extends Activity {

    private ImageView imgLeft;
    private TextView title,tv_time;
    private String company =null;

    private Spinner spin_trust;
    private TallyManageAdapter tallyManageAdapter;
    private ProgressDialog progressDialog;
    private Toast mToast;
    private int flag = 1;
    private List<Map<String, Object>> dataList = null;
    private XListView listView;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tally_main);
        company = GlobalApplication.getLoginStatus().getCodeCompany();
        showProgressDialog();
        Init();
        loadDateAndWork();
        initListView();
        showData(null, null, null, null, null);
    }

    private void initListView() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(true);
        listView.setItemsCanFocus(true);
        listView.setLongClickable(true);
        tallyManageAdapter = new TallyManageAdapter(TallyActivity.this, dataList);
        tallyManageAdapter.notifyDataSetChanged();
        listView.setAdapter(tallyManageAdapter);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                String count = "5";
                String stratcount = String.valueOf(flag);
                String cargo = null;
                String trustno = null;
                loadValue(count, stratcount, company, cargo, trustno);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                HashMap map = (HashMap) arg0.getItemAtPosition(arg2);
//                派工编码
//                委托编码
//                票货编码
                String[] strings = new String[]{
                        map.get("pmno").toString(),
                        map.get("tv_Entrust").toString(),
                        map.get("gbno").toString()
                };
                Bundle b = new Bundle();
                b.putStringArray("detailString", strings);
                Log.i("valuedetailString的值是", strings[0] + "" + strings[1] + "" + strings[2] + "");
                intent = new Intent(TallyActivity.this, TallyManage.class);
                intent.putExtras(b);
                startActivity(intent);


            }


        });

    }
    //获取货位数据
    private void loadDateAndWork (){
        GetDataAndWorkTeamWork getDateWork = new GetDataAndWorkTeamWork();
        getDateWork.setWorkEndListener(new WorkBack<Map<String, Object>>() {
            @Override
            public void doEndWork(boolean b, Map<String, Object> stringObjectMap) {
                if (b) {
                    if (stringObjectMap != null) {
                        Log.i("loadDateAndWork", "" + stringObjectMap);
                        tv_time.setText(stringObjectMap.get("TakeDate").toString());
                        if(stringObjectMap.get("WorkTime").toString().equals("白班")){
                            spin_trust.setSelection(0,true);
                            showData(null, null, null, tv_time.getText().toString(), "白班");
                        }
                        if(stringObjectMap.get("WorkTime").toString().equals("夜班")){
                            spin_trust.setSelection(1,true);
//                            spin_trust.isSelected()
//                                    spin_trust.
//                                    spin_trust.setSelection();
                            showData(null, null, null, tv_time.getText().toString(), "夜班");
                        }

                    }else{
                        Log.i("loadDateAndWork","获取失败");
                    }
                }
            }
        });
        getDateWork.beginExecute(GlobalApplication.getLoginStatus().getCodeCompany());
    }

    private void Init() {
        // TODO Auto-generated method stub
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        listView = (XListView) findViewById(R.id.xListView);
        tv_time =(TextView)findViewById(R.id.tv_time);
        spin_trust =(Spinner)findViewById(R.id.et_weituo);
        title.setText("理货作业票");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        spin_trust.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String[] languages = getResources().getStringArray(R.array.work);

                showData(null, null, null, tv_time.getText().toString(), languages[pos]);

//                Toast.makeText(TallyActivity.this, "你点击的是:" + languages[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


    imgLeft.setOnClickListener(new OnClickListener() {
        //			@Override
        public void onClick(View arg0) {
            finish();
        }
    });
        dataList = new ArrayList<>();
        tv_time.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                initDate();
            }
        });
    }
    /**
     * 初始化日期控件
     */
    private void initDate() {
         Calendar calendar = Calendar.getInstance();
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

//        String nowDate = format.format(calendar.getTime());
//
//            tv_time.setText(nowDate);

        final DatePickerDialog dutyDateDialog = new DatePickerDialog(TallyActivity
                .this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String date = year + "-" + (monthOfYear + 1) + "-" +
                        dayOfMonth;

                try {
                    tv_time.setText(format.format(format.parse(date)));
                } catch (ParseException e) {

                    tv_time.setText(date);
                }
                showData(null, null, null, tv_time.getText().toString(), "");

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar
                .DAY_OF_MONTH));

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dutyDateDialog.show();
            }
        });
    }


    //    显示数据
    private void showData(String key, String type, String company,String cargo,String trustno) {
        key = "5";
        type = "1";

        loadValue(key, type, company, cargo, trustno);
    }



    private void onLoad() {

        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("刚刚");
    }

    //给个控件赋值
    private void loadValue(String key, final String type, String company, String cargo, String trustno) {

        //实例化，传入参数
        ToallyManageWork toallyManageWork = new ToallyManageWork();

        toallyManageWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {
                if ("1".equals(type)) {
                    dataList.clear();
                    flag = 1;
                }
                if (b && data != null) {
                    flag += data.size();
                    listView.setPullLoadEnable(true);
                    dataList.addAll(data);
                    tallyManageAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    //清空操作
                    if(flag ==1){
                        if(dataList!=null){
                            dataList.clear();
                            if(tallyManageAdapter!=null)
                                tallyManageAdapter.notifyDataSetChanged();
                            listView.setPullLoadEnable(true);
                        }
                    }else if(flag>1){
                        if(dataList==null){
                            listView.setPullLoadEnable(false);
                            dataList.clear();
                        }else {
                            listView.setPullLoadEnable(true);
                        }
                        if(tallyManageAdapter!=null)
                        tallyManageAdapter.notifyDataSetChanged();
                    }
                    showToast("无更多数据");
                }
                onLoad();
            }
        });
        toallyManageWork.beginExecute(key, company, type, cargo, trustno);
    }

    private void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

        } else {
            mToast.setText(msg);

            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }
    private void showProgressDialog(){
        //创建ProgressDialog对象
        progressDialog = new ProgressDialog(TallyActivity.this);
        // 设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在加载数据...");
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(true);
        // 让ProgressDialog显示
        progressDialog.show();
    }
}