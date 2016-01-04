package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.util.Log;

import com.port.tally.management.bean.Stock;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 堆存列表任务数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/10/13
 * @since 1.0
 */
public class StockListData extends SimpleJsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "StockListData.";

    /**
     * 堆存列表
     */
    private List<Stock> stockList = null;

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
     * 货物
     */
    private String cargo = null;

    /**
     * 货主
     */
    private String cargoOwner = null;

    /**
     * 货代
     */
    private String forwarder = null;

    /**
     * 货场
     */
    private String storage = null;

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
     * 设置货代
     *
     * @param forwarder 货代
     */
    public void setForwarder(String forwarder) {
        this.forwarder = forwarder;
    }

    /**
     * 设置货场
     *
     * @param storage 货场
     */
    public void setStorage(String storage) {
        this.storage = storage;
    }

    /**
     * 获取堆存列表
     *
     * @return 堆存列表
     */
    public List<Stock> getStockList() {
        return stockList;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("CodeCompany", company);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeCompany is " + company);
        map.put("StartRow", startRow);
        Log.i(LOG_TAG + "onFillRequestParameters", "StartRow is " + startRow);
        map.put("Count", countRow);
        Log.i(LOG_TAG + "onFillRequestParameters", "Count is " + countRow);
        map.put("Cargo", cargo);
        Log.i(LOG_TAG + "onFillRequestParameters", "Cargo is " + cargo);
        map.put("CargoOwner", cargoOwner);
        Log.i(LOG_TAG + "onFillRequestParameters", "CargoOwner is " + cargoOwner);
        map.put("Client", forwarder);
        Log.i(LOG_TAG + "onFillRequestParameters", "Client is " + forwarder);
        map.put("Storage", storage);
        Log.i(LOG_TAG + "onFillRequestParameters", "Storage is " + storage);
    }

    @Override
    protected void onExtractData(JSONObject jsonData) throws Exception {
        JSONArray jsonArray = jsonData.getJSONArray("Data");
        Log.i(LOG_TAG + "onExtractData", "get stockList count is " + jsonArray.length());

        // 新建堆存列表
        stockList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONArray jsonRow = jsonArray.getJSONArray(i);

            if (jsonRow.length() > 8) {
                // 一条委托数据
                Stock stock = new Stock();

                stock.setId(jsonRow.getString(0));
                stock.setCargoOwner(jsonRow.getString(1));
                stock.setCargo(jsonRow.getString(2));
                stock.setVoyage(jsonRow.getString(3));
                stock.setForwarder(jsonRow.getString(4));
                stock.setStorage(jsonRow.getString(5));
                stock.setWeight(jsonRow.getString(6));
                stock.setStorageCode(jsonRow.getString(7));
                stock.setPositionCode(jsonRow.getString(8));

                // 添加到列表
                stockList.add(stock);
            }
        }

        Log.i(LOG_TAG + "onExtractData", "stock list count is " + stockList.size());
    }
}
