package com.port.tally.management.data;

/**
 * Created by song on 2015/10/1.
 */

import android.util.Log;

import com.port.tally.management.bean.MapBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "MapData.";

    /**
     * 货主列表
     */
    private List<MapBean> mapBeanList = null;

    /**
     * 获取货主列表
     *
     * @return 货主列表
     */
    public List<MapBean> getMapBeanList() {
        return mapBeanList;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {

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

            Log.i(LOG_TAG + "onRequestSuccess", "get mapBeanList count is " + jsonArray.length
                    ());

            // 新建地图列表
            mapBeanList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray jsonMap = jsonArray.getJSONArray(i);

                if (jsonMap.length() > 2) {
                    // 一条货主数据
                    MapBean mapBean = new MapBean();
                    mapBean.setMassNum(jsonMap.getString(0));
                    mapBean.setVertexCount(jsonMap.getString(1));
                    mapBean.setLatitude1(jsonMap.getString(2));
                    mapBean.setLongitude1(jsonMap.getString(3));
                    mapBean.setLatitude2(jsonMap.getString(4));
                    mapBean.setLongitude2(jsonMap.getString(5));
                    mapBean.setLatitude3(jsonMap.getString(6));
                    mapBean.setLongitude3(jsonMap.getString(7));
                    mapBean.setLatitude4(jsonMap.getString(8));
                    mapBean.setLongitude4(jsonMap.getString(9));
                    mapBean.setLatitude5(jsonMap.getString(10));
                    mapBean.setLongitude5(jsonMap.getString(11));
                    mapBean.setLatitude6(jsonMap.getString(12));
                    mapBean.setLongitude6(jsonMap.getString(13));
                    // 加入列表
                    mapBeanList.add(mapBean);

                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "cargo owner list count is " + mapBeanList
                    .size());
        }
    }
}

