package com.port.tally.management.function;
/**
 * Created by 超悟空 on 2015/12/24.
 */

import android.media.MediaPlayer;
import android.util.Log;

import org.mobile.library.cache.util.CacheTool;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 计算音频文件录音时长的功能类
 *
 * @author 超悟空
 * @version 1.0 2015/12/24
 * @since 1.0
 */
public class AudioFileLengthFunction {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "AudioFileLengthFunction.";

    /**
     * 音频播放器
     */
    private MediaPlayer mediaPlayer = null;

    /**
     * 存放已读取的长度集
     */
    private Map<String, String> lengthMap = null;

    /**
     * 自身静态实例
     */
    private static AudioFileLengthFunction function = null;

    /**
     * 构造函数
     */
    private AudioFileLengthFunction() {
        mediaPlayer = new MediaPlayer();
        lengthMap = new HashMap<>();
    }

    /**
     * 获取音频长度计算工具
     *
     * @return 工具实例
     */
    public synchronized static AudioFileLengthFunction getFunction() {
        if (function == null) {
            function = new AudioFileLengthFunction();
        }

        return function;
    }

    /**
     * 获取音频长度文本，
     * 如果不同的缓存工具使用相同key提取，
     * 则认为是同一个文件而不再二次读取
     *
     * @param cacheTool 存放音频的缓存工具
     * @param key       缓存key
     *
     * @return 长度文本，文件不存在或损坏则返回null
     */
    public String getAudioLength(CacheTool cacheTool, String key) {
        return getAudioLength(cacheTool, key, false);
    }

    /**
     * 获取音频长度文本，
     * 如果不同的缓存工具使用相同key提取，
     * 则认为是同一个文件而不再二次读取
     *
     * @param cacheTool 存放音频的缓存工具
     * @param key       缓存key
     * @param reload    标识重新读取，单相同key指向不同文件需要覆盖原长度值时设为true
     *
     * @return 长度文本，文件不存在或损坏则返回null
     */
    public String getAudioLength(CacheTool cacheTool, String key, boolean reload) {

        if (reload || !lengthMap.containsKey(key)) {

            File file = cacheTool.getForFile(key);
            if (file != null) {

                String lengthString = getAudioLength(file.getPath());

                if (lengthString != null) {
                    lengthMap.put(key, lengthString);
                } else {
                    Log.d(LOG_TAG + "getAudioLength", "key:" + key + " audio file error.");
                }
            } else {
                Log.d(LOG_TAG + "onBindViewHolder", "key:" + key + " no audio file");
            }
        }

        return lengthMap.get(key);
    }

    /**
     * 获取音频长度文本
     *
     * @param path 音频路径
     *
     * @return 格式化后的文本
     */
    private synchronized String getAudioLength(String path) {
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            // 音频长度
            int length = mediaPlayer.getDuration();
            mediaPlayer.reset();

            String lengthString = "";

            if (length / 60000 > 0) {
                lengthString += length / 60000 + "'";
            }
            lengthString += (length / 1000) % 60 + "\"";

            lengthString += length % 1000;

            return lengthString;
        } catch (IOException e) {
            Log.e(LOG_TAG + "getAudioLength", "IOException is " + e.getMessage());
            return null;
        }
    }

    /**
     * 释放资源
     */
    public static void release() {
        if (function != null) {
            function.mediaPlayer.release();
            function.lengthMap.clear();
            function = null;
        }
    }
}
