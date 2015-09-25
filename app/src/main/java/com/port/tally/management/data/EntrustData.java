package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/25.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 委托数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/25
 * @since 1.0
 */
public class EntrustData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "EntrustData.";

    /**
     * 委托数据
     */
    private Map<String, String> entrust = null;

    /**
     * 委托ID
     */
    private String entrustId = null;

    /**
     * 设置委托ID
     *
     * @param entrustId ID
     */
    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    /**
     * 获取委托详情
     *
     * @return 委托
     */
    public Map<String, String> getEntrust() {
        return entrust;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("Cgno", entrustId);
        Log.i(LOG_TAG + "onFillRequestParameters", "Cgno is " + entrustId);
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

        // 结果数据
        JSONObject data = jsonObject.getJSONObject("Data");

        String[] order = data.getString("Order").split("[+]");

        entrust = new LinkedHashMap<>();

        for (String key : order) {
            entrust.put(key, data.getString(key));
        }
    }
}
