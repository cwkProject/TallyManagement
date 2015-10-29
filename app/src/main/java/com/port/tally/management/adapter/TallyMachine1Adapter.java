package com.port.tally.management.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.port.tally.management.R;
import com.port.tally.management.activity.TallyActivity;
import com.port.tally.management.activity.TallyDetail;

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
        Map<String, Object> item = getItem(position);

        Log.i("item的值", "" + item);
        final Hand hand;
        if (convertView == null) {
            hand = new Hand();
            convertView = inflater.inflate(R.layout.machine_item, null);
            hand.ck_mac = (CheckBox) convertView.findViewById(R.id.im_mac);
            hand.tv_mac = (TextView) convertView.findViewById(R.id.tv_mac);
            hand.tv_start = (TextView) convertView.findViewById(R.id.tv_start);
            hand.tv_end = (TextView) convertView.findViewById(R.id.tv_end);
            hand.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            hand.tv_macpeo = (TextView) convertView.findViewById(R.id.tv_macpeo);

        } else {
            hand = (Hand) convertView.getTag();
        }
        final CharSequence[] itemsname = {"杨洋", "陈小春", "山鸡"};
        hand.tv_macpeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("请选择司机").setSingleChoiceItems(itemsname, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show();
                        hand.tv_macpeo.setText(itemsname[which]);
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });
        final CharSequence[] itemsmachine = {"机械1", "机械二", "机械三"};
        hand.tv_mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("请选择司机").setSingleChoiceItems(itemsmachine, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show();
                        hand.tv_mac.setText(itemsmachine[which]);
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });

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
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());
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
                        sb.append(timePicker.getCurrentHour())
                                .append(":").append(timePicker.getCurrentMinute());
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
        } if(!item.get("name").equals("")){
            hand.tv_macpeo.setText((CharSequence) item.get("name"));
        }
        if(!item.get("amount").equals("")){
            hand.tv_count.setText((CharSequence) item.get("amount"));
        }
        convertView.setTag(hand);
        return convertView;
    }
    private class Hand {
       CheckBox ck_mac;
        TextView tv_mac,tv_start,tv_end,tv_count,tv_macpeo;
    }

    private void showDialog(String str){
        Dialog dialog = new AlertDialog.Builder(this.context)
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
}
