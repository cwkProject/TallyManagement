package com.port.tally.management.work;

import com.port.tally.management.data.TallyDetailData;
import com.port.tally.management.data.TallyDetail_MachineData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/10/23.
 */
public class TallyDetail_MachineWork  extends DefaultWorkModel<String,List<Map<String, Object>>, TallyDetail_MachineData> {

    /**
     * 参数合法性检测，
     * 用于检测传入参数是否合法，
     * 需要子类重写检测规则，
     *
     * @param parameters 传入参数
     *
     * @return 检测结果，合法返回true，非法返回false
     */

    protected boolean onCheckParameters(String... parameters) {
        // 需要至少两个传入参数
        return !(parameters == null || parameters.length < 1);
    }

    /**
     * 设置任务请求地址
     *
     * @return 地址字符串
     */
    @Override
    protected String onTaskUri() {
        return StaticValue.HTTP_GET_Machine_URL;
    }

    /**
     * 服务返回数据解析成功后，
     * 并且服务执行为成功即{@link org.mobile.library.model.data.IDefaultDataModel#isSuccess()}返回true时，
     * 设置任务返回数据，
     * 即设置{@link #setResult(Object)}的参数。
     * 该方法在{@link #onParseSuccess(org.mobile.library.model.data.IDefaultDataModel)}之后调用
     *
     * @param data 解析后的数据模型对象
     *
     * @return 任务返回数据
     */
    @Override
    protected List<Map<String, Object>> onRequestSuccessSetResult(TallyDetail_MachineData data) {
        return data.getAll();
    }

    /**
     * 服务返回数据解析成功后，
     * 但是服务执行为失败即{@link org.mobile.library.model.data.IDefaultDataModel#isSuccess()}返回false时，
     * 设置任务返回数据，
     * 即设置{@link #setResult(Object)}的参数。
     * 该方法在{@link #onParseSuccess(org.mobile.library.model.data.IDefaultDataModel)}之后调用
     *
     * @param data 解析后的数据模型对象
     *
     * @return 任务返回数据
     */
    @Override
    protected List<Map<String, Object>> onRequestFailedSetResult(TallyDetail_MachineData data) {
        return null;
    }

    /**
     * 创建数据模型对象并填充参数，
     * {@link #onCheckParameters(String...)}检测成功后调用
     *
     * @param parameters 传入参数
     *
     * @return 参数设置完毕后的数据模型对象
     */

    protected TallyDetail_MachineData onCreateDataModel(String... parameters) {
        TallyDetail_MachineData tallyDetail_MachineData = new TallyDetail_MachineData();

        tallyDetail_MachineData.setSearchContent(parameters[0]);
        tallyDetail_MachineData.setSearchContent1(parameters[1]);
        tallyDetail_MachineData.setSearchContent2(parameters[2]);
        return tallyDetail_MachineData;
    }
}
