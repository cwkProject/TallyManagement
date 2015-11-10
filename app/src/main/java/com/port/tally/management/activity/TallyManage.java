package com.port.tally.management.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.adapter.TallyManageTwoAdapter;
import com.port.tally.management.work.TallyManageWork;
import com.port.tally.management.xlistview.XListView;
import org.mobile.library.model.work.WorkBack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/11/7.
 */
public class TallyManage extends Activity {
    private ImageView imgLeft;
    private TextView title,text_btn;
    private TallyManageTwoAdapter tallyManageAdapter;
    private ProgressDialog progressDialog;
    private Toast mToast;
    private int flag = 1;
    private String[] strings;
    private List<Map<String, Object>> dataList = null;
    private XListView listView;
    private Intent intent;
   private Bundle b;
    Bundle b1;
    String[] value=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tallymanage);
        showProgressDialog();
        b1=getIntent().getExtras();
        value=b1.getStringArray("detailString");
        Init();
        initListView();
        showData();
    }
    private void initListView() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        listView.setItemsCanFocus(true);
        listView.setLongClickable(true);
        tallyManageAdapter = new TallyManageTwoAdapter(TallyManage.this, dataList);
        tallyManageAdapter.notifyDataSetChanged();
        listView.setAdapter(tallyManageAdapter);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                listView.setPullLoadEnable(false);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                HashMap map = (HashMap) arg0.getItemAtPosition(arg2);
//                派工编码
//                委托编码
//                票货编码
//                提交标志

                Bundle b=getIntent().getExtras();
                value=b.getStringArray("detailString");
                String[] valueflag= new String[4];
                valueflag[0] =value[0];
                valueflag[1] =value[1];
                valueflag[2] = value[2];
                valueflag[3] =map.get("MarkFinish").toString();
                b.putStringArray("detailString", valueflag);
//                Log.i("bflag的值是", map.get("MarkFinish").toString());
                Log.i("valuedetailString的值是", value[0] + "" + value[1] + "" + value[2] +""+ valueflag[3]+"");
                intent = new Intent(TallyManage.this, TallyDetail.class);
                intent.putExtras(b);
                startActivity(intent);


            }


        });
    }

    private void Init() {
        // TODO Auto-generated method stub
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        text_btn=(TextView) findViewById(R.id.text_btn);
        listView = (XListView) findViewById(R.id.xListView);
        title.setText("理货作业票");
        text_btn.setText("新建");
        title.setVisibility(View.VISIBLE);
        text_btn.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        text_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strings = new String[]{ value[0], value[1], value[2]};
                b1.putStringArray("detailString", strings);
                Log.i("value1的值是", value[0] + "" + value[1] + "" + value[2] + "");
                intent = new Intent(TallyManage.this, TallyDetailNew.class);
                intent.putExtras(b1);
                startActivity(intent);
            }
        });
        imgLeft.setOnClickListener(new View.OnClickListener() {
            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        dataList = new ArrayList<>();

    }

    //    显示数据
    private void showData() {
        loadValue();
    }
    private void onLoad() {
        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("刚刚");
    }
    //给个控件赋值
    private void loadValue() {

        //实例化，传入参数
        TallyManageWork toallyManageWork = new TallyManageWork();

        toallyManageWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {
//        Log.i("TallyActivity的Data的值是","TallyActivity的Data的值是"+ data.size()+"/"+data);

                if (b && data != null) {
                    dataList.addAll(data);
                    tallyManageAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                } else{
                    progressDialog.dismiss();
                    showToast("无更多数据");
                }
//
                }


        });
        toallyManageWork.beginExecute("20151010000161","d1bff20fa2d54a0b87e4385a5cb46914");
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

    private void showProgressDialog() {
        //创建ProgressDialog对象
        progressDialog = new ProgressDialog(TallyManage.this);
        // 设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在加载数据...");
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(true);
        // 让ProgressDialog显示
        progressDialog.show();
    }
}
