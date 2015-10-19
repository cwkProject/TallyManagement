package com.port.tally.management.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.bean.TrunkQueryBean;
import com.port.tally.management.util.FloatTextToast;
import com.port.tally.management.work.TrunkQueryWork;

import org.mobile.library.model.work.WorkBack;

//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;

public class TrunkQuery extends Activity {

    /**
     * @param
     */
    private TextView title_tv,tv_cargo,tv_forwarder,tv_vehiclenum,tv_group,tv_case;
    private ImageView return_im;
    private EditText search_edt;
    private Button search_btn;
    private ArrayAdapter adapter_city,adapter_letter,adapter_record;
    private Spinner spinner_city,spinner_letter,spinner_record;
    private String searchContent= null;
    private String searchCity = null;
    private String searchletter = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trunkquery);

        Init();
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent =searchCity+searchletter+search_edt.getText().toString();

                if (validate(searchContent)) {
                    //实例化，传入参数
                    TrunkQueryWork trunkQueryWork = new TrunkQueryWork();

                    trunkQueryWork.setWorkEndListener(new WorkBack<TrunkQueryBean>() {
                        @Override
                        public void doEndWork(boolean b, TrunkQueryBean trunkQueryBean) {
                            if (b) {
                                tv_forwarder.setText(trunkQueryBean.getClient());
                                tv_cargo.setText(trunkQueryBean.getCagro());
                                tv_group.setText(trunkQueryBean.getWorkteam());
                                tv_vehiclenum.setText(trunkQueryBean.getVehicleNum());
                                tv_case.setText(trunkQueryBean.getAmount());
                            } else {
                                tv_forwarder.setText("");
                                tv_cargo.setText("");
                                tv_group.setText("");
                                tv_vehiclenum.setText("");
                                tv_case.setText("");
                                FloatTextToast.makeText(TrunkQuery.this, search_edt, "结果不存在", FloatTextToast.LENGTH_SHORT).show();
                            }
                        }
                    });



                    trunkQueryWork.beginExecute(searchContent);


                }

            }
        });

    }
    //判断输入框是否为空
    public boolean validate( String Keycontent){

        if(Keycontent==null||Keycontent.equals("")){

            FloatTextToast.makeText(TrunkQuery.this, search_edt, "请输入关键字", FloatTextToast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void Init() {
//        initToolbar();
//        setTitle(R.string.jiyunguanli_title);

        title_tv=(TextView)findViewById(R.id.title);
        return_im=(ImageView)findViewById(R.id.left);
        search_edt =(EditText)findViewById(R.id.search_edt);
        search_btn = (Button)findViewById(R.id.search_btn);
        tv_cargo = (TextView) findViewById(R.id.tv_cargo);
        tv_forwarder = (TextView) findViewById(R.id.tv_forwarder);
        tv_vehiclenum = (TextView) findViewById(R.id.tv_vehiclenum);
        tv_group = (TextView) findViewById(R.id.tv_group);
        tv_case = (TextView) findViewById(R.id.tv_case);
        spinner_city = (Spinner)findViewById(R.id.spinner1);
        spinner_letter = (Spinner)findViewById(R.id.spinner2);
        spinner_record = (Spinner)findViewById(R.id.spinner3);
        title_tv.setVisibility(View.VISIBLE);
        return_im.setVisibility(View.VISIBLE);
        title_tv.setText("汽运查询");
        //将可选内容与ArrayAdapter连接起来
        adapter_city = ArrayAdapter.createFromResource(TrunkQuery.this, R.array.trunkquery_city,android.R.layout.simple_spinner_item );
       adapter_letter =ArrayAdapter.createFromResource(TrunkQuery.this, R.array.trunkquery_letter,android.R.layout.simple_spinner_item);
        //设置下拉列表的风格
        adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       adapter_letter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adapter_city 添加到spinner中
        spinner_city.setAdapter(adapter_city);
        spinner_letter.setAdapter(adapter_letter);
        //定义子元素监听器
        AdapterView.OnItemSelectedListener city_spinner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchCity = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        AdapterView.OnItemSelectedListener letter_spinner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               searchletter = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        //添加事件Spinner事件监听
        spinner_city.setOnItemSelectedListener(city_spinner);
        spinner_letter.setOnItemSelectedListener(letter_spinner);

        return_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    /**
     * 初始化标题栏
     */
//    private void initToolbar() {
//        // 得到Toolbar标题栏
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        // 得到标题文本
//        //toolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
//
//        // 关联ActionBar
//        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.mipmap.ic_launcher);
//
//        // 取消原actionBar标题
//        //getSupportActionBar().setDisplayShowTitleEnabled(false);
//    }

}
