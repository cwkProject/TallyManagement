package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.port.tally.management.R;
import com.port.tally.management.adapter.EntrustRecyclerViewAdapter;
import com.port.tally.management.bean.Entrust;
import com.port.tally.management.work.PullEntrustList;

import org.mobile.library.model.work.WorkBack;

import java.util.List;

/**
 * 委托查询Activity
 *
 * @author 超悟空
 * @version 1.0 2015/9/19
 * @since 1.0
 */
public class EntrustQueryActivity extends AppCompatActivity {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "EntrustQueryActivity.";

    /**
     * 控件集
     */
    private class LocalViewHolder {

        /**
         * 委托列表数据适配器
         */
        public EntrustRecyclerViewAdapter recyclerViewAdapter = null;
    }

    /**
     * 控件集对象
     */
    private LocalViewHolder viewHolder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust_query);

        // 初始化控件引用
        initViewHolder();
        // 加载界面
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 初始化数据
        initData();
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder() {
        viewHolder = new LocalViewHolder();

        // 委托列表适配器
        viewHolder.recyclerViewAdapter = new EntrustRecyclerViewAdapter();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 初始化Toolbar
        initToolbar();
        setTitle(R.string.entrust_query);
        // 初始化列表
        initListView();
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

        // RecyclerView列表对象
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id
                .activity_entrust_query_recyclerView);

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 创建布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 设置列表适配器
        recyclerView.setAdapter(viewHolder.recyclerViewAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        loadData(0, 30);
    }

    /**
     * 加载数据
     *
     * @param start 起始位置
     * @param count 加载数量
     */
    private void loadData(int start, int count) {

        if (count < 0 || start < 0) {
            return;
        }

        // 委托列表任务
        PullEntrustList pullEntrustList = new PullEntrustList();

        pullEntrustList.setWorkBackListener(new WorkBack<List<Entrust>>() {
            @Override
            public void doEndWork(boolean state, List<Entrust> data) {
                if (state) {
                    // 插入新数据
                    viewHolder.recyclerViewAdapter.addData(viewHolder.recyclerViewAdapter
                            .getItemCount(), data);
                }
            }
        });

        // 执行任务
        pullEntrustList.beginExecute(String.valueOf(start), String.valueOf(count), "14",
                "2015-08-19", "2015-09-19");
    }
}
