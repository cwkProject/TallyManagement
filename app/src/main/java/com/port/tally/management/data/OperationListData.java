package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.util.Log;

import com.port.tally.management.bean.Operation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作业过程列表任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class OperationListData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "OperationListData.";

    /**
     * 作业过程列表
     */
    private List<Operation> operationList = null;

    /**
     * 获取作业过程列表
     *
     * @return 作业过程列表
     */
    public List<Operation> getOperationList() {
        return operationList;
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

            Log.i(LOG_TAG + "onRequestSuccess", "get operationList count is " + jsonArray.length());

            // 新建列表
            operationList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray jsonRow = jsonArray.getJSONArray(i);

                if (jsonRow.length() > 2) {
                    // 一条数据
                    Operation operation = new Operation();
                    operation.setId(jsonRow.getString(0));
                    operation.setName(jsonRow.getString(1));
                    operation.setShortCode(jsonRow.getString(2));

                    // 加入列表
                    operationList.add(operation);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "operation list count is " + operationList.size());
        }
    }
}
