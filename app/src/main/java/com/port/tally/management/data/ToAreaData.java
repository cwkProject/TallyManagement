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
public class ToAreaData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ToAreaData.";

    /**
     * 服务请求传入参数
     */

    private String searchContent1 = null;
    public void setSearchContent2(String searchContent2) {
        this.searchContent2 = searchContent2;
    }
    public void setSearchContent1(String searchContent1) {
        this.searchContent1 = searchContent1;
    }
    private String searchContent2 = null;
    public List<Map<String, Object>> getAll() {
        return all;
    }
    List<Map<String,Object>> all = new ArrayList<Map<String,Object>>() ;
    protected void onFillRequestParameters(Map<String, String> map) {
         map.put("Pmno", searchContent1);
        map.put("CodeCarries", searchContent2);
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

            Log.i(LOG_TAG + "ToAreaData的值", "get toAreaData count is " + jsonArray.length() + jsonArray.toString());


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray toAreaData = jsonArray.getJSONArray(i);

                if (toAreaData.length() > 1) {
                    // 一条委托数据
                    Map<String,Object> map = new HashMap<String,Object>() ;
                    map.put("tv1", toAreaData.getString(0));
                    map.put("tv2",toAreaData.getString(1));
                    // 添加到列表
                    all.add(map);
                }
            }
            Log.i(LOG_TAG + "onRequestSuccess", "toAreaData list count is " + all.size());
        }
    }

}
