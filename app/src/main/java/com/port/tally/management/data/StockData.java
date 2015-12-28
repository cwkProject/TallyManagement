package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/25.
 */

import android.util.Log;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 堆存数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/10/13
 * @since 1.0
 */
public class StockData extends SimpleJsonDataModel {

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
     * 货场编码
     */
    private String storageCode = null;

    /**
     * 货位编码
     */
    private String positionCode = null;

    /**
     * 设置堆存编码
     *
     * @param stockId 堆存编码
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    /**
     * 设置货场编码
     *
     * @param storageCode 货场编码
     */
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    /**
     * 设置货位编码
     *
     * @param positionCode 货位编码
     */
    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
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
        map.put("CodeStorage", storageCode);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeStorage is " + storageCode);
        map.put("CodeBooth", positionCode);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeBooth is " + positionCode);
    }

    @Override
    protected void onExtractData(JSONObject jsonData) throws Exception {
        // 结果数据
        JSONObject data = jsonData.getJSONObject("Data");

        String[] order = data.getString("Order").split("[+]");

        stock = new LinkedHashMap<>();

        for (String key : order) {
            stock.put(key, data.getString(key));
        }
    }
}
