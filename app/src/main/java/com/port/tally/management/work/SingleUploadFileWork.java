package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/12/14.
 */

import com.port.tally.management.data.SingleUploadFileData;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.work.DefaultWorkModel;
import org.mobile.library.network.factory.NetworkType;

/**
 * 单文件上传
 *
 * @author 超悟空
 * @version 1.0 2015/12/14
 * @since 1.0
 */
public class SingleUploadFileWork extends DefaultWorkModel<String, Void, SingleUploadFileData> {

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length >= 2;
    }

    @Override
    protected String onTaskUri() {
       return StaticValue.UPLOAD_FILE_URL;
    }

    @Override
    protected NetworkType onNetworkType() {
        return NetworkType.UPLOAD;
    }

    @Override
    protected Void onRequestSuccessSetResult(SingleUploadFileData data) {
        return null;
    }

    @Override
    protected SingleUploadFileData onCreateDataModel(String... parameters) {
        SingleUploadFileData data = new SingleUploadFileData();

        data.setToken(parameters[0]);
        data.setFilePath(parameters[1]);
        data.setSuffix(parameters.length > 2 ? parameters[2] : null);
        data.setFileName(parameters.length > 3 ? parameters[3] : null);

        return data;
    }
}
