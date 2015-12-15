package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/12/12.
 */

import java.util.Map;

/**
 * 交接班内容列表数据对象
 *
 * @author 超悟空
 * @version 1.0 2015/12/12
 * @since 1.0
 */
public class ShiftChangeContent {

    /**
     * 消息对应的唯一编码
     */
    private String token = null;

    /**
     * 发送者姓名
     */
    private String name = null;

    /**
     * 消息内容
     */
    private String message = null;

    /**
     * 发送时间
     */
    private String time = null;

    /**
     * 图片缓存key列表及当前进度
     */
    private Map<String, Integer> imageList = null;

    /**
     * 音频缓存key列表及当前进度
     */
    private Map<String, Integer> audioList = null;

    /**
     * 标识是否为发送消息
     */
    private boolean send = false;

    /**
     * 获取发送者姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置发送者姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取消息内容
     *
     * @return 内容文本
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置消息内容
     *
     * @param message 内容文本
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取发送时间
     *
     * @return 时间字符串
     */
    public String getTime() {
        return time;
    }

    /**
     * 设置发送时间
     *
     * @param time 时间字符串
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取图片缓存key列表及当前进度
     *
     * @return 图片缓存key及当前进度
     */
    public Map<String, Integer> getImageList() {
        return imageList;
    }

    /**
     * 设置图片缓存key列表及当前进度
     *
     * @param imageList 图片缓存key及当前进度
     */
    public void setImageList(Map<String, Integer> imageList) {
        this.imageList = imageList;
    }

    /**
     * 获取音频缓存key列表及当前进度
     *
     * @return 音频缓存key及当前进度
     */
    public Map<String, Integer> getAudioList() {
        return audioList;
    }

    /**
     * 设置音频缓存key列表及当前进度
     *
     * @param audioList 音频缓存key及当前进度
     */
    public void setAudioList(Map<String, Integer> audioList) {
        this.audioList = audioList;
    }

    /**
     * 判断是否为待发送消息
     *
     * @return true表示这是一条要发送的消息
     */
    public boolean isSend() {
        return send;
    }

    /**
     * 设置是否为待发送消息
     *
     * @param send true表示这是一条要发送的消息
     */
    public void setSend(boolean send) {
        this.send = send;
    }

    /**
     * 获取消息唯一标识
     *
     * @return 消息token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置消息唯一标识
     *
     * @param token 消息token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
