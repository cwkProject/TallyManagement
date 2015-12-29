package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/9/22.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.function.CodeListManager;
import com.port.tally.management.holder.ISelectListener;

import org.mobile.library.model.operate.DataChangeObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询过滤器布局模板，
 * 仅列表内容布局
 *
 * @param <DataModel> 列表项数据模型
 * @param <Result>    向外界返回的结果
 *
 * @author 超悟空
 * @version 1.0 2015/9/22
 * @since 1.0
 */
public abstract class BaseCodeListFragment<DataModel, Result> extends Fragment implements
        ISelectListener<Result> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "BaseCodeListFragment.";

    /**
     * 列表布局
     */
    protected ListView listView = null;

    /**
     * 列表使用的数据适配器
     */
    protected SimpleAdapter adapter = null;

    /**
     * 当前选中的数据项
     */
    private Result currentData = null;

    /**
     * 列表使用的数据集
     */
    protected List<Map<String, String>> mapList = new ArrayList<>();

    /**
     * 数据选中监听器
     */
    private DataChangeObserver<Result> selectedListener = null;

    /**
     * 关联的过滤输入框
     */
    protected EditText editText = null;

    /**
     * 名称取值标签
     */
    protected static final String NAME_TAG = "name_tag";

    /**
     * 速记码取值标签
     */
    protected static final String SHORT_CODE_TAG = "short_code_tag";

    /**
     * 本地广播管理器
     */
    private LocalBroadcastManager localBroadcastManager = null;

    /**
     * 数据加载结果的广播接收者
     */
    private LoadingReceiver loadingReceiver = null;

    @Override
    public void setSelectedListener(DataChangeObserver<Result> selectedListener) {
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

        // 列表数据源
        List<DataModel> dataList = onCreateDataList(onActionTag());

        // 生成数据集
        onFillDataList(dataList, mapList);

        // 加载适配器
        adapter = onCreateAdapter(mapList);

        // 配置适配器
        listView.setAdapter(adapter);

        // 设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentData = itemClick(parent, view, position, id);
                if (selectedListener != null) {
                    selectedListener.notifyDataChange(currentData);
                }
            }
        });

        // 获取过滤输入框
        editText = onFilterEditText(rootView);


        if (editText != null) {
            // 设置数据过滤器
            onSetFilter(editText);
        }

        // 自定义
        onCustom(rootView, listView, adapter, dataList);

        return rootView;
    }

    /**
     * 自定义布局
     *
     * @param rootView 根布局
     * @param listView 列表布局
     * @param adapter  适配器
     * @param dataList 数据源
     */
    protected void onCustom(View rootView, ListView listView, SimpleAdapter adapter, @Nullable
    List<DataModel> dataList) {
    }

    /**
     * 获取关联的列表过滤输入框，默认为null表示没有过滤功能
     *
     * @param rootView 根布局
     *
     * @return 过滤文本框
     */
    protected EditText onFilterEditText(View rootView) {
        return null;
    }

    /**
     * 设置数据过滤器
     *
     * @param editText 过滤文本框
     */
    protected void onSetFilter(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    /**
     * 创建数据适配器
     *
     * @param mapList 数据源
     *
     * @return 简单适配器
     */
    protected abstract SimpleAdapter onCreateAdapter(List<Map<String, String>> mapList);

    /**
     * 填充列表数据集
     *
     * @param dataList 数据源列表
     * @param mapList  列表适配器数据集
     */
    protected abstract void onFillDataList(@Nullable List<DataModel> dataList, List<Map<String,
            String>> mapList);

    /**
     * 创建数据源
     *
     * @param codeListTag 数据源列表对应的提取标签
     *
     * @return 数据源列表
     */
    protected List<DataModel> onCreateDataList(String codeListTag) {
        if (CodeListManager.get(codeListTag) == null) {
            // 注册广播接收者
            registerReceivers();
            // 数据源丢失，需要重新加载
            CodeListManager.create(codeListTag);
            return null;
        }

        if (CodeListManager.get(codeListTag).isLoading()) {
            // 注册广播接收者
            registerReceivers();
            return null;
        }

        return CodeListManager.get(codeListTag).getDataList();
    }

    /**
     * 点击列表项触发
     *
     * @param parent   布局适配器
     * @param view     当前item
     * @param position 当前索引位置
     * @param id       布局id
     *
     * @return 要返回的数据
     */
    protected abstract Result itemClick(AdapterView<?> parent, View view, int position, long id);

    /**
     * 数据过滤
     *
     * @param filterText 过滤文本
     */
    protected void filter(String filterText) {
        adapter.getFilter().filter(filterText);
    }

    /**
     * 数据提取标签，同接收者监听器动作
     *
     * @return 标识字符串
     */
    protected abstract String onActionTag();

    /**
     * 数据加载结果的广播接收者
     *
     * @author 超悟空
     * @version 1.0 2015/10/12
     * @since 1.0
     */
    private class LoadingReceiver extends BroadcastReceiver {

        /**
         * 得到本接收者监听的动作集合
         *
         * @return 填充完毕的意图集合
         */
        public final IntentFilter getRegisterIntentFilter() {
            // 新建动作集合
            IntentFilter filter = new IntentFilter();
            filter.addAction(onActionTag());
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // 得到动作字符串
            String actionString = intent.getAction();
            Log.i(LOG_TAG + "LoadReceiver.onReceive", "action is " + actionString);

            if (actionString.equals(onActionTag())) {
                // 加载完毕
                // 注销广播接收者
                unregisterReceivers();

                // 重置数据
                mapList.clear();
                onFillDataList(onCreateDataList(onActionTag()), mapList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 注册广播接收者
     */
    private void registerReceivers() {
        Log.i(LOG_TAG + "registerReceivers", "registerReceivers() is invoked");
        // 新建本地广播管理器
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        // 新建接收者
        loadingReceiver = new LoadingReceiver();

        // 注册
        localBroadcastManager.registerReceiver(loadingReceiver, loadingReceiver
                .getRegisterIntentFilter());
    }

    /**
     * 注销广播接收者
     */
    private void unregisterReceivers() {
        Log.i(LOG_TAG + "unregisterReceivers", "unregisterReceivers() is invoked");
        if (localBroadcastManager == null) {
            return;
        }

        if (loadingReceiver != null) {
            localBroadcastManager.unregisterReceiver(loadingReceiver);
        }
    }
}
