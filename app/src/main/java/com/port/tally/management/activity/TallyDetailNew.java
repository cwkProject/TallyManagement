package com.port.tally.management.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
import com.port.tally.management.R;
import com.port.tally.management.adapter.FromAreaAdapter;
import com.port.tally.management.adapter.SubprocessesFlagWorkAdapter;
import com.port.tally.management.adapter.TallyMachine1Adapter;
import com.port.tally.management.adapter.TallyTeamAdapter;
import com.port.tally.management.adapter.ToAreaAdapter;
import com.port.tally.management.adapter.Trust1Adapter;
import com.port.tally.management.adapter.Trust2Adapter;
import com.port.tally.management.work.AllCarryWork;
import com.port.tally.management.work.CodeCarryWork;
import com.port.tally.management.work.FromAreaWork;
import com.port.tally.management.work.SubprocessesFlagWork;
import com.port.tally.management.work.TallyDetail_MachineWork;
import com.port.tally.management.work.TallyDetail_teamWork;
import com.port.tally.management.work.TallySaveWork;
import com.port.tally.management.work.ToAreaWork;
import com.port.tally.management.work.ToallyDetailWork;
import com.port.tally.management.work.Trust1Work;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.work.WorkBack;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/11/7.
 */
public class TallyDetailNew extends TabActivity {
    /**
     * @param args
     */
    //公司编码1
    private  String CodeCompany= null;
    //部门编码2
    private String CodeDepartment= null;
    //委托编码3
    private String Cgno= null;
    //派工编码4
    private String Pmno= null;
    //用户编码5
    private String CodeTallyman= null;
    //用户姓名6
    private String Tallyman= null;
    //源航次编码7
    private String Vgno= null;
    //源仓别8
    private String Cabin= null;
    //源车别代码9
    private String CodeCarrier= null;
    //源车号10
    private String CarrierNum= null;
    //源驳船船舶规范编码11
    private String CodeNvessel= null;
    //源驳船属性12
    private String Bargepro= null;
    //源场地编码13
    private String CodeStorage= null;
    //源货位编码14
    private String CodeBooth= null;
    //源桩角编码15
    private String CodeAllocation= null;
    //源载体描述16
    private String Carrier1= null;
    //源载体属性17
    private String Carrier1Num= null;
    //目的航次编码18
    private String VgnoLast= null;
    //目的仓别19
    private String CabinLast= null;
    //目的车别代码20
    private String CodeCarrierLast= null;
    //目的车号21
    private String CarrierNumLast= null;
    //目的驳船船舶规范编码22
    private String CodeNvesselLast= null;
    //目的驳船属性23
    private String BargeproLast= null;
    //目的场地编码24
    private String CodeStorageLast= null;
    //目的货位编码25
    private String CodeBoothLast= null;
    //目的桩角编码26
    private String CodeAllocationLast= null;
    //目的载体描述27
    private String Carrier2= null;
    //目的载体属性28
    private String Carrier2num= null;
    //票货编码29
    private String CodeGoodsBill= null;
    //票货描述30
    private String GoodsBillDisplay= null;
    //商务票货编码31
    private String CodeGbBusiness= null;
    //商务票货描述32
    private String GbBusinessDisplay= null;
    //子过程特殊标志编码33
    private String CodeSpecialMark= null;
    //源区域编码34
    private String CodeWorkingArea= null;
    //目的区域编码35
    private String CodeWorkingAreaLast= null;
    //质量36
    private String Quality= null;
    //件数1 37
    private String Amount= null;
    //重量1 38
    private String Weight= null;
    //数量1 39
    private String Count= null;
    //件数2 40
    private String Amount2= null;
//    41
    private String Weight2= null;
//    42
    private String Count2= null;
//    43
    private String Machine= null;
//    44
    private String WorkTeam= null;
    private Button btn_save,btn_upload;
    private List<Map<String, Object>> dataList = null;
    private List<Map<String, Object>> dataListMachine =null;
    private List<Map<String, Object>> dataListTeam =null;
    private ListView listView1,listView2;
    private ImageView imgLeft;
    private ProgressDialog progressDialog;
    private TallyMachine1Adapter tallyMachineAdapter;
    private TallyTeamAdapter tallyTeamAdapter;
    private LinearLayout shangwu_ly,xiaozhang_ly,linear_show;
    private TextView title,tv_shipment,start,end,shipment,business,tv_messgae,tv_cardstate,tv_boatname
            ,tv_tvboatdetail,tv_changbie,tv_huowei,tv_huoweidetail,tv_Eboatname,tv_Eboatdetail,tv_Echangbie,tv_Ehuowei,tv_Ehuoweidetail;
    EditText tv_changbiedetail,tv_Echangbiedetail;
    private Spinner entrust1_spinner,entrust2_spinner,flag_spinner,quality_spinner,toarea_spinner,fromarea_spinner;
    private Toast mToast;
    EditText et_count1,et_count2,et_count3;
    String[] value=null;
    // 定义5个记录当前时间的变量
    private AlertDialog.Builder builder;
    private TimePicker timePicker;
    TabHost mTabHost = null;
    TabWidget mTabWidget = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tallydetail);
        init();
        showProgressDialog();
        loadFlagData();
        Bundle b=getIntent().getExtras();
        value=b.getStringArray("detailString");
//                派工编码
//                委托编码
//                票货编码
        Log.i("value1的值是", value[0] + "" + value[1] + "" + value[2] + "");
        //委托编码
        Cgno= value[1];
        //派工编码
        Pmno= value[0];
        initShipment();
        loadTrust1Data();
        dataList = new ArrayList<>();
        dataListMachine = new ArrayList<>();
        dataListTeam = new ArrayList<>();
        initMachine();
        initTeam();
        initCodeCarrer();
        initAllCarrer();
        loadToAreaData();
        loadFromAreaData();
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
    //    加载标志数据
    private void loadFlagData(){
        SubprocessesFlagWork subprocessesFlagWork = new SubprocessesFlagWork();
        subprocessesFlagWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {
                        //绑定Adapter
                        SubprocessesFlagWorkAdapter subprocessesFlagWorkAdapter = new SubprocessesFlagWorkAdapter(maps, TallyDetailNew.this.getApplicationContext());
                        flag_spinner.setAdapter(subprocessesFlagWorkAdapter);
                        flag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                String str = parent.getItemAtPosition(position).toString();
//                                    Toast.makeText(TallyDetail.this, "你点击的是:" + str, 2000).show();
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
    //    加载到区域数据
    private void loadToAreaData(){
        ToAreaWork toAreaWork = new ToAreaWork();
        toAreaWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {
                        //绑定Adapter
                        ToAreaAdapter toAreaAdapter = new ToAreaAdapter(maps, TallyDetailNew.this.getApplicationContext());
                        toarea_spinner.setAdapter(toAreaAdapter);

                        fromarea_spinner= (Spinner)findViewById(R.id.fromarea_spinner);
                        toarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                String str = parent.getItemAtPosition(position).toString();
//                                    Toast.makeText(TallyDetail.this, "你点击的是:" + str, 2000).show();
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

        toAreaWork.beginExecute("20151021000241","02");
    }
    //    加载源区域数据
    private void loadFromAreaData(){

        FromAreaWork fromAreaWork = new FromAreaWork();
        fromAreaWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {
                        //绑定Adapter
                        FromAreaAdapter fromAreaAdapter = new FromAreaAdapter(maps, TallyDetailNew.this.getApplicationContext());
                        fromarea_spinner.setAdapter(fromAreaAdapter);
                        fromarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                String str = parent.getItemAtPosition(position).toString();
//                                    Toast.makeText(TallyDetail.this, "你点击的是:" + str, 2000).show();
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
                    showToast("获取数据失败");
                }
            }
        });

        fromAreaWork.beginExecute("20151021000241","02");
    }
    //    加载票货数据
    private void loadTrust1Data(){
        Trust1Work trust1Work = new Trust1Work();
        trust1Work.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    Log.i("loadTrust1Data加载的值是","TallyDetail的loadTrust1Data加载的值是"+b+maps);
                    if (!maps.equals("")) {
                        //绑定Adapter
                        if (maps.get(0).get("amount2visible").toString().equals("1")) {
                            linear_show.setVisibility(View.VISIBLE);
                        } else {
                            linear_show.setVisibility(View.GONE);
                            tv_cardstate.setVisibility(View.GONE);
                            tv_messgae.setVisibility(View.GONE);
                        }
//                        Log.i("maps.get(0).get(\"tv3\").toString().length()","maps.get(0).get(\"tv3\").toString().length()"+maps.get(0).get("tv3").toString().length());
                        if (!"".equals(maps.get(0).get("tv3").toString()) || maps.get(0).get("tv3").toString().length()!=1) {
                            xiaozhang_ly.setVisibility(View.VISIBLE);
                            Trust1Adapter trust1Adapter = new Trust1Adapter((List<Map<String, Object>>) maps.get(0).get("tv3"), TallyDetailNew.this.getApplicationContext());
                            entrust1_spinner.setAdapter(trust1Adapter);
                            entrust1_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
                                    String str = parent.getItemAtPosition(position).toString();
//                                    Toast.makeText(TallyDetail.this, "你点击的是:" + str, 2000).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // TODO Auto-generated method stub
                                }
                            });
                        }
                        if (!"".equals(maps.get(0).get("tv5"))) {
                            shangwu_ly.setVisibility(View.VISIBLE);
                            Trust2Adapter trust2Adapter = new Trust2Adapter((List<Map<String, Object>>) maps.get(0).get("tv5"), TallyDetailNew.this.getApplicationContext());
                            entrust2_spinner.setAdapter(trust2Adapter);
                            entrust2_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
                                    String str = parent.getItemAtPosition(position).toString();
//                                    Toast.makeText(TallyDetail.this, "你点击的是:" + str, 2000).show();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    // TODO Auto-generated method stub
                                }
                            });
                        }
                        Log.i("maps.get(0).get(\"tv3\")", "" + maps.get(0).get("tv3"));
                        Log.i("maps.get(0).get(\"tv5\")", "" + maps.get(0).get("tv5"));
                    } else {
                        showToast("数据为空");
                    }
                } else {
                }
            }
        });
        trust1Work.beginExecute(value[0],value[1]);
    }
    private void initShipment(){
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
    private void initAllCarrer(){
        AllCarryWork allCarryWork = new AllCarryWork();
        allCarryWork.setWorkEndListener(new WorkBack<Map<String, Object>>() {
            @Override
            public void doEndWork(boolean b,  Map<String, Object> stringObjectMap) {
                if (b) {
//                  showToast(stringObjectMap.toString());
                    Log.i("initAllCarrer()","stringObjectMap的值是"+stringObjectMap);
                    if(!"".equals(stringObjectMap.get("VgDisplay")))
                        tv_tvboatdetail.setText(stringObjectMap.get("VgDisplay").toString());
                    tv_Eboatdetail.setText(stringObjectMap.get("VgDisplay").toString());
                    if(!"".equals(stringObjectMap.get("Cabin")))
                        tv_changbiedetail.setText(stringObjectMap.get("Cabin").toString());
                    tv_Echangbiedetail.setText(stringObjectMap.get("Cabin").toString());
                    if(!"".equals(stringObjectMap.get("CodeCarrier")))
                        tv_changbiedetail.setText(stringObjectMap.get("CodeCarrier").toString());
                    tv_Echangbiedetail.setText(stringObjectMap.get("CodeCarrier").toString());
                    if(!"".equals(stringObjectMap.get("Storage")))
                        tv_tvboatdetail.setText(stringObjectMap.get("Storage").toString());
                    tv_Eboatdetail.setText(stringObjectMap.get("Storage").toString());
                    if(!"".equals(stringObjectMap.get("Nvessel")))
                        tv_tvboatdetail.setText(stringObjectMap.get("Nvessel").toString());
                    tv_Eboatdetail.setText(stringObjectMap.get("Nvessel").toString());
                } else {
                }
            }

        });
        Log.i("value2的值是", value[0] + "" + value[1] + "" + value[2] + "");
        allCarryWork.beginExecute(value[0], value[1], value[2]);
    }
    private void initCodeCarrer(){
        CodeCarryWork codeCarryWork = new CodeCarryWork();
        codeCarryWork.setWorkEndListener(new WorkBack<Map<String, Object>>() {
            @Override
            public void doEndWork(boolean b, Map<String, Object> stringObjectMap) {
                if(b){
                    Log.i("tringObjectMap的值是", "tringObjectMap的值是" + stringObjectMap);
                    String CodeCarriesS = stringObjectMap.get("CodeCarriesS").toString();
                    String CodeCarriesE = stringObjectMap.get("CodeCarriesE").toString();
//                    设置源票货
                    if(CodeCarriesS.equals("02")){
//                      表示  船
                        showToast(CodeCarriesS);
                        tv_boatname.setVisibility(View.VISIBLE);
                        tv_tvboatdetail.setVisibility(View.VISIBLE);
                        tv_boatname.setText("船舶航次");
                        tv_changbie.setVisibility(View.VISIBLE);
                        tv_changbiedetail.setVisibility(View.VISIBLE);
                        tv_changbie.setText("舱别");
                    }else if(CodeCarriesS .equals("03")||CodeCarriesS .equals("04")||CodeCarriesS .equals("06")){
//                    03  、04、06  表示车、汽、集装箱
                        tv_boatname.setVisibility(View.VISIBLE);
                        tv_tvboatdetail.setVisibility(View.VISIBLE);
                        tv_boatname.setText("车型");
                        tv_changbie.setVisibility(View.VISIBLE);
                        tv_changbiedetail.setVisibility(View.VISIBLE);
                        tv_changbie.setText("车号");
                        showToast(CodeCarriesS);

                    }else if(CodeCarriesS .equals("05")){
//                        表示  驳船
                        tv_boatname.setVisibility(View.VISIBLE);
                        tv_tvboatdetail.setVisibility(View.VISIBLE);
                        tv_changbie.setVisibility(View.VISIBLE);
                        tv_boatname.setText("船名");
                        tv_changbie.setVisibility(View.VISIBLE);
                        tv_changbiedetail.setVisibility(View.VISIBLE);
                        tv_changbie.setText("描述");

                        showToast(CodeCarriesS);
                    }else{
//                   表示  场地；
                        tv_huoweidetail.setVisibility(View.VISIBLE);
                        tv_huowei.setVisibility(View.VISIBLE);
                        tv_boatname.setText("场地");
                        tv_changbie.setVisibility(View.VISIBLE);
                        tv_changbiedetail.setVisibility(View.VISIBLE);
                        tv_changbie.setText("桩角");
                        tv_huowei.setText("货位");
                        showToast(CodeCarriesS);
                    }
//                设置目的票货
                    if(CodeCarriesE.equals("02")){
//                      表示  船
                        showToast(CodeCarriesE);
                        tv_Eboatname.setText("船舶航次");
                        tv_Echangbie.setText("车型");
                    }else if(CodeCarriesE.equals("03")||CodeCarriesE .equals("04")||CodeCarriesE .equals("06")){
//                    03  、04、06  表示车、汽、集装箱
                        tv_Eboatname.setText("车型");
                        tv_Echangbie.setText("车号");
                        showToast(CodeCarriesE);
                    }else if(CodeCarriesE.equals("05")){
//                        表示  驳船
                        tv_Eboatname.setText("船名");
                        tv_Echangbie.setText("描述");
                        showToast(CodeCarriesE);
                    }else{
//                   表示  场地；
                        tv_Ehuoweidetail.setVisibility(View.VISIBLE);
                        tv_Ehuowei.setVisibility(View.VISIBLE);
                        tv_Eboatname.setText("场地");
                        tv_Echangbie.setVisibility(View.VISIBLE);
                        tv_Echangbiedetail.setVisibility(View.VISIBLE);
                        tv_Echangbie.setText("桩角");
                        tv_Ehuowei.setText("货位");
                        showToast(CodeCarriesE);
                    }
                    progressDialog.dismiss();
                }else{

                }
            }
        });
        Log.i("value2的值是", value[0] + "" + value[1] + "" + value[2] + "");
        codeCarryWork.beginExecute(value[0]);
    }
    //    得到机械数据
    private void initMachine(){
        TallyDetail_MachineWork tallyDetail_MachineWork = new TallyDetail_MachineWork();
        tallyDetail_MachineWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    dataListMachine.addAll(maps);
                    Log.i("initMachinedata", "" + dataListMachine.toString());
                    Log.i("maps", "" + maps.toString());
                    listView1 = (ListView) findViewById(R.id.list1);
                    tallyMachineAdapter = new TallyMachine1Adapter(TallyDetailNew.this, dataListMachine);
                    listView1.setAdapter(tallyMachineAdapter);
                    setListViewHeightBasedOnChildren(listView1);
                } else {

                }
            }
        });
        tallyDetail_MachineWork.beginExecute("20151010000161");
//        tallyDetail_MachineWork.beginExecute(value[0], value[1], value[2]);
    }
    private void initTeam(){
        TallyDetail_teamWork tallyDetail_TeamWork = new TallyDetail_teamWork();
        tallyDetail_TeamWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    dataListTeam.addAll(maps);
                    Log.i("dataListinitTeam", "" + dataListTeam.toString());
                    Log.i("maps", "" + maps.toString());
                    listView2 = (ListView) findViewById(R.id.list2);
                    Log.i("dataListinitTeam", "" + dataListTeam.toString());
                    tallyTeamAdapter = new TallyTeamAdapter(TallyDetailNew.this, maps);
                    listView2.setAdapter(tallyTeamAdapter);
                    setListViewHeightBasedOnChildren(listView2);
                }
            }
        });
        tallyDetail_TeamWork.beginExecute("20151010000161");
//        tallyDetail_TeamWork.beginExecute(value[0], value[1], value[2]);
    }
    private void init() {
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        start = (TextView)findViewById(R.id.start);
        end = (TextView)findViewById(R.id.end);
        btn_save =(Button) findViewById(R.id.btn_save);
        btn_upload = (Button)findViewById(R.id.btn_upload);
        tv_shipment =(TextView)findViewById(R.id.tv_shipment);
        et_count1=(EditText) findViewById(R.id.et_count1);
        et_count2=(EditText) findViewById(R.id.et_count2);
        et_count3=(EditText) findViewById(R.id.et_count3);
        entrust1_spinner = (Spinner)findViewById(R.id.entrust1_spinner);
        entrust2_spinner = (Spinner)findViewById(R.id.entrust2_spinner);
        flag_spinner = (Spinner)findViewById(R.id.flag_spinner);
        toarea_spinner= (Spinner)findViewById(R.id.toarea_spinner);
        fromarea_spinner= (Spinner)findViewById(R.id.fromarea_spinner);
        shipment = (TextView)findViewById(R.id.shipment);
        business = (TextView)findViewById(R.id.business);
        shangwu_ly  = (LinearLayout)findViewById(R.id.entrust2);
        tv_messgae = (TextView)findViewById(R.id.tv_messgae);
        tv_boatname = (TextView)findViewById(R.id.tv_boatname);
        tv_tvboatdetail = (TextView)findViewById(R.id.tv_tvboatdetail);
        tv_changbie = (TextView)findViewById(R.id.tv_changbie);
        tv_changbiedetail = (EditText)findViewById(R.id.tv_changbiedetail);
        tv_huowei = (TextView)findViewById(R.id.tv_huowei);
        tv_huoweidetail = (TextView)findViewById(R.id.tv_huoweidetail);
        tv_Eboatname = (TextView)findViewById(R.id.tv_Eboatname);
        tv_Eboatdetail = (TextView)findViewById(R.id.tv_Eboatdetail);
        tv_Echangbie = (TextView)findViewById(R.id.tv_Echangbie);
        tv_Echangbiedetail = (EditText)findViewById(R.id.tv_Echangbiedetail);
        tv_Ehuowei = (TextView)findViewById(R.id.tv_Ehuowei);
        tv_Ehuoweidetail = (TextView)findViewById(R.id.tv_Ehuoweidetail);
        quality_spinner = (Spinner) findViewById(R.id.quality_spinner);
        tv_cardstate = (TextView)findViewById(R.id.tv_cardstate);
        xiaozhang_ly = (LinearLayout)findViewById(R.id.entrust1);
        linear_show= (LinearLayout)findViewById(R.id.linear_show);
        et_count1.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_count2.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_count3.setInputType(InputType.TYPE_CLASS_NUMBER);
        title.setText("作业票生成");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabWidget = mTabHost.getTabWidget();
        mTabHost.addTab(mTabHost.newTabSpec("机械").setContent(
                R.id.LinearLayout001).setIndicator("机械"));
        mTabHost.addTab(mTabHost.newTabSpec("班组").setContent(
                R.id.LinearLayout002).setIndicator("班组"));
        imgLeft.setOnClickListener(new View.OnClickListener() {

            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tallyMachineAdapter.notifyDataSetChanged();
                Log.i("dataListMachine",""+dataListMachine.size());
                //44
                Machine = listmap_to_json_string(dataListMachine);
                //43
                WorkTeam =listmap_to_json_string(dataListTeam);
                saveData();

            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    private void saveData(){
        TallySaveWork tallySaveWork = new TallySaveWork();
        tallySaveWork.setWorkEndListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean b, String s) {

            }
        });
         tallySaveWork.beginExecute(CodeCompany, CodeDepartment,Cgno,Pmno,CodeTallyman,Tallyman,Vgno,Cabin,
                 CodeCarrier,CarrierNum,CodeNvessel,Bargepro,CodeStorage,
                 CodeBooth,CodeAllocation,Carrier1,Carrier1Num,VgnoLast,CabinLast,CodeCarrierLast,CarrierNumLast,
                 CodeNvesselLast,BargeproLast,CodeStorageLast,CodeBoothLast,CodeAllocationLast,Carrier2,Carrier2num,
                 CodeGoodsBill,GoodsBillDisplay,CodeGbBusiness,GbBusinessDisplay,CodeSpecialMark,
                CodeWorkingArea,CodeWorkingAreaLast,Quality,Amount,Weight,Count,Amount2,Weight2,Count2,Machine,WorkTeam);
    }
    private void updateData(){

    }
    /**
     * List<Map<String, Object>> To JsonString
//     * @param list
     * @return
     */
    public String listmap_to_json_string(List<Map<String, Object>> list)
    {
        JSONArray json_arr=new JSONArray();
        for (Map<String, Object> map : list) {
            JSONObject json_obj=new JSONObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                try {
                    json_obj.put(key,value);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            json_arr.put(json_obj);
        }
        return json_arr.toString();
    }
    //    得到listview高度
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    //    显示Toast提示框
    private void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();    }
    //显示进度对话框
    private void showProgressDialog(){
        //创建ProgressDialog对象
        progressDialog = new ProgressDialog(TallyDetailNew.this);
        // 设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在加载数据...");
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(true);
        // 让ProgressDialog显示
        progressDialog.show();
    }


}

