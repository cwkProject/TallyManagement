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
public class TallyCagoAtoData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TallyCagoAtoData.";

    /**
     * 服务请求传入参数  起始行+行数+公司编码
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

            Log.i(LOG_TAG + "TalltcagoData的值", "get TalltcagoDataData count is " + jsonArray.length()+jsonArray.toString());


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray jsonEntrust = jsonArray.getJSONArray(i);

                if (jsonEntrust.length() > 2) {
                    // 一条委托数据
                    Map<String,Object> map = new HashMap<String,Object>() ;
                    map.put("tv1", jsonEntrust.getString(0));
                    map.put("tv2",jsonEntrust.getString(1));
                    map.put("tv3", jsonEntrust.getString(2));
                    // 添加到列表
                    all.add(map);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "EndWorkAutoTeamData list count is " + all.size());
        }
    }

}
