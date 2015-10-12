package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能数据列表工具管理器
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public class CodeListManager {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "CodeListManager.";

    /**
     * 线程池线程数
     */
    private static final int POOL_COUNT = Runtime.getRuntime().availableProcessors() * 2 + 2;

    /**
     * 线程池
     */
    private static ExecutorService taskExecutor = Executors.newFixedThreadPool(POOL_COUNT);

    /**
     * 功能数据工具列表
     */
    private static Map<String, BaseCodeListFunction> functionList = new HashMap<>();

    /**
     * 将一个功能数据列表工具加入管理器，
     * 并自动完成数据初始化，
     * 如果标签已存在则不会将新对象加入管理器，
     * 而是继续使用原有工具对象，
     * 同时会正常发送加载完成广播
     *
     * @param tag              工具索引标签
     * @param codeListFunction 工具对象
     */
    public static void put(String tag, final BaseCodeListFunction codeListFunction) {
        put(tag, codeListFunction, false);
    }

    /**
     * 将一个功能数据列表工具加入管理器，
     * 并自动完成数据初始化，
     * 如果replace为true则会强制替换已存在的tag标签对应的工具对象
     *
     * @param tag              工具索引标签
     * @param codeListFunction 工具对象
     * @param replace          标识是否替换已存在的对象
     */
    public static void put(String tag, final BaseCodeListFunction codeListFunction, boolean
            replace) {
        Log.i(LOG_TAG + "put", "put tag " + tag);

        Log.i(LOG_TAG + "put", "replace tag " + replace);

        // 尝试获取指定标签的工具对象
        BaseCodeListFunction codeList = get(tag);

        if (!replace && codeList != null) {
            // 不必替换且目标已存在
            if (codeList.isLoading()) {
                // 正在加载
                return;
            } else {
                // 加载完毕
                if (codeList.getDataList() != null) {
                    // 通知加载完成
                    codeList.notifyExist();
                    return;
                }
            }
        }

        // 目标不存在(可能是上次加载失败)或需要替换
        taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Log.i(LOG_TAG + "put", "task run");
                codeListFunction.onCreate();
            }
        });

        Log.i(LOG_TAG + "put", "put list");
        functionList.put(tag, codeListFunction);
    }

    /**
     * 获取功能数据列表工具
     *
     * @param tag 工具标签
     *
     * @return 列表工具对象，没有返回null
     */
    public static BaseCodeListFunction get(String tag) {
        Log.i(LOG_TAG + "get", "object exist is " + functionList.containsKey(tag));
        return functionList.get(tag);
    }
}
