package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/12/14.
 */

import android.util.Log;
import android.webkit.MimeTypeMap;

import org.json.JSONObject;
import org.mobile.library.model.data.base.UploadDataModel;
import org.mobile.library.struct.FileInfo;

import java.util.Map;

/**
 * 单文件上传数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/12/14
 * @since 1.0
 */
public class SingleUploadFileData extends UploadDataModel {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "UploadFile.";

    /**
     * 上传目标文件名
     */
    private String fileName = null;

    /**
     * 要上传的文件路径
     */
    private String filePath = null;

    /**
     * 文件后缀，不带'.'
     */
    private String suffix = null;

    /**
     * 接口令牌
     */
    private String token = null;

    /**
     * 设置文件名
     *
     * @param fileName 文件名
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 设置文件后缀
     *
     * @param suffix 后缀字符串(不带'.')
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 设置接口令牌
     *
     * @param token 标识令牌
     */
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected void onFillRequestParameters(Map<String, Object> dataMap) {
        dataMap.put("CodeToken", token);
        Log.i(LOG_TAG + "onFillRequestParameters", "CodeToken is " + token);

        String mimeType = null;

        if (suffix != null) {
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
        }

        dataMap.put("File", new FileInfo(filePath, fileName, mimeType));

        Log.i(LOG_TAG + "onFillRequestParameters", "file path is " + filePath);
        Log.i(LOG_TAG + "onFillRequestParameters", "file name is " + fileName);
        Log.i(LOG_TAG + "onFillRequestParameters", "mime type is " + mimeType);
    }

    @Override
    protected boolean onRequestResult(JSONObject handleResult) throws Exception {
        // 得到执行结果
        String resultState = handleResult.getString("IsSuccess");

        return resultState != null && "yes".equals(resultState.trim().toLowerCase());
    }

    @Override
    protected String onRequestMessage(boolean result, JSONObject handleResult) throws Exception {
        return handleResult.getString("Message");
    }

    @Override
    protected void onRequestSuccess(JSONObject handleResult) throws Exception {

    }
}
