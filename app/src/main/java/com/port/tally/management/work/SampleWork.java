package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/9/8.
 */

import com.port.tally.management.data.SampleData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;

/**
 * 业务请求样例任务模型，
 * 与网络数据模型类型绑定为强类型任务，
 * {@link SampleData}
 * 泛型第一参数为业务请求要传入的参数类型，
 * 泛型第二参数为业务请求返回的数据类型，
 * 泛型第三参数为业务请求强绑定的请求数据模型类型（data包中类型）
 *
 * @author 超悟空
 * @version 1.0 2015/9/8
 * @since 1.0
 */
public class SampleWork extends DefaultWorkModel<String, String, SampleData> {
    /**
     * 参数合法性检测，
     * 用于检测传入参数是否合法，
     * 需要子类重写检测规则，
     *
     * @param parameters 传入参数
     *
     * @return 检测结果，合法返回true，非法返回false
     */
    @Override
    protected boolean onCheckParameters(String... parameters) {
        // 需要至少两个传入参数
        return !(parameters == null || parameters.length < 2);
    }

    /**
     * 设置任务请求地址
     *
     * @return 地址字符串
     */
    @Override
    protected String onTaskUri() {
        return StaticValue.HTTP_GET_SAMPLE_URL;
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
    protected String onRequestSuccessSetResult(SampleData data) {
        return data.getOutParameter() + data.getOutParameter2();
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
    protected String onRequestFailedSetResult(SampleData data) {
        return data.getMessage();
    }

    /**
     * 创建数据模型对象并填充参数，
     * {@link #onCheckParameters(String...)}检测成功后调用
     *
     * @param parameters 传入参数
     *
     * @return 参数设置完毕后的数据模型对象
     */
    @Override
    protected SampleData onCreateDataModel(String... parameters) {
        SampleData sampleData = new SampleData();
        sampleData.setInParameter(parameters[0]);
        sampleData.setInParameter2(Integer.parseInt(parameters[1]));

        return sampleData;
    }
}
