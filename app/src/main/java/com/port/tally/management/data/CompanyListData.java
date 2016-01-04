package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import android.util.Log;

import com.port.tally.management.bean.Company;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 货主列表任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/10/14
 * @since 1.0
 */
public class CompanyListData extends SimpleJsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "CompanyListData.";

    /**
     * 公司列表
     */
    private List<Company> companyList = null;

    /**
     * 获取公司列表
     *
     * @return 公司列表
     */
    public List<Company> getCompanyList() {
        return companyList;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {

    }

    @Override
    protected void onExtractData(JSONObject jsonData) throws Exception {
        JSONArray jsonArray = jsonData.getJSONArray("Data");
        Log.i(LOG_TAG + "onExtractData", "get companyList count is " + jsonArray.length());

        // 新建公司列表
        companyList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONArray jsonRow = jsonArray.getJSONArray(i);

            if (jsonRow.length() > 1) {
                // 一条公司数据
                Company company = new Company();
                company.setId(jsonRow.getString(0));
                company.setName(jsonRow.getString(1));
                // 加入列表
                companyList.add(company);
            }
        }

        Log.i(LOG_TAG + "onExtractData", "company list count is " + companyList.size());
    }
}
