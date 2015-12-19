package com.port.tally.management.service;
/**
 * Created by 超悟空 on 2015/12/19.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;

import java.util.HashMap;
import java.util.Map;

/**
 * 交接班文件上传下载服务
 *
 * @author 超悟空
 * @version 1.0 2015/12/19
 * @since 1.0
 */
public class ShiftChangeService extends Service {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeService.";

    /**
     * 绑定客户端消息标识
     */
    public static final int BIND_MESSENGER = 1;

    /**
     * 启动一个图片上传
     */
    public static final int UPLOAD_IMAGE = 2;

    /**
     * 启动一个音频上传
     */
    public static final int UPLOAD_AUDIO = 3;

    /**
     * 启动一个图片下载
     */
    public static final int DOWNLOAD_IMAGE = 4;

    /**
     * 启动一个音频下载
     */
    public static final int DOWNLOAD_AUDIO = 5;

    /**
     * 更新进度
     */
    public static final int UPDATE_PROGRESS = 6;

    /**
     * 修正进度
     */
    public static final int CORRECT_PROGRESS = 7;

    /**
     * 数据加载失败
     */
    public static final int LOAD_FAILED = 8;

    /**
     * 客户端消息
     */
    private Messenger clientMessenger = null;

    /**
     * 服务端端消息
     */
    private Messenger serviceMessenger = null;

    /**
     * 客户端连接状态
     */
    private boolean connecting = false;

    /**
     * 存放数据加载进度的列表
     */
    private Map<String, Map<String, Integer>> progressList = null;

    /**
     * 存放文件的缓存工具
     */
    private CacheTool cacheTool = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG + "onCreate", "service create");

        progressList = new HashMap<>();

        cacheTool = CacheManager.getCacheTool("shift_change_content");

        // 注册消息响应事件
        serviceMessenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case BIND_MESSENGER:
                        // 绑定客户端消息
                        break;
                    case UPLOAD_IMAGE:
                        // 启动一个图片上传
                        break;
                    case UPLOAD_AUDIO:
                        // 启动一个音频上传
                        break;
                    case DOWNLOAD_IMAGE:
                        // 启动一个图片下载
                        break;
                    case DOWNLOAD_AUDIO:
                        // 启动一个音频下载
                        break;
                    case UPDATE_PROGRESS:
                        // 更新进度
                        break;
                    case CORRECT_PROGRESS:
                        // 修正进度
                        break;
                    case LOAD_FAILED:
                        // 数据加载失败
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG + "onBind", "service bind");
        connecting = true;
        return serviceMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG + "onUnbind", "service unbind");
        connecting = false;
        clientMessenger = null;

        if (progressList.isEmpty()) {
            stopSelf();
        }

        return super.onUnbind(intent);
    }

    /**
     * 增加一条任务
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     *
     * @return 执行结果
     */
    private boolean addTask(String token, String key) {
        Log.i(LOG_TAG + "addTask", "token:" + token + " key:" + key);

        if (progressList.containsKey(token)) {
            Log.i(LOG_TAG + "addTask", "token:" + token + " key:" + key + ", token exist");
            Map<String, Integer> map = progressList.get(token);

            if (map.containsKey(key)) {
                Log.d(LOG_TAG + "addTask", "token:" + token + " key:" + key + ", key exist, not " +
                        "start");
                return false;
            } else {
                Log.i(LOG_TAG + "addTask", "token:" + token + " key:" + key + ", key not exist, " +
                        "put " + key);
                map.put(key, 0);
            }
        } else {
            Log.i(LOG_TAG + "addTask", "token:" + token + " key:" + key + ", token not exist");
            Map<String, Integer> map = new HashMap<>();
            map.put(key, 0);
            progressList.put(token, map);
        }

        return true;
    }

    /**
     * 移除一条任务
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     *
     * @return 执行结果
     */
    private boolean removeTask(String token, String key) {
        Log.i(LOG_TAG + "removeTask", "token:" + token + " key:" + key);

        if (progressList.containsKey(token)) {
            Log.i(LOG_TAG + "removeTask", "token:" + token + " key:" + key + ", token exist");
            Map<String, Integer> map = progressList.get(token);

            if (map.containsKey(key)) {
                Log.i(LOG_TAG + "removeTask", "token:" + token + " key:" + key + ", key exist, " +
                        "remove " + key);
                map.remove(key);
                if (map.isEmpty()) {
                    Log.i(LOG_TAG + "removeTask", "token:" + token + " key:" + key + ", token " +
                            "children empty, remove " + token);
                    progressList.remove(token);
                }

                return true;
            } else {
                Log.d(LOG_TAG + "removeTask", "token:" + token + " key:" + key + ", key not " +
                        "exist");
                if (map.isEmpty()) {
                    Log.i(LOG_TAG + "removeTask", "token:" + token + " key:" + key + ", token " +
                            "children empty, remove " + token);
                    progressList.remove(token);
                }
            }
        } else {
            Log.d(LOG_TAG + "removeTask", "token:" + token + " key:" + key + ", token not exist");
        }

        return false;
    }

    /**
     * 更新一条数据加载进度
     *
     * @param token    消息标签
     * @param key      缓存key(不含前缀)
     * @param progress 新进度
     */
    private void updateProgress(String token, String key, int progress) {
        Log.i(LOG_TAG + "updateProgress", "token:" + token + " key:" + key + " new " +
                "progress:" + progress);

        if (progressList.containsKey(token)) {
            Log.i(LOG_TAG + "updateProgress", "token:" + token + " key:" + key + ", token exist");
            Map<String, Integer> map = progressList.get(token);

            if (map.containsKey(key)) {
                Log.i(LOG_TAG + "updateProgress", "token:" + token + " key:" + key + ", key " +
                        "exist, update progress");
                map.put(key, progress);
            } else {
                Log.d(LOG_TAG + "updateProgress", "token:" + token + " key:" + key + ", key not " +
                        "exist");
            }
        } else {
            Log.d(LOG_TAG + "updateProgress", "token:" + token + " key:" + key + ", token not " +
                    "exist");
        }
    }

    /**
     * 上传一个图片
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     */
    private void uploadImage(String token, String key) {

    }

    /**
     * 上传一个音频
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     */
    private void uploadAudio(String token, String key) {

    }

    /**
     * 下载一个音频
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     * @param url   下载地址
     */
    private void downloadAudio(String token, String key, String url) {

    }

    /**
     * 下载一个图片
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     * @param url   下载地址
     */
    private void downloadImage(String token, String key, String url) {

    }

    /**
     * 传输失败
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     */
    private void loadFailed(String token, String key) {

    }

    /**
     * 修正进度
     *
     * @param token 消息标签
     * @param key   缓存key(不含前缀)
     */
    private void correctProgress(String token, String key) {

    }
}
