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
 * Created by song on 2015/10/3.
 */
public class TallyManageData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TallyManageData.";

    /**
     * 服务请求传入参数  起始行+行数+公司编码
     */
    private String companyCode = null;
    private  String Cargo =null;

    public void setTaskNo(String taskNo) {
        TaskNo = taskNo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    private String TaskNo=null;
    public void setEndCount(String endCount) {
        this.endCount = endCount;
    }

    public void setStartCount(String startCount) {
        this.startCount = startCount;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    private String startCount =null;
    private String endCount = null;

    public List<Map<String, Object>> getAll() {
        return all;
    }

    List<Map<String,Object>> all = new ArrayList<Map<String,Object>>() ;
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("CodeCompany", companyCode);
        map.put("startRow", startCount);
        map.put("count", endCount);
        map.put("Cargo", Cargo);
        map.put("TaskNo", TaskNo);
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

            Log.i(LOG_TAG + "onRequestSuccess", "get TallyManage count is " + jsonArray.length());


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray jsonEntrust = jsonArray.getJSONArray(i);

                if (jsonEntrust.length() > 11) {
                    // 一条委托数据
                    Map<String,Object> map = new HashMap<String,Object>() ;
//                    派工编码
                    map.put("pmno", jsonEntrust.getString(0));
//                    委托编码
                    map.put("tv_Entrust", jsonEntrust.getString(1));
//                    票货编码
                    map.put("gbno", jsonEntrust.getString(2));
//                    委托人
                    map.put("tv_consignor", jsonEntrust.getString(3));
//                    委托号
                    map.put("taskno", jsonEntrust.getString(4));
//                    货物
                    map.put("tv_cargo", jsonEntrust.getString(5));
//                    计划作业量
                    map.put("tv_planwork",jsonEntrust.getString(7));
//                    源载体
                    map.put("tv_sourcevector", jsonEntrust.getString(10));
//                    源票货
                    map.put("tv_sourcecargo", jsonEntrust.getString(12));
//                    目的票货
                    map.put("tv_purposecargo", jsonEntrust.getString(13));
//                    作业过程
                    map.put("tv_operationprocess", jsonEntrust.getString(6));
//                    开始时间
                    map.put("tv_starttime", jsonEntrust.getString(8));
//                    结束时间
                    map.put("tv_terminaltime", jsonEntrust.getString(9));
//                    目的载体
                    map.put("tv_destinationvector", jsonEntrust.getString(11));
                    // 添加到列表
                    all.add(map);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "TallyManage list count is " + all.size());
        }
    }

}
