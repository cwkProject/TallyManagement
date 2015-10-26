package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/9/7.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.port.tally.management.R;
import com.port.tally.management.adapter.MainFunctionRecyclerViewAdapter;
import com.port.tally.management.holder.MainFunctionItemViewHolder;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.common.function.CheckUpdate;
import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;
import org.mobile.library.util.LoginStatus;

import static com.port.tally.management.adapter.FunctionIndex.toFunction;

/**
 * 主界面
 *
 * @author 超悟空
 * @version 2.0 2015/10/20
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "MainActivity.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 加载界面
        initView();


    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 初始化Toolbar
        initToolbar();
        setTitle(R.string.app_name);

        // 初始化折叠布局
        initCollapsing();

        // 初始化功能布局
        initGridView();

        // 执行检查更新
        checkUpdate();
    }

    /**
     * 初始化CollapsingToolbarLayout
     */
    private void initCollapsing() {
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById
                (R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setExpandedTitleColor(getTheme().obtainStyledAttributes(new
                int[]{R.attr.colorPrimary}).getColor(0, Color.BLACK));
    }

    /**
     * 检查新版本
     */
    private void checkUpdate() {
        // 新建版本检查工具
        CheckUpdate checkUpdate = new CheckUpdate(this, StaticValue.APP_CODE);
        // 执行检查
        checkUpdate.checkInBackground();
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
        toolbar.setLogo(R.mipmap.ic_launcher);

        // 取消原actionBar标题
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 初始化表格布局
     */
    private void initGridView() {

        // RecyclerView列表对象
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);

        // 创建布局管理器
        GridLayoutManager gridlayoutManager = new GridLayoutManager(this, 2);

        // 设置布局管理器
        recyclerView.setLayoutManager(gridlayoutManager);

        // 新建数据适配器
        MainFunctionRecyclerViewAdapter adapter = new MainFunctionRecyclerViewAdapter(this);

        // 设置点击事件
        adapter.setOnItemClickListener(new OnItemClickListenerForRecyclerViewItem<String[],
                MainFunctionItemViewHolder>() {
            @Override
            public void onClick(String[] dataSource, MainFunctionItemViewHolder holder) {
                onGridItemClick(holder.getAdapterPosition());
            }
        });

        // 绑定数据适配器
        recyclerView.setAdapter(adapter);
    }

    /**
     * 表格项点击事件触发操作，
     * 默认触发功能跳转，
     * 并检测登录状态
     *
     * @param position 点击的位置索引
     */
    private void onGridItemClick(int position) {

        if (!LoginStatus.getLoginStatus().isLogin()) {
            // 未登录
            // 新建意图,跳转到登录页面
            Intent intent = new Intent(this, LoginActivity.class);
            // 执行跳转
            startActivity(intent);
            finish();
            return;
        }

        // 跳转到功能
        toFunction(this, position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                // 退出操作
                doLogout();
                break;
        }
        return true;
    }

    /**
     * 退出操作
     */
    private void doLogout() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
