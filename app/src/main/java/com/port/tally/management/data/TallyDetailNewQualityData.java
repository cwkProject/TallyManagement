package com.port.tally.management.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * Created by song on 2015/11/17.
 */
public class TallyDetailNewQualityData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TallyDetailNewQualityData.";

    /**
     * 服务请求传入参数
     */
    private String searchContent = null;
    public String getDetailTitle() {
        return detailTitle;
    }
    private String detailTitle=null;

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
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
        dataMap.put("Tbno", searchContent);
        Log.i(LOG_TAG + "onFillRequestParameters", "tbno is " + searchContent);
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

        JSONObject jsonObject=jsonResult.getJSONObject("Data");
        Log.i("jsonResult的值",""+jsonResult);
        detailTitle =jsonObject.getString("Quality");

        Log.i(LOG_TAG+"jsonResult的值", "Success");
    }
    @Override
    protected void onRequestFailed(JSONObject jsonResult) throws JSONException {
        detailTitle =getMessage();


    }
}
