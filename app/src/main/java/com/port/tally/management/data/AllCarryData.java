package com.port.tally.management.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 2015/10/27.
 */
public class AllCarryData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "AllCarryData.";

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

    private Map<String,Object> all;
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        // 传入请求参数
        dataMap.put("Pmno", searchContent);
        dataMap.put("CarriesType", searchContent1);
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
            all = new HashMap<String,Object>() ;
        if(jsonResult instanceof JSONObject){
            all.put("VgDisplay", jsonResult.getString("VgDisplay"));
            all.put("Vgno",jsonResult.getString("Vgno"));
            all.put("Cabin", jsonResult.getString("Cabin"));
            all.put("CodeCarrier",jsonResult.getString("CodeCarrier"));
            all.put("Nvessel", jsonResult.getString("Nvessel"));
            all.put("CodeNvessel",jsonResult.getString("CodeNvessel"));
            all.put("Storage", jsonResult.getString("Storage"));
            all.put("CodeStorage",jsonResult.getString("CodeStorage"));
            all.put("CodeOpstype",jsonResult.getString("CodeOpstype"));
            all.put("Carrier",jsonResult.getString("Carrier"));
        }
        Log.i("allCarry的值是", "allCarry的值是" + all);
    }
}