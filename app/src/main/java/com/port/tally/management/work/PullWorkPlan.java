package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/12/18.
 */

import com.port.tally.management.bean.WorkPlan;
import com.port.tally.management.data.WorkPlanData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取计划任务列表
 *
 * @author 超悟空
 * @version 1.0 2015/12/18
 * @since 1.0
 */
public class PullWorkPlan extends DefaultWorkModel<String, List<WorkPlan>, WorkPlanData> {
    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length > 0;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.WORK_PLAN_URL;

    }

    @Override
    protected List<WorkPlan> onRequestSuccessSetResult(WorkPlanData data) {
        return data.getDataList();
    }

    @Override
    protected WorkPlanData onCreateDataModel(String... parameters) {
        WorkPlanData data = new WorkPlanData();

        data.setUserCode(parameters[0]);

        return data;
    }
}
