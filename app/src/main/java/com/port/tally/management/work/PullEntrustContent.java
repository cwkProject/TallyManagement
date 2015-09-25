package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/25.
 */

import com.port.tally.management.data.EntrustData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.Map;

/**
 * 获取委托详情任务
 *
 * @author 超悟空
 * @version 1.0 2015/9/25
 * @since 1.0
 */
public class PullEntrustContent extends DefaultWorkModel<String, Map<String, String>, EntrustData> {
    @Override
    protected boolean onCheckParameters(String... strings) {
        return strings != null && strings.length > 0;
    }

    @Override
    protected String onTaskUri() {
        return StaticValue.ENTRUST_CONTENT_URL;
    }

    @Override
    protected Map<String, String> onRequestSuccessSetResult(EntrustData entrustData) {
        return entrustData.getEntrust();
    }

    @Override
    protected Map<String, String> onRequestFailedSetResult(EntrustData entrustData) {
        return null;
    }

    @Override
    protected EntrustData onCreateDataModel(String... strings) {
        EntrustData data = new EntrustData();

        data.setEntrustId(strings[0]);

        return data;
    }
}
