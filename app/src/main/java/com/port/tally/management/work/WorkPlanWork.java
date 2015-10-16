package com.port.tally.management.work;

import com.port.tally.management.activity.WorkPlan;
import com.port.tally.management.data.TallyManageData;
import com.port.tally.management.data.WorkPlanData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/10/14.
 */
public class WorkPlanWork extends DefaultWorkModel<String, List<Map<String, Object>>, WorkPlanData> {
    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length >= 2;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.HTTP_GET_WORKPLAN_URL;
    }

    @Override
    protected List<Map<String, Object>> onRequestSuccessSetResult(WorkPlanData workPlanData) {
        return workPlanData.getAll();
    }

    @Override
    protected List<Map<String, Object>> onRequestFailedSetResult(WorkPlanData workPlanData) {
        return null;
    }

    @Override
    protected WorkPlanData onCreateDataModel(String... parameters) {
        // 设置参数
        WorkPlanData workPlanData = new WorkPlanData();
        workPlanData.setCompanyCode(parameters[1]);
        workPlanData.setStartCount(parameters[2]);
        workPlanData.setEndCount(parameters[0]);

        return  workPlanData;
    }
}