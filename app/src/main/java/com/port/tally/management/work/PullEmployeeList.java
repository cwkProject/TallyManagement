package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/12/28.
 */

import com.port.tally.management.bean.Employee;
import com.port.tally.management.data.EmployeeData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取员工列表的任务
 *
 * @author 超悟空
 * @version 1.0 2015/12/28
 * @since 1.0
 */
public class PullEmployeeList extends DefaultWorkModel<String, List<Employee>, EmployeeData> {
    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length > 0;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.EMPLOYEE_URL;
    }

    @Override
    protected List<Employee> onRequestSuccessSetResult(EmployeeData data) {
        return data.getDataList();
    }

    @Override
    protected EmployeeData onCreateDataModel(String... parameters) {
        EmployeeData data = new EmployeeData();

        data.setCompany(parameters[0]);

        return data;
    }
}
