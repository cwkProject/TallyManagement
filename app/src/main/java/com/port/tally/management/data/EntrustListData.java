package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.util.Log;

import com.port.tally.management.bean.Entrust;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 委托列表任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class EntrustListData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "EntrustListData.";

    /**
     * 委托列表
     */
    private List<Entrust> entrustList = null;

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
     * 开始日期
     */
    private String startDate = null;

    /**
     * 结束日期
     */
    private String endDate = null;

    /**
     * 货物
     */
    private String cargo = null;

    /**
     * 货主
     */
    private String cargoOwner = null;

    /**
     * 航次
     */
    private String voyage = null;

    /**
     * 作业
     */
    private String work = null;

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
     * 设置开始日期
     *
     * @param startDate 开始日期字符串
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 设置结束日期
     *
     * @param endDate 结束日期字符串
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 设置货物
     *
     * @param cargo 货物
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * 设置货主
     *
     * @param cargoOwner 货主
     */
    public void setCargoOwner(String cargoOwner) {
        this.cargoOwner = cargoOwner;
    }

    /**
     * 设置航次
     *
     * @param voyage 航次
     */
    public void setVoyage(String voyage) {
        this.voyage = voyage;
    }

    /**
     * 设置作业
     *
     * @param work 作业
     */
    public void setWork(String work) {
        this.work = work;
    }

    /**
     * 获取委托列表
     *
     * @return 委托列表
     */
    public List<Entrust> getEntrustList() {
        return entrustList;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("CodeCompany", company);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeCompany is " + company);
        map.put("startRow", startRow);
        Log.i(LOG_TAG + "onFillRequestParameters", "startRow is " + startRow);
        map.put("count", countRow);
        Log.i(LOG_TAG + "onFillRequestParameters", "count is " + countRow);
        map.put("StartTime", startDate);
        Log.i(LOG_TAG + "onFillRequestParameters", "StartTime is " + startDate);
        map.put("EndTime", endDate);
        Log.i(LOG_TAG + "onFillRequestParameters", "EndTime is " + endDate);
        map.put("Cargo", cargo);
        Log.i(LOG_TAG + "onFillRequestParameters", "Cargo is " + cargo);
        map.put("CargoOwner", cargoOwner);
        Log.i(LOG_TAG + "onFillRequestParameters", "CargoOwner is " + cargoOwner);
        map.put("Voyage", voyage);
        Log.i(LOG_TAG + "onFillRequestParameters", "Voyage is " + voyage);
        map.put("Operation", work);
        Log.i(LOG_TAG + "onFillRequestParameters", "Operation is " + work);
    }

    @Override
    protected boolean onRequestResult(JSONObject jsonObject) throws JSONException {
        // 得到执行结果
        String resultState = jsonObject.getString("IsSuccess");

        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
    }

    @Override
    protected String onRequestMessage(boolean result, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("Message");
    }

    @Override
    protected void onRequestSuccess(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("Data");

        if (jsonArray != null) {

            Log.i(LOG_TAG + "onRequestSuccess", "get entrustList count is " + jsonArray.length());

            // 新建委托列表
            entrustList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray jsonEntrust = jsonArray.getJSONArray(i);

                if (jsonEntrust.length() > 7) {
                    // 一条委托数据
                    Entrust entrust = new Entrust();

                    entrust.setId(jsonEntrust.getString(0));
                    entrust.setCargoOwner(jsonEntrust.getString(1));
                    entrust.setCargo(jsonEntrust.getString(2));
                    entrust.setVoyage(jsonEntrust.getString(3));
                    entrust.setWork(jsonEntrust.getString(4));
                    entrust.setEntrustNumber(jsonEntrust.getString(5));
                    entrust.setPlanAmount(jsonEntrust.getString(6));
                    entrust.setPlanWeight(jsonEntrust.getString(7));

                    // 添加到列表
                    entrustList.add(entrust);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "entrust list count is " + entrustList.size());
        }
    }
}
