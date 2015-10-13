package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import com.port.tally.management.bean.Storage;
import com.port.tally.management.data.StorageListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取货场列表任务
 *
 * @author 超悟空
 * @version 1.0 2015/10/12
 * @since 1.0
 */
public class PullStorageList extends DefaultWorkModel<String, List<Storage>, StorageListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length > 0;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.STORAGE_LIST_URL;
    }

    @Override
    protected List<Storage> onRequestSuccessSetResult(StorageListData data) {
        return data.getStorageList();
    }

    @Override
    protected List<Storage> onRequestFailedSetResult(StorageListData data) {
        return null;
    }

    @Override
    protected StorageListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        StorageListData data = new StorageListData();

        data.setCompany(parameters[0]);

        return data;
    }
}
