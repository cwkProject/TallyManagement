package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/9/8.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * 业务请求样例数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/8
 * @since 1.0
 */
public class SampleData extends JsonDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "SampleData.";

    /**
     * 服务请求传入参数
     */
    private String inParameter = null;

    /**
     * 服务请求传入参数2
     */
    private int inParameter2 = 0;

    /**
     * 服务请求传出参数
     */
    private String outParameter = null;

    /**
     * 服务请求传出参数2
     */
    private int outParameter2 = 0;

    /**
     * 设置请求参数
     *
     * @param inParameter 要发送的参数
     */
    public void setInParameter(String inParameter) {
        this.inParameter = inParameter;
    }

    /**
     * 设置请求参数2
     *
     * @param inParameter2 要发送的参数
     */
    public void setInParameter2(int inParameter2) {
        this.inParameter2 = inParameter2;
    }

    /**
     * 获取请求结果
     *
     * @return 响应数据
     */
    public String getOutParameter() {
        return outParameter;
    }

    /**
     * 获取请求结果2
     *
     * @return 响应数据
     */
    public int getOutParameter2() {
        return outParameter2;
    }

    /**
     * 填充服务请求所需的参数，
     * 即设置{@link #serialization()}返回值
     *
     * @param dataMap 参数数据集<参数名,参数值>
     */
    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        // 传入请求参数
        dataMap.put("Parameter1", inParameter);
        Log.i(LOG_TAG + "onFillRequestParameters", "Parameter1 is " + inParameter);
        dataMap.put("Parameter2", String.valueOf(inParameter2));
        Log.i(LOG_TAG + "onFillRequestParameters", "Parameter2 is " + inParameter2);
    }

    /**
     * 提取服务执行结果
     *
     * @param jsonResult Json结果集
     *
     * @return 服务请求结果，true表示请求成功，false表示请求失败
     *
     * @throws JSONException 解析过程中出现错误
     */
    @Override
    protected boolean onRequestResult(JSONObject jsonResult) throws JSONException {
        // 得到执行结果
        String resultState = jsonResult.getString("IsSuccess");

        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
    }

    /**
     * 提取服务返回的结果消息，
     * 在{@link #onRequestResult(JSONObject)}之后被调用
     *
     * @param result     服务请求执行结果，
     *                   即{@link #onRequestResult(JSONObject)}返回值
     * @param jsonResult Json结果集
     *
     * @return 消息字符串
     *
     * @throws JSONException 解析过程中出现错误
     */
    @Override
    protected String onRequestMessage(boolean result, JSONObject jsonResult) throws JSONException {
        return result ? null : jsonResult.getString("Message");
    }

    /**
     * 提取服务反馈的结果数据，
     * 在服务请求成功后调用，
     * 即{@link #onRequestResult(JSONObject)}返回值为true时，
     * 在{@link #onRequestMessage(boolean , JSONObject)}之后被调用，
     *
     * @param jsonResult Json结果集
     *
     * @throws JSONException 解析过程中出现错误
     */
    @Override
    protected void onRequestSuccess(JSONObject jsonResult) throws JSONException {
        outParameter = jsonResult.getString("result1");
        Log.i(LOG_TAG + "onRequestSuccess", "result1 is " + outParameter);
        outParameter2 = jsonResult.getInt("result2");
        Log.i(LOG_TAG + "onRequestSuccess", "result2 is " + outParameter2);
    }
}
