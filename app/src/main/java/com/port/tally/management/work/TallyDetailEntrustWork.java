package com.port.tally.management.work;

import com.port.tally.management.data.TallyManageData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/10/11.
 */
public class TallyDetailEntrustWork extends DefaultWorkModel<String, List<Map<String, Object>>, TallyManageData> {
    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length >= 2;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.HTTP_GET_TASKONE_URL;
    }

    @Override
    protected List<Map<String, Object>> onRequestSuccessSetResult(TallyManageData tallyManageData) {
        return tallyManageData.getAll();
    }

    @Override
    protected List<Map<String, Object>> onRequestFailedSetResult(TallyManageData tallyManageData) {
        return null;
    }

    @Override
    protected TallyManageData onCreateDataModel(String... parameters) {
        // 设置参数
        TallyManageData tallyManageData = new TallyManageData();
        tallyManageData.setCompanyCode(parameters[1]);
        tallyManageData.setStartCount(parameters[2]);
        tallyManageData.setEndCount(parameters[0]);

        return tallyManageData;
    }
}
