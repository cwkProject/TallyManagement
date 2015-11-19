package com.port.tally.management.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.port.tally.management.work.TallyDeletWork;
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
        b1=getIntent().getExtras();
        value=b1.getStringArray("detailString");
        Init();
        initListView();
//        showData();
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
                Bundle b = getIntent().getExtras();
                value = b.getStringArray("detailString");
                String[] valueflag = new String[5];
                valueflag[0] = value[0];
                valueflag[1] = value[1];
                valueflag[2] = value[2];
                valueflag[3] = map.get("MarkFinish").toString();
                valueflag[4] = map.get("Tbno").toString();
                b.putStringArray("detailString", valueflag);
//                Log.i("bflag的值是", map.get("MarkFinish").toString());
                Log.i("valuedetailString的值是", value[0] + "" + value[1] + "" + value[2] + "" + valueflag[3] + "");
                intent = new Intent(TallyManage.this, TallyDetail.class);
                intent.putExtras(b);
                startActivity(intent);
            }


        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(final AdapterView<?> arg0, View v,
                                           final int index, long arg3) {
                // TODO Auto-generated method stub
                final HashMap map1 = (HashMap) arg0.getItemAtPosition(index);
                Dialog dialog = new AlertDialog.Builder(TallyManage.this).setTitle("提示").setMessage("确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (map1.get("MarkFinish").toString().equals("1")) {
                                    showToast("已提交，不可删除");
                                } else {
                                    dataList.remove(index - 1);
                                    String tbno = map1.get("Tbno").toString();
                                    Log.i("tbno的值是",""+tbno);
                                    showProgressDialog();
                                    deletListData(tbno);
                                    tallyManageAdapter.notifyDataSetChanged();
                                }

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).create();//创建按钮

                dialog.show();


                return true;
            }
        });
    }

    private void deletListData(String par3){
        //实例化，传入参数
        TallyDeletWork tallyDeletWork = new TallyDeletWork();

        tallyDeletWork.setWorkEndListener(new WorkBack<String>() {

            @Override
            public void doEndWork(boolean b, String s) {
                if(b){
                    Log.i("TallyMangede删除的值",""+s);
                    progressDialog.dismiss();
                    showToast(s);
//                    showToast(s);
                } else{
                    progressDialog.dismiss();
                    Log.i("TallyMangede删除的值",""+s);
                    showToast(s);
//                    showToast(s);
                }
            }
        });
          tallyDeletWork.beginExecute(par3);
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

//    //    显示数据
//    private void showData() {
//        loadValue();
//    }
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
//                Log.i("TallyActivity的Data的值是", "TallyActivity的Data的值是" + data.size() + "/" + data);

                if (b && data != null) {
                    if (data != null) {
                        dataList.addAll(data);
                        tallyManageAdapter.notifyDataSetChanged();
                    }
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    showToast("无更多数据");
                }
//
            }


        });
        toallyManageWork.beginExecute(value[0], value[1]);
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
    protected void onResume(){
        super.onResume();
        showProgressDialog();
        loadValue();
    }
    protected void onPause(){
        super.onPause();
        if(dataList!=null)
        dataList.clear();
        tallyManageAdapter.notifyDataSetChanged();
    }
}
