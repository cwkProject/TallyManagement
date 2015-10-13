package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/9/25.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.port.tally.management.R;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullStockContent;

import org.mobile.library.model.work.WorkBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 堆存内容Activity
 *
 * @author 超悟空
 * @version 1.0 2015/10/13
 * @since 1.0
 */
public class StockContentActivity extends AppCompatActivity {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "StockContentActivity.";

    /**
     * 名称取值标签
     */
    private static final String NAME_TAG = "name_tag";

    /**
     * 内容取值标签
     */
    private static final String VALUE_TAG = "value_tag";

    /**
     * 列表显示数据集
     */
    private List<Map<String, Object>> dataList = new ArrayList<>();

    /**
     * 列表使用的数据适配器
     */
    private SimpleAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_content);

        // 加载界面
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 初始化Toolbar
        initToolbar();
        setTitle(R.string.forwarder_content);
        // 初始化列表
        initListView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 加载数据
        loadData();
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        // 得到Toolbar标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // 得到标题文本
        //toolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);

        // 关联ActionBar
        setSupportActionBar(toolbar);

        // 显示后退
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 与返回键相同
                onBackPressed();
            }
        });

        // 取消原actionBar标题
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 初始化列表
     */
    private void initListView() {

        ListView listView = (ListView) findViewById(R.id.activity_stock_content_listView);

        adapter = new SimpleAdapter(this, dataList, R.layout.content_list_item, new
                String[]{NAME_TAG , VALUE_TAG}, new int[]{R.id.content_list_item_name_textView ,
                                                          R.id.content_list_item_value_textView});

        listView.setAdapter(adapter);
    }

    /**
     * 加载数据
     */
    private void loadData() {
        // 获取堆存编号
        String stockId = getIntent().getStringExtra(StaticValue.IntentTag.STOCK_ID_TAG);

        if (stockId != null) {
            // 拉取数据
            PullStockContent pullStockContent = new PullStockContent();

            pullStockContent.setWorkBackListener(new WorkBack<Map<String, String>>() {
                @Override
                public void doEndWork(boolean state, Map<String, String> data) {
                    if (state) {
                        fillDataList(data);
                    }
                }
            });

            pullStockContent.beginExecute(stockId);
        }
    }

    /**
     * 填充列表显示数据并刷新列表
     *
     * @param data 数据源
     */
    private void fillDataList(Map<String, String> data) {

        if (data == null) {
            return;
        }

        // 填充数据
        for (Map.Entry<String, String> entry : data.entrySet()) {
            Map<String, Object> map = new HashMap<>();

            map.put(NAME_TAG, entry.getKey());
            map.put(VALUE_TAG, entry.getValue());

            dataList.add(map);
        }

        // 通知改变
        adapter.notifyDataSetChanged();
    }
}
