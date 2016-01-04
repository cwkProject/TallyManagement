package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/12/18.
 */

import android.util.Log;

import com.port.tally.management.bean.WorkPlan;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作业计划数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/12/18
 * @since 1.0
 */
public class WorkPlanData extends SimpleJsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "WorkPlanData.";

    /**
     * 作业计划列表
     */
    private List<WorkPlan> dataList = null;

    /**
     * 用户编码
     */
    private String userCode = null;

    /**
     * 设置用户编码
     *
     * @param userCode 用户编码
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 获取计划任务列表
     *
     * @return 计划任务列表
     */
    public List<WorkPlan> getDataList() {
        return dataList;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        dataMap.put("CodeUser", userCode);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeUser is " + userCode);
    }

    @Override
    protected void onExtractData(JSONObject jsonData) throws Exception {
        JSONArray jsonArray = jsonData.getJSONArray("Data");
        dataList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONArray jsonRow = jsonArray.getJSONArray(i);

            if (jsonRow.length() > 11) {
                WorkPlan data = new WorkPlan();

                data.setDispatchCode(jsonRow.getString(0));
                data.setEntrustCode(jsonRow.getString(1));
                data.setTicketCode(jsonRow.getString(2));
                data.setEntrustName(jsonRow.getString(3));
                data.setTaskNumber(jsonRow.getString(4));
                data.setGoods(jsonRow.getString(5));
                data.setOperation(jsonRow.getString(6));
                data.setAmount(jsonRow.getString(7));
                data.setBeginTime(jsonRow.getString(8));
                data.setEndTime(jsonRow.getString(9));
                data.setSourceCarrier(jsonRow.getString(10));
                data.setTargetCarrier(jsonRow.getString(11));

                dataList.add(data);
            }
        }

        Log.i(LOG_TAG + "onExtractData", "data list count " + dataList.size());
    }
}
