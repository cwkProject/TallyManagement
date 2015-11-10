package com.port.tally.management.work;

import com.port.tally.management.data.TallyManageTwoData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/11/7.
 * 理货第一个界面点击跳转的第二个界面
 */
public class TallyManageWork extends DefaultWorkModel<String, List<Map<String, Object>>, TallyManageTwoData> {
    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.HTTP_GET_TALLYTWO_URL;
    }

    @Override
    protected List<Map<String, Object>> onRequestSuccessSetResult(TallyManageTwoData tallyManageTwoData) {
        return tallyManageTwoData.getAll();
    }

    @Override
    protected List<Map<String, Object>> onRequestFailedSetResult(TallyManageTwoData tallyManageTwoData) {
        return null;
    }

    @Override
    protected TallyManageTwoData onCreateDataModel(String... parameters) {
        // 设置参数
        TallyManageTwoData tallyManageTwoData = new TallyManageTwoData();
        tallyManageTwoData.setPmno(parameters[0]);
        tallyManageTwoData.setCargo(parameters[1]);
        return tallyManageTwoData;
    }
}
