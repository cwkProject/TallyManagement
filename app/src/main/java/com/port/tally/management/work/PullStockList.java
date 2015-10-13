package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import com.port.tally.management.bean.Stock;
import com.port.tally.management.data.StockListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取堆存列表
 *
 * @author 超悟空
 * @version 1.0 2015/10/13
 * @since 1.0
 */
public class PullStockList extends DefaultWorkModel<String, List<Stock>, StockListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length >= 3;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.STOCK_LIST_URL;
    }

    @Override
    protected List<Stock> onRequestSuccessSetResult(StockListData data) {
        return data.getStockList();
    }

    @Override
    protected List<Stock> onRequestFailedSetResult(StockListData data) {
        return null;
    }

    @Override
    protected StockListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        StockListData data = new StockListData();

        // 设置参数
        data.setCompany(parameters[0]);
        data.setStartRow(parameters[1]);
        data.setCountRow(parameters[2]);
        data.setCargo(parameters.length > 3 ? parameters[3] : null);
        data.setCargoOwner(parameters.length > 4 ? parameters[4] : null);
        data.setForwarder(parameters.length > 5 ? parameters[5] : null);
        data.setStorage(parameters.length > 6 ? parameters[6] : null);

        return data;
    }
}
