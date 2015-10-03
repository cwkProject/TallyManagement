package com.port.tally.management.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.port.tally.management.R;
import com.port.tally.management.adapter.TallyManageAdapter;
import com.port.tally.management.adapter.LiHuoWeiTuoAdapter;
import com.port.tally.management.bean.LiHuoWeiTuo;
import com.port.tally.management.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

public class WorkPlanQuery extends Activity {
    private XListView listView;

    private ArrayAdapter<String> mAdapter;
    private TallyManageAdapter adapter;
    private List<String> items = new ArrayList<String>();
    private Spinner Date1Sp;
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        Init();
//        listView = (XListView) findViewById(R.id.xListView);
//        listView.setPullLoadEnable(true);
//        adapter = new TallyManageAdapter(WorkPlanQuery.this, items);
//        listView.setAdapter(adapter);
//        listView.setXListViewListener(this);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//
//                Intent intent = new Intent();
//                intent = new Intent(WorkPlanQuery.this, LiHuoDetail.class);
//
//                startActivity(intent);
//
//
//            }
//
//        });
//        mHandler = new Handler();
    }
//
//    private void geneItems() {
//        for (int i = 0; i != 20; ++i) {
//            items.add("refresh cnt " + (++start));
//        }
//    }
//
//    private void onLoad() {
//        listView.stopRefresh();
//        listView.stopLoadMore();
//        listView.setRefreshTime("上拉刷新");
//    }
//
//    @Override
//    public void onRefresh() {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                start = ++refreshCnt;
//                items.clear();
//                geneItems();
//                // mAdapter.notifyDataSetChanged();
//                mAdapter = new ArrayAdapter<String>(WorkPlanQuery.this, R.layout.plan_item, items);
//                listView.setAdapter(adapter);
//                onLoad();
//            }
//        }, 2000);
//    }
//
//    @Override
//    public void onLoadMore() {
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                geneItems();
//                adapter.notifyDataSetChanged();
//                onLoad();
//            }
//        }, 2000);
//    }

    private void Init() {
        // TODO Auto-generated method stub
        Date1Sp = (Spinner) findViewById(R.id.weituo_spinner1);
        // 建立数据源
        List<LiHuoWeiTuo> persons = new ArrayList<LiHuoWeiTuo>();
        persons.add(new LiHuoWeiTuo("张三", "上海 "));
        persons.add(new LiHuoWeiTuo("李四", "上海 "));
        persons.add(new LiHuoWeiTuo("王五", "北京"));
        persons.add(new LiHuoWeiTuo("赵六", "广州 "));
        //  建立Adapter绑定数据源
        LiHuoWeiTuoAdapter LiHuoWeiTuoAdapter = new LiHuoWeiTuoAdapter(this, persons);
        //绑定Adapter
        Date1Sp.setAdapter(LiHuoWeiTuoAdapter);
//        geneItems();


    }
}
