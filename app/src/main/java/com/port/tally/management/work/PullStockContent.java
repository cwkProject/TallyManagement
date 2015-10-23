package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/25.
 */

import com.port.tally.management.data.StockData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.Map;

/**
 * 获取堆存详情任务
 *
 * @author 超悟空
 * @version 1.0 2015/9/25
 * @since 1.0
 */
public class PullStockContent extends DefaultWorkModel<String, Map<String, String>, StockData> {
    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length > 2;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.STOCK_CONTENT_URL;
    }

    @Override
    protected Map<String, String> onRequestSuccessSetResult(StockData data) {
        return data.getStock();
    }

    @Override
    protected Map<String, String> onRequestFailedSetResult(StockData data) {
        return null;
    }

    @Override
    protected StockData onCreateDataModel(String... parameters) {
        StockData data = new StockData();

        data.setStockId(parameters[0]);
        data.setStorageCode(parameters[1]);
        data.setPositionCode(parameters[2]);

        return data;
    }
}
