package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.util.Log;

import com.port.tally.management.bean.BalanceWeight;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 衡重列表任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/10/14
 * @since 1.0
 */
public class BalanceWeightListData extends SimpleJsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "BalanceWeightListData.";

    /**
     * 衡重列表
     */
    private List<BalanceWeight> balanceWeightList = null;

    /**
     * 公司编码
     */
    private String company = null;

    /**
     * 起始行位置
     */
    private String startRow = null;

    /**
     * 获取条数
     */
    private String countRow = null;

    /**
     * 班组日期
     */
    private String date = null;

    /**
     * 白夜班
     */
    private String dayNight = null;

    /**
     * 设置公司编码
     *
     * @param company 公司编码
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 设置起始行位置
     *
     * @param startRow 起始位置
     */
    public void setStartRow(String startRow) {
        this.startRow = startRow;
    }

    /**
     * 设置要获取的行数
     *
     * @param countRow 行数
     */
    public void setCountRow(String countRow) {
        this.countRow = countRow;
    }

    /**
     * 设置班组日期
     *
     * @param date 班组日期字符串
     */
    public void setDate(String date) {
        this.date = date;
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
     * 获取衡重列表
     *
     * @return 委托列表
     */
    public List<BalanceWeight> getBalanceWeightList() {
        return balanceWeightList;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("CodeDepartment", company);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeDepartment is " + company);
        map.put("StartRow", startRow);
        Log.i(LOG_TAG + "onFillRequestParameters", "StartRow is " + startRow);
        map.put("Count", countRow);
        Log.i(LOG_TAG + "onFillRequestParameters", "Count is " + countRow);
        map.put("TeamDate", date);
        Log.i(LOG_TAG + "onFillRequestParameters", "TeamDate is " + date);
        map.put("dayNight", dayNight);
        Log.i(LOG_TAG + "onFillRequestParameters", "dayNight is " + dayNight);
    }

    @Override
    protected void onExtractData(JSONObject jsonData) throws Exception {
        JSONArray jsonArray = jsonData.getJSONArray("Data");
        Log.i(LOG_TAG + "onExtractData", "get balanceWeightList count is " + jsonArray.length());

        // 新建衡重列表
        balanceWeightList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONArray jsonRow = jsonArray.getJSONArray(i);

            if (jsonRow.length() > 5) {
                // 一条衡重数据
                BalanceWeight balanceWeight = new BalanceWeight();

                balanceWeight.setEntrustNumber(jsonRow.getString(0));
                balanceWeight.setShip(jsonRow.getString(1));
                balanceWeight.setEntrust(jsonRow.getString(2));
                balanceWeight.setCargo(jsonRow.getString(3));
                balanceWeight.setPlanWeight(jsonRow.getString(4));
                balanceWeight.setDutyWeight(jsonRow.getString(5));

                // 填充请求数据
                balanceWeight.setCompanyCode(company);
                balanceWeight.setDate(date);
                balanceWeight.setDayNight(dayNight);

                // 添加到列表
                balanceWeightList.add(balanceWeight);
            }
        }

        Log.i(LOG_TAG + "onExtractData", "balance weight list count is " + balanceWeightList.size
                ());
    }
}
