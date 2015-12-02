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
 * Created by song on 2015/12/1.
 */
public class GetAllocatinAndCornerData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "GetAllocatinAndCornerData.";

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    /**
     * 服务请求传入参数
     */
    private String searchContent = null;

    public void setSearchContent1(String searchContent1) {
        this.searchContent1 = searchContent1;
    }

    private String searchContent1 = null;

    public Map<String, Object> getAll() {
        return all;
    }

    private Map<String, Object> all;

    protected void onFillRequestParameters(Map<String, String> dataMap) {
        // 传入请求参数
        dataMap.put("CarriesType", searchContent);
        dataMap.put("Tbno", searchContent1);
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

        JSONObject jsonResult = jsonObject.getJSONObject("Data");
        all = new HashMap<String, Object>();
        if (jsonResult instanceof JSONObject) {
            all.put("Booth", jsonResult.getString("Booth"));
            all.put("Allocation", jsonResult.getString("Allocation"));
        }
        Log.i("GetAllocatinAndCornerData的值是", "GetAllocatinAndCornerData的值是" + all);
    }
}