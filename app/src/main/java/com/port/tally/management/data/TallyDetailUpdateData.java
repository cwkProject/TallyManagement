package com.port.tally.management.data;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;

import java.util.Map;

/**
 * Created by song on 2015/10/13.
 */
public class TallyDetailUpdateData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TallyDetailUpdateData.";

    /**
     * 服务请求传入参数
     */

//   数量、机械状态、起、止、数量、
//    班组、姓名、起、止、数量、
    private String count_Dun=null;
    private String machine =null;
    private String  machine_start =null;
    private String machine_end =null;

    public void setMachine_count(String machine_count) {
        this.machine_count = machine_count;
    }

    public void setCount_Dun(String count_Dun) {
        this.count_Dun = count_Dun;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setMachine_start(String machine_start) {
        this.machine_start = machine_start;
    }

    public void setMachine_end(String machine_end) {
        this.machine_end = machine_end;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public void setTeam_start(String team_start) {
        this.team_start = team_start;
    }

    public void setTeam_end(String team_end) {
        this.team_end = team_end;
    }

    public void setTeam_count(String team_count) {
        this.team_count = team_count;
    }



    private String machine_count =null;
    private String team =null;
    private String team_name =null;
    private String team_start =null;
    private String team_end =null;
    private String team_count =null;

    public String getResult() {
        return result;
    }

    //返回结果
    private String result=null;


    /**
     * 填充服务请求所需的参数，
     * 即设置{@link #serialization()}返回值
     *
     * @param dataMap 参数数据集<参数名,参数值>
     */
    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {
        // 传入请求参数
        dataMap.put("Cgno", machine_count);
        dataMap.put("Cgno", team);
        dataMap.put("Cgno", team_name);
        dataMap.put("Cgno", team_start);
        dataMap.put("Cgno", team_end);
        dataMap.put("Cgno", team_count);
        dataMap.put("Cgno", count_Dun);
        dataMap.put("Cgno", machine);
        dataMap.put("Cgno", count_Dun);
        dataMap.put("Cgno", machine_start);
        dataMap.put("Cgno", machine_end);
        Log.i(LOG_TAG + "onFillRequestParameters", "Cgno is " +  machine_count);
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

        JSONObject jsonObject=jsonResult.getJSONObject("IsSuccess");
        result =jsonObject.getString("IsSuccess");
    }

}
