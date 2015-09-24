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
     * 如果标签已存在则会覆盖原对象
     *
     * @param tag              工具索引标签
     * @param codeListFunction 工具对象
     */
    public static void put(String tag, final BaseCodeListFunction codeListFunction) {
        Log.i(LOG_TAG + "put", "put tag " + tag);
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
