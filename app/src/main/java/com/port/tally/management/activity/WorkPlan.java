package com.port.tally.management.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

/**
 * Created by song on 2015/10/10.
 */
public class WorkPlan extends Activity implements XListView.IXListViewListener {

    private ImageView imgLeft;
    private TextView title;
    private XListView listView;
    private TallyManageAdapter tallyManageAdapter;
    private List<Map<String, Object>> dataList = null;
    int flag = 1;
    private Handler mHandler;
    Intent intent;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lihuomain);
        Init();
        initListView();

        showData();
    }

    private void initListView() {
        listView.setPullRefreshEnable(false);
        tallyManageAdapter = new TallyManageAdapter(WorkPlan.this, dataList);

        tallyManageAdapter.notifyDataSetChanged();
        listView.setAdapter(tallyManageAdapter);

        listView.setXListViewListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
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
                intent = new Intent(WorkPlan.this, WorkPlanDetail.class);
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

        String count = "5";
        String stratcount = "1";
        String company = "14";
        loadValue(count, stratcount, company);
    }

    @Override
    public void onLoadMore() {
        //        mHandler.postDelayed(new Runnable() {
        //
        //            public void run() {
        //                flag = false;
        //                String count = "5";
        //                String stratcount = "1";
        //                String company = "14";
        //                initValue(count, stratcount, company);
        //
        //                tallyManageAdapter.notifyDataSetChanged();
        //                onLoad();
        //            }
        //        }, 2000);

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

                    dataList.addAll(data);

                } else {
                    //清空操作

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
