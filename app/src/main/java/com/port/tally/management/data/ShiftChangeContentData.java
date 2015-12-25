package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/12/16.
 */

import android.util.Log;

import com.port.tally.management.bean.ShiftChange;
import com.port.tally.management.util.CacheKeyUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取交接班内容的数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/12/16
 * @since 1.0
 */
public class ShiftChangeContentData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeContentData.";

    /**
     * 时间节点
     */
    private String time = null;

    /**
     * 用户编码
     */
    private String userCode = null;

    /**
     * 获取到的交接班信息列表
     */
    private List<ShiftChange> shiftChangeList = null;

    /**
     * 设置时间节点
     *
     * @param time 时间字符串
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 设置用户编码
     *
     * @param userCode 用户ID
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取交接班消息列表
     *
     * @return 交接班消息列表
     */
    public List<ShiftChange> getShiftChangeList() {
        return shiftChangeList;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        dataMap.put("Time", time);
        Log.i(LOG_TAG + "onFillRequestParameters", "Time is " + time);
        dataMap.put("CodeUser", userCode);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeUser is " + userCode);
    }

    @Override
    protected boolean onRequestResult(JSONObject handleResult) throws Exception {
        // 得到执行结果
        String resultState = handleResult.getString("IsSuccess");

        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
    }

    @Override
    protected String onRequestMessage(boolean result, JSONObject handleResult) throws Exception {
        return handleResult.getString("Message");
    }

    @Override
    protected void onRequestSuccess(JSONObject handleResult) throws Exception {

        if (!handleResult.isNull("Data")) {
            JSONArray jsonArray = handleResult.getJSONArray("Data");
            Log.i(LOG_TAG + "onRequestSuccess", "get shift change count is " + jsonArray.length());

            // 新建列表
            shiftChangeList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonRow = jsonArray.getJSONObject(i);

                // 一条数据
                ShiftChange shiftChange = new ShiftChange();

                shiftChange.setToken(jsonRow.getString("CodeToken"));
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

                // 加入列表
                shiftChangeList.add(shiftChange);

            }

            Log.i(LOG_TAG + "onRequestSuccess", "shift change list count is " + shiftChangeList
                    .size());
        }
    }
}
