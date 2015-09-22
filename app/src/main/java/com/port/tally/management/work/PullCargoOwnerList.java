package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import com.port.tally.management.bean.CargoOwner;
import com.port.tally.management.data.CargoOwnerListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取货主列表任务
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class PullCargoOwnerList extends DefaultWorkModel<String, List<CargoOwner>, CargoOwnerListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return true;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.CARGO_OWNER_LIST_URL;
    }

    @Override
    protected List<CargoOwner> onRequestSuccessSetResult(CargoOwnerListData data) {
        return data.getCargoOwnerList();
    }

    @Override
    protected List<CargoOwner> onRequestFailedSetResult(CargoOwnerListData data) {
        return null;
    }

    @Override
    protected CargoOwnerListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        return new CargoOwnerListData();
    }
}
