package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;
import android.util.Log;


import org.mobile.library.model.database.BaseOperator;

import java.util.List;

/**
 * 代码列表功能基类
 *
 * @param <DataModel> 代码数据模型
 * @param <Condition> 数据集提取条件参数类型
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public abstract class BaseCodeListFunction<DataModel, Condition> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "BaseCodeListFunction.";

    /**
     * 代码集合
     */
    private List<DataModel> dataList = null;

    /**
     * 上下文
     */
    private Context context = null;

    /**
     * 数据库操作工具
     */
    private BaseOperator<DataModel> operator = null;

    /**
     * 标识是否正在加载数据
     */
    private volatile boolean loading = false;

    /**
     * 数据提取条件参数
     */
    private Condition parameter;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public BaseCodeListFunction(Context context) {
        this.context = context;
        Log.i(LOG_TAG + "BaseCodeListFunction", "BaseCodeListFunction is invoked");
    }

    /**
     * 构造函数
     *
     * @param context   上下文
     * @param parameter 取值条件参数
     */
    public BaseCodeListFunction(Context context, Condition parameter) {
        this.context = context;
        this.parameter = parameter;
        Log.i(LOG_TAG + "BaseCodeListFunction", "BaseCodeListFunction is invoked");
    }

    /**
     * 创建数据库操作对象
     *
     * @param context 上下文
     *
     * @return 数据库操作对象
     */
    protected abstract BaseOperator<DataModel> onCreateOperator(Context context);

    /**
     * 判断是否正在加载数据
     *
     * @return true表示正在加载数据
     */
    public boolean isLoading() {
        return loading;
    }

    /**
     * 数据初始化
     */
    public void onCreate() {
        Log.i(LOG_TAG + "onCreate", "getDataList is invoked");
        // 加载开始
        loading = true;
        Log.i(LOG_TAG + "onCreate", "onCreateOperator is invoked");
        this.operator = onCreateOperator(context);

        if (dataList == null) {
            // 从数据库拉取
            Log.i(LOG_TAG + "onCreate", "from database");
            dataList = onLoadFromDataBase(operator, parameter);
        }

        if (dataList == null || dataList.size() == 0) {
            // 从网络拉取
            Log.i(LOG_TAG + "onCreate", "from network");
            onLoadFromNetWork(parameter);
        } else {
            onNotify(context);
            // 加载结束
            loading = false;
        }
    }

    /**
     * 通知该工具已存在，不必重新加载
     */
    public final void notifyExist() {
        onNotify(context);
    }

    /**
     * 获取数据集合，
     * 对象为空将首先从数据库加载，
     * 如果数据库为空则从网络加载
     *
     * @return 数据集合
     */
    public List<DataModel> getDataList() {
        Log.i(LOG_TAG + "getDataList", "getDataList is invoked");

        return dataList;
    }

    /**
     * 从数据库加载数据
     *
     * @param operator 数据库操作对象
     *
     * @return 数据集合
     */
    protected List<DataModel> onLoadFromDataBase(BaseOperator<DataModel> operator, Condition
            parameter) {
        if (operator == null || operator.IsEmpty()) {
            Log.i(LOG_TAG + "onLoadFromDataBase", "database null");
            return null;
        }

        return operator.queryAll();
    }

    /**
     * 从网络加载数据
     *
     * @param parameter 取值条件参数
     */
    protected abstract void onLoadFromNetWork(Condition parameter);

    /**
     * 网络请求结束后在回调中调用
     *
     * @param state 网络任务执行结果
     * @param data  结果数据
     */
    protected void netWorkEndSetData(boolean state, List<DataModel> data) {
        Log.i(LOG_TAG + "netWorkEndSetData", "result is " + state);
        // 提取结果
        Log.i(LOG_TAG + "netWorkEndSetData", "onNetworkEnd is invoked");
        dataList = onNetworkEnd(state, data);
        // 保存数据
        Log.i(LOG_TAG + "netWorkEndSetData", "onSaveData is invoked");
        onSaveData(operator, dataList);
        onNotify(context);
        // 加载结束
        loading = false;
    }

    /**
     * 整理从服务器取回的数据
     *
     * @param state 执行结果
     * @param data  响应数据
     *
     * @return 整理好的数据集
     */
    protected List<DataModel> onNetworkEnd(boolean state, List<DataModel> data) {
        return state ? data : null;
    }

    /**
     * 将服务器取回的数据持久化到本地
     *
     * @param operator 数据库操作类
     * @param dataList 数据集
     */
    protected void onSaveData(BaseOperator<DataModel> operator, List<DataModel> dataList) {
        if (operator != null && dataList != null) {
            operator.clear();
            operator.insert(dataList);
        }
    }

    /**
     * 数据加载结束发送广播通知
     */
    protected void onNotify(Context context) {
    }
}
