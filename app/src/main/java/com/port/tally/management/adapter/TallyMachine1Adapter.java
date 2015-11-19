package com.port.tally.management.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.port.tally.management.R;


import org.mobile.library.model.work.WorkBack;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/10/26.
 */
public class TallyMachine1Adapter extends BaseAdapter{

    private List<Map<String, Object>> data;
    private Context context;
    private LayoutInflater inflater;
    private String [] machineitem;//机械列表数据
    private String [] machineNameitem;//司机列表数据
//    修改后的值
    private List<Map<String, Object>> updata;
    private Map<String,String> upmap;
    public TallyMachine1Adapter(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    @Override
    public Map<String, Object> getItem(int position) {
        if (data != null && data.size() != 0) {
            return data.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final Map<String, Object> item = getItem(position);

//        Log.i("item的值", "" + item);
        final Hand hand;
        if (convertView == null) {
            hand = new Hand();
            convertView = inflater.inflate(R.layout.machine_item1, null);
            hand.ck_mac = (CheckBox) convertView.findViewById(R.id.im_mac);
            hand.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);
            hand.tv_start = (TextView) convertView.findViewById(R.id.tv_start);
            hand.tv_end = (TextView) convertView.findViewById(R.id.tv_end);
            hand.et_count1 = (EditText) convertView.findViewById(R.id.et_count1);
            hand.et_count2 = (EditText) convertView.findViewById(R.id.et_count2);
            hand.et_count3 = (EditText) convertView.findViewById(R.id.et_count3);
            hand.tv_macpeo = (TextView) convertView.findViewById(R.id.tv_macpeo);

        } else {
            hand = (Hand) convertView.getTag();
        }
        hand.et_count1.setInputType(InputType.TYPE_CLASS_PHONE);
        hand.et_count2.setInputType(InputType.TYPE_CLASS_PHONE);
        hand.et_count3.setInputType(InputType.TYPE_CLASS_PHONE);
        if(!hand.et_count1.getText().toString().equals("")){
            item.put("amount",hand.et_count1.getText().toString());
        }
        if(!hand.et_count2.getText().toString().equals("")){
            item.put("weight",hand.et_count2.getText().toString());
        }
        if(!hand.et_count3.getText().toString().equals("")){
            item.put("count",hand.et_count3.getText().toString());
        }
        if(!hand.tv_start.getText().toString().equals("")){
            item.put("begintime",hand.tv_start.getText().toString());
        }
        if(!hand.tv_end.getText().toString().equals("")){
            item.put("endtime",hand.tv_end.getText().toString());
        }
        hand.tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = View.inflate(context, R.layout.time_dialog, null);
                final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
                builder.setView(view);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                builder.setTitle("选择开始时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuffer sb = new StringBuffer();
                        if(timePicker.getCurrentHour().toString().length()<=1){
                            if(timePicker.getCurrentMinute().toString().length()<=1){
                                sb.append("0").append(timePicker.getCurrentHour()).append(":").append("0").append(timePicker.getCurrentMinute());
                            }else{
                                sb.append("0").append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());
                            }
                        }else{
                            if(timePicker.getCurrentMinute().toString().length()<=1){
                                sb.append(timePicker.getCurrentHour()).append(":").append("0").append(timePicker.getCurrentMinute());
                            }else{
                                sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());
                            }
                        }
                        hand.tv_start.setText(sb);

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        hand.tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = View.inflate(context, R.layout.time_dialog, null);
                final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
                builder.setView(view);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                builder.setTitle("选择结束时间");
                builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringBuffer sb = new StringBuffer();
                        if(timePicker.getCurrentHour().toString().length()<=1){
                            if(timePicker.getCurrentMinute().toString().length()<=1){
                                sb.append("0").append(timePicker.getCurrentHour()).append(":").append("0").append(timePicker.getCurrentMinute());
                            }else{
                                sb.append("0").append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());
                            }
                        }else{
                            if(timePicker.getCurrentMinute().toString().length()<=1){
                                sb.append(timePicker.getCurrentHour()).append(":").append("0").append(timePicker.getCurrentMinute());
                            }else{
                                sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());
                            }

                        }

                        hand.tv_end.setText(sb);


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        hand.ck_mac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    data.get(position).put("select", "1");
                } else {
                    data.get(position).put("select", "0");
                }
            }
        });
        if(item.get("select").equals("1")){
            hand.ck_mac.setChecked(true);}
        if(item.get("select").equals("0")){
            hand.ck_mac.setChecked(false);}
        if(!item.get("machine").equals("")){
            hand.tv_mac.setText((CharSequence) item.get("machine"));
        }
        if(!item.get("amount").equals("")){
            hand.et_count1.setText((CharSequence) item.get("amount"));
        }
        if(!item.get("weight").equals("")){
            hand.et_count2.setText((CharSequence) item.get("weight"));
        }
        if(!item.get("count").equals("")){
            hand.et_count3.setText((CharSequence) item.get("count"));
        }
        if(!item.get("name").equals("")){
            hand.tv_macpeo.setText((CharSequence) item.get("name"));
        }
        if(!item.get("begintime").equals("")){
            if(item.get("begintime").toString().length()==4){
            Log.i("begintime的值",""+item.get("begintime").toString());
            hand.tv_start.setText(item.get("begintime").toString().substring(0,2)+":"+item.get("begintime").toString().substring(2,4));}
        }
        if(!item.get("endtime").equals("")){
            if(item.get("endtime").toString().length()==4){
            Log.i("endtime的值",""+item.get("endtime").toString());
            Log.i("endtime的值",""+item.get("endtime").toString().substring(0,2)+":"+item.get("endtime").toString().substring(2,4));
            hand.tv_end.setText(item.get("endtime").toString().substring(0,2)+":"+item.get("endtime").toString().substring(2,4));}
        }

        if(!"".equals(item.get("pmno"))){
//            initMachine(item.get("pmno").toString());
//            initMachineName(item.get("pmno").toString());
        }

        convertView.setTag(hand);
        return convertView;
    }
    private class Hand {
       CheckBox ck_mac;
        TextView tv_mac,tv_start,tv_end,tv_macpeo;
        EditText et_count1,et_count2,et_count3;
    }
//    private void initMachine(String str){
//        TallyDetailMachineWork tallyDetailMachineWork = new TallyDetailMachineWork();
//        tallyDetailMachineWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
//            @Override
//            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
//                if (b) {
//                    String [][] machinearry = new String[2][maps.size()];
//                     machineitem =new String[maps.size()];
//                    for(int i =0; i<maps.size();i++){
//                        machinearry[0][i]=(String)maps.get(i).get("code_machine");
//                        machinearry[1][i]=(String)maps.get(i).get("machine");
//                        machineitem[i]=(String)maps.get(i).get("machine");
//                    }
//                    Log.i("machineitem[i]","machineitem[i]的值"+machineitem.toString());
//                    Log.i("maps", "" + maps.toString());
//
//
//
//
//                }
//            }
//        });
//        tallyDetailMachineWork.beginExecute(str);
//    }
//    private void initMachineName(String str){
//        TallyDetailMachineNameWork tallyDetailMachineNameWork = new TallyDetailMachineNameWork();
//        tallyDetailMachineNameWork.setWorkEndListener(new WorkBack<List<Map<String, Object>>>() {
//            @Override
//            public void doEndWork(boolean b, List<Map<String, Object>> maps) {
//                if (b) {
//                    String [][] machineNamearry = new String[2][maps.size()];
//                    machineNameitem =new String[maps.size()];
//                    for(int i =0; i<maps.size();i++){
//                        machineNamearry[0][i]=(String)maps.get(i).get("workno");
//                        machineNamearry[1][i]=(String)maps.get(i).get("name");
//                        machineNameitem[i]=(String)maps.get(i).get("name");
//                    }
//                    Log.i("machinenameitem[i]","machinenameitem[i]的值"+machineNameitem.toString());
//                    Log.i("maps", "" + maps.toString());
//                }
//            }
//        });
//        tallyDetailMachineNameWork.beginExecute(str);
//    }
}
