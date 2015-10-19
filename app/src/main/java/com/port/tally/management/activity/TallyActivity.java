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
import com.port.tally.management.work.EndWorkAutoTeamWork;
import com.port.tally.management.work.TallyCagoAtoWork;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TallyActivity extends Activity implements XListView.IXListViewListener {
    private AutoCompleteTextView  cagoAuto;
    private ImageView imgLeft;
    private TextView title;
    private XListView listView;
    private Button mBut =null;
    private EditText et_trust;
    private TallyManageAdapter tallyManageAdapter;
    private TallyCagoAtoAdapter tallyCagoAtoAdapter;
    private List<Map<String, Object>> dataList = null;
    private Toast mToast;
    EndWorkTeamAutoAdapter endWorkTeamAutoAdapter;
     int flag = 1;
    private Handler mHandler;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tally_main);
        Init();
        loadCagoValue();
//        loadValue("010111");
        initListView();

        showData(null, null, null, null, null);


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
        cagoAuto =(AutoCompleteTextView)findViewById(R.id.at_cargo);
        mBut =(Button)findViewById(R.id.mBut);
        et_trust =(EditText)findViewById(R.id.et_weituo);
        et_trust.setInputType(InputType.TYPE_CLASS_NUMBER);
        title.setText("理货作业票");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        mBut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(et_trust.getText().toString()))
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
    }
//    //给个控件赋值
    private void loadValue( String company) {

        //实例化，传入参数
        EndWorkAutoTeamWork endWorkAutoTeamWork = new EndWorkAutoTeamWork();

        endWorkAutoTeamWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {


                if (b) {
                    dataList.addAll(data);
                    Log.i("dataList1的值", dataList.toString());
                    Log.i("data的值", data.toString());
                    endWorkTeamAutoAdapter = new EndWorkTeamAutoAdapter(dataList, TallyActivity.this.getApplicationContext());
                    cagoAuto.setAdapter(endWorkTeamAutoAdapter);
                    cagoAuto.setThreshold(1);  //设置输入一个字符 提示，默认为2
                    cagoAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Map<String, Object> pc = dataList.get(position);
                            cagoAuto.setText(pc.get("tv1") + " " + pc.get("tv2"));
                            Log.i("tv1andtv2", pc.get("tv1") + " " + pc.get("tv2"));

                        }
                    });
                } else {


                }
                endWorkTeamAutoAdapter.notifyDataSetChanged();
            }

        });
        endWorkAutoTeamWork.beginExecute(company);
    }
////    //给个控件赋值
    private void loadCagoValue() {

        //实例化，传入参数
        TallyCagoAtoWork tallyCagoAtoWork= new TallyCagoAtoWork();

        tallyCagoAtoWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {


                if (b) {
                    dataList.addAll(data);
                    Log.i("dataList1的值", dataList.toString());
                    Log.i("data的值", data.toString());
                    tallyCagoAtoAdapter = new TallyCagoAtoAdapter(dataList, TallyActivity.this.getApplicationContext());
                    cagoAuto.setAdapter(tallyCagoAtoAdapter);
                    cagoAuto.setThreshold(1);  //设置输入一个字符 提示，默认为2
                    cagoAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Map<String, Object> pc = dataList.get(position);
                            cagoAuto.setText(pc.get("tv1") + " " + pc.get("tv2"));
                        }
                    });
                } else {


                }
                tallyCagoAtoAdapter.notifyDataSetChanged();
            }

        });
        tallyCagoAtoWork.beginExecute("");
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
        loadValue(count, stratcount, company,cargo,trustno);
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
                } else {
                    //清空操作
//                    listView.setPullLoadEnable(false);
//                    for(int i =0;i<1;i++){
                        showToast("无相关信息");
//                        tallyManageAdapter.notifyDataSetChanged();
//                    }



                }

                tallyManageAdapter.notifyDataSetChanged();
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
}