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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.bean.StartWorkBean;
import com.port.tally.management.data.UpdataStartData;
import com.port.tally.management.util.FloatTextToast;
import com.port.tally.management.work.StartWorkWork;
import com.port.tally.management.work.UpDateStartWork;
import com.port.tally.management.work.UploadEndWork;
import com.port.tally.management.work.VerifyStartWork;
import com.port.tally.management.work.VerifyVehicleWork;

import org.mobile.library.model.work.IWorkEndListener;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.LoginStatus;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by song on 2015/9/29.
 */
public class StartWork extends Activity {
    private static final int msgKey1 = 1;
    private Toast mToast;
    private String ID;
    private TextView tv_vehiclenum, tv_Recordtime,et_noteperson,tv_boatname, tv_huodai, tv_huowu ,tv_place,tv_huowei,tv_port,tv_loader,tv_task,tv_balanceweight,tv_subtime;
    private EditText tongxin_edt;
    private Button tongxing_search_btn, startWork_btn;
    private TextView tv_startTime;
    private TextView title;
    private ImageView imgLeft;
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
        setContentView(R.layout.startwork);
        init();
        new TimeThread().start();
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    private void init() {
        title = (TextView) findViewById(R.id.title);
        imgLeft = (ImageView) findViewById(R.id.left);
        tv_startTime = (TextView) findViewById(R.id.tv_starttime);
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
        tv_Recordtime =(TextView)findViewById(R.id.tv_Recordtime);
        tv_task =(TextView)findViewById(R.id.tv_task);
        tv_balanceweight=(TextView)findViewById(R.id.tv_balanceweight);
        tv_subtime=(TextView)findViewById(R.id.tv_subtime);
        et_noteperson = (TextView) findViewById(R.id.et_noteperson);
        startWork_btn = (Button) findViewById(R.id.startWork_btn);
        title.setText("开工");
        et_noteperson.setText(LoginStatus.getLoginStatus().getNickname());
        tv_startTime.setText("选择时间");
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
        tv_startTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startPopupWindow.showAsDropDown(v);
            }
        });

        //通行证点击事件
        tongxing_search_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String tongxingKey = tongxin_edt.getText().toString();

                if (validate(tongxingKey,v)) {
                    String type ="CARD";
                    String company= "77";
                    Log.i("initValue(tongxingKey,type,company)",""+tongxingKey+type+company);
                    verifyvehicle(tongxingKey, type, company);

                };

            }
        });
        startWork_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (validate(key,v)) {
                    String type ="CARD";
                    String company= "77";
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                    tv_startTime.setText(sysTimeStr);
                   verifyStart(ID, LoginStatus.getLoginStatus().getNickname(), sysTimeStr.toString());
            }
        });
        // 为TimePicker指定监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view
                    , int hourOfDay, int minute) {
                StartWork.this.hour = hourOfDay;
                StartWork.this.minute = minute;
                showDate(year, month, day, hour, minute);

            }
        });

        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day) {
                StartWork.this.year = year;
                StartWork.this.month = month;
                StartWork.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day, hour, minute);
            }
        });
        //NFC
        resolveIntent(getIntent());
        mDialog = new AlertDialog.Builder(StartWork.this).setNeutralButton("Ok", null).create();
        mAdapter = NfcAdapter.getDefaultAdapter(StartWork.this);
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
                    verifyvehicle(tongxingKey, type, company);
                };
            }
        });
        //NFC
    }
    // 定义在EditText中显示当前日期、时间的方法
    private void showDate(int year, int month, int day, int hour, int minute) {
        tv_startTime.setText(+year + "年" + (month + 1) + "月" + day + "日" + hour + "时" + minute + "分");
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
                String company = "77";

                verifyvehicle(getHex(id1).toUpperCase(), type, company);

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
//校验车辆是否黑名单
//给个控件赋值
private void verifyvehicle( final String key, final String type, final String company) {

    //实例化，传入参数
    VerifyVehicleWork verifyVehicleWork = new VerifyVehicleWork();

    verifyVehicleWork.setWorkEndListener(new WorkBack<StartWorkBean>() {

        public void doEndWork(boolean b, StartWorkBean startWorkBean) {
            if (b) {
                if (startWorkBean.getMessage() != null || !"".equals(startWorkBean.getMessage()) || !startWorkBean.getMessage().equals("null")) {
                    Dialog dialog = new AlertDialog.Builder(StartWork.this)
                            .setTitle("提示")
                            .setMessage(startWorkBean.getMessage())
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            initValue(key, type, company);
                                        }
                                    }).create();//创建按钮

                    dialog.show();
                }
                ;

                Log.i("verifyvehicle1", "" + startWorkBean.getMessage());


            } else {
                if (!"".equals(startWorkBean.getMessage())) {
                    showDialog(startWorkBean.getMessage());
                    Log.i("verifyvehicle2", "" + startWorkBean.getMessage());
                }

            }
        }
    });
    verifyVehicleWork.beginExecute(key, company, type);
}
    //校验开工

    private void verifyStart( final String key, final String type, final String company) {

        //实例化，传入参数
        VerifyStartWork verifyStartWork = new VerifyStartWork();

        verifyStartWork.setWorkEndListener(new WorkBack<StartWorkBean>() {

            public void doEndWork(boolean b, StartWorkBean startWorkBean) {
                if (b) {

                    startWorkBean.notifyAll();
                    Dialog dialog = new AlertDialog.Builder(StartWork.this)
                            .setTitle("提示")
                            .setMessage(startWorkBean.getMessage())
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            long sysTime = System.currentTimeMillis();
                                            CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);

                                            uploadValue(ID, LoginStatus.getLoginStatus().getNickname(), sysTimeStr.toString());
                                        }
                                    }).create();//创建按钮

                    dialog.show();

                } else {
                    if(startWorkBean.getMessage()!= null)
                        showDialog(startWorkBean.getMessage());;
                }
            }
        });
        verifyStartWork.beginExecute(key, company, type);
    }
    //给个控件赋值
    private void initValue(String key, final String type,String company) {

        //实例化，传入参数
       StartWorkWork startWorkWork = new StartWorkWork();

        startWorkWork.setWorkEndListener(new WorkBack<StartWorkBean>() {

            public void doEndWork(boolean b, StartWorkBean startWorkBean) {
                if (b) {
                    clearStart();
                    if(!startWorkBean.getVehicleNum().equals("")) tv_vehiclenum.setText(startWorkBean.getVehicleNum());
                    if(!startWorkBean.getBoatName().equals(""))tv_boatname.setText(startWorkBean.getBoatName());
                    if(!startWorkBean.getForwarder().equals(""))tv_huodai.setText(startWorkBean.getForwarder());
                    if(!startWorkBean.getCargo().equals(""))tv_huowu.setText(startWorkBean.getCargo());
                    if(!startWorkBean.getPlace().equals(""))tv_place.setText(startWorkBean.getPlace());
                    if(!startWorkBean.getAllocation().equals(""))tv_huowei.setText(startWorkBean.getAllocation());
                    if(!startWorkBean.getSetport().equals(""))tv_port.setText(startWorkBean.getSetport());
                    if(!startWorkBean.getLoader().equals(""))tv_loader.setText(startWorkBean.getLoader());
                    if(!startWorkBean.getTask().equals(""))tv_task.setText(startWorkBean.getTask());
                    if(!startWorkBean.getStrWeight().equals(""))tv_balanceweight.setText(startWorkBean.getStrWeight());
                    if(!startWorkBean.getStrSubmittime().equals(""))tv_subtime.setText(startWorkBean.getStrSubmittime());
                    if(!startWorkBean.getStrRecordtime().equals(""))tv_Recordtime.setText(startWorkBean.getStrRecordtime());
                    if (type.equals("NFC")) tongxin_edt.setText(startWorkBean.getCardNo());
                    if(!startWorkBean.getId().equals("")) ID =startWorkBean.getId().toString();
                    Log.i("idzhi",""+startWorkBean.getId());

                } else {
                    clearStart();
                    if(!"".equals(startWorkBean.getVehicleNum()))
                        Log.i("tv_vehiclenum",""+startWorkBean.getVehicleNum());
                        tv_vehiclenum.setText(startWorkBean.getVehicleNum());
                    if(!"".equals(startWorkBean.getBoatName()))
                        tv_boatname.setText(startWorkBean.getBoatName());
                    if(!"".equals(startWorkBean.getForwarder()))
                        tv_huodai.setText(startWorkBean.getForwarder());
                    if(!"".equals(startWorkBean.getCargo()))
                        tv_huowu.setText(startWorkBean.getCargo());
                    if(!"".equals(startWorkBean.getPlace()))
                        tv_place.setText(startWorkBean.getPlace());
                    if(!"".equals(startWorkBean.getAllocation()))
                        tv_huowei.setText(startWorkBean.getAllocation());
                    if(!"".equals(startWorkBean.getAllocation()))
                        tv_port.setText(startWorkBean.getAllocation());
                    if(!"".equals(startWorkBean.getLoader()))
                        tv_loader.setText(startWorkBean.getLoader());
                    if(!"".equals(startWorkBean.getTask()))
                        tv_task.setText(startWorkBean.getTask());
                    if(!"".equals(startWorkBean.getStrWeight()))
                        tv_balanceweight.setText(startWorkBean.getStrWeight());
                    if(!"".equals(startWorkBean.getStrSubmittime()))
                        tv_subtime.setText(startWorkBean.getStrSubmittime());
                    if(!"".equals(startWorkBean.getMessage()))
                        showDialog(startWorkBean.getMessage());
                    Log.i("initValuegetMessage", "" + startWorkBean.getMessage());




                }
            }
        });
        startWorkWork.beginExecute(key, company, type);


    }
    //给个控件赋值
    private void uploadValue(String key,String company,String count) {

        //实例化，传入参数
        UpDateStartWork updataStartWork = new UpDateStartWork();
        updataStartWork.setWorkEndListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean b, String s) {
                if (b) {
                    showDialog(s);

                } else {
                    showDialog(s);
                }
            }
        });
        updataStartWork .beginExecute(key, company, count);
    }
    private void clearStart() {
        tv_startTime.setText("请选择时间");
        tv_vehiclenum.setText("");
       tv_boatname.setText("");
       tv_huodai.setText("");
       tv_huowu.setText("");
        tv_place.setText("");
        tv_huowei.setText("");
        tv_port.setText("");
        tv_loader.setText("");
        tv_task.setText("");
        tv_balanceweight.setText("");
        tv_subtime.setText("");
        tv_Recordtime.setText("");



    }



    //判断输入框是否为空
    private boolean validate(String Keycontent, View view) {

        if (Keycontent == null || Keycontent.equals("")) {
            showDialog("请输入卡号");

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
                    tv_startTime.setText(sysTimeStr);
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
        Dialog dialog = new AlertDialog.Builder(StartWork.this)
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
    private void showDialog1(String str){
        Dialog dialog = new AlertDialog.Builder(StartWork.this)
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
