package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.util.Log;

import com.port.tally.management.bean.Voyage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 航次列表任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class VoyageListData extends SimpleJsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "VoyageListData.";

    /**
     * 航次列表
     */
    private List<Voyage> voyageList = null;

    /**
     * 获取航次列表
     *
     * @return 航次列表
     */
    public List<Voyage> getVoyageList() {
        return voyageList;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {

    }

    @Override
    protected void onExtractData(JSONObject jsonData) throws Exception {
        JSONArray jsonArray = jsonData.getJSONArray("Data");
        Log.i(LOG_TAG + "onExtractData", "get voyageList count is " + jsonArray.length());

        // 新建列表
        voyageList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONArray jsonRow = jsonArray.getJSONArray(i);

            if (jsonRow.length() > 2) {
                // 一条数据
                Voyage voyage = new Voyage();
                voyage.setId(jsonRow.getString(0));
                voyage.setName(jsonRow.getString(1));
                voyage.setShortCode(jsonRow.getString(2));

                // 加入列表
                voyageList.add(voyage);
            }
        }

        Log.i(LOG_TAG + "onExtractData", "voyage list count is " + voyageList.size());
    }
}
