//package com.port.tally.management.data;
//
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.mobile.library.model.data.base.JsonDataModel;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by song on 2015/10/29.
// * //获取配工班组班别数据（理货作业票模块）
// */
//public class TallyDetailTeamData extends JsonDataModel {
//    /**
//     * 日志标签前缀
//     */
//    private static final String LOG_TAG = "TallyDetailTeamData.";
//
//    /**
//     * 服务请求传入参数
//     */
//    private String searchContent = null;
//    private List<Map<String,Object>> all = new ArrayList<Map<String,Object>>() ;
//    public List<Map<String, Object>> getAll() {
//        return all;
//    }
//
//    public void setSearchContent(String searchContent) {
//        this.searchContent = searchContent;
//    }
//
//
//
//    /**
//     * 填充服务请求所需的参数，
//     * 即设置{@link #serialization()}返回值
//     *
//     * @param dataMap 参数数据集<参数名,参数值>
//     */
//    @Override
//    protected void onFillRequestParameters(Map<String, String> dataMap) {
//        // 传入请求参数
//        dataMap.put("Pmno", searchContent);
//
//        Log.i(LOG_TAG + "onFillRequestParameters", "Pmno is " + searchContent);
//    }
//
//    /**
//     * 提取服务执行结果
//     *
//     * @param jsonResult Json结果集
//     *
//     * @return 服务请求结果，true表示请求成功，false表示请求失败
//     *
//     * @throws JSONException 解析过程中出现错误
//     */
//    @Override
//    protected boolean onRequestResult(JSONObject jsonResult) throws JSONException {
//        // 得到执行结果
//        String resultState = jsonResult.getString("IsSuccess");
//
//        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
//    }
//
//    /**
//     * 提取服务返回的结果消息，
//     * 在{@link #onRequestResult(JSONObject)}之后被调用
//     *
//     * @param result     服务请求执行结果，
//     *                   即{@link #onRequestResult(JSONObject)}返回值
//     * @param jsonResult Json结果集
//     *
//     * @return 消息字符串
//     *
//     * @throws JSONException 解析过程中出现错误
//     */
//    @Override
//    protected String onRequestMessage(boolean result, JSONObject jsonResult) throws JSONException {
//        return result ? null : jsonResult.getString("Message");
//    }
//
//    /**
//     * 提取服务反馈的结果数据，
//     * 在服务请求成功后调用，
//     * 即{@link #onRequestResult(JSONObject)}返回值为true时，
//     * 在{@link #onRequestMessage(boolean , JSONObject)}之后被调用，
//     *
//     * @param jsonResult Json结果集
//     *
//     * @throws JSONException 解析过程中出现错误
//     */
//    @Override
//    protected void onRequestSuccess(JSONObject jsonObject) throws JSONException {
//        JSONArray jsonArray = jsonObject.getJSONArray("Data");
//
//        if (jsonArray != null) {
//
//            Log.i(LOG_TAG + "onRequestSuccess", "get TallyDetailTeamData count is " + jsonArray.length());
//
//
//            for (int i = 0; i < jsonArray.length(); i++) {
//
//                JSONArray jsonMachine = jsonArray.getJSONArray(i);
//                Log.i(LOG_TAG + "onRequestSuccess", "get TallyDetailTeamData count is " + jsonMachine.length());
//                if (jsonMachine.length() > 1) {
////         复选框标志（0未选中1选中）+机械编号+司机编号+机械+司机+起始时间+终止时间+数量
//                    Map<String,Object> map = new HashMap<String,Object>() ;
//                    map.put("code_workteam", jsonMachine.getString(0));
//                    map.put("workteam", jsonMachine.getString(1));
////
//                    // 添加到列表
//                    Log.i(LOG_TAG + "onRequestSuccess", "TallyDetailTeamData map list count is " + map.toString());
//                    all.add(map);
//                    Log.i(LOG_TAG + "onRequestSuccess", "TallyDetailTeamData  all list count is " + all.toString());
//                }
//            }
//
//            Log.i(LOG_TAG + "onRequestSuccess", "TallyDetailTeamData count list count is " + all.size());
//        }
//    }
//
//}
