package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

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
     * 线程池线程数
     */
    private static final int POOL_COUNT = Runtime.getRuntime().availableProcessors() * 3 + 2;

    /**
     * 线程池
     */
    private static ExecutorService taskExecutor = Executors.newFixedThreadPool(POOL_COUNT);

    /**
     * 功能数据工具列表
     */
    private static Map<String, BaseCodeListFunction<?>> functionList = new HashMap<>();

    /**
     * 将一个功能数据列表工具加入管理器，
     * 并自动完成数据初始化，
     * 如果标签已存在则会覆盖原对象
     *
     * @param tag              工具索引标签
     * @param codeListFunction 工具对象
     */
    public static void put(String tag, final BaseCodeListFunction<?> codeListFunction) {
        taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                codeListFunction.onCreate();
            }
        });

        functionList.put(tag, codeListFunction);
    }

    /**
     * 获取功能数据列表工具
     *
     * @param tag 工具标签
     *
     * @return 列表工具对象，没有返回null
     */
    public static BaseCodeListFunction<?> get(String tag) {
        return functionList.get(tag);
    }
}
