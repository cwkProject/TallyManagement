package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/12/14.
 */

import android.util.Log;

import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * 交接班提交数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/12/14
 * @since 1.0
 */
public class ShiftChangeCommitData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeCommitData.";

    /**
     * 发送者编码
     */
    private String send = null;

    /**
     * 接收者编码
     */
    private String receive = null;

    /**
     * 发送者公司编码
     */
    private String sendCompany = null;

    /**
     * 接收者公司编码
     */
    private String receiveCompany = null;

    /**
     * 要发送的文本信息
     */
    private String content = null;

    /**
     * 发送的图片数量
     */
    private String imageCount = null;

    /**
     * 发送的音频数量
     */
    private String audioCount = null;

    /**
     * 消息令牌
     */
    private String token = null;

    /**
     * 设置消息发送者编码
     *
     * @param send 发送者ID
     */
    public void setSend(String send) {
        this.send = send;
    }

    /**
     * 设置消息接收者编码
     *
     * @param receive 接收者ID
     */
    public void setReceive(String receive) {
        this.receive = receive;
    }

    /**
     * 设置消息发送者公司编码
     *
     * @param sendCompany 公司编码
     */
    public void setSendCompany(String sendCompany) {
        this.sendCompany = sendCompany;
    }

    /**
     * 设置消息接收者公司编码
     *
     * @param receiveCompany 公司编码
     */
    public void setReceiveCompany(String receiveCompany) {
        this.receiveCompany = receiveCompany;
    }

    /**
     * 设置消息正文
     *
     * @param content 消息内容字符串
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 设置要发送的图片数量
     *
     * @param imageCount 图片数量字符串
     */
    public void setImageCount(String imageCount) {
        this.imageCount = imageCount;
    }

    /**
     * 设置要发送的音频数量
     *
     * @param audioCount 音频数量字符串
     */
    public void setAudioCount(String audioCount) {
        this.audioCount = audioCount;
    }

    /**
     * 获取发送消息的消息令牌
     *
     * @return 消息的唯一令牌
     */
    public String getToken() {
        return token;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        dataMap.put("CodeUserFirst", send);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeUserFirst is " + send);
        dataMap.put("CodeCompanyFirst", sendCompany);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeCompanyFirst is " + sendCompany);
        dataMap.put("CodeUserSecond", receive);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeUserSecond is " + receive);
        dataMap.put("CodeCompanySecond", receiveCompany);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeCompanySecond is " + receiveCompany);
        dataMap.put("Text", content);
        Log.i(LOG_TAG + "onFillRequestParameters", "Text is " + content);
        dataMap.put("CodeUserFirst", imageCount);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeUserFirst is " + imageCount);
        dataMap.put("CodeUserFirst", audioCount);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeUserFirst is " + audioCount);
    }

    @Override
    protected boolean onRequestResult(JSONObject handleResult) throws Exception {
        // 得到执行结果
        String resultState = handleResult.getString("IsSuccess");

        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
    }

    @Override
    protected String onRequestMessage(boolean result, JSONObject handleResult) throws Exception {
        return handleResult.getString("Message");
    }

    @Override
    protected void onRequestSuccess(JSONObject handleResult) throws Exception {
        token = handleResult.getString("Data");
        Log.i(LOG_TAG + "onRequestSuccess", "token is " + token);
    }
}
