package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.Context;
import android.util.Log;

import com.port.tally.management.util.StaticValue;

import org.mobile.library.global.GlobalApplication;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    private static Map<String, BaseCodeListFunction> functionList = new ConcurrentHashMap<>();

    /**
     * 创建指定的数据列表工具
     *
     * @param tag 工具索引标签
     */
    public static void create(String tag) {
        create(tag, false);
    }

    /**
     * 创建指定的数据列表工具
     *
     * @param tag      工具索引标签
     * @param recreate 标识当工具已存在时是否重新创建工具对象，true表示重新创建，默认为false
     */
    public static void create(String tag, boolean recreate) {
        create(tag, null, recreate);
    }

    /**
     * 创建指定的数据列表工具
     *
     * @param tag        工具索引标签
     * @param parameters 创建工具所需的参数，没有则传入null
     */
    public static void create(final String tag, final String[] parameters) {
        create(tag, parameters, false);
    }

    /**
     * 创建指定的数据列表工具
     *
     * @param tag        工具索引标签
     * @param parameters 创建工具所需的参数，没有则传入null
     * @param recreate   标识当工具已存在时是否重新创建工具对象，true表示重新创建，默认为false
     */
    public static void create(final String tag, final String[] parameters, final boolean recreate) {
        Log.i(LOG_TAG + "create", "create tag:" + tag + " recreate tag:" + recreate);

        // 目标不存在(可能是上次加载失败)或需要替换
        taskExecutor.submit(new Runnable() {
            @Override
            public void run() {
                Log.i(LOG_TAG + "put", "task run");
                // 尝试获取指定标签的工具对象
                BaseCodeListFunction codeList = get(tag);

                if (codeList != null) {
                    // 目标已存在
                    if (recreate) {
                        // 需要替换
                        // 取消正在加载的对象
                        codeList.cancel();
                    } else {
                        // 不必替换且
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
                }

                onCreate(tag, parameters);
            }
        });
    }

    /**
     * 创建指定的数据列表工具
     *
     * @param tag        工具索引标签
     * @param parameters 创建工具所需的参数，没有则传入null
     */
    private static void onCreate(String tag, String[] parameters) {
        BaseCodeListFunction codeList = null;

        Context context = GlobalApplication.getGlobal();

        switch (tag) {
            case StaticValue.CodeListTag.CARGO_TYPE_LIST:
                // 货物类别
                codeList = new CargoTypeListFunction(context);
                break;
            case StaticValue.CodeListTag.CARGO_OWNER_LIST:
                // 货主
                codeList = new CargoOwnerListFunction(context);
                break;
            case StaticValue.CodeListTag.VOYAGE_LIST:
                // 航次
                codeList = new VoyageListFunction(context);
                break;
            case StaticValue.CodeListTag.OPERATION_LIST:
                // 作业过程
                codeList = new OperationListFunction(context);
                break;
            case StaticValue.CodeListTag.FORWARDER_LIST:
                // 货代
                codeList = new ForwarderListFunction(context, GlobalApplication.getGlobal()
                        .getLoginStatus().getCodeCompany());
                break;
            case StaticValue.CodeListTag.STORAGE_LIST:
                // 货场
                codeList = new StorageListFunction(context, GlobalApplication.getGlobal()
                        .getLoginStatus().getCodeCompany());
                break;
            case StaticValue.CodeListTag.COMPANY_LIST:
                // 公司
                codeList = new CompanyListFunction(context);
                break;
            case StaticValue.CodeListTag.EMPLOYEE_LIST:
                // 员工
                codeList = new EmployeeListFunction(context, GlobalApplication.getGlobal()
                        .getLoginStatus().getCodeCompany());
                break;
        }

        if (codeList != null) {
            Log.i(LOG_TAG + "put", "put list");
            functionList.put(tag, codeList);
            codeList.onCreate();
        }
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
