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
 * Created by song on 2015/10/14.
 */
public class WorkPlanData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "WorkPlanData.";

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

                Log.i(LOG_TAG + "onRequestSuccess", "JSONArray is " + jsonEntrust);
                if (jsonEntrust.length() > 5) {
                    // 一条委托数据
                    Log.i(LOG_TAG + "onRequestSuccess", "now is " + i);
                    Map<String,Object> map = new HashMap<String,Object>() ;
                    map.put("tv_cargoname", jsonEntrust.getString(5));
                    map.put("tv_starttime", jsonEntrust.getString(3));
                    map.put("tv_end", jsonEntrust.getString(4));
                    map.put("tv_trustno", jsonEntrust.getString(0));
                    map.put("tv_bowei", jsonEntrust.getString(1));
                    map.put("tv_process", jsonEntrust.getString(2));
//                    map.put("tv_cargo", jsonEntrust.getString(0));
//                    map.put("cgno", jsonEntrust.getString(1));
//                    map.put("gbno", jsonEntrust.getString(2));
//                    map.put("tv_purposecargo", jsonEntrust.getString(3));
//                    map.put("tv_operationprocess", jsonEntrust.getString(4));
//                    map.put("tv_starttime", jsonEntrust.getString(5));
//                    map.put("tv_terminaltime", jsonEntrust.getString(6));
//                    map.put("tv_destinationvector", jsonEntrust.getString(7));
//                    map.put("tv_terminaltime", jsonEntrust.getString(8));
//                    map.put("tv_sourcecargo", jsonEntrust.getString(9));
//                    map.put("tv_planwork",jsonEntrust.getString(10));
//                    map.put("tv_sourcevector", jsonEntrust.getString(11));
//                    map.put("client", jsonEntrust.getString(12));
//                    map.put("taskno", jsonEntrust.getString());

                    // 添加到列表
                    all.add(map);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "TallyManage list count is " + all.size());
        }
    }

}
