package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/10/14.
 */

import com.port.tally.management.bean.BalanceWeight;
import com.port.tally.management.data.BalanceWeightListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取衡重列表的任务
 *
 * @author 超悟空
 * @version 1.0 2015/10/14
 * @since 1.0
 */
public class PullBalanceWeightList extends DefaultWorkModel<String, List<BalanceWeight>,
        BalanceWeightListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length > 4;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.BALANCE_WEIGHT_LIST_URL;
    }

    @Override
    protected List<BalanceWeight> onRequestSuccessSetResult(BalanceWeightListData data) {
        return data.getBalanceWeightList();
    }

    @Override
    protected List<BalanceWeight> onRequestFailedSetResult(BalanceWeightListData data) {
        return null;
    }

    @Override
    protected BalanceWeightListData onCreateDataModel(String... parameters) {
        BalanceWeightListData data = new BalanceWeightListData();

        data.setStartRow(parameters[0]);
        data.setCountRow(parameters[1]);
        data.setCompany(parameters[2]);
        data.setDate(parameters[3]);
        data.setDayNight(parameters[4]);

        return data;
    }
}
