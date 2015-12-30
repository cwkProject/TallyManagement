package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/12/29.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.port.tally.management.R;
import com.port.tally.management.bean.Employee;
import com.port.tally.management.fragment.EmployeeFragment;
import com.port.tally.management.util.StaticValue;

import org.mobile.library.model.operate.DataChangeObserver;

/**
 * 员工列表
 *
 * @author 超悟空
 * @version 1.0 2015/12/29
 * @since 1.0
 */
public class EmployeeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        // 加载界面
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 初始化Toolbar
        initToolbar();
        setTitle(R.string.employee_list);

        // 初始化列表
        initListFragment();
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
     * 初始化数据列表
     */
    private void initListFragment() {

        EmployeeFragment fragment = new EmployeeFragment();

        fragment.setSelectedListener(new DataChangeObserver<Employee>() {
            @Override
            public void notifyDataChange(Employee data) {
                Intent intent = new Intent();

                intent.putExtra(StaticValue.IntentTag.EMPLOYEE_TAG, data);

                setResult(RESULT_OK, intent);

                finish();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id
                .activity_employee_list_frameLayout, fragment).commit();
    }
}
