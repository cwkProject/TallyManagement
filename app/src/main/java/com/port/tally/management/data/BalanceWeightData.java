package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/25.
 */

import android.util.Log;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 衡重任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/10/15
 * @since 1.0
 */
public class BalanceWeightData extends SimpleJsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "BalanceWeightData.";

    /**
     * 衡重数据
     */
    private Map<String, String> balanceWeight = null;

    /**
     * 委托编码
     */
    private String entrustNumber = null;

    /**
     * 公司编码
     */
    private String companyCode = null;

    /**
     * 班组日期
     */
    private String dutyDate = null;

    /**
     * 白夜班
     */
    private String dayNight = null;

    /**
     * 设置委托编码
     *
     * @param entrustNumber 编码
     */
    public void setEntrustNumber(String entrustNumber) {
        this.entrustNumber = entrustNumber;
    }

    /**
     * 设置公司编码
     *
     * @param companyCode 公司编码
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     * 设置班组日期
     *
     * @param dutyDate 班组日期
     */
    public void setDutyDate(String dutyDate) {
        this.dutyDate = dutyDate;
    }

    /**
     * 设置白夜班
     *
     * @param dayNight 白夜班
     */
    public void setDayNight(String dayNight) {
        this.dayNight = dayNight;
    }

    /**
     * 获取衡重详情
     *
     * @return 衡重
     */
    public Map<String, String> getBalanceWeight() {
        return balanceWeight;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("Cgno", entrustNumber);
        Log.i(LOG_TAG + "onFillRequestParameters", "Cgno is " + entrustNumber);
        map.put("CodeDepartment", companyCode);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeDepartment is " + companyCode);
        map.put("TeamDate", dutyDate);
        Log.i(LOG_TAG + "onFillRequestParameters", "TeamDate is " + dutyDate);
        map.put("DayNight", dayNight);
        Log.i(LOG_TAG + "onFillRequestParameters", "DayNight is " + dayNight);
    }

    @Override
    protected void onExtractData(JSONObject jsonData) throws Exception {
        // 结果数据
        JSONObject data = jsonData.getJSONObject("Data");

        String[] order = data.getString("Order").split("[+]");

        balanceWeight = new LinkedHashMap<>();

        for (String key : order) {
            balanceWeight.put(key, data.getString(key));
        }
    }
}
