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
import com.port.tally.management.adapter.TallyManageAdapter;
import com.port.tally.management.util.FloatTextToast;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.model.work.WorkBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TallyActivity extends Activity implements XListView.IXListViewListener {

    private ImageView imgLeft;
    private TextView title;
    private XListView listView;
    private TallyManageAdapter tallyManageAdapter;
    List<Map<String, Object>> data = null;
    boolean flag = false;
    private Handler mHandler;
    Intent intent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihuomain);
        Init();
        showData();
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

    }
    //显示数据
    private void showData() {
        flag = true;

        String count = "5";
        String stratcount = "1";
        String company ="14";
        initValue(count, stratcount, company);

//        data.clear();

//
//        if(data.size() <= 5 && DataUtil.getPeopleNumber(keyWord) <= 5){
//            listView.setPullLoadEnable(false);
//        }else{
//            listView.setPullLoadEnable(true);
//        }


        listView.setPullRefreshEnable(false);
        tallyManageAdapter = new TallyManageAdapter(TallyActivity.this, initValue(count,  stratcount, company));

        tallyManageAdapter.notifyDataSetChanged();
        listView.setAdapter(tallyManageAdapter);


        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
//                HashMap map = (HashMap) arg0.getItemAtPosition(arg2);
//                String[] strings = new String[]{
//                        map.get("name").toString(),
//                        map.get("vename").toString(),
//
//                        map.get("country").toString(),
//                        map.get("sex").toString(),
//                        map.get("birth").toString(),
//                        map.get("idnumber1").toString(),
//                        map.get("idnumber2").toString(),
//                        map.get("idnumber3").toString(),
//                        map.get("homenumber").toString(),
//                        map.get("phonenumber").toString(),
//
//                        map.get("email").toString(),
//                        map.get("qq").toString(),
//                        map.get("address").toString(),
//                        map.get("bankaccount").toString(),
//                        map.get("characters").toString(),
//                        map.get("vehicle").toString(),
//                        map.get("trackact").toString(),
//                        map.get("source").toString(),
//                        map.get("rank").toString(),
//                        map.get("picPath").toString()
//
//                };
                String cgno = "14";
                Bundle b = new Bundle();
                Intent intent = new Intent();
//                b.putStringArray("Cgno", cgno);
                b.putString("Cgno", cgno);
                intent = new Intent(TallyActivity.this, LiHuoDetail.class);
                intent.putExtras(b);
                startActivity(intent);


            }

        });
    }
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {

            public void run() {
                flag = false;
                String count = "5";
                String stratcount = "1";
                String company ="14";
            initValue(count, stratcount,company);

                tallyManageAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2000);
    }
    private void onLoad() {

        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("刚刚");

    }

    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {

            public void run() {
                flag = false;
                String count = "5";
                String stratcount = "1";
                String company ="14";
                List<Map<String, Object>> data = initValue(count,stratcount,company);

                tallyManageAdapter = new TallyManageAdapter(TallyActivity.this, data);
                listView.setAdapter(tallyManageAdapter);
                onLoad();
            }
        }, 2000);

    }
    //给个控件赋值
    private List<Map<String, Object>> initValue(String key,String type,String company) {

        //实例化，传入参数
        ToallyManageWork toallyManageWork = new ToallyManageWork();

        toallyManageWork.setWorkBackListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {
                if (b) {
                    tallyManageAdapter = new TallyManageAdapter(TallyActivity.this, data);

                    tallyManageAdapter.notifyDataSetChanged();
                    listView.setAdapter(tallyManageAdapter);



                } else {
                    //清空操作

//                    FloatTextToast.makeText(StartWork.this, tongxin_edt, "信息不存在", FloatTextToast.LENGTH_SHORT).show();
                }
            }
        });
        toallyManageWork.beginExecute(key, company, type);

        return  data;
    }
}