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
public class FromAreaData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "FromAreaData.";

    public String getSearchContent1() {
        return searchContent1;
    }

    public void setSearchContent1(String searchContent1) {
        this.searchContent1 = searchContent1;
    }

    public String getSearchContent2() {
        return searchContent2;
    }

    public void setSearchContent2(String searchContent2) {
        this.searchContent2 = searchContent2;
    }

    /**
     * 服务请求传入参数
     */
    private String searchContent1 = null;
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

            Log.i(LOG_TAG + "FromAreaData的值", "get FromAreaData count is " + jsonArray.length() + jsonArray.toString());


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray fromAreaData = jsonArray.getJSONArray(i);

                if (fromAreaData.length() > 1) {
                    // 一条委托数据
                    Map<String,Object> map = new HashMap<String,Object>() ;
                    map.put("tv1", fromAreaData.getString(0));
                    map.put("tv2",fromAreaData.getString(1));

                    // 添加到列表
                    all.add(map);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "FromAreaData list count is " + all.size());
        }
    }

}
