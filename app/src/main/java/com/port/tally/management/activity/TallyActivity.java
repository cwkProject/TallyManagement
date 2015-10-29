package com.port.tally.management.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.adapter.EndWorkTeamAutoAdapter;
import com.port.tally.management.adapter.TallyCagoAtoAdapter;
import com.port.tally.management.adapter.TallyManageAdapter;
import com.port.tally.management.util.InstantAutoComplete;
import com.port.tally.management.work.EndWorkAutoTeamWork;
import com.port.tally.management.work.TallyCagoAtoWork;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TallyActivity extends Activity {
    private InstantAutoComplete cagoAuto;
    private ImageView imgLeft;
    private TextView title;

    private Button mBut =null;
    private EditText et_trust;
    private TallyManageAdapter tallyManageAdapter;
    private TallyCagoAtoAdapter tallyCagoAtoAdapter;
    private ProgressDialog progressDialog;
    private List<Map<String, Object>> dataListCago = null;
    private Toast mToast;
    private int flag = 1;
    private List<Map<String, Object>> dataList = null;
    private XListView listView;
    Intent intent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tally_main);
        showProgressDialog();
        Init();
        initListView();
        loadCagoValue();
        showData(null, null, null, null, null);


    }

    private void initListView() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(true);
        listView.setItemsCanFocus(true);
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
                String company = "14";
                String cargo=null;
                String trustno =null;
                loadValue(count, stratcount, company,cargo,trustno);
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
                Intent intent = new Intent();
                b.putStringArray("detailString", strings);

                Log.i("valuedetailString的值是", strings[0] + "" + strings[1] + "" + strings[2] + "");
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
        cagoAuto =(InstantAutoComplete)findViewById(R.id.at_cargo);
        mBut =(Button)findViewById(R.id.mBut);
        et_trust =(EditText)findViewById(R.id.et_weituo);
        et_trust.setInputType(InputType.TYPE_CLASS_NUMBER);
        title.setText("理货作业票");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        mBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                    showData(null, null, null, cagoAuto.getText().toString(), et_trust.getText().toString());
            }
        });
        imgLeft.setOnClickListener(new OnClickListener() {
            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        dataList = new ArrayList<>();
        dataListCago = new ArrayList<>();
    }

  //给个控件赋值
    private void loadCagoValue() {

        //实例化，传入参数
        TallyCagoAtoWork tallyCagoAtoWork= new TallyCagoAtoWork();

        tallyCagoAtoWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {


                if (b) {
                    dataListCago.addAll(data);
                    Log.i("dataListCago的值", dataListCago.toString());

                    Log.i("data的值", data.toString());
                    tallyCagoAtoAdapter = new TallyCagoAtoAdapter(dataListCago, TallyActivity.this.getApplicationContext());
                    cagoAuto.setAdapter(tallyCagoAtoAdapter);
                    cagoAuto.setThreshold(0);  //设置输入一个字符 提示，默认为2
                    cagoAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Map<String, Object> pccago = dataListCago.get(position);
                            cagoAuto.setText("" + pccago.get("tv2"));
                            showData(null, null, null, pccago.get("tv2").toString(), null);
                            Log.i("showData", "" + pccago.get("tv2").toString());

                        }
                    });
                    // Clear autocomplete
                    cagoAuto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cagoAuto.setText("");
                        }
                    });
                    tallyCagoAtoAdapter.notifyDataSetChanged();
                } else {
                    showToast("无相关信息");

                }

            }

        });
        tallyCagoAtoWork.beginExecute("");
    }

//    显示数据
    private void showData(String key, String type, String company,String cargo,String trustno) {
          key = "3";
         type = "1";
         company = "14";
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
                    //清空操作
                    showToast("无相关信息");

                }


                onLoad();
            }
        });
        toallyManageWork.beginExecute(key, company, type, cargo, trustno);
    }
    //判断输入框是否为空
    public boolean validate( String Keycontent){

        if(Keycontent==null||Keycontent.equals("")){

            showDialog("请输入委托号");
            return false;
        }
        return true;
    }
    private void showDialog(String str){
        Dialog dialog = new AlertDialog.Builder(TallyActivity.this)
                .setTitle("提示")
                .setMessage(str)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).create();//创建按钮

        dialog.show();
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