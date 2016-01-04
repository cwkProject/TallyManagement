package com.port.tally.management.data;
/**
 * Created by 超悟空 on 2015/12/16.
 */

import org.mobile.library.model.data.base.DownloadDataModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * 直接文件下载数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/12/16
 * @since 1.0
 */
public class DirectFileDownLoadData extends DownloadDataModel {

    /**
     * 下载文件的存放路径
     */
    private String path = null;

    /**
     * 设置文件存放路径
     *
     * @param path 文件路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取文件存放路径
     *
     * @return 文件路径
     */
    public String getPath() {
        return path;
    }

    @Override
    protected void onFillRequestParameters(Map<String, String> dataMap) {

    }

    @Override
    protected void onRequestSuccess(InputStream inputStream) throws Exception {

        if (path != null && inputStream != null) {
            File file = new File(path);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            inputStream.close();
        }
    }
}
