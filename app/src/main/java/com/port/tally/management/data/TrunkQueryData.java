package com.port.tally.management.data;

import com.port.tally.management.bean.TrunkQueryBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * Created by song on 2015/9/24.
 * 汽运查询
 */
public class TrunkQueryData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TrunkQueryData.";

    /**
     * 服务请求传入参数
     */
    private String searchContent = null;

    private TrunkQueryBean trunkQueryBean=null;


    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public TrunkQueryBean getTrunkQueryBean() {
        return trunkQueryBean;
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
        dataMap.put("VehicleNum", searchContent);
//        Log.i(LOG_TAG + "onFillRequestParameters", "VehicleNum is " + searchContent);
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

        trunkQueryBean=new TrunkQueryBean();
        trunkQueryBean.setClient(jsonObject.getString("货代"));
        trunkQueryBean.setCagro(jsonObject.getString("货物"));
        trunkQueryBean.setVehicleNum(jsonObject.getString("车号"));
        trunkQueryBean.setWorkteam(jsonObject.getString("班组"));
        trunkQueryBean.setAmount(jsonObject.getString("件数"));
        trunkQueryBean.setGaterecordid(jsonObject.getString("记录"));


    }

}
