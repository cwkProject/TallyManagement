package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/25.
 */

import com.port.tally.management.data.BalanceWeightData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.Map;

/**
 * 获取衡重详情任务
 *
 * @author 超悟空
 * @version 1.0 2015/10/15
 * @since 1.0
 */
public class PullBalanceWeightContent extends DefaultWorkModel<String, Map<String, String>,
        BalanceWeightData> {
    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length > 3;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.BALANCE_WEIGHT_CONTENT_URL;
    }

    @Override
    protected Map<String, String> onRequestSuccessSetResult(BalanceWeightData data) {
        return data.getBalanceWeight();
    }

    @Override
    protected Map<String, String> onRequestFailedSetResult(BalanceWeightData data) {
        return null;
    }

    @Override
    protected BalanceWeightData onCreateDataModel(String... parameters) {
        BalanceWeightData data = new BalanceWeightData();

        data.setEntrustNumber(parameters[0]);
        data.setCompanyCode(parameters[1]);
        data.setDutyDate(parameters[2]);
        data.setDayNight(parameters[3]);

        return data;
    }
}
