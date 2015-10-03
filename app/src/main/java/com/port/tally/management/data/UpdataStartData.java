package com.port.tally.management.data;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * Created by song on 2015/10/2.
 */
public class UpdataStartData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = " UpdataStartData .";


    /**
     * 服务请求传入参数
     */
    private String id = null;

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNotperson(String notperson) {
        this.notperson = notperson;
    }

    private String time = null;
    private String notperson = null;

    protected void onFillRequestParameters(Map<String, String> map) {
        // 传入请求参数
        //通行证
        map.put("Id", id);
        map.put("startTime",time);
        map.put("startUserName",notperson);
    }
    /**
     * 提取服务执行结果
     *
     * @param jsonObject Json结果集
     *
     * @return 服务请求结果，true表示请求成功，false表示请求失败
     *
     * @throws JSONException 解析过程中出现错误
     */
    @Override
    protected boolean onRequestResult(JSONObject jsonObject) throws JSONException {
        // 得到执行结果
        String resultState = jsonObject.getString("IsSuccess");

        return resultState != null && "yes".equals(resultState.trim().toLowerCase());

    }
    /**
     * 提取服务返回的结果消息，
     * 在{@link #onRequestResult(JSONObject)}之后被调用
     *
     * @param b     服务请求执行结果，
     *                   即{@link #onRequestResult(JSONObject)}返回值
     * @param jsonObject Json结果集
     *
     * @return 消息字符串
     *
     * @throws JSONException 解析过程中出现错误
     */
    @Override
    protected String onRequestMessage(boolean b, JSONObject jsonObject) throws JSONException {
        return b ? null : jsonObject.getString("Message");
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



    }
}
