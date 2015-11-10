package com.port.tally.management.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/11/7.
 */
public class TallyManageTwoData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TallyManageData.";
    private  String Cargo =null;

    public void setPmno(String pmno) {
        Pmno = pmno;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    private  String Pmno =null;
    /**
     * 服务请求传入参数  起始行+行数+公司编码
     */



    public List<Map<String, Object>> getAll() {
        return all;
    }

    List<Map<String,Object>> all = new ArrayList<Map<String,Object>>() ;
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("Pmno", Pmno);
        map.put("Cgno", Cargo);

    }

    @Override
    protected boolean onRequestResult(JSONObject jsonObject) throws JSONException {
        // 得到执行结果
        String resultState = jsonObject.getString("IsSuccess");

        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
    }

    @Override
    protected String onRequestMessage(boolean b, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("Message");
    }

    @Override
    protected void onRequestSuccess(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("Data");

        if (jsonArray != null) {

            Log.i(LOG_TAG + "onRequestSuccess", "get TallyManagetwo count is " + jsonArray.length());

//            理货单编码+源票货描述+目的票货描述+源载体描述+目的载体描述+委托号+数量+作业票提交标志
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray jsonEntrust = jsonArray.getJSONArray(i);

                if (jsonEntrust.length() > 7) {
                    // 一条委托数据
                    Map<String,Object> map = new HashMap<String,Object>() ;
//                    派工编码
                    map.put("Tbno", jsonEntrust.getString(0));
//                    委托编码
                    map.put("Gbdisplay", jsonEntrust.getString(1));
//                    票货编码
                    map.put("GbdisplayLast", jsonEntrust.getString(2));
//                    委托人
                    map.put("Carrier1", jsonEntrust.getString(3));
//                    委托号
                    map.put("Carrier2", jsonEntrust.getString(4));
//                    货物
                    map.put("Taskno", jsonEntrust.getString(5));
//                    计划作业量
                    map.put("Count",jsonEntrust.getString(6));
                    map.put("MarkFinish",jsonEntrust.getString(7));
                    // 添加到列表
                    all.add(map);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "TallyManageTwo list count is " + all.size());
        }
    }

}
