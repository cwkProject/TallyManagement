package com.port.tally.management.activity;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.adapter.SubprocessesFlagWorkAdapter;
import com.port.tally.management.adapter.TallyManageAdapter;
import com.port.tally.management.adapter.Trust1Adapter;
import com.port.tally.management.work.SubprocessesFlagWork;
import com.port.tally.management.work.ToallyDetailWork;
import com.port.tally.management.work.ToallyManageWork;
import com.port.tally.management.work.Trust1Work;
import com.port.tally.management.xlistview.XListView;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/10/10.
 */
public class TallyDetail extends TabActivity {
    /**
     * @param args
     */
    private int flag = 1;
    private List<Map<String, Object>> dataList = null;
    private XListView listView;
    private String cargo;
    private ImageView imgLeft;
    private TallyManageAdapter tallyManageAdapter;
    private TextView title, tv_shipment, start, end;
    private Spinner entrust1_spinner, entrust2_spinner, flag_spinner;
    private Toast mToast;
    RadioGroup radiogroup;
    RadioButton radio1, radio2, radio3;
    EditText et_count;
    String[] value = null;
    private PopupWindow startPopupWindow;
    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    //
    TabHost mTabHost = null;
    TabWidget mTabWidget = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tallydetail);
        init();
        loadFlagData();
        loadTrust1Data();
        loadTrust2Data();
        Bundle b = getIntent().getExtras();
        value = b.getStringArray("detailString");
        Log.i("value1的值是", value[0] + "" + value[1] + "" + value[2] + "");
        initShipment();
        dataList = new ArrayList<>();
        listView = (XListView) findViewById(R.id.xListView);
        initListView();

        showData(null, null, null, null, null);


        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == radio2.getId()) {
                    showToast("选择的是吨");
                } else if (checkedId == radio1.getId()) {
                    showToast("选择的是数");
                } else if (checkedId == radio3.getId()) {
                    showToast("选择的是件");
                }
            }
        });
        //标签切换事件处理，setOnTabChangedListener
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            // TODO Auto-generated method stub
            @Override
            public void onTabChanged(String tabId) {
                //                Dialog dialog = new AlertDialog.Builder(TallyDetail.this)
                //                        .setTitle("提示")
                //                        .setMessage("当前选中：" + tabId + "标签")
                //                        .setPositiveButton("确定",
                //                                new DialogInterface.OnClickListener() {
                //                                    public void onClick(DialogInterface dialog,
                // int whichButton) {
                //                                        dialog.cancel();
                //                                    }
                //                                }).create();//创建按钮
                //
                //                dialog.show();
            }
        });
    }

    //    加载标志数据
    private void loadFlagData() {
        SubprocessesFlagWork subprocessesFlagWork = new SubprocessesFlagWork();
        subprocessesFlagWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {

                        //绑定Adapter
                        SubprocessesFlagWorkAdapter subprocessesFlagWorkAdapter = new
                                SubprocessesFlagWorkAdapter(maps, TallyDetail.this
                                .getApplicationContext());
                        flag_spinner.setAdapter(subprocessesFlagWorkAdapter);
                        flag_spinner.setOnItemSelectedListener(new AdapterView
                                .OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int
                                    position, long id) {
                                String str = parent.getItemAtPosition(position).toString();
                                //                                    Toast.makeText(TallyDetail
                                // .this, "你点击的是:" + str, 2000).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // TODO Auto-generated method stub
                            }
                        });
                    } else {
                        showToast("数据为空");
                    }

                } else {

                }
            }
        });

        subprocessesFlagWork.beginExecute("");
    }

    //    加载标志数据
    private void loadTrust1Data() {
        Trust1Work trust1Work = new Trust1Work();
        trust1Work.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {

                        //绑定Adapter
                        Trust1Adapter trust1Adapter = new Trust1Adapter(maps, TallyDetail.this
                                .getApplicationContext());
                        entrust1_spinner.setAdapter(trust1Adapter);
                        entrust1_spinner.setOnItemSelectedListener(new AdapterView
                                .OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int
                                    position, long id) {
                                String str = parent.getItemAtPosition(position).toString();
                                //                                    Toast.makeText(TallyDetail
                                // .this, "你点击的是:" + str, 2000).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // TODO Auto-generated method stub
                            }
                        });
                    } else {
                        showToast("数据为空");
                    }

                } else {

                }
            }
        });

        trust1Work.beginExecute("");
    }

    //    加载标志数据
    private void loadTrust2Data() {
        Trust1Work trust1Work = new Trust1Work();
        trust1Work.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {

                        //绑定Adapter
                        SubprocessesFlagWorkAdapter subprocessesFlagWorkAdapter = new
                                SubprocessesFlagWorkAdapter(maps, TallyDetail.this
                                .getApplicationContext());
                        entrust2_spinner.setAdapter(subprocessesFlagWorkAdapter);
                        entrust2_spinner.setOnItemSelectedListener(new AdapterView
                                .OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int
                                    position, long id) {
                                String str = parent.getItemAtPosition(position).toString();
                                //                                    Toast.makeText(TallyDetail
                                // .this, "你点击的是:" + str, 2000).show();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // TODO Auto-generated method stub
                            }
                        });
                    } else {
                        showToast("数据为空");
                    }

                } else {

                }
            }
        });

        trust1Work.beginExecute("");
    }

    private void initShipment() {
        ToallyDetailWork tallyDetailwork = new ToallyDetailWork();
        tallyDetailwork.setWorkEndListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean b, String s) {
                if (b) {
                    if (!s.equals("")) {
                        tv_shipment.setText(s);
                    } else {
                        showToast("数据为空");
                    }

                } else {
                    showToast(s);
                }

            }

        });
        Log.i("value2的值是", value[0] + "" + value[1] + "" + value[2] + "");
        tallyDetailwork.beginExecute(value[0], value[1], value[2]);
    }

    //    显示数据
    private void showData(String key, String type, String company, String cargo, String trustno) {
        key = "3";
        type = "1";
        company = "14";
        loadValue(key, type, company, cargo, trustno);
    }

    //    给个控件赋值
    private void loadValue(String key, final String type, String company, String cargo, String
            trustno) {

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
                    Log.i("TallyDetailDataList", "" + dataList);
                    tallyManageAdapter.notifyDataSetChanged();
                } else {
                    //清空操作
                    showToast("无相关信息");

                }

                tallyManageAdapter.notifyDataSetChanged();
                onLoad();
            }
        });
        toallyManageWork.beginExecute(key, company, type, cargo, trustno);
    }


    private void onLoad() {

        listView.stopRefresh();
        listView.stopLoadMore();
        listView.setRefreshTime("刚刚");
    }

    private void initListView() {
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(true);
        listView.setItemsCanFocus(true);
        tallyManageAdapter = new TallyManageAdapter(TallyDetail.this, dataList);
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
                String cargo = null;
                String trustno = null;
                loadValue(count, stratcount, company, cargo, trustno);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                HashMap map = (HashMap) arg0.getItemAtPosition(arg2);
                //                派工编码
                //                委托编码
                //                票货编码
                String[] strings = new String[]{map.get("pmno").toString() , map.get
                        ("tv_Entrust").toString() , map.get("gbno").toString()

                };
                Bundle b = new Bundle();
                Intent intent = new Intent();
                b.putStringArray("detailString", strings);

                Log.i("valuedetailString的值是", strings[0] + "" + strings[1] + "" + strings[2] + "");
                intent = new Intent(TallyDetail.this, TallyDetail.class);
                intent.putExtras(b);
                startActivity(intent);


            }

        });
    }

    private void init() {
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        start = (TextView) findViewById(R.id.start);
        end = (TextView) findViewById(R.id.end);
        tv_shipment = (TextView) findViewById(R.id.tv_shipment);
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup1);
        radio1 = (RadioButton) findViewById(R.id.radiobutton1);
        radio2 = (RadioButton) findViewById(R.id.radiobutton2);
        radio3 = (RadioButton) findViewById(R.id.radiobutton3);
        et_count = (EditText) findViewById(R.id.et_count);
        et_count.setInputType(InputType.TYPE_CLASS_NUMBER);
        entrust1_spinner = (Spinner) findViewById(R.id.entrust1_spinner);
        entrust2_spinner = (Spinner) findViewById(R.id.entrust2_spinner);
        title.setText("作业票生成");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        View popupView = getLayoutInflater().inflate(R.layout.timepick, null);
        TimePicker timePicker = (TimePicker) popupView.findViewById(R.id.timePicker);
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        mTabHost.setup();
        mTabWidget = mTabHost.getTabWidget();
        Intent intent1 = new Intent(this, TallyActivity.class);
        mTabHost.addTab(mTabHost.newTabSpec("机械").setContent(R.id.LinearLayout001).setIndicator
                ("机械").setContent(intent1));
        mTabHost.addTab(mTabHost.newTabSpec("班组").setContent(R.id.LinearLayout002).setIndicator
                ("班组"));
        imgLeft.setOnClickListener(new View.OnClickListener() {

            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startPopupWindow.showAsDropDown(v);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startPopupWindow.showAsDropDown(v);
            }
        });
        startPopupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        startPopupWindow.setTouchable(true);
        startPopupWindow.setOutsideTouchable(true);
        startPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        // 为TimePicker指定监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                TallyDetail.this.hour = hourOfDay;
                TallyDetail.this.minute = minute;
                showDate(hour, minute);

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

    // 定义在EditText中显示当前日期、时间的方法
    private void showDate(int hour, int minute) {
        start.setText(+hour + ":" + minute + " ");
    }
}
