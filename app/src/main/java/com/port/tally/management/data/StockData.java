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
 * 堆存数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/10/13
 * @since 1.0
 */
public class StockData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "StockData.";

    /**
     * 堆存数据
     */
    private Map<String, String> stock = null;

    /**
     * 堆存编码
     */
    private String stockId = null;

    /**
     * 设置堆存编码
     *
     * @param stockId 堆存编码
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    /**
     * 获取堆存详情
     *
     * @return 堆存集合
     */
    public Map<String, String> getStock() {
        return stock;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("gbno", stockId);
        Log.i(LOG_TAG + "onFillRequestParameters", "gbno is " + stockId);
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

        stock = new LinkedHashMap<>();

        for (String key : order) {
            stock.put(key, data.getString(key));
        }
    }
}