package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.util.Log;

import com.port.tally.management.bean.CargoOwner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 货主列表任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class CargoOwnerListData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "CargoOwnerListData.";

    /**
     * 货主列表
     */
    private List<CargoOwner> cargoOwnerList = null;

    /**
     * 获取货主列表
     *
     * @return 货主列表
     */
    public List<CargoOwner> getCargoOwnerList() {
        return cargoOwnerList;
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

            Log.i(LOG_TAG + "onRequestSuccess", "get cargoOwnerList count is " + jsonArray.length
                    ());

            // 新建货主列表
            cargoOwnerList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray jsonCargo = jsonArray.getJSONArray(i);

                if (jsonCargo.length() > 2) {
                    // 一条货主数据
                    CargoOwner cargoOwner = new CargoOwner();
                    cargoOwner.setId(jsonCargo.getString(0));
                    cargoOwner.setName(jsonCargo.getString(1));
                    cargoOwner.setShortCode(jsonCargo.getString(2));

                    // 加入列表
                    cargoOwnerList.add(cargoOwner);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "cargo owner list count is " + cargoOwnerList
                    .size());
        }
    }
}
