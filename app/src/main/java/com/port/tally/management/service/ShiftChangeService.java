package com.port.tally.management.service;
/**
 * Created by 超悟空 on 2015/12/19.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.port.tally.management.bean.ShiftChangeContent;
import com.port.tally.management.util.ImageUtil;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.DirectFileDownLoadWork;
import com.port.tally.management.work.SingleUploadFileWork;

import org.mobile.library.cache.util.CacheManager;
import org.mobile.library.cache.util.CacheTool;
import org.mobile.library.model.work.WorkBack;
import org.mobile.library.network.util.NetworkProgressListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
     * 更新进度
     */
    public static final int UPDATE_PROGRESS = 6;

    /**
     * 数据加载失败
     */
    public static final int LOAD_FAILED = 8;

    /**
     * 数据加载成功
     */
    public static final int LOAD_SUCCESS = 9;

    /**
     * 获取消息中token的取值标签
     */
    public static final String TOKEN_TAG = "token_tag";

    /**
     * 获取消息中key的取值标签
     */
    public static final String KEY_TAG = "key_tag";

    /**
     * 获取消息中进度值的取值标签
     */
    public static final String PROGRESS_TAG = "progress_tag";

    /**
     * 获取消息中文件类型的取值标签
     */
    public static final String FILE_TYPE_TAG = "file_type_tag";

    /**
     * 客户端消息
     */
    private Messenger clientMessenger = null;

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

    /**
     * 交接班服务通信接口
     */
    public class ShiftChangeServiceBinder extends Binder {

        /**
         * 绑定客户端消息管理器，用于服务端向客户端推消息
         *
         * @param messenger 客户端消息管理器
         */
        public void bindMessenger(Messenger messenger) {
            connecting = true;
            clientMessenger = messenger;
        }

        /**
         * 上传一个文件
         *
         * @param token 消息标签
         * @param key   文件缓存key(不含前缀)
         * @param type  文件类型
         */
        public void uploadFile(final String token, final String key, final int type) {
            Log.i(LOG_TAG + "uploadFile", "token:" + token + " key:" + key + " type:" + type);
            if (addTask(token, key)) {

                SingleUploadFileWork singleUploadFileWork = new SingleUploadFileWork();

                singleUploadFileWork.setWorkEndListener(new WorkBack<Void>() {
                    @Override
                    public void doEndWork(boolean state, Void aVoid) {
                        // 移除任务
                        if (removeTask(token, key)) {
                            if (state) {
                                // 上传成功
                                sendMessage(LOAD_SUCCESS, token, key, type, -1);
                            } else {
                                // 上传失败
                                sendMessage(LOAD_FAILED, token, key, type, -1);
                            }
                        }
                    }
                }, false);

                singleUploadFileWork.setProgressListener(new NetworkProgressListener() {
                    @Override
                    public void onRefreshProgress(long current, long total, boolean done) {
                        updateProgress(token, key, type, (int) (current * 100 / total));
                    }
                }, false);

                switch (type) {
                    case StaticValue.TypeTag.TYPE_IMAGE_CONTENT:
                        // 图片类型
                        uploadImage(singleUploadFileWork, token, key);
                        break;
                    case StaticValue.TypeTag.TYPE_AUDIO_CONTENT:
                        // 音频类型
                        uploadAudio(singleUploadFileWork, token, key);
                        break;
                }
            }
        }

        /**
         * 下载一个文件
         *
         * @param token 消息标签
         * @param key   文件缓存key(不含前缀)
         * @param type  文件类型
         */
        public void downloadFile(final String token, final String key, String url, final int type) {
            Log.i(LOG_TAG + "downloadFile", "token:" + token + " key:" + key + " url:" + url + " " +
                    "type:" + type);
            if (addTask(token, key)) {

                DirectFileDownLoadWork downLoadWork = new DirectFileDownLoadWork();

                downLoadWork.setWorkEndListener(new WorkBack<String>() {
                    @Override
                    public void doEndWork(boolean state, String s) {
                        // 移除任务
                        if (removeTask(token, key)) {
                            if (state) {
                                // 下载成功
                                sendMessage(LOAD_SUCCESS, token, key, type, -1);
                            } else {
                                // 下载失败
                                sendMessage(LOAD_FAILED, token, key, type, -1);
                            }
                        }
                    }
                }, false);

                downLoadWork.setProgressListener(new NetworkProgressListener() {
                    @Override
                    public void onRefreshProgress(long current, long total, boolean done) {
                        updateProgress(token, key, type, (int) (current * 100 / total));
                    }
                }, false);

                switch (type) {
                    case StaticValue.TypeTag.TYPE_IMAGE_CONTENT:
                        // 图片类型
                        downloadImage(downLoadWork, token, key, url);
                        break;
                    case StaticValue.TypeTag.TYPE_AUDIO_CONTENT:
                        // 音频类型
                        downloadAudio(downLoadWork, token, key, url);
                        break;
                }
            }
        }

        /**
         * 修正进度
         *
         * @param shiftChangeContent 待修正的消息数据
         */
        public void correctProgress(ShiftChangeContent shiftChangeContent) {
            Log.i(LOG_TAG + "correctProgress", "token:" + shiftChangeContent.getToken());
            if (progressList.containsKey(shiftChangeContent.getToken())) {
                Map<String, Integer> progressMap = progressList.get(shiftChangeContent.getToken());

                // 修正图片进度
                if (shiftChangeContent.getImageList() != null) {
                    for (String key : shiftChangeContent.getImageList().keySet()) {
                        if (progressMap.containsKey(key)) {
                            // 修正一条数据
                            shiftChangeContent.getImageList().put(key, progressMap.get(key));
                        }
                    }
                }

                // 修正音频进度
                if (shiftChangeContent.getAudioList() != null) {
                    for (String key : shiftChangeContent.getAudioList().keySet()) {
                        if (progressMap.containsKey(key)) {
                            // 修正一条数据
                            shiftChangeContent.getAudioList().put(key, progressMap.get(key));
                        }
                    }
                }
            }
        }
    }

    /**
     * 服务的功能接口
     */
    private ShiftChangeServiceBinder binder = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG + "onCreate", "service create");

        progressList = new ConcurrentHashMap<>();

        cacheTool = CacheManager.getCacheTool("shift_change_content");

        binder = new ShiftChangeServiceBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG + "onBind", "service bind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(LOG_TAG + "onUnbind", "service unbind");
        connecting = false;
        clientMessenger = null;

        if (progressList.isEmpty()) {
            Log.i(LOG_TAG + "onUnbind", "no task stop service");
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
                    Log.i(LOG_TAG + "removeTask", "token:" + token + " key:" + key + ", token" +
                            " " +

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
     * @param type     文件类型
     * @param progress 新进度
     */
    private void updateProgress(String token, String key, int type, int progress) {
        Log.i(LOG_TAG + "updateProgress", "token:" + token + " key:" + key + " type:" + type + " " +
                "new progress:" + progress);

        if (progressList.containsKey(token)) {
            Log.i(LOG_TAG + "updateProgress", "token:" + token + " key:" + key + ", token exist");
            Map<String, Integer> map = progressList.get(token);

            if (map.containsKey(key)) {
                Log.i(LOG_TAG + "updateProgress", "token:" + token + " key:" + key + ", key " +
                        "exist, update progress");
                map.put(key, progress);
                // 发送消息到客户端
                sendMessage(UPDATE_PROGRESS, token, key, type, progress);
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
     * 向客户端发送一条消息
     *
     * @param target   执行动作
     * @param token    交接班消息标识
     * @param key      资源缓存key
     * @param type     文件类型
     * @param progress 进度，-1表示不发送
     */
    private void sendMessage(int target, String token, String key, int type, int progress) {
        Log.i(LOG_TAG + "sendMessage", "target:" + target + " token:" + token + " key:" + key + "" +
                " progress:" + progress);

        if (!connecting) {
            Log.d(LOG_TAG + "sendMessage", "target:" + target + " token:" + token + " key:" + key
                    + " type:" + type + " progress:" + progress + ", client not connecting");
            return;
        }

        Message message = Message.obtain(null, target);

        Bundle bundle = new Bundle();
        bundle.putString(TOKEN_TAG, token);
        bundle.putString(KEY_TAG, key);
        bundle.putInt(FILE_TYPE_TAG, type);

        if (progress > -1) {
            bundle.putInt(PROGRESS_TAG, progress);
        }

        message.setData(bundle);

        try {
            clientMessenger.send(message);
        } catch (RemoteException e) {
            Log.e(LOG_TAG + "sendMessage", "target:" + target + " token:" + token + " key:" + key
                    + " progress:" + progress + " RemoteException is " + e.getMessage());
        }
    }

    /**
     * 上传一个音频
     *
     * @param singleUploadFileWork 上传任务
     * @param token                消息标签
     * @param key                  缓存key(不含前缀)
     */
    private void uploadAudio(SingleUploadFileWork singleUploadFileWork, String token, String key) {
        singleUploadFileWork.beginExecute(token, cacheTool.getForFile(key).getPath(), "amr");
    }

    /**
     * 上传一个图片
     *
     * @param singleUploadFileWork 上传任务
     * @param token                消息标签
     * @param key                  缓存key(不含前缀)
     */
    private void uploadImage(final SingleUploadFileWork singleUploadFileWork, final String token,
                             String key) {

        final String originalKey = key;

        File file = cacheTool.getForFile(ImageUtil.COMPRESSION_IMAGE_CACHE_PRE + originalKey);

        if (file == null || !file.exists()) {

            ImageUtil.processPicture(cacheTool.getForFile(ImageUtil.SOURCE_IMAGE_CACHE_PRE +
                    originalKey), cacheTool, originalKey, new ImageUtil.ProcessFinishListener() {

                @Override
                public void finish(CacheTool cacheTool, String key) {
                    if (key != null) {
                        // 压缩成功
                        singleUploadFileWork.beginExecute(token, cacheTool.getForFile(key)
                                .getPath(), "jpg");
                    } else {
                        // 压缩失败
                        // 移除任务
                        if (removeTask(token, originalKey)) {
                            // 上传失败
                            sendMessage(LOAD_FAILED, token, originalKey, StaticValue.TypeTag
                                    .TYPE_IMAGE_CONTENT, -1);
                        }
                    }
                }
            });
        } else {
            singleUploadFileWork.beginExecute(token, cacheTool.getForFile(ImageUtil
                    .COMPRESSION_IMAGE_CACHE_PRE + originalKey).getPath(), "jpg");
        }
    }

    /**
     * 下载一个音频
     *
     * @param downLoadWork 下载任务
     * @param token        消息标签
     * @param key          缓存key(不含前缀)
     */
    private void downloadAudio(DirectFileDownLoadWork downLoadWork, String token, String key,
                               String url) {
        try {
            cacheTool.putAndBack(key).close();
        } catch (IOException e) {
            Log.e(LOG_TAG + "downloadAudio", "token:" + token + " key:" + key + " url:" +
                    url + " IOException is " + e.getMessage());
        }

        downLoadWork.beginExecute(url, cacheTool.getForFile(key).getPath());
    }

    /**
     * 下载一个图片
     *
     * @param downLoadWork 下载任务
     * @param token        消息标签
     * @param key          缓存key(不含前缀)
     */
    private void downloadImage(DirectFileDownLoadWork downLoadWork, final String token, String
            key, String url) {
        try {
            cacheTool.putAndBack(ImageUtil.COMPRESSION_IMAGE_CACHE_PRE + key).close();
        } catch (IOException e) {
            Log.e(LOG_TAG + "downloadImage", "token:" + token + " key:" + key + " url:" +
                    url + " IOException is " + e.getMessage());
        }

        downLoadWork.beginExecute(url, cacheTool.getForFile(ImageUtil.COMPRESSION_IMAGE_CACHE_PRE
                + key).getPath());
    }
}
