package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/10/12.
 */

import android.content.Context;
import android.util.Log;

import com.port.tally.management.bean.Storage;
import com.port.tally.management.database.StorageOperator;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullStorageList;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.util.BroadcastUtil;

import java.util.List;

/**
 * 货场数据列表管理器
 *
 * @author 超悟空
 * @version 1.0 2015/10/12
 * @since 1.0
 */
public class StorageListFunction extends BaseCodeListFunction<Storage, String> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "StorageListFunction.";

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param parameter 查询条件参数
     */
    public StorageListFunction(Context context, String parameter) {
        super(context, parameter);
    }

    @Override
    protected BaseOperator<Storage> onCreateOperator(Context context) {
        return new StorageOperator(context);
    }

    @Override
    protected void onLoadFromNetWork(String parameter) {

        PullStorageList pullStorageList = new PullStorageList();

        pullStorageList.setWorkBackListener(new WorkBack<List<Storage>>() {
            @Override
            public void doEndWork(boolean state, List<Storage> data) {
                netWorkEndSetData(state, data);
            }
        });

        pullStorageList.execute(parameter);
    }

    @Override
    protected List<Storage> onLoadFromDataBase(BaseOperator<Storage> operator, String parameter) {
        if (operator == null || operator.IsEmpty() || parameter == null) {
            Log.i(LOG_TAG + "onLoadFromDataBase", "database null or parameter null");
            return null;
        }

        return operator.queryWithCondition(parameter);
    }

    @Override
    protected void onNotify(Context context) {
        BroadcastUtil.sendBroadcast(context, StaticValue.CodeListTag.STORAGE_LIST);
    }
}
