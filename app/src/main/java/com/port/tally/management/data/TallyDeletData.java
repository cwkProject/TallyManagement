package com.port.tally.management.data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mobile.library.model.data.base.JsonDataModel;
import java.util.Map;

/**
 * Created by song on 2015/11/11.
 */
public class TallyDeletData extends JsonDataModel {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "TallyDeletData.";
    /**
     * 服务请求传入参数
     */
    private String searchContent1 = null;

    public void setSearchContent3(String searchContent3) {
        this.searchContent3 = searchContent3;
    }

    private String searchContent3 = null;
    public void setSearchContent2(String searchContent2) {
        this.searchContent2 = searchContent2;
    }
    public void setSearchContent1(String searchContent1) {
        this.searchContent1 = searchContent1;
    }
    private String searchContent2 = null;
    protected void onFillRequestParameters(Map<String, String> map) {
        map.put("Pmno", searchContent1);
        map.put("Cgno", searchContent2);
        map.put("Tbno", searchContent3);
    }
    @Override
    protected boolean onRequestResult(JSONObject jsonObject) throws JSONException {
        // 得到执行结果
        String resultState = jsonObject.getString("IsSuccess");
        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
    }
    @Override
    protected String onRequestMessage(boolean b, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("Message");
    }
    @Override
    protected void onRequestSuccess(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("Data");
    }

}
