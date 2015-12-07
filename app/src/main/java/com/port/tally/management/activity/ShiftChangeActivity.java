package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/12/2.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.port.tally.management.R;

/**
 * 交接班界面
 *
 * @author 超悟空
 * @version 1.0 2015/12/2
 * @since 1.0
 */
public class ShiftChangeActivity extends AppCompatActivity {
    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "ShiftChangeActivity.";

    /**
     * 控件集
     */
    private class LocalViewHolder {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_change);

        // 初始化控件引用
        initViewHolder();
        // 加载界面
        initView();
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder() {

    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 初始化Toolbar
        initToolbar();
        setTitle(R.string.shift_change);
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
}
