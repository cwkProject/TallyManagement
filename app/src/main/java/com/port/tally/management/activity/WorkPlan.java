package com.port.tally.management.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.adapter.EndWorkTeamAutoAdapter;
import com.port.tally.management.adapter.TallyCagoAtoAdapter;
import com.port.tally.management.adapter.TallyManageAdapter;
import com.port.tally.management.adapter.WorkPlanAdapter;
import com.port.tally.management.work.EndWorkAutoTeamWork;
import com.port.tally.management.work.TallyCagoAtoWork;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.work.WorkPlanWork;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/10/10.
 */
public class WorkPlan extends Activity implements XListView.IXListViewListener {
    private ImageView imgLeft;
    private TextView title;
    private XListView listView;
    private WorkPlanAdapter workPlanAdapter;
    private List<Map<String, Object>> dataList=null;
    int flag = 1;
    private Handler mHandler;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workplan);
        Init();
        showData();
        initListView();


    }

    private void initListView() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(true);
        Log.i("dataList的值是",dataList.toString());
        workPlanAdapter = new WorkPlanAdapter(WorkPlan.this, dataList);
        workPlanAdapter.notifyDataSetChanged();
        listView.setAdapter(workPlanAdapter);
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

//                String cgno = "14";
//                Bundle b = new Bundle();
//                Intent intent = new Intent();
//                //                b.putStringArray("Cgno", cgno);
//                b.putString("Cgno", cgno);
//                intent = new Intent(WorkPlan.this, WorkPlanDetail.class);
//                intent.putExtras(b);
//                startActivity(intent);
            }

        });
    }

    private void Init() {
        // TODO Auto-generated method stub
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        listView = (XListView) findViewById(R.id.xListView);
        title.setText("作业计划");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);

        imgLeft.setOnClickListener(new View.OnClickListener() {
            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        dataList = new ArrayList<>();
    }


    //显示数据
    private void showData() {
        String key = "6";
        String type = "1";
        String company = "14";
        loadValue(key, type, company);
    }

    @Override
    public void onLoadMore() {
        String count = "5";
        String stratcount = String.valueOf(flag);
        String company = "14";
        loadValue(count, stratcount, company);
    }

    private void onLoad() {

        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("刚刚");
    }

    public void onRefresh() {

    }

    //给个控件赋值
    private void loadValue(String key, final String type, String company) {

        //实例化，传入参数
        WorkPlanWork toallyManageWork = new  WorkPlanWork();

        toallyManageWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {

                if ("1".equals(type)) {
                    dataList.clear();

                    flag = 1;
                }

                if (b && data != null) {
                    Log.i("data的值是",data.toString());
                    flag += data.size();
                    listView.setPullLoadEnable(true);
                    dataList.addAll(data);

                } else {
                    //清空操作
                    listView.setPullLoadEnable(false);
                }
                workPlanAdapter.notifyDataSetChanged();
                onLoad();
            }
        });
        toallyManageWork.beginExecute(key, company, type);
    }

}
