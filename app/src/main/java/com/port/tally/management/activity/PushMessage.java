package com.port.tally.management.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.adapter.MessgaePushAdapter;
import com.port.tally.management.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2015/9/28.
 */
public class PushMessage extends Activity implements XListView.IXListViewListener{
    private ListView listPush;
    private TextView tv_title;
    private ImageView imgLeft;
    //
    private XListView listView;
    private ArrayAdapter<String> mAdapter;
    private MessgaePushAdapter adapter;
    private List<String> items = new ArrayList<String>();

    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;
    //
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.messagepush);
        initView();
        Init();
//        listPush = (ListView) findViewById(R.id.listView_push);
//        String[] strings = {"title","time","detail"};//Map的key集合数组
//        int[] ids = {R.id.tv_title,R.id.tv_time,R.id.tv_detail};//对应布局文件的id
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
//                getData(), R.layout.push_item, strings, ids);
//        listPush.setAdapter(simpleAdapter);//绑定适配器
}
//    // 初始化一个List
//    private List<HashMap<String, Object>> getData() {
//        // 新建一个集合类，用于存放多条数据
//        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
//        HashMap<String, Object> map = null;
//        for (int i = 1; i <= 40; i++) {
//            map = new HashMap<String, Object>();
//            map.put("title", "系统消息" + i);
//            map.put("time", "4-02" +i + "日");
//            map.put("detail", "我通过了你的好友验证请求，现在我们可以开始对话啦");
//            list.add(map);
//        }
//        return list;
//    }
    private void initView(){
        tv_title =(TextView)findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        tv_title.setText("消息中心");
        imgLeft.setVisibility(View.VISIBLE);
        imgLeft.setOnClickListener(new View.OnClickListener() {

            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            items.add("消息" + (++start));
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
                mAdapter = new ArrayAdapter<String>(PushMessage.this, R.layout.push_item, items);
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
        geneItems();
        listView = (XListView) findViewById(R.id.xListView);
        listView.setPullLoadEnable(true);
        adapter = new MessgaePushAdapter(PushMessage.this, items);
        listView.setAdapter(adapter);
        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Intent intent = new Intent();
                intent = new Intent(PushMessage.this, WorkPlanDetail.class);

                startActivity(intent);


            }

        });
        mHandler = new Handler();

    }
}
