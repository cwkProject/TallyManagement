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
 * Created by song on 2015/11/25.
 */
public class GetAllocationData  extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "GetAllocationData.";

    public void setSearchContent1(String searchContent1) {
        this.searchContent1 = searchContent1;
    }

    private String searchContent1 = null;
    public List<Map<String, Object>> getAll() {
        return all;
    }

    List<Map<String,Object>> all = new ArrayList<Map<String,Object>>() ;
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("CodeCompany", searchContent1);
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

            Log.i(LOG_TAG + "getAllocationData的值", "get getAllocationData count is " + jsonArray.length() + jsonArray.toString());


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray getAllocationData = jsonArray.getJSONArray(i);

                if (getAllocationData.length() > 1) {
                    // 一条委托数据
                    Map<String,Object> map = new HashMap<String,Object>() ;
                    map.put("tv1", getAllocationData.getString(0));
                    map.put("tv2",getAllocationData.getString(1));
                    map.put("tv3",getAllocationData.getString(1));
                    // 添加到列表
                    all.add(map);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "getAllocationData list count is " + all.size());
        }
    }

}