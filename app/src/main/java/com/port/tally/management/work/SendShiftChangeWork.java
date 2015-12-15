package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/12/14.
 */

import com.port.tally.management.data.ShiftChangeCommitData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

/**
 * 发送交接班信息的任务
 *
 * @author 超悟空
 * @version 1.0 2015/12/14
 * @since 1.0
 */
public class SendShiftChangeWork extends DefaultWorkModel<String, String, ShiftChangeCommitData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length >= 5;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.SHIFT_CHANGE_COMMIT_URL;
    }

    @Override
    protected String onRequestSuccessSetResult(ShiftChangeCommitData data) {
        return data.getToken();
    }

    @Override
    protected ShiftChangeCommitData onCreateDataModel(String... parameters) {
        ShiftChangeCommitData data = new ShiftChangeCommitData();

        data.setSend(parameters[0]);
        data.setReceive(parameters[1]);
        data.setSendCompany(parameters[2]);
        data.setReceiveCompany(parameters[3]);
        data.setContent(parameters[4]);
        data.setImageCount(parameters.length > 5 ? parameters[5] : null);
        data.setAudioCount(parameters.length > 6 ? parameters[6] : null);

        return data;
    }
}
