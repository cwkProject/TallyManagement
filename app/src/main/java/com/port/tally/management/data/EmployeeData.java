package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/12/28.
 */

import android.util.Log;

import com.port.tally.management.bean.Employee;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 获取员工信息的数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/12/28
 * @since 1.0
 */
public class EmployeeData extends SimpleJsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "EmployeeData.";

    /**
     * 存放员工信息列表
     */
    private List<Employee> dataList = null;

    /**
     * 公司编码
     */
    private String company = null;

    /**
     * 获取员工信息列表
     *
     * @return 员工信息列表
     */
    public List<Employee> getDataList() {
        return dataList;
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
    protected void onExtractData(JSONObject jsonData) throws Exception {
        JSONArray jsonArray = jsonData.getJSONArray("Data");
        Log.i(LOG_TAG + "onExtractData", "get data count is " + jsonArray.length());

        // 新建列表
        dataList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONArray jsonRow = jsonArray.getJSONArray(i);

            if (jsonRow.length() > 2) {
                // 一条数据
                Employee employee = new Employee();
                employee.setId(jsonRow.getString(0));
                employee.setName(jsonRow.getString(1));
                employee.setShortCode(jsonRow.getString(2));

                // 加入列表
                dataList.add(employee);
            }
        }

        Log.i(LOG_TAG + "onExtractData", "data list count is " + dataList.size());
    }
}
