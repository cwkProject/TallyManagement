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
 * Created by song on 2015/10/21.
 */
public class SubprocessesFlagData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "SubprocessesFlagData.";

    /**
     * 服务请求传入参数
     */


    public List<Map<String, Object>> getAll() {
        return all;
    }

    List<Map<String,Object>> all = new ArrayList<Map<String,Object>>() ;
    protected void onFillRequestParameters(Map<String, String> map) {
//        map.put("CodeCompany", companyCode);

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

            Log.i(LOG_TAG + "subprocessesFlagData的值", "get subprocessesFlagData count is " + jsonArray.length()+jsonArray.toString());


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray subprocessesFlagData = jsonArray.getJSONArray(i);

                if (subprocessesFlagData.length() > 2) {
                    // 一条委托数据
                    Map<String,Object> map = new HashMap<String,Object>() ;
                    map.put("tv1", subprocessesFlagData.getString(0));
                    map.put("tv2",subprocessesFlagData.getString(1));
                    map.put("tv3", subprocessesFlagData.getString(2));
                    // 添加到列表
                    all.add(map);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "subprocessesFlagData list count is " + all.size());
        }
    }

}
