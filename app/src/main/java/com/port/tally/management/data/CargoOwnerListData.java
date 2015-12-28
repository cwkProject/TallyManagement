package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.util.Log;

import com.port.tally.management.bean.CargoOwner;

import org.json.JSONArray;
import org.json.JSONObject;

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
public class CargoOwnerListData extends SimpleJsonDataModel {

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
    protected void onExtractData(JSONObject jsonData) throws Exception {
        JSONArray jsonArray = jsonData.getJSONArray("Data");
        Log.i(LOG_TAG + "onExtractData", "get cargoOwnerList count is " + jsonArray.length());

        // 新建货主列表
        cargoOwnerList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONArray jsonRow = jsonArray.getJSONArray(i);

            if (jsonRow.length() > 2) {
                // 一条货主数据
                CargoOwner cargoOwner = new CargoOwner();
                cargoOwner.setId(jsonRow.getString(0));
                cargoOwner.setName(jsonRow.getString(1));
                cargoOwner.setShortCode(jsonRow.getString(2));

                // 加入列表
                cargoOwnerList.add(cargoOwner);
            }
        }

        Log.i(LOG_TAG + "onExtractData", "cargo owner list count is " + cargoOwnerList.size());
    }
}
