package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import com.port.tally.management.bean.Forwarder;
import com.port.tally.management.data.ForwarderListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取货代列表任务
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class PullForwarderList extends DefaultWorkModel<String, List<Forwarder>,
        ForwarderListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length > 0;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.FORWARDER_LIST_URL;
    }

    @Override
    protected List<Forwarder> onRequestSuccessSetResult(ForwarderListData data) {
        return data.getForwarderList();
    }

    @Override
    protected List<Forwarder> onRequestFailedSetResult(ForwarderListData data) {
        return null;
    }

    @Override
    protected ForwarderListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        ForwarderListData data = new ForwarderListData();

        data.setCompany(parameters[0]);

        return data;
    }
}
