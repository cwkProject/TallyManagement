package com.port.tally.management.activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.port.tally.management.R;
import com.port.tally.management.adapter.FromAreaAdapter;
import com.port.tally.management.adapter.GetAllocationDataAdapter;
import com.port.tally.management.adapter.GetCornerPileDataAdapter;
import com.port.tally.management.adapter.GetFlagAutoDataAdapter;
import com.port.tally.management.adapter.SubprocessesFlagWorkAdapter;
import com.port.tally.management.adapter.TallyMachineAdapter;
import com.port.tally.management.adapter.TallyTeamAdapter;
import com.port.tally.management.adapter.ToAreaAdapter;
import com.port.tally.management.adapter.Trust1Adapter;
import com.port.tally.management.adapter.Trust2Adapter;
import com.port.tally.management.data.GetCornerPileData;
import com.port.tally.management.util.InstantAutoComplete;
import com.port.tally.management.work.AllCarryWork;
import com.port.tally.management.work.CodeCarryWork;
import com.port.tally.management.work.FromAreaNewWork;
import com.port.tally.management.work.GetAllocationAndConerWork;
import com.port.tally.management.work.GetAllocationDataWork;
import com.port.tally.management.work.GetCornerPileDataWork;
import com.port.tally.management.work.GetFlagAutoDataWork;
import com.port.tally.management.work.SubprocessNewFlagWork;
import com.port.tally.management.work.TallyDetailNewMountWork;
import com.port.tally.management.work.TallyDetailNewQualityWork;
import com.port.tally.management.work.TallyDetailTrustWork;
import com.port.tally.management.work.TallyDetail_MachineWork;
import com.port.tally.management.work.TallyDetail_teamWork;
import com.port.tally.management.work.TallySaveWork;
import com.port.tally.management.work.ToAreaNewWork;
import com.port.tally.management.work.ToallyDetailWork;
import com.port.tally.management.work.Trust1Work;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.global.GlobalApplication;
import org.mobile.library.model.work.WorkBack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by song on 2015/11/7.
 */
public class TallyDetail extends TabActivity {
    /**
     * @param args
     */
    //判断是否为场地标志，源，目的
    private String placeSflag="";
    private String placeEflag="";
    private String flagboats3="";

    private String flagboats1="";
    private String flagboats="";
    private String flagboate3="";

    private String flagboate1="";
    private String flagboate="";
    // 判断是否显示货位下拉列表
    private String allspinsflag="";
    private String allspineflag="";
    private String str="";
    //公司编码1
    private  String CodeCompany= "";
    //部门编码2
    private String CodeDepartment= "";
    //委托编码3
    private String Cgno= "";
    //派工编码4
    private String CodeOperationFact="";
    private String Pmno= "";
    //用户编码5
    private String CodeTallyman= "";
    //用户姓名6
    private String Tallyman= "";

    //源航次编码7
    private String Vgno= "";
    //源仓别8
    private String Cabin= "";
    //源车别代码9
    private String CodeCarrier= "";
    //源车号10
    private String CarrierNum= "";
    //源驳船船舶规范编码11
    private String CodeNvessel= "";
    //源驳船属性12
    private String Bargepro= "";
    //源场地编码13
    private String CodeStorage= "";
    //源货位编码14
    private String CodeBooth= "";
    //源桩角编码15
    private String CodeAllocation= "";
    //源载体描述16
    private String Carrier1= "";
    //源载体属性17
    private String Carrier1Num= "";
    //目的航次编码18
    private String VgnoLast= "";
    //目的仓别19
    private String CabinLast= "";
    //目的车别代码20
    private String CodeCarrierLast= "";
    //目的车号21
    private String CarrierNumLast= "";
    //目的驳船船舶规范编码22
    private String CodeNvesselLast="";
    //目的驳船属性23
    private String BargeproLast= "";
    //目的场地编码24
    private String CodeStorageLast= "";
    //目的货位编码25
    private String CodeBoothLast= "";
    //目的桩角编码26
    private String CodeAllocationLast= "";
    //目的货位26
    private String AllocationLast= "";
    //源货位26
    private String Allocation= "";
    //目的载体描述27
    private String Carrier2= "";
    //目的载体属性28
    private String Carrier2num= "";
    //票货编码29
    private String CodeGoodsBill= "";
    //票货描述30
    private String GoodsBillDisplay= "";
    //商务票货编码31
    private String CodeGbBusiness= "";
    //商务票货描述32
    private String GbBusinessDisplay= "";
    //子过程特殊标志编码33
    private String CodeSpecialMark= "";
    //源区域编码34
    private String CodeWorkingArea= "";
    //目的区域编码35
    private String CodeWorkingAreaLast= "";
    //质量36
    private String Quality= "";
    //件数1 37
    private String Amount= "";
    //重量1 38
    private String Weight= "";
    //数量1 39
    private String Count= "";
    //件数2 40
    private String Amount2= "";
    //    41
    private String Weight2= "";
    //    42
    private String Count2= "";
    //    43
    private String Machine= "";
    //    44
    private String WorkTeam="";
    //45 车数
    private String TrainNum= "";
    //
    private String Tbno= "";
    private String MarkFinish= "";
    private Button btn_save,btn_upload;
    private List<Map<String, Object>> dataList = null;
    private List<Map<String, Object>> dataListMachine =null;
    private List<Map<String, Object>> dataListTeam =null;
    private ListView listView1,listView2;
    private ImageView imgLeft;
    private int uploadflag = 0;
    private ProgressDialog progressDialog;
    private TallyMachineAdapter tallyMachineAdapter;
    private TallyTeamAdapter tallyTeamAdapter;
    private InstantAutoComplete flag_auto;
    private LinearLayout shangwu_ly,xiaozhang_ly,linear_show,linear_quality2,linear_quality1,linear_Ehuowei,linear_huowei;;
    private TextView title,tv_shipment,start,end,shipment,business,tv_messgae,tv_cardstate,tv_boatname,tv_quality
            ,tv_tvboatdetail,tv_changbie,tv_huowei,tv_huoweidetail,tv_Eboatname,tv_Eboatdetail,tv_Echangbie,tv_Ehuowei,tv_Ehuoweidetail, tv_changbiedetail,tv_Echangbiedetail;
    private Spinner entrust1_spinner,entrust2_spinner,flag_spinner,quality_spinner,toarea_spinner,fromarea_spinner;
    private Toast mToast;
    EditText et_count1,et_count2,et_count3,et_count21,et_count22,et_count23,et_vehicle;
    String[] value=null;
    // 定义5个记录当前时间的变量
    TabHost mTabHost = null;
    TabWidget mTabWidget = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tallydetailmain);
        Bundle b=getIntent().getExtras();
        value=b.getStringArray("detailString");
//                派工编码//                委托编码//                票货编码
        Log.i("value1的值是", value[0] + "/" + value[1] + "/" + value[2] + "/" + value[4]);
        dataList = new ArrayList<>();
        dataListMachine = new ArrayList<>();
        dataListTeam = new ArrayList<>();
        CodeCompany = GlobalApplication.getGlobal().getLoginStatus().getCodeCompany();//公司编码1
        CodeDepartment=  GlobalApplication.getGlobal().getLoginStatus().getCodeDepartment();//部门编码2
        Log.i(" CodeDepartment",""+CodeDepartment);
        CodeTallyman=  GlobalApplication.getGlobal().getLoginStatus().getUserID();
        Tallyman= GlobalApplication.getGlobal().getLoginStatus().getNickname();
        init();
        showProgressDialog();

        //委托编码3
        Cgno= value[1];
        //派工编码4
        Pmno= value[0];
        Tbno = value[4];
        Log.i("Tbno来自Value[2]", "" + Tbno + "/" + value[2]);
        loadFlagAutoData();
        loadTrust1Data();
        loadFlagData();
        initShipment();

        initMachine();
        initTeam();
        initAllCarrer();
        initAllCarrerE();

        loadToAreaData();
        Log.i("loadToAreaData1", "" + value[2]);
        initQuality();
        initMount();
        loadFromAreaData();
        if(value[3].equals("1")){
            btn_save.setClickable(false);
            btn_save.setBackgroundColor(Color.parseColor("#DCDCDC"));
            btn_upload.setClickable(false);
            btn_upload.setBackgroundColor(Color.parseColor("#DCDCDC"));
        }

    }
    //    加载标志数据
    private void loadFlagData(){
        SubprocessNewFlagWork subprocessesFlagWork = new SubprocessNewFlagWork();
        subprocessesFlagWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {
                        //绑定Adapter

                        SubprocessesFlagWorkAdapter subprocessesFlagWorkAdapter = new SubprocessesFlagWorkAdapter(maps, TallyDetail.this.getApplicationContext());
                        flag_spinner.setAdapter(subprocessesFlagWorkAdapter);
                        flag_spinner.setEnabled(false);
                        flag_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                HashMap map = (HashMap) parent.getItemAtPosition(position);
                                CodeSpecialMark = map.get("tv1").toString();
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
        Log.i("GetSavedSpecialMark",""+Tbno);
        subprocessesFlagWork.beginExecute(value[4]);
    }
    //    加载到区域数据
    private void loadToAreaData(){

        ToAreaNewWork toAreaWork = new ToAreaNewWork();
        toAreaWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {
                        //绑定Adapter
                        ToAreaAdapter toAreaAdapter = new ToAreaAdapter(maps, TallyDetail.this.getApplicationContext());
                        toarea_spinner.setAdapter(toAreaAdapter);
                        toarea_spinner = (Spinner) findViewById(R.id.toarea_spinner);
                        toarea_spinner.setEnabled(false);
                        toarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                HashMap map = (HashMap) parent.getItemAtPosition(position);
                                CodeWorkingArea = map.get("tv1").toString();
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

        toAreaWork.beginExecute(value[0],value[4],"1");
    }
    //    加载源区域数据
    private void loadFromAreaData(){
        FromAreaNewWork fromAreaWork = new FromAreaNewWork();
        fromAreaWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    if (!maps.equals("")) {
                        //绑定Adapter
                        FromAreaAdapter fromAreaAdapter = new FromAreaAdapter(maps, TallyDetail.this.getApplicationContext());
                        fromarea_spinner.setAdapter(fromAreaAdapter);
                        fromarea_spinner.setEnabled(false);
                        fromarea_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                HashMap map = (HashMap) parent.getItemAtPosition(position);
                                CodeWorkingAreaLast = map.get("tv1").toString();
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
//                    showToast("获取数据失败");
                }
            }
        });
        fromAreaWork.beginExecute(value[0],value[4],"0");
    }
    // 获取子过程数据

    private void loadFlagAutoData(){
        GetFlagAutoDataWork getFlagAutoDataWork = new GetFlagAutoDataWork();
        getFlagAutoDataWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, final List<Map<String, Object>> maps) {

                if(b && maps!= null){
                    Log.i("getFlagAutoDataWork1", "" + maps.get(0).get("tv2"));
                    flag_auto.setText(maps.get(0).get("tv2").toString());

                    initCodeCarrer(maps.get(0).get("tv3").toString());
                    CodeOperationFact=maps.get(0).get("tv3").toString();


                }else{

                    showToast("子过程数据不存在");
                }
            }});
        getFlagAutoDataWork.beginExecute(Pmno);
    }
    //    加载票货数据
    private void loadTrust1Data(){
        TallyDetailTrustWork trust1Work = new TallyDetailTrustWork();
        trust1Work.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    Log.i("loadTrust1Data加载的值是", "TallyDetail的loadTrust1Data加载的值是" + b + maps);
                    if (!maps.equals("")) {
                        //绑定Adapter
                        if (maps.get(0).get("amount2visible").toString().equals("1")) {
                            linear_show.setVisibility(View.VISIBLE);
                            et_count21.setEnabled(false);
                            et_count22.setEnabled(false);
                            et_count23.setEnabled(false);
                        } else {
                            linear_show.setVisibility(View.GONE);

                        }
//                        Log.i("maps.get(0).get(\"tv3\").toString().length()","maps.get(0).get(\"tv3\").toString().length()"+maps.get(0).get("tv3").toString().length());
                        if (!"".equals(maps.get(0).get("tv3").toString()) || maps.get(0).get("tv3").toString().length() != 1) {
                            xiaozhang_ly.setVisibility(View.VISIBLE);
                            Trust1Adapter trust1Adapter = new Trust1Adapter((List<Map<String, Object>>) maps.get(0).get("tv3"), TallyDetail.this.getApplicationContext());
                            entrust1_spinner.setAdapter(trust1Adapter);
                            entrust1_spinner.setEnabled(false);
                            entrust1_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
                                    HashMap map = (HashMap) parent.getItemAtPosition(position);
                                    CodeGoodsBill = map.get("tv2").toString();
                                    GoodsBillDisplay = map.get("tv3").toString();
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
                            Trust2Adapter trust2Adapter = new Trust2Adapter((List<Map<String, Object>>) maps.get(0).get("tv5"), TallyDetail.this.getApplicationContext());
                            entrust2_spinner.setAdapter(trust2Adapter);
                            entrust2_spinner.setEnabled(false);
                            entrust2_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view,
                                                           int position, long id) {
                                    HashMap map = (HashMap) parent.getItemAtPosition(position);
                                    CodeGbBusiness = map.get("tv4").toString();
                                    GbBusinessDisplay = map.get("tv5").toString();
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
        Log.i("trust1Work", "" + value[0] + "/" + value[1] + "/" + value[2]);
        trust1Work.beginExecute(value[0], value[1], value[4]);
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
        tallyDetailwork.beginExecute(value[0], value[1],value[2]);
    }
    private void initQuality(){
        TallyDetailNewQualityWork tallyDetailNewQualityWork = new TallyDetailNewQualityWork();
        tallyDetailNewQualityWork.setWorkEndListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean b, String s) {
                if (b) {
                        Log.i("tv_quality", "" + s);
                        tv_quality.setText(s);
                    Quality = tv_quality.getText().toString();
                } else {

                }
            }

        });
        Log.i("value2的值是", value[0] + "" + value[1] + "" + value[2] + "");
        tallyDetailNewQualityWork.beginExecute(value[4]);
    }

    private void initMount(){
        TallyDetailNewMountWork tallyDetailNewAmountWork = new TallyDetailNewMountWork();
        tallyDetailNewAmountWork.setWorkEndListener(new WorkBack<Map<String,String>>() {
            @Override
            public void doEndWork(boolean b, Map<String, String> sMap) {
                if (b) {

                    et_count3.setText(sMap.get("count1"));

                    et_count1.setText(sMap.get("acount1"));

                    et_count2.setText(sMap.get("weight1"));

                    et_count22.setText("acount2");

                    et_vehicle.setText(sMap.get("vehiclecount"));

                } else {
//                    showToast("");
                }
            }
        });
        Log.i("value2的值是", value[0] + "" + value[1] + "" + value[2] + "");
        tallyDetailNewAmountWork.beginExecute(value[4]);
    }
    private void initAllCarrer(){
        AllCarryWork allCarryWork = new AllCarryWork();
        allCarryWork.setWorkEndListener(new WorkBack<Map<String, Object>>() {
            @Override
            public void doEndWork(boolean b,  Map<String, Object> stringObjectMap) {
                if (b) {
                    Log.i("initAllCarrer()", "stringObjectMap的值是" + stringObjectMap);
                    if(!"".equals(stringObjectMap.get("Vgno"))){
                        Vgno =stringObjectMap.get("Vgno").toString();
                    }
                    if(!"".equals(stringObjectMap.get("VgDisplay"))){
                        tv_tvboatdetail.setText(stringObjectMap.get("VgDisplay").toString());
                        Carrier1 =stringObjectMap.get("VgDisplay").toString();
                    }
                    if(!"".equals(stringObjectMap.get("Cabin"))){
                        tv_changbiedetail.setText(stringObjectMap.get("Cabin").toString());
                        Cabin =stringObjectMap.get("Cabin").toString();}
                    if(!"".equals(stringObjectMap.get("CodeCarrier"))){
//                        tv_tvboatdetail.setText(stringObjectMap.get("CodeCarrier").toString());
                        CodeCarrier = stringObjectMap.get("CodeCarrier").toString();

                    }
                    if(!"".equals(stringObjectMap.get("Carrier"))){
                        Carrier1 =stringObjectMap.get("Carrier").toString();
                        tv_tvboatdetail.setText(stringObjectMap.get("Carrier").toString());
//                        CodeCarrier = stringObjectMap.get("CodeCarrier").toString();
//                        Carrier1 =stringObjectMap.get("CodeCarrier").toString();
                    }
                    if(!"".equals(stringObjectMap.get("Storage"))) {
                        tv_tvboatdetail.setText(stringObjectMap.get("Storage").toString());
                        CodeStorage = stringObjectMap.get("CodeStorage").toString();
                        Carrier1 = stringObjectMap.get("Storage").toString();
                    }
                    if(!"".equals(stringObjectMap.get("Nvessel"))){
                        tv_tvboatdetail.setText(stringObjectMap.get("Nvessel").toString());
                        CodeNvessel =stringObjectMap.get("CodeNvessel").toString();
                        Carrier1 = stringObjectMap.get("Nvessel").toString();}

                } else {
                }
            }

        });
        Log.i("value2的值是", value[0] + "" + value[1] + "" + value[2] + "");
        allCarryWork.beginExecute(value[0],"0");
    }
    private void initAllCarrerE(){
        AllCarryWork allCarryWork = new AllCarryWork();
        allCarryWork.setWorkEndListener(new WorkBack<Map<String, Object>>() {
            @Override
            public void doEndWork(boolean b,  Map<String, Object> stringObjectMap) {
                if (b) {
                    Log.i("initAllCarrer()", "stringObjectMap的值是" + stringObjectMap);
                    if(!"".equals(stringObjectMap.get("Vgno"))){
                        VgnoLast=stringObjectMap.get("Vgno").toString();

                    }
                    if(!"".equals(stringObjectMap.get("VgDisplay"))){
                        tv_Eboatdetail.setText(stringObjectMap.get("VgDisplay").toString());
                        Carrier2 =stringObjectMap.get("VgDisplay").toString();
                    }
                    if(!"".equals(stringObjectMap.get("Cabin"))){

                        CabinLast =stringObjectMap.get("Cabin").toString();
                        tv_Echangbiedetail.setText(stringObjectMap.get("Cabin").toString());
                    }
                    if(!"".equals(stringObjectMap.get("CodeCarrier"))){
//                        tv_Echangbiedetail.setText(stringObjectMap.get("CodeCarrier").toString());
                        CodeCarrierLast =stringObjectMap.get("CodeCarrier").toString();

                    }
                    if(!"".equals(stringObjectMap.get("Carrier"))){
                        Carrier2 =stringObjectMap.get("Carrier").toString();
                        tv_Eboatdetail.setText(stringObjectMap.get("Carrier").toString());
//                        CodeCarrier = stringObjectMap.get("CodeCarrier").toString();
//                        Carrier1 =stringObjectMap.get("CodeCarrier").toString();
                    }
                    if(!"".equals(stringObjectMap.get("Storage")))
                        tv_Eboatdetail.setText(stringObjectMap.get("Storage").toString());
                    CodeStorageLast =stringObjectMap.get("CodeStorage").toString();
                    Carrier2 =stringObjectMap.get("Storage").toString();
                    if(!"".equals(stringObjectMap.get("Nvessel")))
                        tv_Eboatdetail.setText(stringObjectMap.get("Nvessel").toString());
                    Carrier2 =stringObjectMap.get("Nvessel").toString();
                    CodeNvesselLast =stringObjectMap.get("CodeNvessel").toString();
                    Log.i("CodeOpstype",""+stringObjectMap.get("CodeNvessel").toString());

                } else {
                }
            }

        });
        Log.i("value2的值是", value[0] + "" + value[1] + "" + value[2] + "");
        allCarryWork.beginExecute(value[0],"1");
    }
    private void initCodeCarrer(String str){
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
                        flagboats = "1";
                        showToast(CodeCarriesS);
                        tv_boatname.setText("船舶航次");
                        tv_changbie.setText("舱别");
                        linear_huowei.setVisibility(View.GONE);
                        tv_changbiedetail.setVisibility(View.VISIBLE);
                        //源车别代码9
                        CodeCarrier= "";
                        //源车号10
                        CarrierNum= "";
                        //源驳船船舶规范编码11
                        CodeNvessel= "";
                        //源驳船属性12
                        Bargepro= "";
                        //源场地编码13
                        CodeStorage= "";
                        //源货位编码14
                        CodeBooth= "";
                        //源桩角编码15
                        CodeAllocation= "";
                    }else if(CodeCarriesS .equals("03")||CodeCarriesS .equals("04")||CodeCarriesS .equals("06")){
//                    03  、04、06  表示车、汽、集装箱
                        flagboats1 = "1";
                        tv_boatname.setText("车型");
                        linear_huowei.setVisibility(View.GONE);
                        tv_changbie.setText("车号");
                        tv_changbiedetail.setVisibility(View.VISIBLE);
                        //源航次编码7
                        Vgno= "";
                        //源仓别8
                        Cabin= "";
                        //源驳船船舶规范编码11
                        CodeNvessel= "";
                        //源驳船属性12
                        Bargepro= "";
                        //源场地编码13
                        CodeStorage= "";
                        //源货位编码14
                        CodeBooth= "";
                        //源桩角编码15
                        CodeAllocation= "";
                        showToast(CodeCarriesS);
                    }else if(CodeCarriesS .equals("05")){
//                        表示  驳船
                        flagboats3 = "1";
                        tv_boatname.setText("船名");
                        linear_huowei.setVisibility(View.GONE);
                        tv_changbie.setText("描述");
                        tv_changbiedetail.setVisibility(View.VISIBLE);
                        //源航次编码7
                        Vgno= "";
                        //源仓别8
                        Cabin= "";
                        //源车别代码9
                        CodeCarrier= "";
                        //源车号10
                        CarrierNum= "";
                        //源场地编码13
                        CodeStorage= "";
                        //源货位编码14
                        CodeBooth= "";
                        //源桩角编码15
                        CodeAllocation= "";
                        showToast(CodeCarriesS);
                    }else{
//                   表示  场地；
                        placeSflag ="1";
                        loadAllocationAndCornerData("0");
                        Log.i("placeSflag", "" + placeSflag);
                        tv_boatname.setText("场地");
                        tv_changbie.setText("桩角");
                        tv_huowei.setText("堆");
                        tv_changbie.setVisibility(View.VISIBLE);
                        //源航次编码7
                        Vgno= "";
                        //源仓别8
                        Cabin= "";
                        //源车别代码9
                        CodeCarrier= "";
                        //源车号10
                        CarrierNum= "";
                        //源驳船船舶规范编码11
                        CodeNvessel= "";
                        //源驳船属性12
                        Bargepro= "";
                        showToast(CodeCarriesS);
                    }
//                设置目的票货
                    if(CodeCarriesE.equals("02")){
//                      表示  船
                        flagboate = "1";
                        showToast(CodeCarriesE);
                        tv_Eboatname.setText("船舶航次");
                        tv_Echangbie.setText("车型");
                        tv_Echangbiedetail.setVisibility(View.VISIBLE);
                        linear_Ehuowei.setVisibility(View.GONE);
                        //目的车别代码20
                        CodeCarrierLast= "";
                        //目的车号21
                        CarrierNumLast= "";
                        //目的驳船船舶规范编码22
                        CodeNvesselLast="";
                        //目的驳船属性23
                        BargeproLast= "";
                        //目的场地编码24
                        CodeStorageLast= "";
                        //目的货位编码25
                        CodeBoothLast= "";
                        //目的桩角编码26
                        CodeAllocationLast= "";
                    }else if(CodeCarriesE.equals("03")||CodeCarriesE .equals("04")||CodeCarriesE .equals("06")){
//                    03  、04、06  表示车、汽、集装箱
                        tv_Eboatname.setText("车型");
                        tv_Echangbie.setText("车号");
                        flagboate1 = "1";
                        tv_Echangbiedetail.setVisibility(View.VISIBLE);
                        linear_Ehuowei.setVisibility(View.GONE);
                        //目的航次编码18
                        VgnoLast= "";
                        //目的仓别19
                        CabinLast= "";
                        //目的驳船船舶规范编码22
                        CodeNvesselLast="";
                        //目的驳船属性23
                        BargeproLast= "";
                        //目的场地编码24
                        CodeStorageLast= "";
                        //目的货位编码25
                        CodeBoothLast= "";
                        //目的桩角编码26
                        CodeAllocationLast= "";

                        showToast(CodeCarriesE);
                    }else if(CodeCarriesE.equals("05")){
//                        表示  驳船
                        flagboate3 = "1";
                        tv_Eboatname.setText("船名");
                        tv_Echangbie.setText("描述");
                        tv_Echangbiedetail.setVisibility(View.VISIBLE);
                        linear_Ehuowei.setVisibility(View.GONE);
                        //目的航次编码18
                        VgnoLast= "";
                        //目的仓别19
                        CabinLast= "";
                        //目的车别代码20
                        CodeCarrierLast= "";
                        //目的车号21
                        CarrierNumLast= "";
                        //目的场地编码24
                        CodeStorageLast= "";
                        //目的货位编码25
                        CodeBoothLast= "";
                        //目的桩角编码26
                        CodeAllocationLast= "";
                        showToast(CodeCarriesE);
                    }else{
//                   表示  场地；
                        placeEflag ="1";
                        Log.i("placeEflag", "" + placeEflag);
                        loadAllocationAndCornerData("1");
                        tv_Eboatname.setText("场地");
                        tv_Echangbie.setText("桩角");
                        tv_Ehuowei.setText("堆");
                        tv_Echangbie.setVisibility(View.VISIBLE);
                        tv_Echangbiedetail.setVisibility(View.VISIBLE);

                        //目的航次编码18
                        VgnoLast= "";
                        //目的仓别19
                        CabinLast= "";
                        //目的车别代码20
                        CodeCarrierLast= "";
                        //目的车号21
                        CarrierNumLast= "";
                        //目的驳船船舶规范编码22
                        CodeNvesselLast="";
                        //目的驳船属性23
                        BargeproLast= "";
                        showToast(CodeCarriesE);
                    }
                    progressDialog.dismiss();
                }else{

                }
            }
        });
        Log.i("value2的值是", value[0] + "" + value[1] + "" + value[2] + "");
        codeCarryWork.beginExecute(str);
    }
    //获取货位数据
    private void loadAllocationAndCornerData(final String CarriesType){
        GetAllocationAndConerWork getAllocationDataWork = new GetAllocationAndConerWork();
        getAllocationDataWork.setWorkEndListener(new WorkBack<Map<String, Object>>() {
            @Override
            public void doEndWork(boolean b, Map<String, Object> stringObjectMap) {
                if (b) {
                    if (stringObjectMap != null) {
                        if (CarriesType.equals("0")) {
                            tv_changbiedetail.setText(stringObjectMap.get("Booth").toString());
                            tv_huoweidetail.setText(stringObjectMap.get("Allocation").toString());
                            CodeBooth =stringObjectMap.get("Booth").toString();
                            //源货位26
                            Allocation= stringObjectMap.get("Allocation").toString();

                        }
                        if (CarriesType.equals("1")) {
                            tv_Echangbiedetail.setText(stringObjectMap.get("Booth").toString());
                            tv_Ehuoweidetail.setText(stringObjectMap.get("Allocation").toString());
                            AllocationLast= stringObjectMap.get("Allocation").toString();
                            CodeBoothLast =stringObjectMap.get("Booth").toString();
                        }
                    }
                }
            }
        });
            getAllocationDataWork.beginExecute(value[4],CarriesType);
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
                    tallyMachineAdapter = new TallyMachineAdapter(TallyDetail.this, dataListMachine);
                    listView1.setAdapter(tallyMachineAdapter);
                    setListViewHeightBasedOnChildren(listView1);
                } else {

                }
            }
        });
        tallyDetail_MachineWork.beginExecute(value[0],value[4]);
    }
    private void initTeam(){
        TallyDetail_teamWork tallyDetail_TeamWork = new  TallyDetail_teamWork();
        tallyDetail_TeamWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
            @Override
            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
                if (b) {
                    dataListTeam.addAll(maps);
                    Log.i("dataListinitTeam", "" + dataListTeam.toString());
                    Log.i("maps", "" + maps.toString());
                    listView2 = (ListView) findViewById(R.id.list2);
                    Log.i("dataListinitTeam", "" + dataListTeam.toString());
                    tallyTeamAdapter = new TallyTeamAdapter(TallyDetail.this, maps);
                    listView2.setAdapter(tallyTeamAdapter);
                    setListViewHeightBasedOnChildren(listView2);
                }
            }
        });
        tallyDetail_TeamWork.beginExecute(value[0],value[4]);
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
        et_count21=(EditText) findViewById(R.id.et_count21);
        et_count22=(EditText) findViewById(R.id.et_count22);
        et_count23=(EditText) findViewById(R.id.et_count23);
        et_vehicle =(EditText) findViewById(R.id.et_vehicle);
        entrust1_spinner = (Spinner)findViewById(R.id.entrust1_spinner);
        entrust2_spinner = (Spinner)findViewById(R.id.entrust2_spinner);
        linear_Ehuowei = (LinearLayout)findViewById(R.id.linear_Ehuowei);
        linear_huowei = (LinearLayout)findViewById(R.id.linear_huowei);
        flag_spinner = (Spinner)findViewById(R.id.flag_spinner);
        toarea_spinner= (Spinner)findViewById(R.id.toarea_spinner);
        fromarea_spinner= (Spinner)findViewById(R.id.fromarea_spinner);
        linear_quality2 =(LinearLayout)findViewById(R.id.linear_quality2);
        linear_quality1 =(LinearLayout)findViewById(R.id.linear_quality1);
        flag_auto= (InstantAutoComplete) findViewById(R.id.flag_auto);
        linear_quality1.setVisibility(View.GONE);
        linear_quality2.setVisibility(View.VISIBLE);
        tv_quality=(TextView)findViewById(R.id.tv_quality);
        shipment = (TextView)findViewById(R.id.shipment);
        business = (TextView)findViewById(R.id.business);
        shangwu_ly  = (LinearLayout)findViewById(R.id.entrust2);
        tv_messgae = (TextView)findViewById(R.id.tv_messgae);
        tv_boatname = (TextView)findViewById(R.id.tv_boatname);
        tv_tvboatdetail = (TextView)findViewById(R.id.tv_tvboatdetail);
        tv_changbie = (TextView)findViewById(R.id.tv_changbie);
        tv_changbiedetail = (TextView)findViewById(R.id.tv_changbiedetail);
        tv_huowei = (TextView)findViewById(R.id.tv_huowei);
        tv_huoweidetail = (TextView)findViewById(R.id.tv_huoweidetail);
        tv_Eboatname = (TextView)findViewById(R.id.tv_Eboatname);
        tv_Eboatdetail = (TextView)findViewById(R.id.tv_Eboatdetail);
        tv_Echangbie = (TextView)findViewById(R.id.tv_Echangbie);
        tv_Echangbiedetail = (TextView)findViewById(R.id.tv_Echangbiedetail);
        tv_Ehuowei = (TextView)findViewById(R.id.tv_Ehuowei);
        tv_Ehuoweidetail = (TextView)findViewById(R.id.tv_Ehuoweidetail);
        quality_spinner = (Spinner) findViewById(R.id.quality_spinner);
        quality_spinner.setVisibility(View.GONE);
        tv_cardstate = (TextView)findViewById(R.id.tv_cardstate);
        xiaozhang_ly = (LinearLayout)findViewById(R.id.entrust1);
        linear_show= (LinearLayout)findViewById(R.id.linear_show);
        et_count1.setInputType(InputType.TYPE_CLASS_PHONE);
        et_count2.setInputType(InputType.TYPE_CLASS_PHONE);
        et_count3.setInputType(InputType.TYPE_CLASS_PHONE);
        et_count21.setInputType(InputType.TYPE_CLASS_PHONE);
        et_count22.setInputType(InputType.TYPE_CLASS_PHONE);
        et_count23.setInputType(InputType.TYPE_CLASS_PHONE);
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
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                if (tabId.equals("班组")) {
                    str = tabId;
                    if (tallyMachineAdapter != null) {
                        tallyMachineAdapter.notifyDataSetChanged();
                        Log.i("tallyMachineAdapter", "" + tallyMachineAdapter.toString() + "/" + tallyMachineAdapter.isEmpty());
                    }

                    if (tallyTeamAdapter != null) {
                        tallyTeamAdapter.notifyDataSetChanged();
                        Log.i("tallyTeamAdapter", "" + tallyTeamAdapter.toString() + "/" + tallyTeamAdapter.isEmpty());
                    }
                    Log.i("dataListMachine", "" + dataListMachine + "/" + dataListMachine.toString());
                }
                if (tabId.equals("机械")) {
                    str = tabId;
                    if (tallyMachineAdapter != null) {
                        tallyMachineAdapter.notifyDataSetChanged();
                    }

                    if (tallyTeamAdapter != null) {
                        tallyTeamAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        imgLeft.setOnClickListener(new View.OnClickListener() {

            //			@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!placeSflag.equals("1")) {
                    if (flagboats3.equals("1")) {
                        Bargepro = tv_changbiedetail.getText().toString();
                        Carrier1Num = tv_changbiedetail.getText().toString();
                    }
                    if (flagboats1.equals("1")) {
                        CarrierNum = tv_changbiedetail.getText().toString();
                        Carrier1Num = tv_changbiedetail.getText().toString();
                    }
                    if (flagboats.equals("1")) {
                        Cabin = tv_changbiedetail.getText().toString();
                        Carrier1Num = tv_changbiedetail.getText().toString();
                    }

                }else{
                    Carrier1Num =CodeBooth;
                }

                if (!placeEflag.equals("1")) {
                    if (flagboate3.equals("1")){
                        Carrier2num=tv_Echangbiedetail.getText().toString();
                        BargeproLast = tv_Echangbiedetail.getText().toString();}
                    if (flagboate1.equals("1")){
                        Carrier2num=tv_Echangbiedetail.getText().toString();
                        CarrierNumLast = tv_Echangbiedetail.getText().toString();}
                    if (flagboate.equals("1")){
                        Carrier2num=tv_Echangbiedetail.getText().toString();
                        CabinLast = tv_Echangbiedetail.getText().toString();}
                }else{
                    Carrier2num =CodeBoothLast;
                }
                if(allspinsflag.equals("1")) CodeAllocation =tv_huoweidetail.getText().toString();
                if(allspineflag.equals("1")) CodeAllocationLast =tv_Ehuoweidetail.getText().toString();
                if(str.equals("班组")){
                    mTabHost.setCurrentTab(0);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mTabHost.setCurrentTab(1);
                        }
                    }, 1000);

                }
                else if(str.equals("机械")){
                    mTabHost.setCurrentTab(1);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mTabHost.setCurrentTab(0);
                        }
                    }, 1000);
                }
                if (tallyMachineAdapter != null) {
                    tallyMachineAdapter.notifyDataSetChanged();
                }
                if (tallyTeamAdapter != null) {
                    tallyTeamAdapter.notifyDataSetChanged();
                }

                final  Dialog dialog = new AlertDialog.Builder(TallyDetail.this).setTitle("提示").setMessage("确定暂存吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Log.i("dataListMachine", "" + dataListMachine.size());
                                //44
                                Machine = listmap_to_json_string(dataListMachine);
                                Weight = et_count1.getText().toString();
                                Amount = et_count2.getText().toString();
                                Count = et_count3.getText().toString();
                                Amount2 = et_count21.getText().toString();
                                Weight2 = et_count22.getText().toString();
                                Count2 = et_count23.getText().toString();
                                TrainNum = et_vehicle.getText().toString();
                                MarkFinish = "0";
//                                CodeWorkingAreaLast = fromarea_spinner.getSelectedItem().toString();
//                                CodeWorkingArea =toarea_spinner.getSelectedItem().toString();
                                Log.i("Machine", "" + Machine);
                                //43
                                WorkTeam = listmap_to_json_string(dataListTeam);

                                saveData("0");


                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).create();//创建按钮
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        dialog.show();
                    }
                }, 1000);

            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!placeSflag.equals("1")) {
                    if (flagboats3.equals("1")) {
                        Bargepro = tv_changbiedetail.getText().toString();
                        Carrier1Num = tv_changbiedetail.getText().toString();
                    }
                    if (flagboats1.equals("1")) {
                        CarrierNum = tv_changbiedetail.getText().toString();
                        Carrier1Num = tv_changbiedetail.getText().toString();
                    }
                    if (flagboats.equals("1")) {
                        Cabin = tv_changbiedetail.getText().toString();
                        Carrier1Num = tv_changbiedetail.getText().toString();
                    }

                }else{
                    Carrier1Num =CodeBooth;
                }

                if (!placeEflag.equals("1")) {
                    if (flagboate3.equals("1")){
                        Carrier2num=tv_Echangbiedetail.getText().toString();
                        BargeproLast = tv_Echangbiedetail.getText().toString();}
                    if (flagboate1.equals("1")){
                        Carrier2num=tv_Echangbiedetail.getText().toString();
                        CarrierNumLast = tv_Echangbiedetail.getText().toString();}
                    if (flagboate.equals("1")){
                        Carrier2num=tv_Echangbiedetail.getText().toString();
                        CabinLast = tv_Echangbiedetail.getText().toString();}
                }else{
                    Carrier2num =CodeBoothLast;
                }
                if(allspinsflag.equals("1")) CodeAllocation =tv_huoweidetail.getText().toString();
                if(allspineflag.equals("1")) CodeAllocationLast =tv_Ehuoweidetail.getText().toString();
                if(str.equals("班组")){
                    mTabHost.setCurrentTab(0);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mTabHost.setCurrentTab(1);
                        }
                    }, 1000);

                }
                else if(str.equals("机械")){
                    mTabHost.setCurrentTab(1);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mTabHost.setCurrentTab(0);
                        }
                    }, 1000);
                }
                if (tallyMachineAdapter != null) {
                    tallyMachineAdapter.notifyDataSetChanged();
                }
                if (tallyTeamAdapter != null) {
                    tallyTeamAdapter.notifyDataSetChanged();
                }

                final Dialog dialog = new AlertDialog.Builder(TallyDetail.this).setTitle("提示").setMessage("确定提交吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.i("dataListMachine",""+dataListMachine.size());
                                //44
                                Machine = listmap_to_json_string(dataListMachine);
                                Weight = et_count1.getText().toString();
                                Amount = et_count2.getText().toString();
                                Count = et_count3.getText().toString();
                                Amount2 = et_count21.getText().toString();
                                Weight2 = et_count22.getText().toString();
                                Count2 =  et_count23.getText().toString();
                                TrainNum =et_vehicle.getText().toString();
                                MarkFinish = "1";
//                                CodeWorkingAreaLast = fromarea_spinner.getSelectedItem().toString();
//                                CodeWorkingArea =toarea_spinner.getSelectedItem().toString();
                                Log.i("Machine", "" + Machine);
                                //43
                                WorkTeam =listmap_to_json_string(dataListTeam);

                                saveData("1");


                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        }).create();//创建按钮
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        dialog.show();
                    }
                }, 1000);


            }
        });}
    private void saveData(final String i){
        if (tallyMachineAdapter != null) {
            tallyMachineAdapter.notifyDataSetChanged();
        }
        if (tallyTeamAdapter != null) {
            tallyTeamAdapter.notifyDataSetChanged();
        }
        TallySaveWork tallySaveWork = new TallySaveWork();
        tallySaveWork.setWorkEndListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean b, String s) {
                if (b) {
                    showDialog(s);
                    if (i.equals("1")) {
                        Log.i("uploadflag的值", "" + uploadflag);
                        btn_save.setClickable(false);
                        btn_save.setBackgroundColor(Color.parseColor("#DCDCDC"));
                        btn_upload.setClickable(false);
                        btn_upload.setBackgroundColor(Color.parseColor("#DCDCDC"));
                        Log.i("uploadflag的值", "" + uploadflag);
                    }
                } else {
                    showDialog(s);
                    uploadflag = 0;
                    showToast(s);
                }
            }
        });
//   Allocation,AllocationLast
        tallySaveWork.beginExecute(CodeCompany, CodeDepartment,Cgno,Pmno,CodeTallyman,Tallyman,Vgno,Cabin,
                CodeCarrier,CarrierNum,CodeNvessel,Bargepro,CodeStorage,
                CodeBooth,CodeAllocation,Carrier1,Carrier1Num,VgnoLast,CabinLast,CodeCarrierLast,CarrierNumLast,
                CodeNvesselLast,BargeproLast,CodeStorageLast,CodeBoothLast,CodeAllocationLast,Carrier2,Carrier2num,
                CodeGoodsBill,GoodsBillDisplay,CodeGbBusiness,GbBusinessDisplay,CodeSpecialMark,
                CodeWorkingArea,CodeWorkingAreaLast,Quality,Amount,Weight,Count,Amount2,Weight2,
                Count2,Machine,WorkTeam,TrainNum,value[4],MarkFinish, Allocation, AllocationLast,CodeOperationFact);
    }
    /**
     * List<Map<String, Object>> To JsonString
     //     * @param list
     * @return
     */
    private void showDialog(String str) {
        Dialog dialog = new AlertDialog.Builder(TallyDetail.this).setTitle("提示").setMessage(str)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).create();//创建按钮

        dialog.show();
    }
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
        progressDialog = new ProgressDialog(TallyDetail.this);
        // 设置进度条风格，风格为圆形，旋转的
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("正在加载数据...");
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(true);
        // 让ProgressDialog显示
        progressDialog.show();
    }


}

