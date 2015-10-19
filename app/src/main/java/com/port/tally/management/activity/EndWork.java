package com.port.tally.management.activity;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.adapter.EndWorkTeamAutoAdapter;
import com.port.tally.management.bean.StartWorkBean;
import com.port.tally.management.util.FloatTextToast;
import com.port.tally.management.util.InstantAutoComplete;
import com.port.tally.management.work.EndWorkAutoTeamWork;
import com.port.tally.management.work.StartWorkWork;
import com.port.tally.management.work.UploadEndWork;

import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.LoginStatus;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//
/**
 * Created by song on 2015/9/29.
 */
public class EndWork extends Activity {
    private static final int msgKey1 = 1;
    private InstantAutoComplete teamAuto;
    EndWorkTeamAutoAdapter endWorkTeamAutoAdapter;
    private  List<Map<String, Object>> dataList=null;
    private String ID;
    private Toast mToast;
    private TextView tv_vehiclenum, tv_note,tv_boatname, tv_huodai, tv_huowu ,tv_place,tv_huowei,tv_port,tv_loader,tv_task,tv_balanceweight,tv_subtime;
    private EditText tongxin_edt,et_count;
    private Button tongxing_search_btn, endWork_btn;
    private TextView tv_entime;
    private TextView title;
    private ImageView imgLeft;
    private Spinner spinner_over ;
    private String spinner_overText;
    private PopupWindow startPopupWindow;
    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    //NFC部分
    private TextView tv_cardnum;
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private NdefMessage mNdefPushMessage;
    private AlertDialog mDialog;
    private Button card_btn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.endwork);
        init();

        new TimeThread().start();
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void init() {

        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        tv_entime = (TextView) findViewById(R.id.tv_entime);
        tongxing_search_btn = (Button) findViewById(R.id.search_btn);
        tongxin_edt = (EditText) findViewById(R.id.tongxin_edt);
        tongxin_edt.setInputType(InputType.TYPE_CLASS_NUMBER);
        card_btn = (Button) findViewById(R.id.card_btn);
        tv_cardnum = (TextView) findViewById(R.id.tv_cardnum);
        tv_vehiclenum = (TextView) findViewById(R.id.tv_vehiclenum);
        tv_boatname = (TextView) findViewById(R.id.tv_boatname);
        tv_huodai = (TextView) findViewById(R.id.tv_huodai);
        tv_huowu = (TextView) findViewById(R.id.tv_huowu);
        tv_place =(TextView) findViewById(R.id.tv_place);
        tv_huowei=(TextView) findViewById(R.id.tv_huowei);
        tv_port=(TextView) findViewById(R.id.tv_port);
        tv_loader=(TextView)findViewById(R.id.tv_loader);
        tv_task =(TextView)findViewById(R.id.tv_task);
        et_count = (EditText) findViewById(R.id.et_count);
        tv_note =(TextView) findViewById(R.id.tv_note);
        tv_balanceweight=(TextView) findViewById(R.id.tv_balanceweight);
        tv_subtime=(TextView) findViewById(R.id.tv_subtime);
        tv_note.setText(LoginStatus.getLoginStatus().getNickname());
        teamAuto =(InstantAutoComplete)findViewById(R.id.et_group);
        dataList = new ArrayList<>();
        spinner_over =(Spinner)findViewById(R.id.spinner_over);

          loadValue("010111");
        Log.i("dataList的值",dataList.toString());
        endWork_btn = (Button) findViewById(R.id.save_btn);
        title.setText("完工");
        tv_entime.setText("选择时间");
        title.setVisibility(View.VISIBLE);
        imgLeft.setVisibility(View.VISIBLE);
        View popupView = getLayoutInflater().inflate(R.layout.datepopupwin, null);
        DatePicker datePicker = (DatePicker) popupView.findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker) popupView.findViewById(R.id.timePicker);
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        startPopupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams
                .WRAP_CONTENT, true);
        startPopupWindow.setTouchable(true);
        startPopupWindow.setOutsideTouchable(true);
        startPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//        返回事件
        imgLeft.setOnClickListener(new View.OnClickListener() {

            //				@Override
            public void onClick(View arg0) {
                finish();
            }
        });
        //开工时间点击事件
        tv_entime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startPopupWindow.showAsDropDown(v);
            }
        });
        endWork_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner_overText = spinner_over.getSelectedItem().toString();
                long sysTime = System.currentTimeMillis();
                CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                uploadValue(spinner_overText, teamAuto.getText().toString(),et_count.getText().toString(),sysTimeStr.toString(),ID, LoginStatus.getLoginStatus().getNickname());

            }
        });
        //通行证点击事件
        tongxing_search_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String tongxingKey = tongxin_edt.getText().toString();

                if (validate(tongxingKey,v)) {
                    String type ="CARD";
                    String company= "77";
                    initValue(tongxingKey,type,company);
                };

            }
        });

        // 为TimePicker指定监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view
                    , int hourOfDay, int minute) {
                EndWork.this.hour = hourOfDay;
                EndWork.this.minute = minute;
                showDate(year, month, day, hour, minute);

            }
        });

        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day) {
                EndWork.this.year = year;
                EndWork.this.month = month;
                EndWork.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day, hour, minute);
            }
        });
        //NFC
        resolveIntent(getIntent());
        mDialog = new AlertDialog.Builder(EndWork.this).setNeutralButton("Ok", null).create();
        mAdapter = NfcAdapter.getDefaultAdapter(EndWork.this);
        if (mAdapter == null) {
            showMessage(R.string.error, R.string.no_nfc);

            finish();
            return;
        }
        ;
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        mNdefPushMessage = new NdefMessage(new NdefRecord[]{newTextRecord(
                "Message from NFC Reader :-)", Locale.ENGLISH, true)});
        card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null) {
                    if (!mAdapter.isEnabled())
                        showWirelessSettingsDialog();

                };
                String tongxingKey = tv_cardnum.getText().toString();
                if (validate(tongxingKey,v)) {
                    String type ="NFC";
                    String company= "77";
                    initValue(tongxingKey,type,company);
                };
            }
        });
        //NFC
    }
    // 定义在EditText中显示当前日期、时间的方法
    private void showDate(int year, int month, int day, int hour, int minute) {
        tv_entime.setText(+year + "年" + (month + 1) + "月" + day + "日" + hour + "时" + minute + "分");
    }



    //给个控件赋值
    private void loadValue( String company) {

        //实例化，传入参数
        EndWorkAutoTeamWork endWorkAutoTeamWork = new EndWorkAutoTeamWork();

        endWorkAutoTeamWork.setWorkBackListener(new WorkBack<List<Map<String, Object>>>() {

            public void doEndWork(boolean b, List<Map<String, Object>> data) {



                if (b) {
                    dataList.addAll(data);
                    Log.i("dataList1的值", dataList.toString());
                    Log.i("data的值", data.toString());
                    endWorkTeamAutoAdapter = new EndWorkTeamAutoAdapter( dataList, EndWork.this.getApplicationContext());
                    teamAuto.setAdapter(endWorkTeamAutoAdapter);
                    teamAuto.setThreshold(0);  //设置输入一个字符 提示，默认为2
                    teamAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Map<String, Object> pc = dataList.get(position);
                            teamAuto.setText(pc.get("tv1") + " " + pc.get("tv2"));
                        }
                    });

                    // Clear autocomplete
                    teamAuto.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           teamAuto.setText("");
                       }
                   });
                } else {


                }
                endWorkTeamAutoAdapter.notifyDataSetChanged();
            }

        });
        endWorkAutoTeamWork.beginExecute(company);
    }
    //NFC部分
    private void showMessage(int title, int message) {
        mDialog.setTitle(title);
        mDialog.setMessage(getText(message));
        mDialog.show();
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private NdefRecord newTextRecord(String text, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));

        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = text.getBytes(utfEncoding);

        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);

        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                showWirelessSettingsDialog();
            }
            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
            mAdapter.enableForegroundNdefPush(this, mNdefPushMessage);
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
            mAdapter.disableForegroundNdefPush(this);
        }
    }

    private void showWirelessSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.nfc_disabled);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

                mDialog.dismiss();
            }
        });
        builder.create().show();
        return;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                Parcelable tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                Tag tag1 = (Tag) tag;
                byte[] id1 = tag1.getId();

                tv_cardnum.setText(getHex(id1).toUpperCase());
                String type ="NFC";
                String company= "77";
                initValue(getHex(id1).toUpperCase(), type, company);
            }


        }
    }

    private String getHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= bytes.length - 1; i++) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }


        return sb.toString();
    }

    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }

    //给个控件赋值
    private void initValue(String key, final String type,String company) {

        //实例化，传入参数
        StartWorkWork startWorkWork = new StartWorkWork();

        startWorkWork.setWorkBackListener(new WorkBack<StartWorkBean>() {

            public void doEndWork(boolean b, StartWorkBean startWorkBean) {
                if (b) {
                    tv_vehiclenum.setText(startWorkBean.getVehicleNum());
                    tv_boatname.setText(startWorkBean.getBoatName());
                    tv_huodai.setText(startWorkBean.getForwarder());
                    tv_huowu.setText(startWorkBean.getCargo());
                    tv_place.setText(startWorkBean.getPlace());
                    tv_huowei.setText(startWorkBean.getAllocation());
                    tv_port.setText(startWorkBean.getSetport());
                    tv_loader.setText(startWorkBean.getLoader());
                    tv_task.setText(startWorkBean.getTask());
                    ID =startWorkBean.getId().toString();
                    tv_balanceweight.setText(startWorkBean.getStrWeight());
                    tv_subtime.setText(startWorkBean.getStrSubmittime());
                    if (type.equals("NFC")) tongxin_edt.setText(startWorkBean.getCardNo());
                    Log.i("idzhi", "" + startWorkBean.getId());
                    //如果开始时间值不为空，记录人也不为空，则设置记录人为不可编辑状态
//                    tv_startTime.setText(startWorkBean.getStartTime());
//                    et_noteperson.setText(startWorkBean.getNotePerson());

                } else {
                    tv_vehiclenum.setText("");
                    tv_boatname.setText("");
                    tv_huodai.setText("");
                    tv_huowu.setText("");
                    tv_place.setText("");
                    tv_huowei.setText("");
                    tv_port.setText("");
                    if (type.equals("NFC")) tongxin_edt.setText("");
                    tv_loader.setText("");
                    tv_task.setText("");
                    clearEnd();
                    showToast(startWorkBean.getMessage());
                }
            }
        });
        startWorkWork.beginExecute(key, company, type);

//公司、
    }

    //给个控件赋值
    private void uploadValue(String key,String company,String count,String time,String team,String teamwork) {

        //实例化，传入参数
        UploadEndWork uploadEndWork = new UploadEndWork();
        uploadEndWork.setWorkBackListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean b, String s) {
                if(b&& s.equals("IsSuccess")){
                    showDialog("提交成功");

                }
                else {
                    showDialog("提交失败");
                }
            }
        });

        uploadEndWork.beginExecute(key,company,count,time,team,teamwork);


    }
    private void clearEnd() {
        tv_entime.setText("请选择时间");
        teamAuto.setText("");
    }



    //判断输入框是否为空
    private boolean validate(String Keycontent, View view) {

        if (Keycontent == null || Keycontent.equals("")) {

            showDialog("请输入通行证号");
            return false;
        }
        return true;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msgKey1:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                    tv_entime.setText(sysTimeStr);
                    break;
                default:
                    break;
            }
        }
    };

    public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
    private void showDialog(String str){
        Dialog dialog = new AlertDialog.Builder(EndWork.this)
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

