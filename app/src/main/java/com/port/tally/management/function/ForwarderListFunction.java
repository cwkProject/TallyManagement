package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/10/12.
 */

import android.content.Context;
import android.util.Log;

import com.port.tally.management.bean.Forwarder;
import com.port.tally.management.database.ForwarderOperator;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullForwarderList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.BroadcastUtil;

import java.util.List;

/**
 * 货代数据列表管理器
 *
 * @author 超悟空
 * @version 1.0 2015/10/12
 * @since 1.0
 */
public class ForwarderListFunction extends BaseCodeListFunction<Forwarder, String> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ForwarderListFunction.";

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param parameter 查询条件参数
     */
    public ForwarderListFunction(Context context, String parameter) {
        super(context, parameter);
    }

    @Override
    protected BaseOperator<Forwarder> onCreateOperator(Context context) {
        return new ForwarderOperator(context);
    }

    @Override
    protected void onLoadFromNetWork(String parameter) {

        PullForwarderList pullForwarderList = new PullForwarderList();

        pullForwarderList.setWorkEndListener(new WorkBack<List<Forwarder>>() {
            @Override
            public void doEndWork(boolean state, List<Forwarder> data) {
                netWorkEndSetData(state, data);
            }
        }, false);

        pullForwarderList.execute(parameter);
    }

    @Override
    protected List<Forwarder> onLoadFromDataBase(BaseOperator<Forwarder> operator, String
            parameter) {
        if (operator == null || operator.isEmpty() || parameter == null) {
            Log.i(LOG_TAG + "onLoadFromDataBase", "database null or parameter null");
            return null;
        }

        return operator.queryWithCondition(parameter);
    }

    @Override
    protected void onNotify(Context context) {
        BroadcastUtil.sendBroadcast(context, StaticValue.CodeListTag.FORWARDER_LIST);
    }
}
