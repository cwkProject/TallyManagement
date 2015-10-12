package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/9/7.
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.port.tally.management.R;
import com.port.tally.management.function.CargoOwnerListFunction;
import com.port.tally.management.function.CargoTypeListFunction;
import com.port.tally.management.function.CodeListManager;
import com.port.tally.management.function.ForwarderListFunction;
import com.port.tally.management.function.OperationListFunction;
import com.port.tally.management.function.VoyageListFunction;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.common.function.AutoLogin;
import org.mobile.library.util.BroadcastUtil;
import org.mobile.library.util.LoginStatus;

/**
 * 启动页
 *
 * @author 超悟空
 * @version 1.0 2015/9/7
 * @since 1.0
 */
public class SplashActivity extends Activity {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "SplashActivity.";

    /**
     * 本地广播管理器
     */
    private LocalBroadcastManager localBroadcastManager = null;

    /**
     * 数据加载结果的广播接收者
     */
    private LoadingReceiver loadingReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 检测并加载数据
        checkLoadData();
    }

    /**
     * 检测并加载数据
     */
    private void checkLoadData() {

        // 注册广播接收者
        registerReceivers();

        // 自动登录
        autoLogin();

        // 尝试加载本地数据
        loadData();
    }

    /**
     * 尝试加载本地数据
     */
    private void loadData() {
        CodeListManager.put(StaticValue.CodeListTag.CARGO_TYPE_LIST, new CargoTypeListFunction
                (this));
        CodeListManager.put(StaticValue.CodeListTag.CARGO_OWNER_LIST, new CargoOwnerListFunction
                (this));
        CodeListManager.put(StaticValue.CodeListTag.VOYAGE_LIST, new VoyageListFunction(this));
        CodeListManager.put(StaticValue.CodeListTag.OPERATION_LIST, new OperationListFunction
                (this));
    }

    /**
     * 登录成功后加载数据
     */
    private void onLoginLoadData() {
        CodeListManager.put(StaticValue.CodeListTag.FORWARDER_LIST, new ForwarderListFunction
                (this, LoginStatus.getLoginStatus().getCodeCompany()), true);
    }

    /**
     * 自动登录
     */
    private void autoLogin() {
        Log.i(LOG_TAG + "autoLogin", "autoLogin() is invoked");

        // 自动登录
        AutoLogin.checkAutoLogin(SplashActivity.this, StaticValue.APP_CODE);
    }

    //    /**
    //     * 初始化推送通知
    //     */
    //    private void initNotify() {
    //        Log.i(LOG_TAG + "initNotify", "android push open");
    //        // 启动推送服务
    //        ServiceManager serviceManager = new ServiceManager(this);
    //        serviceManager.setNotificationIcon(R.mipmap.ic_launcher);
    //        serviceManager.startService();
    //    }

    /**
     * 注册广播接收者
     */
    private void registerReceivers() {
        Log.i(LOG_TAG + "registerReceivers", "registerReceivers() is invoked");
        // 新建本地广播管理器
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        // 新建接收者
        loadingReceiver = new LoadingReceiver();

        // 注册
        localBroadcastManager.registerReceiver(loadingReceiver, loadingReceiver
                .getRegisterIntentFilter());
    }

    /**
     * 注销广播接收者
     */
    private void unregisterReceivers() {
        Log.i(LOG_TAG + "unregisterReceivers", "unregisterReceivers() is invoked");
        if (localBroadcastManager == null) {
            return;
        }

        if (loadingReceiver != null) {
            localBroadcastManager.unregisterReceiver(loadingReceiver);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 注销广播接收者
        unregisterReceivers();
    }

    /**
     * 数据加载结果的广播接收者，
     * 用于提前结束启动页
     *
     * @author 超悟空
     * @version 1.0 2015/1/31
     * @since 1.0
     */
    private class LoadingReceiver extends BroadcastReceiver {

        /**
         * 动作队列信号量，
         * 初始时为注册的动作数量，
         * 当减少到0时表示数据加载完毕
         */
        private volatile int actionSemaphore = 6;

        /**
         * 得到本接收者监听的动作集合
         *
         * @return 填充完毕的意图集合
         */
        public final IntentFilter getRegisterIntentFilter() {
            // 新建动作集合
            IntentFilter filter = new IntentFilter();
            // 登录结果监听
            filter.addAction(BroadcastUtil.MEMORY_STATE_LOGIN);
            // 数据加载
            filter.addAction(StaticValue.CodeListTag.CARGO_TYPE_LIST);
            filter.addAction(StaticValue.CodeListTag.CARGO_OWNER_LIST);
            filter.addAction(StaticValue.CodeListTag.VOYAGE_LIST);
            filter.addAction(StaticValue.CodeListTag.OPERATION_LIST);
            filter.addAction(StaticValue.CodeListTag.FORWARDER_LIST);
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {

            // 得到动作字符串
            String actionString = intent.getAction();
            Log.i(LOG_TAG + "LoadingReceiver.onReceive", "action is " + actionString);

            switch (actionString) {
                case BroadcastUtil.MEMORY_STATE_LOGIN:
                    // 登录执行完毕
                    if (LoginStatus.getLoginStatus().isLogin()) {
                        // 登录成功
                        // 加载数据
                        onLoginLoadData();
                    } else {
                        // 跳过一条数据加载
                        actionSemaphore--;
                    }
                case StaticValue.CodeListTag.CARGO_TYPE_LIST:
                case StaticValue.CodeListTag.CARGO_OWNER_LIST:
                case StaticValue.CodeListTag.VOYAGE_LIST:
                case StaticValue.CodeListTag.OPERATION_LIST:
                case StaticValue.CodeListTag.FORWARDER_LIST:
                    // 完成一个动作信号量减1
                    actionSemaphore--;
                    Log.i(LOG_TAG + "LoadingReceiver.onReceive", "actionSemaphore--");
            }
            Log.i(LOG_TAG + "LoadingReceiver.onReceive", "now actionSemaphore is " +
                    actionSemaphore);

            if (actionSemaphore <= 0) {
                // 数据加载完成
                // 注销广播接收者
                unregisterReceivers();
                // 跳转
                jump();
            }
        }
    }

    /**
     * 数据加载完毕跳转
     */
    private void jump() {
        Log.i(LOG_TAG + "instantJump", "instantJump() is invoked");

        if (LoginStatus.getLoginStatus().isLogin()) {
            // 已登录
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(mainIntent);
        } else {
            // 未登录
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        finish();
    }
}
