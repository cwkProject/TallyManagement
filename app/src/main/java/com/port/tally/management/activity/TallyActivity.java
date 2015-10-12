package com.port.tally.management.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.adapter.TallyManageAdapter;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TallyActivity extends Activity implements XListView.IXListViewListener {

    private ImageView imgLeft;
    private TextView title;
    private XListView listView;
    private TallyManageAdapter tallyManageAdapter;
    private List<Map<String, Object>> dataList = null;
    int flag = 1;
    private Handler mHandler;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihuomain);
        Init();

        initListView();
        showData();


    }

    private void initListView() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(true);
        tallyManageAdapter = new TallyManageAdapter(TallyActivity.this, dataList);

        tallyManageAdapter.notifyDataSetChanged();
        listView.setAdapter(tallyManageAdapter);

        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String cgno = "14";
                Bundle b = new Bundle();
                Intent intent = new Intent();
                //                b.putStringArray("Cgno", cgno);
                b.putString("Cgno", cgno);
                intent = new Intent(TallyActivity.this, TallyDetail.class);
                intent.putExtras(b);
                startActivity(intent);
            }

        });
    }

    private void Init() {
        // TODO Auto-generated method stub
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        listView = (XListView) findViewById(R.id.xListView);
        title.setText("理货作业票");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        imgLeft.setOnClickListener(new OnClickListener() {
            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        dataList = new ArrayList<>();
    }

    //显示数据
    private void showData() {

        String count = "3";
        String stratcount = "1";
        String company = "14";
        loadValue(count, stratcount, company);
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
        showData();
    }

    //给个控件赋值
    private void loadValue(String key, final String type, String company) {

        //实例化，传入参数
        ToallyManageWork toallyManageWork = new ToallyManageWork();

        toallyManageWork.setWorkBackListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {

                if ("1".equals(type)) {
                    dataList.clear();

                    flag = 1;
                }

                if (b && data != null) {

                    flag += data.size();
                    listView.setPullLoadEnable(true);
                    dataList.addAll(data);

                } else {
                    //清空操作
                    listView.setPullLoadEnable(false);
                    //                    FloatTextToast.makeText(StartWork.this, tongxin_edt,
                    // "信息不存在", FloatTextToast.LENGTH_SHORT).show();
                }
                tallyManageAdapter.notifyDataSetChanged();
                onLoad();
            }
        });
        toallyManageWork.beginExecute(key, company, type);
    }
}