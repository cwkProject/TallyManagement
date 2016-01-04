package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/12/16.
 */

import com.port.tally.management.bean.ShiftChange;
import com.port.tally.management.data.ShiftChangeContentData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取交接班消息
 *
 * @author 超悟空
 * @version 1.0 2015/12/16
 * @since 1.0
 */
public class PullShiftChangeContent extends DefaultWorkModel<String, List<ShiftChange>,
        ShiftChangeContentData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length >= 2;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.SHIFT_CHANGE_CONTENT_URL;

    }

    @Override
    protected List<ShiftChange> onRequestSuccessSetResult(ShiftChangeContentData data) {
        return data.getShiftChangeList();
    }

    @Override
    protected ShiftChangeContentData onCreateDataModel(String... parameters) {
        ShiftChangeContentData data = new ShiftChangeContentData();

        data.setUserCode(parameters[0]);
        data.setTime(parameters[1]);

        return data;
    }
}
