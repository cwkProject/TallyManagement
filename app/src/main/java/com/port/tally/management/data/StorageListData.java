package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.util.Log;

import com.port.tally.management.bean.Storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 货场列表任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/10/12
 * @since 1.0
 */
public class StorageListData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "StorageListData.";

    /**
     * 货代列表
     */
    private List<Storage> storageList = null;

    /**
     * 公司编码
     */
    private String company = null;

    /**
     * 获取货代列表
     *
     * @return 货代列表
     */
    public List<Storage> getStorageList() {
        return storageList;
    }

    /**
     * 设置公司编码
     *
     * @param company 公司编码
     */
    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        dataMap.put("CodeCompany", company);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeCompany is " + company);
    }

    @Override
    protected boolean onRequestResult(JSONObject jsonResult) throws JSONException {
        // 得到执行结果
        String resultState = jsonResult.getString("IsSuccess");

        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
    }

    @Override
    protected String onRequestMessage(boolean result, JSONObject jsonResult) throws JSONException {
        return jsonResult.getString("Message");
    }

    @Override
    protected void onRequestSuccess(JSONObject jsonResult) throws JSONException {
        JSONArray jsonArray = jsonResult.getJSONArray("Data");

        if (jsonArray != null) {

            Log.i(LOG_TAG + "onRequestSuccess", "get storageList count is " + jsonArray.length());

            // 新建货主列表
            storageList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray jsonRow = jsonArray.getJSONArray(i);

                if (jsonRow.length() > 2) {
                    // 一条货主数据
                    Storage storage = new Storage();
                    storage.setId(jsonRow.getString(0));
                    storage.setName(jsonRow.getString(1));
                    storage.setShortCode(jsonRow.getString(2));
                    storage.setCompany(company);

                    // 加入列表
                    storageList.add(storage);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "storage list count is " + storageList.size());
        }
    }
}
