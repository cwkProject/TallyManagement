package com.port.tally.management.activity;
/**
 * Created by 超悟空 on 2015/9/19.
 */

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.port.tally.management.R;
import com.port.tally.management.adapter.BalanceWeightRecyclerViewAdapter;
import com.port.tally.management.bean.BalanceWeight;
import com.port.tally.management.fragment.BaseCodeListFragment;
import com.port.tally.management.fragment.CompanyFragment;
import com.port.tally.management.holder.BalanceWeightItemViewHolder;
import com.port.tally.management.util.StaticValue;
import com.port.tally.management.work.PullBalanceWeightList;

import org.mobile.library.common.function.InputMethodController;
import org.mobile.library.model.operate.DataChangeObserver;
import org.mobile.library.model.operate.OnItemClickListenerForRecyclerViewItem;
import org.mobile.library.model.work.DefaultWorkModel;
import org.mobile.library.model.work.WorkBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 衡重查询Activity
 *
 * @author 超悟空
 * @version 1.0 2015/10/15
 * @since 1.0
 */
public class BalanceWeightQueryActivity extends AppCompatActivity {

    /**
     * 日志标签前缀
     */
    private static final String LOG_TAG = "BalanceWeightQueryActivity.";

    /**
     * 一次性加载的数据行数
     */
    private static final int ROW_COUNT = 30;

    /**
     * 加载更多数据的触发剩余行数
     */
    private static final int LAST_ROW_COUNT = 15;

    /**
     * 控件集
     */
    private class LocalViewHolder {

        /**
         * 衡重列表数据适配器
         */
        public BalanceWeightRecyclerViewAdapter recyclerViewAdapter = null;

        /**
         * 上一个执行的加载任务
         */
        public volatile DefaultWorkModel beforeLoadWork = null;

        /**
         * 表示是否还有更多数据
         */
        public volatile boolean hasMoreData = false;

        /**
         * 表示是否正在加载
         */
        public volatile boolean loading = false;

        /**
         * 侧滑抽屉
         */
        public DrawerLayout drawerLayout = null;

        /**
         * 公司编辑框
         */
        public EditText companyEditText = null;

        /**
         * 缓存原公司名称
         */
        public String oldCompanyName = null;

        /**
         * 当前选中的公司编码
         */
        public String companyCode = null;

        /**
         * 保留上次查询数据
         */
        public String oldParameter = null;

        /**
         * 当班日期
         */
        public TextView dutyDateTextView = null;

        /**
         * 白夜班
         */
        public Spinner dayNightSpinner = null;
    }

    /**
     * 控件集对象
     */
    private LocalViewHolder viewHolder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_weight_query);

        // 初始化控件引用
        initViewHolder();
        // 加载界面
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 初始化数据
        resetData();
    }

    /**
     * 初始化控件引用
     */
    private void initViewHolder() {
        viewHolder = new LocalViewHolder();

        // 衡重列表适配器
        viewHolder.recyclerViewAdapter = new BalanceWeightRecyclerViewAdapter();

        // 侧滑栏
        viewHolder.drawerLayout = (DrawerLayout) findViewById(R.id
                .activity_balance_weight_query_drawer_layout);

        viewHolder.companyEditText = (EditText) findViewById(R.id.company_edit_editText);

        viewHolder.dutyDateTextView = (TextView) findViewById(R.id
                .balance_weight_query_filter_date_textView);

        viewHolder.dayNightSpinner = (Spinner) findViewById(R.id
                .balance_weight_query_filter_dayNight_spinner);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        // 初始化Toolbar
        initToolbar();
        setTitle(R.string.balance_weight_query);
        // 初始化列表
        initListView();
        // 初始化过滤器
        initFilter();
        // 初始化日期控件
        initDate();
        // 初始化白夜班控件
        initSpinner();
    }

    /**
     * 初始化选择列表控件
     */
    private void initSpinner() {
        viewHolder.dayNightSpinner.setOnItemSelectedListener(new AdapterView
                .OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resetData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 初始化日期控件
     */
    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String nowDate = format.format(calendar.getTime());

        viewHolder.dutyDateTextView.setText(nowDate);

        final DatePickerDialog dutyDateDialog = new DatePickerDialog(BalanceWeightQueryActivity
                .this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                String date = year + "-" + (monthOfYear + 1) + "-" +
                        dayOfMonth;

                try {
                    viewHolder.dutyDateTextView.setText(format.format(format.parse(date)));
                } catch (ParseException e) {
                    Log.e(LOG_TAG + "initDate", "onDateSet ParseException is " + e.getMessage());
                    viewHolder.dutyDateTextView.setText(date);
                }

                resetData();

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar
                .DAY_OF_MONTH));

        viewHolder.dutyDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelect();
                closeDrawer();
                InputMethodController.CloseInputMethod(BalanceWeightQueryActivity.this);
                dutyDateDialog.show();
            }
        });
    }

    /**
     * 初始化过滤器
     */
    private void initFilter() {
        // 抽屉布局
        viewHolder.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        viewHolder.drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (isSelecting()) {
                    // 选择结束
                    if (!resetData()) {
                        // 数据未变化，表示未选择公司，还原公司名称
                        viewHolder.companyEditText.setText(viewHolder.oldCompanyName);
                    }
                    // 关闭软键盘
                    Log.i(LOG_TAG + "initFilter", "close soft input");
                    InputMethodController.CloseInputMethod(BalanceWeightQueryActivity.this);
                    clearSelect();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // 延迟抢夺焦点
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getFocus();
                            }
                        });
                    }
                }, 200);

                // 弹出软键盘
                // 得到InputMethodManager的实例
                InputMethodManager imm = (InputMethodManager) getSystemService(Context
                        .INPUT_METHOD_SERVICE);

                Log.i(LOG_TAG + "initFilter", "open soft input");
                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        });

        // 清除编辑框焦点
        clearFocus();

        // 初始化company
        initFilterFragment(new CompanyFragment(), viewHolder.companyEditText, StaticValue
                .CodeListTag.COMPANY_LIST);
    }

    /**
     * 初始化选择列表布局
     */
    private void initFilterFragment(BaseCodeListFragment<?, String> fragment, final EditText
            editText, final String tag) {

        // 用tag保存激活状态，初始化为未激活状态
        editText.setTag(false);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean flag = (boolean) editText.getTag();

                if (flag) {
                    // 第二次点击
                    // 关闭抽屉
                    closeDrawer();
                } else {
                    // 第一次点击
                    // 缓存原值
                    viewHolder.oldCompanyName = editText.getText().toString();
                    editText.setText("");
                    clearSelect();
                    // 替换内容片段
                    showFragment(tag);
                    // 设置操作状态
                    editText.setTag(true);
                    // 打开抽屉
                    viewHolder.drawerLayout.openDrawer(Gravity.LEFT);
                    // 获得焦点
                    getFocus();
                }
            }
        });

        fragment.setSelectedListener(new DataChangeObserver<String>() {
            @Override
            public void notifyDataChange(String data) {
                // 公司编码赋值
                viewHolder.companyCode = data;
                // 关闭抽屉
                closeDrawer();
            }
        });

        // 加入布局管理器
        getSupportFragmentManager().beginTransaction().add(R.id
                .activity_balance_weight_query_frameLayout, fragment, tag).hide(fragment).commit();
    }

    /**
     * 显示指定标签的片段布局
     *
     * @param tag 指定的标签
     */
    private void showFragment(String tag) {
        // 获取片段管理器
        FragmentManager fragmentManager = getSupportFragmentManager();
        // 尝试获取该片段
        Fragment tagFragment = fragmentManager.findFragmentByTag(tag);
        // 尝试获取当前显示的片段对象
        Fragment currentFragment = getVisibleFragment();

        if (currentFragment != null) {
            // 有显示的片段则先隐藏
            Log.i(LOG_TAG + "showFragment", currentFragment.getTag() + " fragment is gone");
            fragmentManager.beginTransaction().hide(currentFragment).commit();
        }

        // 显示
        Log.i(LOG_TAG + "showFragment", tagFragment.getTag() + " fragment is show");
        fragmentManager.beginTransaction().show(tagFragment).commit();
    }

    /**
     * 获取当前显示的片段对象
     *
     * @return 当前显示的片段实例，没有则返回null
     */
    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.size() == 0) {
            Log.i(LOG_TAG + "getVisibleFragment", "no fragment");
            return null;
        } else {
            Log.i(LOG_TAG + "getVisibleFragment", "fragment count is " + fragments.size());
        }
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible()) {
                Log.i(LOG_TAG + "getVisibleFragment", "fragment tag is " + fragment.getTag());
                return fragment;
            }
        }
        return null;
    }

    /**
     * 判断是否有使用中的选择器
     *
     * @return 如果有返回true
     */
    private boolean isSelecting() {

        // 获取状态
        boolean companyUse = (boolean) viewHolder.companyEditText.getTag();

        return companyUse;
    }

    /**
     * 让指定编辑框获得焦点
     */
    private EditText getFocus() {
        Log.i(LOG_TAG + "getFocus", "get focus");

        EditText editText = null;

        // 获取状态
        boolean companyUse = (boolean) viewHolder.companyEditText.getTag();

        if (companyUse) {
            editText = viewHolder.companyEditText;
        }

        if (editText != null) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
        }

        return editText;
    }

    /**
     * 清除选择器状态
     */
    private void clearSelect() {
        Log.i(LOG_TAG + "clearSelect", "clear select");
        viewHolder.companyEditText.setTag(false);

        clearFocus();
    }

    /**
     * 清除焦点
     */
    private void clearFocus() {
        viewHolder.companyEditText.setFocusable(false);
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
                .activity_balance_weight_query_recyclerView);

        // 设置item动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 创建布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 设置点击事件
        viewHolder.recyclerViewAdapter.setOnItemClickListener(new OnItemClickListenerForRecyclerViewItem<List<BalanceWeight>, BalanceWeightItemViewHolder>() {
            @Override
            public void onClick(List<BalanceWeight> balanceWeights, BalanceWeightItemViewHolder
                    balanceWeightItemViewHolder) {
                BalanceWeight balanceWeight = balanceWeights.get(balanceWeightItemViewHolder
                        .getAdapterPosition());

                // 委托编号
                String entrustNumber = balanceWeight.getEntrustNumber();
                // 公司编号
                String companyCode = balanceWeight.getCompanyCode();
                // 班组日期
                String dutyDate = balanceWeight.getDate();
                // 白夜班
                String dayNight = balanceWeight.getDayNight();

                // 跳转意图
                Intent intent = new Intent(BalanceWeightQueryActivity.this,
                        BalanceWeightContentActivity.class);


                // 加入委托编号
                intent.putExtra(StaticValue.IntentTag.ENTRUST_ID_TAG, entrustNumber);
                // 加入公司编号
                intent.putExtra(StaticValue.IntentTag.COMPANY_CODE_TAG, companyCode);
                // 加入班组日期
                intent.putExtra(StaticValue.IntentTag.DATE_TAG, dutyDate);
                // 加入白夜班
                intent.putExtra(StaticValue.IntentTag.DAY_NIGHT_TAG, dayNight);

                // 跳转到详情页面
                startActivity(intent);
            }
        });

        // 设置列表适配器
        recyclerView.setAdapter(viewHolder.recyclerViewAdapter);

        // 设置加载更多
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int lastCount = recyclerView.getAdapter().getItemCount() - recyclerView
                        .getChildAdapterPosition(recyclerView.getChildAt(0)) - recyclerView
                        .getChildCount();
                Log.i(LOG_TAG + "initListView", "onScrolled lastCount is " + lastCount);
                if (dy > 0 && !viewHolder.loading && viewHolder.hasMoreData && lastCount <=
                        LAST_ROW_COUNT) {
                    // 有必要加载更多
                    Log.i(LOG_TAG + "initListView", "onScrolled now load more");
                    loadData(false);
                }
            }
        });
    }

    /**
     * 尝试重置数据
     *
     * @return true表示数据发生了改变并且执行了数据重载
     */
    private boolean resetData() {

        // 新参数
        String newParameter = viewHolder.companyCode +
                viewHolder.dutyDateTextView.getText().toString() + viewHolder.dayNightSpinner
                .getSelectedItem().toString();

        if (viewHolder.oldParameter == null || !viewHolder.oldParameter.equals(newParameter)) {
            initData();
            viewHolder.oldParameter = newParameter;
            return true;
        }
        return false;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        loadData(true);
    }

    /**
     * 加载数据
     *
     * @param reload 表示是否为全新加载，true表示为全新加载
     */
    private void loadData(boolean reload) {
        Log.i(LOG_TAG + "loadData", "reload tag is " + reload);

        if (reload) {
            // 属于全新加载数据，清空原数据
            viewHolder.recyclerViewAdapter.clear();
            // 中断上次请求
            if (viewHolder.beforeLoadWork != null) {
                viewHolder.beforeLoadWork.cancel();
            }
        }

        // 衡重列表任务
        PullBalanceWeightList pullBalanceWeightList = new PullBalanceWeightList();

        pullBalanceWeightList.setWorkEndListener(new WorkBack<List<BalanceWeight>>() {
            @Override
            public void doEndWork(boolean state, List<BalanceWeight> data) {
                if (state && data != null) {
                    // 插入新数据
                    viewHolder.recyclerViewAdapter.addData(viewHolder.recyclerViewAdapter
                            .getItemCount(), data);

                    if (data.size() == ROW_COUNT) {
                        // 取到了预期条数的数据
                        viewHolder.hasMoreData = true;
                    }
                }

                // 改变请求状态
                viewHolder.loading = false;
            }
        });

        // 改变请求状态
        viewHolder.loading = true;
        // 初始化更多预期
        viewHolder.hasMoreData = false;

        // 执行任务
        pullBalanceWeightList.beginExecute(String.valueOf(viewHolder.recyclerViewAdapter
                .getItemCount()), String.valueOf(ROW_COUNT), viewHolder.companyCode, viewHolder
                .dutyDateTextView.getText().toString(), viewHolder.dayNightSpinner
                .getSelectedItem().toString());

        // 保存新的加载任务对象
        viewHolder.beforeLoadWork = pullBalanceWeightList;
    }

    /**
     * 关闭导航抽屉
     *
     * @return 成功关闭返回true，未打开则返回false
     */
    public boolean closeDrawer() {
        if (viewHolder.drawerLayout != null) {
            if (viewHolder.drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                viewHolder.drawerLayout.closeDrawer(Gravity.RIGHT);
                return true;
            }
            if (viewHolder.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                viewHolder.drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        // 如果抽屉已打开，则先关闭抽屉
        if (!closeDrawer()) {
            super.onBackPressed();
        }
    }

}
