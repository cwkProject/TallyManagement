package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/12/24.
 */

import com.port.tally.management.bean.ShiftChange;
import com.port.tally.management.data.ShiftChangeData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

/**
 * 获取指定编号的交接班记录
 *
 * @author 超悟空
 * @version 1.0 2015/12/24
 * @since 1.0
 */
public class ShiftChangeWork extends DefaultWorkModel<String, ShiftChange, ShiftChangeData> {
    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length > 0;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.SHIFT_CHANGE_URL;
    }

    @Override
    protected ShiftChange onRequestSuccessSetResult(ShiftChangeData data) {
        return data.getShiftChange();
    }

    @Override
    protected ShiftChangeData onCreateDataModel(String... parameters) {
        ShiftChangeData data = new ShiftChangeData();
        data.setToken(parameters[0]);
        return data;
    }
}
