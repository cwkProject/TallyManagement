package com.port.tally.management.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.work.ToallyDetailWork;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2015/10/10.
 */
public class TallyDetail extends TabActivity {

    /**
     * @param args
     */
    private String cargo;
    private ImageView imgLeft;
    private TextView title,tv_shipment;
    private Spinner weiTuoSp;
    private Toast mToast;
    RadioGroup radiogroup;
    RadioButton radio1,radio2,radio3;
    EditText et_count;
    String[] value=null;
    //
    TabHost mTabHost = null;
    TabWidget mTabWidget = null;
    Button btnVisiable, btnStrip;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tallydetail);
        init();

        Bundle b=getIntent().getExtras();
        value=b.getStringArray("detailString");
        Log.i("value1的值是",value[0]+""+value[1]+""+value[2]+"");
        initShipment();
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
//                                    public void onClick(DialogInterface dialog, int whichButton) {
//                                        dialog.cancel();
//                                    }
//                                }).create();//创建按钮
//
//                dialog.show();
            }
        });
    }

    private void initShipment(){
        ToallyDetailWork tallyDetailwork = new ToallyDetailWork();
        tallyDetailwork.setWorkEndListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean b, String s) {
                if (b) {

                    tv_shipment.setText(s);
                    Log.i("S的值",s );
                }else{
                    showToast("销账票货不存在");
                }

            }

        });
        tallyDetailwork.beginExecute(value[0], value[1], value[2]);
    }
    private void init() {
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        tv_shipment =(TextView)findViewById(R.id.tv_shipment);
        radiogroup=(RadioGroup)findViewById(R.id.radiogroup1);
        radio1=(RadioButton)findViewById(R.id.radiobutton1);
        radio2=(RadioButton)findViewById(R.id.radiobutton2);
        radio3=(RadioButton)findViewById(R.id.radiobutton3);
        et_count=(EditText) findViewById(R.id.et_count);
        et_count.setInputType(InputType.TYPE_CLASS_NUMBER);
        title.setText("作业票生成");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);




        weiTuoSp = (Spinner) findViewById(R.id.weituo_spinner1);
//
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabWidget = mTabHost.getTabWidget();
        mTabHost.addTab(mTabHost.newTabSpec("机械").setContent(
                R.id.LinearLayout001).setIndicator("机械"));
        mTabHost.addTab(mTabHost.newTabSpec("班组").setContent(
                R.id.LinearLayout002).setIndicator("班组"));


        // mTabHost.setCurrentTab(1);


        //
//        // 建立数据源
//        List<LiHuoWeiTuo> persons = new ArrayList<LiHuoWeiTuo>();
//        persons.add(new LiHuoWeiTuo("张三", "上海 "));
//        persons.add(new LiHuoWeiTuo("李四", "上海 "));
//        persons.add(new LiHuoWeiTuo("王五", "北京"));
//        persons.add(new LiHuoWeiTuo("赵六", "广州 "));
//        //  建立Adapter绑定数据源
//        LiHuoWeiTuoAdapter LiHuoWeiTuoAdapter = new LiHuoWeiTuoAdapter(this, persons);
//        //绑定Adapter
//        weiTuoSp.setAdapter(LiHuoWeiTuoAdapter);
        imgLeft.setOnClickListener(new View.OnClickListener() {

            //			@Override
            public void onClick(View arg0) {
                finish();
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
