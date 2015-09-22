package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import com.port.tally.management.bean.Voyage;
import com.port.tally.management.data.VoyageListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取航次列表任务
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class PullVoyageList extends DefaultWorkModel<String, List<Voyage>, VoyageListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return true;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.VOYAGE_LIST_URL;
    }

    @Override
    protected List<Voyage> onRequestSuccessSetResult(VoyageListData data) {
        return data.getVoyageList();
    }

    @Override
    protected List<Voyage> onRequestFailedSetResult(VoyageListData data) {
        return null;
    }

    @Override
    protected VoyageListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        return new VoyageListData();
    }
}
