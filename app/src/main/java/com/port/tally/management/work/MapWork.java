package com.port.tally.management.work;

/**
 * Created by song on 2015/10/1.
 */
import com.port.tally.management.bean.MapBean;
import com.port.tally.management.data.MapData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

public class MapWork extends DefaultWorkModel<String, List<MapBean>, MapData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return true;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.HTTP_GET_MAP_URL;
    }


    protected List<MapBean> onRequestSuccessSetResult(MapData data) {
        return data.getMapBeanList();
    }

    @Override
    protected List<MapBean> onRequestFailedSetResult(MapData data) {
        return null;
    }

    @Override
    protected MapData onCreateDataModel(String... parameters) {
        // 新建数据模型
        return new MapData();
    }
}
