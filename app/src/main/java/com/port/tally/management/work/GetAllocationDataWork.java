package com.port.tally.management.work;
import com.port.tally.management.data.GetAllocationData;
import com.port.tally.management.util.StaticValue;
import org.mobile.library.model.work.DefaultWorkModel;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/11/25.
 */
public class GetAllocationDataWork  extends DefaultWorkModel<String,List<Map<String, Object>>, GetAllocationData> {


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
        return StaticValue.HTTP_GetALLOCATIONDATA_URL;
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
    protected List<Map<String, Object>> onRequestSuccessSetResult(GetAllocationData data) {
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
    protected List<Map<String, Object>> onRequestFailedSetResult(GetAllocationData data) {
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
    protected GetAllocationData onCreateDataModel(String... parameters) {
        GetAllocationData getAllocationData = new GetAllocationData();
        getAllocationData.setSearchContent1(parameters[0]);
        return getAllocationData;
    }
}
