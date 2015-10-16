package com.port.tally.management.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.adapter.MessgaePushAdapter;
import com.port.tally.management.adapter.PushMessageAdapter;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/9/28.
 */
public class PushMessage extends Activity implements XListView.IXListViewListener{
    private ListView listPush;
    private TextView tv_title;
    private ImageView imgLeft;
    private XListView listView;
    private PushMessageAdapter pushMessageAdapter;
    private List<Map<String, Object>> dataList = null;
    private Handler mHandler;
    int flag = 1;
    private Toast mToast;
    //
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.messagepush);
        initView();
        initListView();
    }



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
    //显示数据
    private void showData(String key, String type, String company,String cargo,String trustno) {
        key = "3";
        type = "1";
        company = "14";
        loadValue(key, type, company, cargo, trustno);
    }

    @Override
    public void onLoadMore() {
        String count = "5";
        String stratcount = String.valueOf(flag);
        String company = "14";
        String cargo=null;
        String trustno =null;
        loadValue(count, stratcount, company, cargo, trustno);
    }

    private void onLoad() {

        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("刚刚");
    }

    public void onRefresh() {
//        showData();
    }

    //给个控件赋值
    private void loadValue(String key, final String type, String company,String cargo,String trustno) {

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
                    pushMessageAdapter.notifyDataSetChanged();
                } else {
                    //清空操作
//                    listView.setPullLoadEnable(false);
//                    for(int i =0;i<1;i++){
                    showToast("无相关信息");
//                        tallyManageAdapter.notifyDataSetChanged();
//                    }



                }

                pushMessageAdapter.notifyDataSetChanged();
                onLoad();
            }
        });
        toallyManageWork.beginExecute(key, company, type, cargo, trustno);
    }

    private void initListView() {

        listView = (XListView) findViewById(R.id.xListView);
        dataList = new ArrayList<>();
        showData(null, null, null, null, null);
        listView.setPullLoadEnable(true);
        pushMessageAdapter = new PushMessageAdapter(PushMessage.this, dataList);

        pushMessageAdapter.notifyDataSetChanged();
        listView.setAdapter(pushMessageAdapter);

        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

//                String cgno = "14";
//                Bundle b = new Bundle();
//                Intent intent = new Intent();
//                //                b.putStringArray("Cgno", cgno);
//                b.putString("Cgno", cgno);
//                intent = new Intent(TallyActivity.this, TallyDetail.class);
//                intent.putExtras(b);
//                startActivity(intent);
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
                Intent intent = new Intent();
                b.putStringArray("detailString", strings);

                Log.i("valuedetailString的值是", strings[0] + "" + strings[1] + "" + strings[2] + "");
                intent = new Intent(PushMessage.this, TallyDetail.class);
                intent.putExtras(b);
                startActivity(intent);


            }

        });
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
}
