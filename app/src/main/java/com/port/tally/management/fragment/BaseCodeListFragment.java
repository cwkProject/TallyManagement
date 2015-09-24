package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.holder.ISelectListener;

import org.mobile.library.model.operate.DataChangeObserver;

import java.util.List;

/**
 * 查询过滤器布局模板，
 * 仅列表内容布局
 *
 * @param <DataModel> 列表项数据模型
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public abstract class BaseCodeListFragment<DataModel> extends Fragment implements
        ISelectListener<DataModel> {

    /**
     * 列表布局
     */
    protected ListView listView = null;

    /**
     * 列表使用的数据适配器
     */
    private SimpleAdapter adapter = null;

    /**
     * 列表数据源
     */
    private List<DataModel> dataList = null;

    /**
     * 当前选中的数据项
     */
    private DataModel currentData = null;

    /**
     * 数据选中监听器
     */
    private DataChangeObserver<DataModel> selectedListener = null;

    /**
     * 目标控件点击触发监听器
     */
    private DataChangeObserver<DataModel> clickListener = null;

    /**
     * 名称取值标签
     */
    protected static final String NAME_TAG = "name_tag";

    /**
     * 速记码取值标签
     */
    protected static final String SHORT_CODE_TAG = "short_code_tag";

    @Override
    public void setClickListener(DataChangeObserver<DataModel> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void setSelectedListener(DataChangeObserver<DataModel> selectedListener) {
        this.selectedListener = selectedListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // 根布局
        View rootView = inflater.inflate(R.layout.fragment_only_list, container, false);

        // 列表布局
        listView = (ListView) rootView.findViewById(R.id.fragment_only_list_listView);

        // 加载数据源
        dataList = onCreateDataList();

        // 加载适配器
        adapter = onCreateAdapter(dataList);

        // 配置适配器
        listView.setAdapter(adapter);

        // 设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentData = dataList.get(position);
                itemClick(currentData);
                if (selectedListener != null) {
                    selectedListener.notifyDataChange(currentData);
                }
            }
        });

        // 自定义
        onCustom(rootView, listView, adapter, dataList);

        // 设置数据过滤器
        onSetFilter(listView, adapter, dataList);

        // 设置点击触发器
        onSetClickBind(clickListener);

        return rootView;
    }

    /**
     * 设置目标控件点击触发器绑定
     *
     * @param clickListener 要绑定的触发器
     */
    protected abstract void onSetClickBind(DataChangeObserver<DataModel> clickListener);

    /**
     * 自定义布局
     *
     * @param rootView 根布局
     * @param listView 列表布局
     * @param adapter  适配器
     * @param dataList 数据源
     */
    protected void onCustom(View rootView, ListView listView, SimpleAdapter adapter,
                            List<DataModel> dataList) {

    }

    /**
     * 设置数据过滤器
     *
     * @param listView 列表对象
     * @param adapter  数据适配器
     * @param dataList 数据源
     */
    protected void onSetFilter(ListView listView, SimpleAdapter adapter, List<DataModel> dataList) {
    }

    /**
     * 创建数据适配器
     *
     * @param dataList 数据源
     *
     * @return 简单适配器
     */
    protected abstract SimpleAdapter onCreateAdapter(List<DataModel> dataList);

    /**
     * 创建数据源
     *
     * @return 数据源列表
     */
    protected abstract List<DataModel> onCreateDataList();

    /**
     * 点击列表项触发
     *
     * @param data 选中数据对象
     */
    protected abstract void itemClick(DataModel data);

    /**
     * 数据过滤
     *
     * @param filterText 过滤文本
     */
    protected void filter(String filterText) {

        adapter.getFilter().filter(filterText);
    }
}
