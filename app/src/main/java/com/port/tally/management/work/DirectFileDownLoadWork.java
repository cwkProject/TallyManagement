package com.port.tally.management.work;
/**
 * Created by 超悟空 on 2015/12/16.
 */

import com.port.tally.management.data.DirectFileDownLoadData;

import org.mobile.library.model.work.DownloadWorkModel;

/**
 * 直接文件下载任务
 *
 * @author 超悟空
 * @version 1.0 2015/12/16
 * @since 1.0
 */
public class DirectFileDownLoadWork extends DownloadWorkModel<String, String,
        DirectFileDownLoadData> {
    @Override
    protected String onTaskUri(String... parameters) {
        return parameters[0];
    }

    @Override
    protected boolean onCheckParameters(String... parameters) {
        return parameters != null && parameters.length >= 2;
    }

    @Override
    protected String onRequestSuccessSetResult(DirectFileDownLoadData data) {
        return data.getPath();
    }

    @Override
    protected DirectFileDownLoadData onCreateDataModel(String... parameters) {
        DirectFileDownLoadData data = new DirectFileDownLoadData();
        data.setPath(parameters[1]);
        return data;
    }
}
