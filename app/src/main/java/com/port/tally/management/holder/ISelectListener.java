package com.port.tally.management.holder;
/**
 * Created by 超悟空 on 2015/9/23.
 */

import org.mobile.library.model.operate.DataChangeObserver;

/**
 * 过滤器设置监听器的功能接口
 *
 * @param <DataModel> 数据类型
 *
 * @author 超悟空
 * @version 1.0 2015/9/23
 * @since 1.0
 */
public interface ISelectListener<DataModel> {

    void setSelectedListener(DataChangeObserver<DataModel> selectedListener);
}
