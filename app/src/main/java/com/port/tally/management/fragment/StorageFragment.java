package com.port.tally.management.fragment;
/**
 * Created by 超悟空 on 2015/10/12.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.bean.Storage;
import com.port.tally.management.function.CodeListManager;
import com.port.tally.management.util.StaticValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 货场片段
 *
 * @author 超悟空
 * @version 1.0 2015/10/12
 * @since 1.0
 */
public class StorageFragment extends BaseCodeListFragment<Storage, String> {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "StorageFragment.";

    /**
     * 列表使用的数据集
     */
    private List<Map<String, String>> mapList = new ArrayList<>();

    /**
     * 关联的输入框
     */
    private EditText editText = null;

    /**
     * 本地广播管理器
     */
    private LocalBroadcastManager localBroadcastManager = null;

    /**
     * 数据加载结果的广播接收者
     */
    private LoadingReceiver loadingReceiver = null;

    @Override
    protected SimpleAdapter onCreateAdapter(List<Storage> dataList) {

        fillDataList(dataList);

        return new SimpleAdapter(getContext(), mapList, R.layout.two_row_text_item, new
                String[]{NAME_TAG , SHORT_CODE_TAG}, new int[]{R.id.two_row_text_item_top_textView , R.id.two_row_text_item_bottom_textView});
    }

    /**
     * 填充列表数据集
     *
     * @param dataList 数据源列表
     */
    private void fillDataList(List<Storage> dataList) {
        if (dataList == null) {
            // 数据加载失败或未完成
            dataList = new ArrayList<>();
        }

        mapList.clear();

        for (Storage data : dataList) {
            Map<String, String> map = new HashMap<>();

            map.put(NAME_TAG, data.getName());
            map.put(SHORT_CODE_TAG, data.getShortCode());

            mapList.add(map);
        }
    }

    @Override
    protected void onCustom(View rootView, ListView listView, SimpleAdapter adapter,
                            List<Storage> dataList) {

        // 获取activity布局中的关联控件
        editText = (EditText) getActivity().findViewById(R.id.storage_edit_editText);
    }

    @Override
    protected void onSetFilter(ListView listView, SimpleAdapter adapter, List<Storage> dataList) {

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

    @Override
    protected List<Storage> onCreateDataList() {

        if (CodeListManager.get(StaticValue.CodeListTag.STORAGE_LIST).isLoading()) {
            // 仍然在加载，注册广播接收者
            registerReceivers();
        }

        return CodeListManager.get(StaticValue.CodeListTag.STORAGE_LIST).getDataList();
    }

    @Override
    protected String itemClick(AdapterView parent, View view, int position, long id) {

        Map<String, String> map = (Map<String, String>) parent.getItemAtPosition(position);

        return map.get(NAME_TAG);
    }

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
            filter.addAction(StaticValue.CodeListTag.STORAGE_LIST);
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // 得到动作字符串
            String actionString = intent.getAction();
            Log.i(LOG_TAG + "LoadReceiver.onReceive", "action is " + actionString);

            switch (actionString) {
                case StaticValue.CodeListTag.STORAGE_LIST:
                    // 加载完毕
                    fillDataList(onCreateDataList());
                    adapter.notifyDataSetChanged();
                    unregisterReceivers();
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
