package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/21.
 */

import com.port.tally.management.bean.CargoType;
import com.port.tally.management.data.CargoTypeListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取货物种类列表任务
 *
 * @author 超悟空
 * @version 1.0 2015/9/21
 * @since 1.0
 */
public class PullCargoTypeList extends DefaultWorkModel<String, List<CargoType>,
        CargoTypeListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return true;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.CARGO_TYPE_LIST_URL;
    }

    @Override
    protected List<CargoType> onRequestSuccessSetResult(CargoTypeListData data) {
        return data.getCargoTypeList();
    }

    @Override
    protected List<CargoType> onRequestFailedSetResult(CargoTypeListData data) {
        return null;
    }

    @Override
    protected CargoTypeListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        return new CargoTypeListData();
    }
}
