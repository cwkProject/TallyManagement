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
public class Trust1Data extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "Trust1Data.";

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public void setSearchContent1(String searchContent1) {
        this.searchContent1 = searchContent1;
    }

    /**
     * 服务请求传入参数
     */
    private String searchContent = null;
    private String searchContent1 = null;

    public List<Map<String, Object>> getAll() {
        return all;
    }

    List<Map<String,Object>> all = new ArrayList<Map<String,Object>>() ;
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        // 传入请求参数
        dataMap.put("Pmno", searchContent);
        dataMap.put("Cgno", searchContent1);

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
//        JSONArray jsonArray = jsonObject.getJSONArray("");
        JSONObject jsonResult = jsonObject.getJSONObject("Data");
        Map<String,Object> map = new HashMap<String,Object>() ;
        if(jsonResult instanceof JSONObject){

            map.put("amount2visible",jsonResult.getString("Amount2Visible"));
            map.put("GoodsBill1Name",jsonResult.getString("GoodsBill1Name"));
            map.put("GoodsBill2Name", jsonResult.getString("GoodsBill2Name"));
            Log.i("amount2visible", "amount2visible is " + jsonResult.getString("Amount2Visible"));
            if (!jsonResult.isNull("GoodsBill1")) {
                Log.i("jsonResult.isNull(\"GoodsBill1\")", "jsonResult.isNull(\"GoodsBill1\") 的值is " + jsonResult.isNull("GoodsBill1"));
            JSONArray trustjsonArray = jsonResult.getJSONArray("GoodsBill1");
            Log.i("jsonResult23.getJSONObject(\"GoodsBill1\")", "" + jsonResult.getJSONArray("GoodsBill1"));
                if (trustjsonArray != null) {
                    Log.i("trustjsonArray", "trustjsonArray 的值 is " + jsonResult.getJSONArray("GoodsBill1"));
                    for (int i = 0; i < trustjsonArray.length(); i++) {
                        JSONArray trust1Data = trustjsonArray.getJSONArray(i);

                        if (trust1Data.length() > 1) {
                            // 一条委托数据
                            map.put("tv2", trust1Data.getString(0));
                            map.put("tv3", trust1Data.getString(1));
                            // 添加到列表
                            all.add(map);
                        }
                    }
                }

            }
            else {
                map.put("tv3", "");
                all.add(map);
            }

            if (!jsonResult.isNull("GoodsBill2")) {
                JSONArray trust1jsonArray = jsonResult.getJSONArray("GoodsBill2");
                if(trust1jsonArray != null){
                    Log.i("trustjsonArray", "trustjsonArray 的值 is " + jsonResult.getJSONArray("GoodsBill2"));
                    for(int i = 0; i< trust1jsonArray.length();i++){
                        JSONArray trust2Data = trust1jsonArray.getJSONArray(i);

                        if (trust2Data.length() > 1) {
                            // 一条委托数据
                            map.put("tv4", trust2Data.getString(0));
                            map.put("tv5",trust2Data.getString(1));
                            // 添加到列表
                            all.add(map);
                        }
                    }
                }
            }
            else{
                Log.i("jsonResult.isNull(\"GoodsBill2\")", "jsonResult.isNull(\"GoodsBill2\") 的值is " + jsonResult.isNull("GoodsBill2"));
                map.put("tv5","");
                all.add(map);
            }

        }
    }

}
