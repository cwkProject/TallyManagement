package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import com.port.tally.management.bean.Operation;
import com.port.tally.management.data.OperationListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取作业过程列表任务
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class PullOperationList extends DefaultWorkModel<String, List<Operation>,
        OperationListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return true;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.OPERATION_LIST_URL;
    }

    @Override
    protected List<Operation> onRequestSuccessSetResult(OperationListData data) {
        return data.getOperationList();
    }

    @Override
    protected List<Operation> onRequestFailedSetResult(OperationListData data) {
        return null;
    }

    @Override
    protected OperationListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        return new OperationListData();
    }
}
