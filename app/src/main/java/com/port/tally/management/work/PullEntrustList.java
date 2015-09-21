package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import com.port.tally.management.bean.Entrust;
import com.port.tally.management.data.EntrustListData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;

/**
 * 获取委托列表
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class PullEntrustList extends DefaultWorkModel<String, List<Entrust>, EntrustListData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length >= 5;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.ENTRUST_QUERY_LIST_URL;
    }

    @Override
    protected List<Entrust> onRequestSuccessSetResult(EntrustListData data) {
        return data.getEntrustList();
    }

    @Override
    protected List<Entrust> onRequestFailedSetResult(EntrustListData data) {
        return null;
    }

    @Override
    protected EntrustListData onCreateDataModel(String... parameters) {
        // 新建数据模型
        EntrustListData data = new EntrustListData();

        // 设置参数
        data.setCompany(parameters[0]);
        data.setStartRow(parameters[1]);
        data.setCountRow(parameters[2]);
        data.setStartDate(parameters[3]);
        data.setEndDate(parameters[4]);
        data.setCargo(parameters.length > 5 ? parameters[5] : null);
        data.setCargoOwner(parameters.length > 6 ? parameters[6] : null);
        data.setVoyage(parameters.length > 7 ? parameters[7] : null);
        data.setWork(parameters.length > 8 ? parameters[8] : null);

        return data;
    }
}
