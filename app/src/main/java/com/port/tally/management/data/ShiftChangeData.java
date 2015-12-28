package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/12/24.
 */

import android.util.Log;

import com.port.tally.management.bean.ShiftChange;
import com.port.tally.management.util.CacheKeyUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于获取指定交接班记录的数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/12/24
 * @since 1.0
 */
public class ShiftChangeData extends SimpleJsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeData.";

    /**
     * 交接班记录
     */
    private ShiftChange shiftChange = null;

    /**
     * 消息标识
     */
    private String token = null;

    /**
     * 获取交接班记录
     *
     * @return 交接班记录
     */
    public ShiftChange getShiftChange() {
        return shiftChange;
    }

    /**
     * 设置要获取的交接班记录标识
     *
     * @param token 记录标识
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        dataMap.put("CodeToken", token);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeToken is " + token);
    }

    @Override
    protected void onExtractData(JSONObject jsonData) throws Exception {
        JSONObject jsonRow = jsonData.getJSONObject("Data");

        // 一条数据
        shiftChange = new ShiftChange();

        shiftChange.setToken(token);
        shiftChange.setSend(jsonRow.getString("UserNameFirst"));
        shiftChange.setReceive(jsonRow.getString("UserNameSecond"));
        shiftChange.setTime(jsonRow.getString("CreateTime"));
        shiftChange.setContent(jsonRow.getString("Text"));

        if (!jsonRow.isNull("PICURL")) {
            // 图片地址集合
            Map<String, String> map = new HashMap<>();

            JSONArray array = jsonRow.getJSONArray("PICURL");
            for (int j = 0; j < array.length(); j++) {
                map.put(CacheKeyUtil.getRandomKey(), array.getString(j));
            }

            shiftChange.setImageUrlList(map);
        }
        if (!jsonRow.isNull("VOICEURL")) {

            // 地址集合
            Map<String, String> map = new HashMap<>();

            JSONArray array = jsonRow.getJSONArray("VOICEURL");

            for (int j = 0; j < array.length(); j++) {
                map.put(CacheKeyUtil.getRandomKey(), array.getString(j));
            }

            shiftChange.setAudioUrlList(map);
        }
    }
}