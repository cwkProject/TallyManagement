package com.port.tally.management.data;

import android.util.Log;

import com.port.tally.management.bean.StartWorkBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * Created by song on 2015/10/1.
 */
public class StartWorkData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "startWorkData.";

    public void setType(String type) {
        this.type = type;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 服务请求传入参数
     */
    private String company = null;
    private String type = null;
    private String searchContent = null;
    private StartWorkBean startWorkBean=null;
    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
    public StartWorkBean getStartWorkBean() {
        return startWorkBean;
    }
    @Override
    protected void onFillRequestParameters(Map<String, String> map) {
        // 传入请求参数
        //通行证

        map.put("CodeCompany",company);
        map.put("RecognizeMethod",type);
        map.put("NO", searchContent);

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
        JSONObject jsonObject=jsonResult.getJSONObject("Data");

        startWorkBean=new StartWorkBean();
        startWorkBean.setId(jsonObject.getString("ID"));
        startWorkBean.setVehicleNum(jsonObject.getString("车号"));
        startWorkBean.setBoatName(jsonObject.getString("船名"));
        startWorkBean.setForwarder(jsonObject.getString("货代"));
        startWorkBean.setCargo(jsonObject.getString("货物"));
        startWorkBean.setPlace(jsonObject.getString("场地"));
        startWorkBean.setAllocation(jsonObject.getString("货位"));
        startWorkBean.setSetport(jsonObject.getString("集疏港"));
        startWorkBean.setLoader(jsonObject.getString("装卸车"));
        startWorkBean.setTask(jsonObject.getString("任务号"));
        startWorkBean.setMessage(getMessage());
          Log.i("getMessage()", "" + getMessage());
        Log.i("ID",""+jsonObject.getString("ID"));
    }

    @Override
    protected void onRequestFailed(JSONObject jsonResult) throws JSONException {
        startWorkBean=new StartWorkBean();

        startWorkBean.setMessage(getMessage());
    }
}
