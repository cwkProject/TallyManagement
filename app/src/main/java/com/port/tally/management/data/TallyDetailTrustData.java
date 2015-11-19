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
 * Created by song on 2015/11/18.
 */
public class TallyDetailTrustData extends JsonDataModel {

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

    public void setSearchContent2(String searchContent2) {
        this.searchContent2 = searchContent2;
    }

    private String searchContent2 = null;
    public List<Map<String, Object>> getAll() {
        return all;
    }

    List<Map<String,Object>> all = new ArrayList<Map<String,Object>>() ;
    List<Map<String ,Object>> goodsBill1 = new ArrayList<Map<String ,Object>>();
    List<Map<String ,Object>> goodsBill2 = new ArrayList<Map<String ,Object>>();
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        // 传入请求参数
        dataMap.put("Pmno", searchContent);
        dataMap.put("Cgno", searchContent1);
        dataMap.put("Tbno", searchContent2);

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
            map.put("amount2visible", jsonResult.getString("Amount2Visible"));
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
                        Map<String,Object> goodsBill1map = new HashMap<String,Object>() ;
                        Log.i("trustjsonArray", "trustjsonArray.getJSONArray(i)" + trustjsonArray.getJSONArray(i) + "/" + trustjsonArray.getJSONArray(i).length());
                        // 一条委托数据
                        goodsBill1map.put("tv2", trustjsonArray.getJSONArray(i).get(0));
                        goodsBill1map.put("tv3", trustjsonArray.getJSONArray(i).getString(1));
                        goodsBill1.add(goodsBill1map);
                        Log.i("goodsBill1map的值是", "goodsBill1map的值是" + goodsBill1map.size() + "/" + goodsBill1map);
                    }
                    Log.i("goodsBill1的值是", "goodsBill1的值是" + goodsBill1.size() +"/"+goodsBill1);
                    map.put("tv3", goodsBill1);
                    all.add(map);
                    Log.i("jsonResult.map", "jsonResult.map的值is " + map);
                }
            }
            else {
                map.put("tv3", "");
                all.add(map);
            }
            if (!jsonResult.isNull("GoodsBill2")) {
                Log.i("jsonResult.isNull(\"GoodsBill2\")", "jsonResult.isNull(\"GoodsBill2\") 的值is " + jsonResult.isNull("GoodsBill2"));
                JSONArray trustjson2Array = jsonResult.getJSONArray("GoodsBill2");
                Log.i("jsonResult24.getJSONObject(\"GoodsBill2\")", "" + jsonResult.getJSONArray("GoodsBill2"));
                if (trustjson2Array != null) {
                    Log.i("trustjsonArray2", "trustjsonArray 的值 is " + jsonResult.getJSONArray("GoodsBill2"));
                    for (int i = 0; i < trustjson2Array.length(); i++) {
                        Map<String,Object> goodsBill2map = new HashMap<String,Object>() ;
                        Log.i("trustjsonArray", "trustjsonArray.getJSONArray(i)" + trustjson2Array.getJSONArray(i) + "/" + trustjson2Array.getJSONArray(i).length());
                        // 一条委托数据
                        goodsBill2map.put("tv4", trustjson2Array.getJSONArray(i).get(0));
                        goodsBill2map.put("tv5", trustjson2Array.getJSONArray(i).getString(1));
                        goodsBill2.add(goodsBill2map);
                        Log.i("goodsBill2map的值是", "goodsBill2map的值是" + goodsBill2map.size() + "/" + goodsBill2map);
                    }
                    Log.i("goodsBill2的值是", "goodsBill2的值是" + goodsBill2.size() +"/"+goodsBill2);
                    map.put("tv5", goodsBill2);
                    all.add(map);
                    Log.i("jsonResult.map", "jsonResult.map的值is " + map);
                }
            }
            else {
                map.put("tv5", "");
                all.add(map);
            }

        }
    }

}
