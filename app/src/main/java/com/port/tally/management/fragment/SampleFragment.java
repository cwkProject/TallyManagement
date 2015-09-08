package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/9/8.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.port.tally.management.work.SampleWork;

import org.mobile.library.model.work.WorkBack;

/**
 * 样例功能片段
 *
 * @author 超悟空
 * @version 1.0 2015/9/8
 * @since 1.0
 */
public class SampleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        // 创建根布局
        // View rootView = inflater.inflater.inflate(R.layout.sample,container,false);

        // 调用业务请求
        SampleWork sampleWork = new SampleWork();

        // 设置执行回调
        sampleWork.setWorkBackListener(new WorkBack<String>() {
            @Override
            public void doEndWork(boolean state, String data) {
                if (state) {
                    // 业务请求成功
                    // 处理返回数据data
                    // sampleTextView.setText(data);
                } else {
                    // 业务请求失败
                    // 显示错误消息，此时一般情况下data被设置为返回错误信息
                }
            }
        });

        // 异步启动任务，传入参数
        sampleWork.beginExecute("parameter1", "parameter2");

        // 同步启动任务，需要自己开启线程执行
        // sampleWork.execute("parameter1","parameter2");

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
