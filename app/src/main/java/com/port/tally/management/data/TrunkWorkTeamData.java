package com.port.tally.management.data;

import android.util.Log;

import com.port.tally.management.bean.TrunkWorkTeamBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2015/9/24.
 * 汽运查询
 */
public class TrunkWorkTeamData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TrunkWorkTeamData.";

    /**
     * 服务请求传入参数
     */
    private String searchContent = null;



    /**
     * 货主列表
     */
    private List<TrunkWorkTeamBean> trunkWorkTeamBeanList = null;

    /**
     * 获取货主列表
     *
     * @return 货主列表
     */

    public List<TrunkWorkTeamBean> getTrunkWorkTeamBeanList() {
        return trunkWorkTeamBeanList;
    }
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
        dataMap.put("CodeCompany", searchContent);
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

        JSONArray jsonArray=jsonResult.getJSONArray("Data");
        if (jsonArray != null) {

            Log.i(LOG_TAG + "onRequestSuccess", "get entrustList count is " + jsonArray.length());

            // 新建委托列表
            trunkWorkTeamBeanList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONArray trunkWorkTeam = jsonArray.getJSONArray(i);

                if (trunkWorkTeam.length() > 2) {
                    // 一条委托数据
                    TrunkWorkTeamBean trunkWorkTeamBean = new TrunkWorkTeamBean();

                    trunkWorkTeamBean.setCodeName1(trunkWorkTeam.getString(0));
                    trunkWorkTeamBean.setTeamWork(trunkWorkTeam.getString(1));
                    trunkWorkTeamBean.setCodeName2(trunkWorkTeam.getString(2));


                    // 添加到列表
                    trunkWorkTeamBeanList.add(trunkWorkTeamBean);
                }
            }

            Log.i(LOG_TAG + "onRequestSuccess", "trunkWorkTeamBeanList list count is " + trunkWorkTeamBeanList.size());
        }


    }

}

