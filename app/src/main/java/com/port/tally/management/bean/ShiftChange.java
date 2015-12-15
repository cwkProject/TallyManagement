package com.port.tally.management.bean;
/**
 * Created by 超悟空 on 2015/12/14.
 */

import java.util.Map;

/**
 * 交接班数据持久化模型
 *
 * @author 超悟空
 * @version 1.0 2015/12/14
 * @since 1.0
 */
public class ShiftChange {

    /**
     * 消息对应的唯一编码
     */
    private String token = null;

    /**
     * 发送者姓名
     */
    private String send = null;

    /**
     * 接收者姓名
     */
    private String receive = null;

    /**
     * 消息内容
     */
    private String content = null;

    /**
     * 发送时间
     */
    private String time = null;

    /**
     * 图片缓存key及对应图片url地址
     */
    private Map<String, String> imageUrlList = null;

    /**
     * 音频缓存key及对应音频url地址
     */
    private Map<String, String> audioUrlList = null;

    /**
     * 获取发送者姓名
     *
     * @return 姓名
     */
    public String getSend() {
        return send;
    }

    /**
     * 设置发送者姓名
     *
     * @param send 姓名
     */
    public void setSend(String send) {
        this.send = send;
    }

    /**
     * 获取消息内容
     *
     * @return 内容文本
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 内容文本
     */
    public void setContent(String content) {
        this.content = content;
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
     * 获取图片缓存key及对应图片url地址
     *
     * @return 图片缓存key及对应图片url地址
     */
    public Map<String, String> getImageUrlList() {
        return imageUrlList;
    }

    /**
     * 设置图片缓存key及对应图片url地址
     *
     * @param imageUrlList 图片缓存key及对应图片url地址
     */
    public void setImageUrlList(Map<String, String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    /**
     * 获取音频缓存key及对应音频url地址
     *
     * @return 音频缓存key及对应音频url地址
     */
    public Map<String, String> getAudioUrlList() {
        return audioUrlList;
    }

    /**
     * 设置音频缓存key及对应音频url地址
     *
     * @param audioUrlList 音频缓存key及对应音频url地址
     */
    public void setAudioUrlList(Map<String, String> audioUrlList) {
        this.audioUrlList = audioUrlList;
    }

    /**
     * 获得接收者姓名
     *
     * @return 接收者姓名
     */
    public String getReceive() {
        return receive;
    }

    /**
     * 设置接收者姓名
     *
     * @param receive 接收者姓名
     */
    public void setReceive(String receive) {
        this.receive = receive;
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
