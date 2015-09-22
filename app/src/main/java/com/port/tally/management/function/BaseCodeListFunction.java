package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;
import android.util.Log;

import org.mobile.library.model.database.BaseOperator;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.model.work.WorkModel;

import java.util.List;

/**
 * 代码列表功能基类
 *
 * @param <DataModel> 代码数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public abstract class BaseCodeListFunction<DataModel> {

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
     * 网络任务
     */
    private WorkModel work = null;

    /**
     * 标识是否正在从网络加载
     */
    private volatile boolean networkLoading = false;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public BaseCodeListFunction(Context context) {
        this.context = context;
        Log.i(LOG_TAG + "BaseCodeListFunction", "BaseCodeListFunction is invoked");
        Log.i(LOG_TAG + "BaseCodeListFunction", "onCreateOperator is invoked");
        this.operator = onCreateOperator(context);
        Log.i(LOG_TAG + "BaseCodeListFunction", "onCreateWork is invoked");
        this.work = onCreateWork(this.operator, context);
        // 初始化数据
        onCreate();
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
     * 创建获取数据源的网络任务对象
     *
     * @param operator 数据库操作对象{@link #onCreateOperator(Context)}中返回的对象
     * @param context  上下文
     *
     * @return 网络任务模型对象
     */
    protected abstract WorkModel<?, List<DataModel>> onCreateWork(BaseOperator<DataModel>
                                                                          operator, Context
            context);

    /**
     * 判断是否正在从网络获取数据
     *
     * @return true表示正在从网络加载数据
     */
    public boolean isNetworkLoading() {
        return networkLoading;
    }

    /**
     * 数据初始化
     */
    public void onCreate() {
        getDataList();
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
        if (dataList == null) {
            // 从数据库拉取
            Log.i(LOG_TAG + "getDataList", "from database");
            dataList = onLoadFromDataBase(operator);
        }

        if (dataList == null || dataList.size() == 0) {
            // 从网络拉取
            Log.i(LOG_TAG + "getDataList", "from network");
            networkLoading = true;
            onLoadFromNetWork(work);
        }

        return dataList;
    }

    /**
     * 从数据库加载数据
     *
     * @param operator 数据库操作对象
     *
     * @return 数据集合
     */
    protected List<DataModel> onLoadFromDataBase(BaseOperator<DataModel> operator) {
        if (operator == null || operator.IsEmpty()) {
            Log.i(LOG_TAG + "onLoadFromDataBase", "database null");
            return null;
        }

        return operator.queryAll();
    }

    /**
     * 从网络加载数据
     *
     * @param work 网络任务
     */
    private void onLoadFromNetWork(WorkModel<?, List<DataModel>> work) {
        if (work == null) {
            onNetworkEnd(false, null);
            return;
        }

        // 设置回调
        work.setWorkBackListener(new WorkBack<List<DataModel>>() {
            @Override
            public void doEndWork(boolean state, List<DataModel> data) {
                Log.i(LOG_TAG + "onLoadFromNetWork", "result is " + state);
                // 提取结果
                Log.i(LOG_TAG + "onLoadFromNetWork", "onNetworkEnd is invoked");
                dataList = onNetworkEnd(state, data);
                // 保存数据
                Log.i(LOG_TAG + "onLoadFromNetWork", "onSaveData is invoked");
                onSaveData(operator, dataList);
                networkLoading = false;
            }
        });

        // 启动任务
        onStartWork(work);
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
        if (operator != null) {
            operator.clear();
            operator.insert(dataList);
        }
    }

    /**
     * 启动网络任务，
     * 可在这里加载参数
     *
     * @param work 网络任务
     */
    protected void onStartWork(WorkModel<?, List<DataModel>> work) {
        work.beginExecute();
    }
}
