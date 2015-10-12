package com.port.tally.management.data;

import com.port.tally.management.bean.TrunkQueryBean;
import com.port.tally.management.bean.WorkPlanDetailBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * Created by song on 2015/10/10.
 */
public class WorkPlanDetailData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "WorkPlanDetailData.";

    /**
     * 服务请求传入参数
     */
    private String searchContent = null;

    private WorkPlanDetailBean workPlanDetailBean=null;


    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    public WorkPlanDetailBean getWorkPlanDetailBean() {
        return workPlanDetailBean;
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
        dataMap.put("Cgno", searchContent);
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
        workPlanDetailBean=new WorkPlanDetailBean();
        workPlanDetailBean.setCargoowner(jsonObject.getString("货主"));
        workPlanDetailBean.setCargo(jsonObject.getString("货物"));
        workPlanDetailBean.setVgdisplay(jsonObject.getString("航次"));
        workPlanDetailBean.setClient(jsonObject.getString("货代"));
        workPlanDetailBean.setStorage(jsonObject.getString("货场"));
        workPlanDetailBean.setWeight(jsonObject.getString("重量"));
        workPlanDetailBean.setFirst_indate(jsonObject.getString("进场日期"));
        workPlanDetailBean.setInout(jsonObject.getString("进出口"));
        workPlanDetailBean.setTrade(jsonObject.getString("内外贸"));
        workPlanDetailBean.setMark(jsonObject.getString("唛头"));
        workPlanDetailBean.setBooth(jsonObject.getString("货位"));
        workPlanDetailBean.setPack(jsonObject.getString("包装"));
        workPlanDetailBean.setAmount(jsonObject.getString("件数"));
        workPlanDetailBean.setPieceweight(jsonObject.getString("件重"));


    }

}
